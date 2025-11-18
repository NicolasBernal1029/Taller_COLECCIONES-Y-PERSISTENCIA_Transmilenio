import java.util.HashMap;
import java.util.Objects;

/**
 * Representa una estación del sistema TransMilenio con capacidad de
 * gestionar su estado de ocupación y calcular tiempos de espera dinámicos.
 * 
 * Cada estación mantiene un identificador único y parámetros de tiempo
 * de espera configurables según el nivel de ocupación actual.
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Estacion {
    private final String identificador;
    private NivelOcupacion estadoOcupacion;
    private final HashMapMap<NivelOcupacion, Integer> parametrosEspera;

    /**
     * Construye una nueva estación con sus parámetros de tiempo de espera.
     * 
     * @param identificador Nombre único de la estación
     * @param estadoInicial Estado de ocupación inicial
     * @param esperaAlto Tiempo de espera en minutos cuando ocupación es ALTA
     * @param esperaMedio Tiempo de espera en minutos cuando ocupación es MEDIA
     * @param esperaBajo Tiempo de espera en minutos cuando ocupación es BAJA
     * @throws IllegalArgumentException si el identificador es nulo o vacío
     * @throws IllegalArgumentException si algún tiempo de espera es negativo
     */
    public Estacion(String identificador, NivelOcupacion estadoInicial, int esperaAlto, int esperaMedio, int esperaBajo) {
        
        validarIdentificador(identificador);
        validarTiemposEspera(esperaAlto, esperaMedio, esperaBajo);
        
        this.identificador = identificador.trim();
        this.estadoOcupacion = estadoInicial != null ? 
            estadoInicial : NivelOcupacion.MEDIO;
        
        this.parametrosEspera = new HashMap<>(NivelOcupacion.class);
        parametrosEspera.put(NivelOcupacion.ALTO, esperaAlto);
        parametrosEspera.put(NivelOcupacion.MEDIO, esperaMedio);
        parametrosEspera.put(NivelOcupacion.BAJO, esperaBajo);
    }

    /**
     * Valida que el identificador sea válido.
     */
    private void validarIdentificador(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El identificador de la estación no puede ser nulo o vacío"
            );
        }
    }

    /**
     * Valida que los tiempos de espera sean no negativos.
     */
    private void validarTiemposEspera(int alto, int medio, int bajo) {
        if (alto < 0 || medio < 0 || bajo < 0) {
            throw new IllegalArgumentException(
                "Los tiempos de espera no pueden ser negativos"
            );
        }
    }

    /**
     * Obtiene el identificador único de la estación.
     * 
     * @return Identificador de la estación
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Obtiene el estado actual de ocupación de la estación.
     * 
     * @return Estado de ocupación actual
     */
    public NivelOcupacion getEstadoOcupacion() {
        return estadoOcupacion;
    }

    /**
     * Actualiza el estado de ocupación de la estación.
     * Este cambio afecta inmediatamente el cálculo del tiempo de espera.
     * 
     * @param nuevoEstado Nuevo estado de ocupación
     * @throws IllegalArgumentException si el estado es nulo
     */
    public void actualizarEstadoOcupacion(NivelOcupacion nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException(
                "El nuevo estado de ocupación no puede ser nulo"
            );
        }
        this.estadoOcupacion = nuevoEstado;
    }

    /**
     * Calcula el tiempo de espera actual en la estación basado en
     * el estado de ocupación presente.
     * 
     * @return Tiempo de espera en minutos
     */
    public int calcularTiempoEsperaActual() {
        return parametrosEspera.get(estadoOcupacion);
    }

    /**
     * Obtiene el tiempo de espera configurado para un nivel de ocupación específico.
     * 
     * @param nivel Nivel de ocupación a consultar
     * @return Tiempo de espera en minutos para ese nivel
     */
    public int consultarTiempoEsperaPorNivel(NivelOcupacion nivel) {
        return parametrosEspera.getOrDefault(nivel, 5); // 5 minutos por defecto
    }

    /**
     * Verifica si esta estación es operativa (tiene tiempos de espera válidos).
     * 
     * @return true si la estación tiene configuración válida
     */
    public boolean esOperativa() {
        return parametrosEspera.values().stream()
            .allMatch(tiempo -> tiempo >= 0 && tiempo <= 30);
    }



}
