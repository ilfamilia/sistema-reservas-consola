import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ReservaService service = new ReservaService();

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1: {
                    System.out.println("\n--- Crear Reserva ---");

                    String nombre = leerTexto(sc, "Nombre: ");
                    LocalDate fecha = leerFecha(sc, "Año: ", "Mes: ", "Día: ");
                    LocalTime hora = leerHora(sc, "Hora: ", "Minutos: ");

                    Reserva r = new Reserva(nombre, fecha, hora);

                    boolean operacionValida = service.crearReserva(r);

                    if (operacionValida) {
                        System.out.println("\nReserva creada correctamente.");
                    } else {
                        System.out.println("\nNo se pudo crear la reserva.");
                    }

                    break;
                }

                case 6:
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nOpción inválida. Intenta de nuevo.");
            }

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


    private static String leerTexto(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String line = sc.nextLine();
            if (!line.trim().isEmpty()) return line.trim();
            System.out.println("\nEste campo no puede estar vacío.\n");
        }
    }

    private static LocalDate leerFecha(Scanner sc, String mensaje1, String mensaje2, String mensaje3) {
        while (true) {

            System.out.print(mensaje1);
            String line1 = sc.nextLine();

            System.out.print(mensaje2);
            String line2 = sc.nextLine();

            System.out.print(mensaje3);
            String line3 = sc.nextLine();

            try {

                int anio = Integer.parseInt(line1.trim());
                int mes = Integer.parseInt(line2.trim());
                int dia = Integer.parseInt(line3.trim());

                LocalDate fecha = LocalDate.of(anio, mes, dia);

                return fecha;

            } catch (NumberFormatException e) {
                System.out.println("\nDebes escribir números.\n");
            } catch (DateTimeException e) {
                System.out.println("\nFecha inválida. Intente de nuevo.\n");
            }
        }
    }

    private static LocalTime leerHora(Scanner sc, String mensaje1, String mensaje2) {
        while (true) {

            System.out.print(mensaje1);
            String line1 = sc.nextLine();

            System.out.print(mensaje2);
            String line2 = sc.nextLine();

            try {

                int horas = Integer.parseInt(line1.trim());
                int minutos = Integer.parseInt(line2.trim());

                LocalTime hora = LocalTime.of(horas, minutos);

                return hora;

            } catch (NumberFormatException e) {
                System.out.println("\nDebes escribir números.\n");
            } catch (DateTimeException e) {
                System.out.println("\nHora inválida. Intente de nuevo.\n");
            }
        }
    }

}
