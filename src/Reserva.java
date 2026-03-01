import java.time.*;

public class Reserva {
    private int id;
    private String nombre;
    private LocalDate fecha;
    private LocalTime hora;

    private static int idCount = 1;

    public Reserva(String nombre, LocalDate fecha, LocalTime hora) {
        id = idCount;
        idCount++;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return "Reserva (" + id + ")" + "\nNombre: " + nombre +
                "\nFecha: " + fecha + "\nHora: " + hora;
    }
}
