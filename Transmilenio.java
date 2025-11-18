import java.util.*;

/**
 * Sistema principal de gestión del TransMilenio.
 * 
 * Administra estaciones, rutas y troncales, proporcionando servicios de
 * consulta optimizados para información de tiempos, conexiones y recorridos.
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Transmilenio {
    private final HashMap<String, Estacion> registroEstaciones;
    private final TreeMap<String, Ruta> catalogoRutas;
    private final HashMap<String, Troncal> redTroncales;
    

    /**
     * Construye un nuevo sistema TransMilenio vacío.
     */
    public Transmilenio() {
        this.registroEstaciones = new HashMap<>();
        this.catalogoRutas = new TreeMap<>();
        this.redTroncales = new HashMap<>();
        this.listaAdyacencia = new HashMap<>();
        this.cacheRecorridos = new LinkedHashMap<String, ResultadoRecorrido>();
    }
    
    /**
     * Registra una nueva estación en el sistema.
     */
    public void registrarEstacion(Estacion estacion) {
        if (estacion == null) {
            throw new IllegalArgumentException("No se puede registrar una estación nula");
        }
        
        String id = estacion.getIdentificador();
        if (registroEstaciones.containsKey(id)) {
            throw new IllegalStateException("Ya existe una estación con el identificador: " + id);
        }
        
        registroEstaciones.put(id, estacion);
        listaAdyacencia.putIfAbsent(estacion, new ArrayList<>());
    }

    /**
     * Registra una nueva ruta en el sistema.
     */
    public void registrarRuta(Ruta ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("No se puede registrar una ruta nula");
        }
        
        String codigo = ruta.getCodigoRuta();
        if (catalogoRutas.containsKey(codigo)) {
            throw new IllegalStateException("Ya existe una ruta con el código: " + codigo);
        }
        
        catalogoRutas.put(codigo, ruta);
        actualizarGrafoConRuta(ruta);
    }

    /**
     * Agrega una troncal al sistema.
     */
    public void agregarTroncal(Troncal troncal) {
        if (troncal == null) {
            throw new IllegalArgumentException("No se puede agregar una troncal nula");
        }
        
        redTroncales.put(troncal.getNombreTroncal(), troncal);
        actualizarGrafoConTroncal(troncal);
    }

    /**
     * Actualiza el grafo con las estaciones de una ruta.
     */
    private void actualizarGrafoConRuta(Ruta ruta) {
        List<Estacion> estaciones = ruta.obtenerSecuenciaEstaciones();
        
        for (int i = 0; i < estaciones.size() - 1; i++) {
            Estacion actual = estaciones.get(i);
            Estacion siguiente = estaciones.get(i + 1);
            
            double tiempo = calcularTiempoEstimado(actual, siguiente);
            agregarConexionBidireccional(actual, siguiente, tiempo, ruta);
        }
    }

    /**
     * Actualiza el grafo con información de una troncal.
     */
    private void actualizarGrafoConTroncal(Troncal troncal) {
        List<Estacion> estaciones = troncal.obtenerTopologiaEstaciones();
        
        for (int i = 0; i < estaciones.size() - 1; i++) {
            Estacion actual = estaciones.get(i);
            Estacion siguiente = estaciones.get(i + 1);
            
            try {
                double tiempoReal = troncal.calcularTiempoViaje(actual, siguiente);
                Ruta rutaConectora = buscarRutaQueConecta(actual, siguiente);
                
                if (rutaConectora != null) {
                    agregarConexionBidireccional(actual, siguiente, tiempoReal, rutaConectora);
                }
            } catch (TransmilenioException e) {
                // Estaciones no conectadas, continuar
            }
        }
    }
    
    /**
     * Agrega una conexión bidireccional en el grafo.
     */
    private void agregarConexionBidireccional(Estacion e1, Estacion e2, double tiempo, Ruta ruta) {
        listaAdyacencia.putIfAbsent(e1, new ArrayList<>());
        listaAdyacencia.putIfAbsent(e2, new ArrayList<>());
        
        listaAdyacencia.get(e1).add(new Conexion(e2, tiempo, ruta));
        listaAdyacencia.get(e2).add(new Conexion(e1, tiempo, ruta));
    }

    /**
     * Busca una ruta que conecte dos estaciones.
     */
    private Ruta buscarRutaQueConecta(Estacion e1, Estacion e2) {
        for (Ruta ruta : catalogoRutas.values()) {
            if (ruta.verificarConexion(e1, e2)) {
                return ruta;
            }
        }
        return null;
    }

    /**
     * Calcula tiempo estimado entre estaciones.
     */
    private double calcularTiempoEstimado(Estacion e1, Estacion e2) {
        for (Troncal troncal : redTroncales.values()) {
            try {
                return troncal.calcularTiempoViaje(e1, e2);
            } catch (TransmilenioException ex) {
                // Continuar con siguiente troncal
            }
        }
        return 3.0; // Tiempo por defecto
    }

    // ==================== SERVICIOS PRINCIPALES ====================

    /**
     * SERVICIO 1: Consulta el tiempo de espera actual en una estación.
     * El tiempo depende del nivel de ocupación actual de la estación.
     * 
     * @param nombreEstacion Nombre de la estación a consultar
     * @return Tiempo de espera en minutos
     * @throws TransmilenioException si la estación no existe
     * 
     */
    public int consultarTiempoEspera(String nombreEstacion) 
            throws TransmilenioException {
        
        validarNombreNoVacio(nombreEstacion, "estación");
        
        Estacion estacion = registroEstaciones.get(nombreEstacion);
        
        if (estacion == null) {
            throw new TransmilenioException(
                TransmilenioException.ESTACION_NO_ENCONTRADA + ": '" + nombreEstacion + "'"
            );
        }
        
        return estacion.calcularTiempoEsperaActual();
    }

    /**
     * SERVICIO 2: Lista todos los nombres de rutas ordenados alfabéticamente.
     * Gracias al TreeMap, las rutas ya están ordenadas automáticamente.
     * 
     * @return Lista de nombres de rutas en orden alfabético
     * 
     */
    public List<String> listarRutasAlfabeticamente() {
        return new ArrayList<>(catalogoRutas.keySet());
    }

    /**
     * SERVICIO 3: Cuenta las paradas para ir de una estación a otra en una ruta específica.
     * Solo cuenta paradas intermedias (no incluye origen ni destino).
     * 
     * @param nombreRuta Nombre de la ruta
     * @param nombreOrigen Nombre de la estación de origen
     * @param nombreDestino Nombre de la estación de destino
     * @return Número de paradas intermedias
     * @throws TransmilenioException si algún elemento no existe o no están conectados
     * 
     */
    public int contarParadasEntreEstaciones(String nombreRuta, String nombreOrigen,String nombreDestino) throws TransmilenioException {
        
        validarNombreNoVacio(nombreRuta, "ruta");
        validarNombreNoVacio(nombreOrigen, "estación origen");
        validarNombreNoVacio(nombreDestino, "estación destino");
        
        Ruta ruta = catalogoRutas.get(nombreRuta);
        if (ruta == null) {
            throw new TransmilenioException(
                TransmilenioException.RUTA_NO_ENCONTRADA + ": '" + nombreRuta + "'"
            );
        }
        
        Estacion origen = registroEstaciones.get(nombreOrigen);
        Estacion destino = registroEstaciones.get(nombreDestino);
        
        if (origen == null) {
            throw new TransmilenioException(
                TransmilenioException.ESTACION_NO_ENCONTRADA + ": '" + nombreOrigen + "'"
            );
        }
        
        if (destino == null) {
            throw new TransmilenioException(
                TransmilenioException.ESTACION_NO_ENCONTRADA + ": '" + nombreDestino + "'"
            );
        }
        
        return ruta.determinarParadasIntermedias(origen, destino);
    }

    /**
     * SERVICIO 4: Busca rutas directas (sin transbordos) entre dos estaciones.
     * Ordena por: 1) Menor número de paradas, 2) Orden alfabético
     * 
     * @param nombreOrigen Nombre de la estación de origen
     * @param nombreDestino Nombre de la estación de destino
     * @return Lista ordenada de nombres de rutas directas
     * @throws TransmilenioException si alguna estación no existe
     * 
     */
    public List<String> buscarRutasDirectasOrdenadas(String nombreOrigen, String nombreDestino) throws TransmilenioException {
        
        validarNombreNoVacio(nombreOrigen, "estación origen");
        validarNombreNoVacio(nombreDestino, "estación destino");
        
        Estacion origen = registroEstaciones.get(nombreOrigen);
        Estacion destino = registroEstaciones.get(nombreDestino);
        
        if (origen == null) {
            throw new TransmilenioException(
                TransmilenioException.ESTACION_NO_ENCONTRADA + ": '" + nombreOrigen + "'"
            );
        }
        
        if (destino == null) {
            throw new TransmilenioException(
                TransmilenioException.ESTACION_NO_ENCONTRADA + ": '" + nombreDestino + "'"
            );
        }
        
        if (origen.equals(destino)) {
            throw new TransmilenioException(
                TransmilenioException.ESTACIONES_IGUALES
            );
        }
        
        List<RutaConParadas> rutasValidas = new ArrayList<>();
        
        for (Ruta ruta : catalogoRutas.values()) {
            if (!ruta.verificarConexion(origen, destino)) {
                continue;
            }
            
            try {
                int paradas = ruta.determinarParadasIntermedias(origen, destino);
                rutasValidas.add(new RutaConParadas(ruta.getCodigoRuta(), paradas));
            } catch (TransmilenioException e) {
            }
        }
        
        Collections.sort(rutasValidas, new Comparator<RutaConParadas>() {
            @Override
            public int compare(RutaConParadas r1, RutaConParadas r2) {
                if (r1.paradas != r2.paradas) {
                    return Integer.compare(r1.paradas, r2.paradas);
                }
                return r1.nombre.compareTo(r2.nombre);
            }
        });
        
        List<String> resultado = new ArrayList<>();
        for (RutaConParadas rc : rutasValidas) {
            resultado.add(rc.nombre);
        }
        
        return resultado;
    }
    


}
