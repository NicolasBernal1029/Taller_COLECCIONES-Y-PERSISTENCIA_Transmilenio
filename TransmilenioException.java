/**
 * Excepción personalizada para errores del sistema TransMilenio.
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class TransmilenioException extends Exception {
    

    public static final String ESTACION_NO_ENCONTRADA = "No se encontró la estación especificada en el sistema";
    public static final String RUTA_NO_ENCONTRADA = "No se encontró la ruta especificada en el sistema";
    public static final String RUTA_DEBE_TENER_ESTACIONES = "La ruta debe contener al menos dos estaciones";
    public static final String RUTA_NO_CONECTA_ESTACIONES = "La ruta especificada no conecta las estaciones indicadas";
    public static final String ESTACIONES_IGUALES = "Las estaciones de origen y destino no pueden ser iguales";
    public static final String SIN_CAMINO_DISPONIBLE = "No existe un camino disponible entre las estaciones especificadas";
    public static final String TRONCAL_NO_ENCONTRADA = "No se encontró la troncal especificada en el sistema";
    public static final String PARAMETRO_INVALIDO = "El parámetro proporcionado no es válido";
    public static final String SISTEMA_NO_INICIALIZADO = "El sistema no está inicializado correctamente";
    public static final String DATOS_INCONSISTENTES = "Se detectó una inconsistencia en los datos del sistema";
    public static final String LIMITE_EXCEDIDO = "Se ha excedido un límite del sistema";
    public static final String SIN_NOMBRE = "El nombre proporcionado no puede estar vacío o ser nulo";

    /**
     * Construye una nueva excepción con un mensaje específico.
     * 
     * @param mensaje Mensaje descriptivo del error
     */
    public TransmilenioException(String mensaje) {
        super(mensaje);
    }

}