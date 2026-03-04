import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaService {

    private ArrayList<Reserva> reservas;
    private String ultimoError;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    public boolean crearReserva(Reserva r) {
        if (r == null) {
            ultimoError = "La reserva es null.";
            return false;
        }

        if (buscarReserva(r.getId()) != null) {
            ultimoError = "Ya existe una reserva con ese ID.";
            return false;
        }

        if (!esFechaHoraValida(r.getFecha(), r.getHora())) {
            ultimoError = "No se permiten reservas con fecha u hora pasada.";
            return false;
        }

        if (hayConflicto(r.getFecha(), r.getHora(), null)) {
            ultimoError = "Ya existe una reserva en ese horario.";
            return false;
        }

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

        if (r == null) {
            ultimoError = "No existe una reserva con ese ID.";
            return false;
        }

        if (!esFechaHoraValida(fecha, hora)) {
            ultimoError = "No se permiten reservas con fecha u hora pasada.";
            return false;
        }

        if (hayConflicto(fecha, hora, id)) {
            ultimoError = "Ya existe una reserva en ese horario.";
            return false;
        }

        r.setNombre(nombre);
        r.setFecha(fecha);
        r.setHora(hora);

        return true;
    }

    public boolean eliminarReserva(int id) {
        Reserva r = buscarReserva(id);

        if (r == null) {
            ultimoError = "No existe una reserva con ese ID.";
            return false;
        }

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

    public String getUltimoError() {
        return ultimoError;
    }

    private boolean hayConflicto(LocalDate fecha, LocalTime hora, Integer idAIgnorar) {
        for (Reserva r : reservas) {
            boolean mismoHorario = r.getFecha().isEqual(fecha) && r.getHora().equals(hora);
            boolean esLaMisma = (idAIgnorar != null) && (r.getId() == idAIgnorar);

            if (mismoHorario && !esLaMisma) {
                return true;
            }
        }
        return false;
    }

}
