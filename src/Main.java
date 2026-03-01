import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        ReservaService myRs = new ReservaService();
        myRs.crearReserva("Israel", LocalDate.now(), LocalTime.now());
        myRs.crearReserva("Luisa", LocalDate.now(), LocalTime.now());
        myRs.crearReserva("Maria", LocalDate.now(), LocalTime.now());

        myRs.listarReservas();
    }
}
