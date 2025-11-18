/**
 * Excepción personalizada para errores del sistema TransMilenio.
 * 
 * Proporciona mensajes de error estandarizados y específicos para
 * diferentes tipos de fallos en las operaciones del sistema.
 * 
 * Jerarquía de excepciones:
 * Exception → TransmilenioException
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class TransmilenioException extends Exception {
    
    /**
     * Error cuando no se encuentra una estación en el sistema.
     */
    public static final String ESTACION_NO_ENCONTRADA = "No se encontró la estación especificada en el sistema";
    
    /**
     * Error cuando no se encuentra una ruta en el sistema.
     */
    public static final String RUTA_NO_ENCONTRADA = "No se encontró la ruta especificada en el sistema";
    
    /**
     * Error cuando una ruta no conecta dos estaciones dadas.
     */
    public static final String RUTA_NO_CONECTA_ESTACIONES = "La ruta especificada no conecta las estaciones indicadas";
    
    /**
     * Error cuando las estaciones de origen y destino son iguales.
     */
    public static final String ESTACIONES_IGUALES = "Las estaciones de origen y destino no pueden ser iguales";
    
    /**
     * Error cuando no existe un camino entre dos estaciones.
     */
    public static final String SIN_CAMINO_DISPONIBLE = "No existe un camino disponible entre las estaciones especificadas";
    
    /**
     * Error cuando no se encuentra una troncal en el sistema.
     */
    public static final String TRONCAL_NO_ENCONTRADA = "No se encontró la troncal especificada en el sistema";
    
    /**
     * Error cuando un parámetro proporcionado es inválido.
     */
    public static final String PARAMETRO_INVALIDO = "El parámetro proporcionado no es válido";
    
    /**
     * Error cuando se intenta una operación en un sistema no inicializado.
     */
    public static final String SISTEMA_NO_INICIALIZADO = "El sistema no está inicializado correctamente";
    
    /**
     * Error cuando hay inconsistencia en los datos.
     */
    public static final String DATOS_INCONSISTENTES = "Se detectó una inconsistencia en los datos del sistema";
    
    /**
     * Error cuando se excede un límite del sistema.
     */
    public static final String LIMITE_EXCEDIDO = "Se ha excedido un límite del sistema";

    
    /**
     * Construye una nueva excepción con un mensaje específico.
     * 
     * @param mensaje Mensaje descriptivo del error
     */
    public TransmilenioException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción encadenando otra excepción.
     * 
     * @param causa Excepción que causó este error
     */
    public TransmilenioException(Throwable causa) {
        super(causa);
    }

 

}
