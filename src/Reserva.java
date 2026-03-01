import java.time.*;

public class Reserva {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;

    private static int idCount = 1;

    public Reserva(String nombre, LocalDate fecha, LocalTime hora) {
        this.id = idCount;
        idCount++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

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

    @Override
    public String toString(){
        return "Reserva (" + id + ")" + "\nNombre: " + nombre +
                "\nFecha: " + fecha + "\nHora: " + hora;
    }
}
