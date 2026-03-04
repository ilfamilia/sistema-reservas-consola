import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaService {

    private ArrayList<Reserva> reservas;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public boolean crearReserva(Reserva r) {
        if (r == null) return false;

        if (buscarReserva(r.getId()) != null) return false;

        if (!esFechaHoraValida(r.getFecha(), r.getHora())) return false;

        reservas.add(r);
        return true;
    }

    public Reserva buscarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }

        return null;
    }

    public boolean actualizarReserva(int id, String nombre, LocalDate fecha, LocalTime hora) {

        Reserva r = buscarReserva(id);

        if (r == null) return false;

        if (!esFechaHoraValida(fecha, hora)) return false;

        r.setNombre(nombre);
        r.setFecha(fecha);
        r.setHora(hora);

        return true;
    }

    public boolean eliminarReserva(int id) {
        Reserva r = buscarReserva(id);

        if (r == null) return false;

        reservas.remove(r);

        return true;
    }

    public ArrayList<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    private boolean esFechaHoraValida(LocalDate fecha, LocalTime hora) {
        if (fecha == null || hora == null) return false;

        LocalDate hoy = LocalDate.now();

        // Fecha pasada
        if (fecha.isBefore(hoy)) return false;

        // Si es hoy, la hora debe ser ahora o después
        if (fecha.isEqual(hoy)) {
            LocalTime ahora = LocalTime.now();
            if (hora.isBefore(ahora)) return false;
        }

        return true;
    }

}
