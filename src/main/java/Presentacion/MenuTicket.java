package Presentacion;

import Logica.TicketService;
import java.util.Scanner;

public class MenuTicket {

    private final Scanner scanner = new Scanner(System.in);
    private final TicketService service = new TicketService();

    public void MenuTicket() {
        int opc;
        do {
            System.out.println("|========= MENU TICKETS =========|");
            System.out.println("| 1 | COMPRAR TICKETS            |");
            System.out.println("| 2 | CANCELAR TICKETS           |");
            System.out.println("| 3 | SALIR                      |");
            System.out.println("|================================|");
            System.out.print("Ingrese una opcion (1 - 3): ");
            opc = scanner.nextInt();
            switch (opc) {
                case 1 -> ComprarTicket();
                case 2 -> CancelarTicket();
                case 3 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 3);
    }

    public void ComprarTicket() {
        try {
            System.out.print("Ingrese cedula del pasajero: ");
            int cedula = scanner.nextInt();
            System.out.print("Ingrese placa del vehiculo: ");
            String placa = scanner.next();
            System.out.print("Ingrese fecha de compra (YYYY-MM-DD): ");
            String fecha = scanner.next();
            System.out.print("Ingrese origen: ");
            String origen = scanner.next();
            System.out.print("Ingrese destino: ");
            String destino = scanner.next();

            // El valor y descuento se calculan automáticamente en el service
            service.registrar(cedula, placa, fecha, origen, destino);

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       TICKET REGISTRADO              ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.println("  Ticket comprado exitosamente.");
            System.out.println("  El valor fue calculado según la tarifa");
            System.out.println("  del vehículo y el tipo de pasajero.");

        } catch (Exception e) {
            System.out.println("Error al registrar ticket: " + e.getMessage());
        }
    }

    public void CancelarTicket() {
        try {
            System.out.print("Ingrese id del ticket a cancelar: ");
            int id = scanner.nextInt();
            service.cancelar(id);
            System.out.println("Ticket cancelado correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}