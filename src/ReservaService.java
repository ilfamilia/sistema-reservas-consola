import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ReservaService {

    private ArrayList<Reserva> reservas;
    private String ultimoError;

    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);

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
            if (!esHorarioPermitido(r.getHora())) {
                ultimoError = "Horario no permitido. Solo se aceptan reservas de 08:00 a 18:00.";
            } else if (!esIntervaloValido(r.getHora())) {
                ultimoError = "Las reservas solo pueden hacerse en intervalos de 30 minutos (00 o 30).";
            } else {
                ultimoError = "No se permiten reservas con fecha u hora pasada.";
            }
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
            if (!esHorarioPermitido(hora)) {
                ultimoError = "Horario no permitido. Solo se aceptan reservas de 08:00 a 18:00.";
            } else if (!esIntervaloValido(hora)) {
                ultimoError = "Las reservas solo pueden hacerse en intervalos de 30 minutos (00 o 30).";
            } else {
                ultimoError = "No se permiten reservas con fecha u hora pasada.";
            }
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

        if (!esHorarioPermitido(hora)) return false;

        if (!esIntervaloValido(hora)) return false;

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

    private boolean esHorarioPermitido(LocalTime hora) {
        if (hora == null) return false;

        return !hora.isBefore(HORA_APERTURA) && !hora.isAfter(HORA_CIERRE);
    }

    private boolean esIntervaloValido(LocalTime hora) {
        if (hora == null) return false;

        int minutos = hora.getMinute();
        return minutos == 0 || minutos == 30;
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
