import java.util.*;

/**
 * Representa una ruta del sistema TransMilenio.
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Ruta {
    private String nombre;
    private ArrayList<Estacion> estaciones;
    private HashMap<String, Integer> indiceEstaciones;

    /**
     * Construye una nueva ruta con el nombre y estaciones especificados.
     * 
     * @param nombre Nombre de la ruta
     * @param estaciones Lista de estaciones que componen la ruta
     */
    public Ruta(String nombre, ArrayList<Estacion> estaciones) {
        this.nombre = nombre;
        this.estaciones = estaciones;
        this.indiceEstaciones = new HashMap<>();
        
        for (int i = 0; i < estaciones.size(); i++) {
            indiceEstaciones.put(estaciones.get(i).getNombre(), i);
        }
    }
    
    /**
     * Obtiene el nombre de la ruta.
     * @return nombre de la ruta
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene las estaciones de la ruta.
     * @return lista de estaciones
     */
    public ArrayList<Estacion> getEstaciones() {
        return estaciones;
    }
    
    /**
     * Verifica si una estación pertenece a esta ruta.
     * @param nombreEstacion nombre de la estación a verificar
     * @return true si la estación está en la ruta
     */
    public boolean contieneEstacion(String nombreEstacion) {
        return indiceEstaciones.containsKey(nombreEstacion);
    }
    
    /**
     * Obtiene el índice de una estación en la ruta.
     * @param nombreEstacion nombre de la estación
     * @return índice de la estación, o -1 si no existe
     */
    public int getIndiceEstacion(String nombreEstacion) {
        return indiceEstaciones.getOrDefault(nombreEstacion, -1);
    }
    
    /**
     * Servicio 3: El número de paradas para ir de una estación a otra tomando esta ruta.
     * @param nombreOrigen nombre de la estación de origen
     * @param nombreDestino nombre de la estación de destino
     * @return número de paradas (estaciones intermedias + destino)
     * @throws TransmilenioException si alguna estación no está en la ruta o son iguales
     */
    public int getNumeroParadas(String nombreOrigen, String nombreDestino) throws TransmilenioException {
        if (nombreOrigen.equals(nombreDestino)) {
            throw new TransmilenioException(TransmilenioException.ESTACIONES_IGUALES);
        }
        
        if (!contieneEstacion(nombreOrigen)) {
            throw new TransmilenioException(TransmilenioException.RUTA_NO_CONECTA_ESTACIONES);
        }
        
        if (!contieneEstacion(nombreDestino)) {
            throw new TransmilenioException(TransmilenioException.RUTA_NO_CONECTA_ESTACIONES);
        }
        
        int indiceOrigen = getIndiceEstacion(nombreOrigen);
        int indiceDestino = getIndiceEstacion(nombreDestino);
        
        return Math.abs(indiceDestino - indiceOrigen);
    }
    
    /**
     * Verifica si esta ruta conecta dos estaciones.
     * @param nombreOrigen nombre de la estación de origen
     * @param nombreDestino nombre de la estación de destino
     * @return true si la ruta conecta ambas estaciones
     */
    public boolean conectaEstaciones(String nombreOrigen, String nombreDestino) {
        return contieneEstacion(nombreOrigen) && contieneEstacion(nombreDestino);
    }
}