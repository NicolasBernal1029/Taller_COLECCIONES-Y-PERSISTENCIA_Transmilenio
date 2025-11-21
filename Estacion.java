import java.util.HashMap;
import java.util.Objects;

/**
 * Representa una estación del sistema TransMilenio
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Estacion {
    private String nombre;
    private String nivelOcupacion; // "BAJO", "MEDIO", "ALTO"
    private HashMap<String, Integer> tiemposEsperaPorOcupacion;

    /**
     * Construye una nueva estación con el nombre especificado.
     * @param nombre Nombre de la estación
     */
    public Estacion(String nombre) {
        this.nombre = nombre;
        this.nivelOcupacion = "BAJO";
        this.tiemposEsperaPorOcupacion = new HashMap<>();
        this.tiemposEsperaPorOcupacion.put("BAJO", 2);
        this.tiemposEsperaPorOcupacion.put("MEDIO", 5);
        this.tiemposEsperaPorOcupacion.put("ALTO", 10);
    }
    
    /**
     * Obtiene el nombre de la estación.
     * @return nombre de la estación
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el nivel de ocupación actual de la estación.
     * @return nivel de ocupación ("BAJO", "MEDIO", "ALTO")
     */
    public String getNivelOcupacion() {
        return nivelOcupacion;
    }
    
    /**
     * Establece el nivel de ocupación de la estación.
     * @param nivelOcupacion nivel de ocupación ("BAJO", "MEDIO", "ALTO")
     * @throws TransmilenioException si el nivel no es válido
     */
    public void setNivelOcupacion(String nivelOcupacion) throws TransmilenioException {
        if (!tiemposEsperaPorOcupacion.containsKey(nivelOcupacion)) {
            throw new TransmilenioException(TransmilenioException.PARAMETRO_INVALIDO);
        }
        this.nivelOcupacion = nivelOcupacion;
    }
    
    /**
     * Obtiene el tiempo de espera actual según el nivel de ocupación.
     * Servicio 1: El tiempo de espera de una estación (Dado su nombre)
     * @return tiempo de espera en minutos
     */
    public int getTiempoEspera() {
        return tiemposEsperaPorOcupacion.get(nivelOcupacion);
    }
    
    /**
     * Compara esta estación con otro objeto para igualdad basado en el nombre.
     * @param o objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estacion estacion = (Estacion) o;
        return nombre.equals(estacion.nombre);
    }
    
    /**
     * Genera un código hash basado en el nombre de la estación.
     * @return código hash de la estación
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}