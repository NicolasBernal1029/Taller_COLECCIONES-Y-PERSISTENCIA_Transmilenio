import java.util.*;

/**
 * Representa una troncal del sistema TransMilenio
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Troncal {
    private String nombreTroncal;
    private double velocidadPromedio;
    private ArrayList<Estacion> estaciones;
    private HashMap<String, HashMap<String, Double>> tramos;

    /**
     * Construye una nueva troncal con el nombre y velocidad especificados.
     * 
     * @param nombreTroncal Nombre de la troncal
     * @param velocidadPromedio Velocidad promedio de los buses en metros/minuto
     */
    public Troncal(String nombreTroncal, double velocidadPromedio) {
        this.nombreTroncal = nombreTroncal;
        this.velocidadPromedio = velocidadPromedio;
        this.estaciones = new ArrayList<>();
        this.tramos = new HashMap<>();
    }
    
    /**
     * Agrega una estación a la troncal.
     * @param estacion estación a agregar
     */
    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }
    
    /**
     * Agrega un tramo entre dos estaciones con su distancia.
     * @param estacionOrigen nombre de la estación de origen
     * @param estacionDestino nombre de la estación de destino
     * @param distancia distancia en metros
     */
    public void agregarTramo(String estacionOrigen, String estacionDestino, double distancia) {
        if (!tramos.containsKey(estacionOrigen)) {
            tramos.put(estacionOrigen, new HashMap<>());
        }
        tramos.get(estacionOrigen).put(estacionDestino, distancia);
        
        if (!tramos.containsKey(estacionDestino)) {
            tramos.put(estacionDestino, new HashMap<>());
        }
        tramos.get(estacionDestino).put(estacionOrigen, distancia);
    }
    
    /**
     * Obtiene la distancia entre dos estaciones.
     * @param estacionOrigen nombre de la estación de origen
     * @param estacionDestino nombre de la estación de destino
     * @return distancia en metros, o -1 si no existe el tramo
     */
    public double getDistancia(String estacionOrigen, String estacionDestino) {
        if (tramos.containsKey(estacionOrigen) && 
            tramos.get(estacionOrigen).containsKey(estacionDestino)) {
            return tramos.get(estacionOrigen).get(estacionDestino);
        }
        return -1;
    }
    
    /**
     * Obtiene el nombre de la troncal.
     * @return nombre de la troncal
     */
    public String getNombreTroncal() {
        return nombreTroncal;
    }
    
    /**
     * Obtiene la velocidad promedio.
     * @return velocidad en metros/minuto
     */
    public double getVelocidadPromedio() {
        return velocidadPromedio;
    }
    
    /**
     * Obtiene las estaciones de la troncal.
     * @return lista de estaciones
     */
    public ArrayList<Estacion> getEstaciones() {
        return estaciones;
    }
}