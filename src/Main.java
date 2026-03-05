import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ReservaService service = new ReservaService();

        service.crearReserva(new Reserva("Steve Vai", LocalDate.of(2027, 3, 12), LocalTime.of(10, 30)));
        service.crearReserva(new Reserva("Jimi Hendrix", LocalDate.of(2027, 5, 21), LocalTime.of(13, 0)));
        service.crearReserva(new Reserva("Stephen Curry", LocalDate.of(2028, 3, 8), LocalTime.of(17, 30)));

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
                        System.out.println(service.getUltimoError());
                    }

                    pausa(sc);

                    break;
                }

                case 2: {
                    System.out.println("\n--- Mostrar todas las Reservas ---");
                    ArrayList<Reserva> todas = service.listarReservas();

                    if (todas.isEmpty()) {
                        System.out.println("\nNo hay reservas registradas.");
                    } else {
                        System.out.println("Total: " + todas.size());
                        for (Reserva r : todas) {
                            System.out.println("--------------------");
                            System.out.println(r);
                        }
                    }

                    pausa(sc);

                    break;
                }

                case 3: {
                    System.out.println("\n--- Buscar reserva ---");
                    int id = leerEntero(sc, "ID a buscar: ");

                    Reserva encontrada = service.buscarReserva(id);

                    if (encontrada == null) {
                        System.out.println("\nNo se encontró ninguna reserva con ese ID.");
                    } else {
                        System.out.println("\nEncontrada:\n" + encontrada);
                    }

                    pausa(sc);

                    break;
                }

                case 4: {
                    System.out.println("\n--- Modificar reserva ---");
                    int id = leerEntero(sc, "ID de reserva a actualizar: ");

                    Reserva actual = service.buscarReserva(id);

                    if (actual == null) {
                        System.out.println("\nNo existe una reserva con ese ID.");
                        
                        pausa(sc);

                        break;
                    }

                    System.out.println("Reserva actual:\n" + actual);
                    System.out.println("\nIntroduce los nuevos datos: ");

                    String nombre = leerTexto(sc, "Nombre: ");
                    LocalDate fecha = leerFecha(sc, "Año: ", "Mes: ", "Día: ");
                    LocalTime hora = leerHora(sc, "Hora: ", "Minutos: ");

                    boolean operacionValida = service.actualizarReserva(id, nombre, fecha, hora);
                    if (operacionValida) {
                        System.out.println("\nActualizada correctamente.");
                    } else {
                        System.out.println("\nNo se pudo actualizar.");
                        System.out.println(service.getUltimoError());
                    }

                    pausa(sc);

                    break;
                }

                case 5: {
                    System.out.println("\n--- Eliminar reserva ---");
                    int id = leerEntero(sc, "ID de reserva a eliminar: ");

                    boolean operacionValida = service.eliminarReserva(id);
                    if (operacionValida) {
                        System.out.println("\nReserva eliminada correctamente.");
                    } else {
                        System.out.println("\nNo se pudo eliminar la reserva.");
                        System.out.println(service.getUltimoError());
                    }

                    pausa(sc);

                    break;
                }

                case 6: {
                    System.out.println("\n--- Horarios disponibles ---");
                    LocalDate fecha = leerFecha(sc, "Año: ", "Mes: ", "Día: ");

                    ArrayList<LocalTime> disponibles = service.obtenerHorariosDisponibles(fecha);

                    if (disponibles.isEmpty()) {
                        System.out.println("\nNo hay horarios disponibles para esa fecha.");
                        String err = service.getUltimoError();
                        if (err != null) System.out.println(err);
                    } else {
                        System.out.println("\nFecha: " + fecha);
                        System.out.println("Disponibles (" + disponibles.size() + "):");
                        for (LocalTime t : disponibles) {
                            System.out.println(t);
                        }
                    }

                    pausa(sc);

                    break;
                }

                case 7:
                    System.out.println("\nSaliendo...");
                    break;

                default:
                    System.out.println("\nOpción inválida. Intenta de nuevo.");
                    pausa(sc);
            }

        } while (opcion != 7);
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
        System.out.println("6) Ver horarios disponibles");
        System.out.println("7) Salir");
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

    private static void pausa(Scanner sc) {
        System.out.print("\nPresiona Enter para continuar...");
        sc.nextLine();
    }

}
