import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Seleccione una opción: ");

        } while (opcion != 6);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== SISTEMA DE RESERVAS =====");
        System.out.println();
        System.out.println("1) Crear reserva");
        System.out.println("2) Ver reservas");
        System.out.println("3) Buscar reserva");
        System.out.println("4) Modificar reserva");
        System.out.println("5) Eliminar reserva");
        System.out.println("6) Salir");
        System.out.println();
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("\nDebes escribir un número entero.\n");
            }
        }
    }
}
