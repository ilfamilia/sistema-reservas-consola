import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaService {

    private ArrayList<Reserva> reservas;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public void crearReserva(String nombre, LocalDate fecha, LocalTime hora) {
        reservas.add(new Reserva(nombre, fecha, hora));
    }

    public Reserva buscarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }

        return null;
    }

    public void eliminarReserva(int id) {
        reservas.remove(id);
    }

    public void listarReservas() {
        for (Reserva r : reservas) {
            System.out.println("------------------------");
            System.out.println(r);
        }
    }
}
