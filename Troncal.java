import java.util.*;

/**
 * Representa una troncal del sistema TransMilenio con topología lineal.
 * Gestiona estaciones secuenciales y calcula tiempos de viaje basados en
 * distancias y velocidad de crucero.
 * 
 * Optimizada para búsquedas de distancias con mapa bidireccional.
 * 
 * @author Nicolas Felipe Bernal Gallo  
 * @author Juan Daniel Bogota Fuentes
 * @version 1.0
 */
public class Troncal {
    private final String nombreTroncal;
    private final double velocidadCrucero; // metros por minuto
    private final ArrayList<Estacion> topologiaEstaciones;
    private final HashMap<ClaveConexion, Double> mapaDistancias;

    

    /*
     * Crea una nueva troncal del sistema.
     * @param nombreTroncal    nombre de la troncal
     * @param velocidadPromedio  velocidad promedio de la troncal
     */
    public Troncal(String nombreTroncal, double velocidadPromedio) {
        this.nombreTroncal = nombreTroncal;
        this.velocidadPromedio = velocidadPromedio;
        this.estaciones = new ArrayList<>();
        this.tramos = new HashMap<>();
    }

    /**
     * Obtiene el nombre de la troncal.
     * 
     * @return Nombre de la troncal
     */
    public String getNombreTroncal() {
        return nombreTroncal;
    }

    /**
     * Obtiene la velocidad de crucero de la troncal.
     * 
     * @return Velocidad en metros/minuto
     */
    public double getVelocidadCrucero() {
        return velocidadCrucero;
    }

    /*
     * Obtiene la lista de estaciones en la troncal.
     */
    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    /*
     * Obtiene la lista de tramos en la troncal.
     */
    public List<Tramo> getTramos() {
        return tramos;
    }

    /**
     * Agrega una estación al final de la troncal.
     * 
     * @param estacion Estación a agregar
     */
    public void agregarEstacion(Estacion estacion) {
        estaciones.add(estacion);
    }
    
}
