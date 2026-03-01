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
}
