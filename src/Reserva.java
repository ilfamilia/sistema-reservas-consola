import java.time.*;


/*
 * Representa una reserva dentro del sistema.
 * Contiene la información básica necesaria: identificador,
 * nombre del cliente, fecha y hora de la reserva.
 */
public class Reserva {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;

    /*
     * Contador estático que simula un identificador autoincremental,
     * similar a una clave primaria en una base de datos.
     */
    private static int idCount = 1;

    /*
     * Al crear una reserva se asigna automáticamente un ID único
     * usando el contador estático.
     */
    public Reserva(String nombre, LocalDate fecha, LocalTime hora) {
        this.id = idCount;
        idCount++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

    /*
     * Los setters incluyen validaciones básicas para garantizar
     * la integridad de los datos antes de modificarlos.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede ser null.");
        }
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        if (hora == null) {
            throw new IllegalArgumentException("La hora no puede ser null.");
        }
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    /*
     * Representación textual de la reserva utilizada para mostrar
     * la información en la consola.
     */
    @Override
    public String toString(){
        return "Reserva (" + id + ")" + "\nNombre: " + nombre +
                "\nFecha: " + fecha + "\nHora: " + hora;
    }
}
