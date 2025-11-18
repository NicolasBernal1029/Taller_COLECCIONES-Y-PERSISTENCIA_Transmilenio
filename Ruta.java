import java.util.*;

/**
 * Representa una ruta del sistema TransMilenio con optimizaciones para
 * consultas frecuentes de paradas y conexiones.
 * 
 * Mantiene un índice inverso de estaciones para búsquedas O(1) y
 * validación eficiente de conexiones entre estaciones.
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Ruta {
    private final String codigoRuta;
    private final LinkedList<Estacion> secuenciaEstaciones;
    private final HashMap<Estacion, Integer> indiceEstaciones;

    /**
     * Construye una nueva ruta con un código identificador.
     * 
     * @param codigoRuta Código único de la ruta
     * @throws IllegalArgumentException si el código es nulo o vacío
     */
    public Ruta(String codigoRuta) {
        if (codigoRuta == null || codigoRuta.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El código de ruta no puede ser nulo o vacío"
            );
        }
        
        this.codigoRuta = codigoRuta.trim();
        this.secuenciaEstaciones = new LinkedList<>();
        this.indiceEstaciones = new HashMap<>();
    }

    /**
     * Obtiene el código identificador de la ruta.
     * 
     * @return Código de la ruta
     */
    public String getCodigoRuta() {
        return codigoRuta;
    }



    /**
     * Obtiene la secuencia completa de estaciones de la ruta.
     * Retorna una copia para preservar encapsulamiento.
     * 
     * @return Lista inmutable de estaciones en orden
     */
    public List<Estacion> obtenerSecuenciaEstaciones() {
        return Collections.unmodifiableList(new ArrayList<>(secuenciaEstaciones));
    }

    /**
     * Verifica si la ruta contiene una estación específica.
     * Operación O(1) gracias al índice inverso.
     * 
     * @param estacion Estación a verificar
     * @return true si la estación está en la ruta
     */
    public boolean contieneEstacion(Estacion estacion) {
        return indiceEstaciones.containsKey(estacion);
    }

    /**
     * Obtiene la posición de una estación en la secuencia de la ruta.
     * 
     * @param estacion Estación a buscar
     * @return Índice de la estación 
    public int obtenerPosicionEstacion(Estacion estacion) {
        return indiceEstaciones.getOrDefault(estacion, -1);
    }

    /**
     * Verifica si esta ruta conecta dos estaciones en algún orden.
     * Operación O(1) gracias al índice inverso.
     * 
     * @param origen Estación de origen
     * @param destino Estación de destino
     * @return true si ambas estaciones están en la ruta
     */
    public boolean verificarConexion(Estacion origen, Estacion destino) {
        return indiceEstaciones.containsKey(origen) && 
               indiceEstaciones.containsKey(destino);
    }

    /**
     * Determina el número de paradas intermedias entre dos estaciones.
     * No cuenta la estación de origen ni la de destino.
     * 
     * @param origen Estación de origen
     * @param destino Estación de destino
     * @return Número de paradas intermedias
     * @throws TransmilenioException si alguna estación no está en la ruta
     * @throws TransmilenioException si las estaciones son iguales
     */
    public int determinarParadasIntermedias(Estacion origen, Estacion destino) 
            throws TransmilenioException {
        
        if (origen.equals(destino)) {
            throw new TransmilenioException(
                "Las estaciones de origen y destino no pueden ser iguales"
            );
        }
        
        Integer posOrigen = indiceEstaciones.get(origen);
        Integer posDestino = indiceEstaciones.get(destino);
        
        if (posOrigen == null) {
            throw new TransmilenioException(
                String.format("La ruta '%s' no contiene la estación de origen '%s'",
                    codigoRuta, origen.getIdentificador())
            );
        }
        
        if (posDestino == null) {
            throw new TransmilenioException(
                String.format("La ruta '%s' no contiene la estación de destino '%s'",
                    codigoRuta, destino.getIdentificador())
            );
        }
        
        // Calcular distancia absoluta menos 1 (no contar origen ni destino)
        int distancia = Math.abs(posDestino - posOrigen);
        
        if (distancia == 0) {
            throw new TransmilenioException(
                "Error interno: las estaciones tienen la misma posición"
            );
        }
        
        return distancia - 1; 
    }

    /**
     * Obtiene el número total de estaciones en la ruta.
     * 
     * @return Cantidad de estaciones
     */
    public int obtenerCantidadEstaciones() {
        return secuenciaEstaciones.size();
    }


    /**
     * Verifica si la ruta está vacía (sin estaciones).
     * 
     * @return true si no tiene estaciones
     */
    public boolean estaVacia() {
        return secuenciaEstaciones.isEmpty();
    }


}
