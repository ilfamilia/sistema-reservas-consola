import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/*
 * Capa de servicio: concentra la lógica de negocio del sistema de reservas.
 * - Mantiene las reservas en memoria (ArrayList) como sustituto de una BD.
 * - Aplica reglas: no fechas pasadas, horario permitido, intervalos de 30 min y no solapamientos.
 * - Expone "ultimoError" para que la capa de UI (Main) pueda informar al usuario.
 */
public class ReservaService {

    private ArrayList<Reserva> reservas;
    private String ultimoError;

    // Reglas de negocio para disponibilidad/creación de reservas
    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);
    private static final int INTERVALO_MINUTOS = 30;

    public ReservaService() {
        reservas = new ArrayList<>();
    }

    /*
     * Crea una reserva si cumple todas las reglas de negocio.
     * En caso de fallo, deja un mensaje explicativo en ultimoError.
     */
    public boolean crearReserva(Reserva r) {
        if (r == null) {
            ultimoError = "La reserva es null.";
            return false;
        }

        // En un sistema real este ID lo controlaría la BD; aquí se valida por consistencia.
        if (buscarReserva(r.getId()) != null) {
            ultimoError = "Ya existe una reserva con ese ID.";
            return false;
        }

        if (!esFechaHoraValida(r.getFecha(), r.getHora())) {
            // Se distingue el motivo para dar feedback claro al usuario.
            if (!esHorarioPermitido(r.getHora())) {
                ultimoError = "Horario no permitido. Solo se aceptan reservas de 08:00 a 18:00.";
            } else if (!esIntervaloValido(r.getHora())) {
                ultimoError = "Las reservas solo pueden hacerse en intervalos de 30 minutos (00 o 30).";
            } else {
                ultimoError = "No se permiten reservas con fecha u hora pasada.";
            }
            return false;
        }

        // Regla: no se permiten dos reservas con la misma fecha y hora.
        if (hayConflicto(r.getFecha(), r.getHora(), null)) {
            ultimoError = "Ya existe una reserva en ese horario.";
            return false;
        }

        reservas.add(r);
        return true;
    }

    /*
     * Búsqueda por ID en memoria (O(n)). Para este proyecto es suficiente.
     * En una BD esto sería una consulta por clave primaria.
     */
    public Reserva buscarReserva(int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return r;
            }
        }

        return null;
    }

    /*
     * Actualiza los datos de una reserva existente.
     * La validación de conflicto ignora el mismo ID para evitar que "choque consigo misma".
     */
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

    /*
     * Devuelve una copia para evitar que la capa de UI modifique la lista interna.
     */
    public ArrayList<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    /*
     * Genera los "slots" disponibles en una fecha, respetando:
     * - horario de apertura/cierre
     * - intervalos fijos de 30 minutos
     * - slots ya ocupados por reservas existentes
     * - si la fecha es hoy, omite horas ya pasadas
     */
    public ArrayList<LocalTime> obtenerHorariosDisponibles(LocalDate fecha) {
        ArrayList<LocalTime> disponibles = new ArrayList<>();
        if (fecha == null) {
            ultimoError = "La fecha no puede ser null.";
            return disponibles;
        }

        if (fecha.isBefore(LocalDate.now())) {
            ultimoError = "No se pueden consultar horarios de una fecha pasada.";
            return disponibles;
        }

        LocalTime slot = HORA_APERTURA;
        while (!slot.isAfter(HORA_CIERRE)) {

            if (fecha.isEqual(LocalDate.now()) && slot.isBefore(LocalTime.now())) {
                slot = slot.plusMinutes(INTERVALO_MINUTOS);
                continue;
            }

            if (!hayConflicto(fecha, slot, null)) {
                disponibles.add(slot);
            }

            slot = slot.plusMinutes(INTERVALO_MINUTOS);
        }

        ultimoError = null;
        return disponibles;
    }


    /*
     * Validación centralizada de fecha/hora.
     * Nota: el mensaje específico se asigna en crear/actualizar para dar feedback más preciso.
     */
    private boolean esFechaHoraValida(LocalDate fecha, LocalTime hora) {
        if (fecha == null || hora == null) return false;

        if (!esHorarioPermitido(hora)) return false;

        if (!esIntervaloValido(hora)) return false;

        LocalDate hoy = LocalDate.now();

        if (fecha.isBefore(hoy)) return false;

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

    /*
     * Detecta si existe una reserva en el mismo día y hora.
     * idAIgnorar se usa al actualizar para no considerar la reserva que se está editando.
     */
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
