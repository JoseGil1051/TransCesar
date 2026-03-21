package Presentacion;

import Logica.ReportesService;
import Logica.TicketService;
import Modelos.Ticket;
import Modelos.TipoPasajero;
import java.util.List;
import java.util.Scanner;

public class MenuReportes {

    Scanner scanner = new Scanner(System.in);
    ReportesService reportesService = ReportesService.getInstancia();
    TicketService ticketService = TicketService.getInstancia();

    public void MenuReportes() {
        int opc;

        do {
            System.out.println("|========= MENU REPORTES =========|");
            System.out.println("| 1 | REPORTE VEHICULOS           |");
            System.out.println("| 2 | REPORTE PERSONAS            |");
            System.out.println("| 3 | REPORTE TICKETS             |");
            System.out.println("| 4 | REPORTE GENERAL             |");
            System.out.println("| 5 | TICKETS POR FECHA           |");
            System.out.println("| 6 | TICKETS POR TIPO VEHICULO   |");
            System.out.println("| 7 | TICKETS POR TIPO PASAJERO   |");
            System.out.println("| 8 | RESUMEN DEL DIA             |");
            System.out.println("| 9 | SALIR                       |");
            System.out.println("|=================================|");
            System.out.print("Ingrese una opcion (1 - 9): ");
            opc = scanner.nextInt();

            switch (opc) {
                case 1 -> reporteVehiculos();
                case 2 -> reportePersonas();
                case 3 -> reporteTicket();
                case 4 -> reporteGeneral();
                case 5 -> ticketsPorFecha();
                case 6 -> ticketsPorTipoVehiculo();
                case 7 -> ticketsPorTipoPasajero();
                case 8 -> resumenDelDia();
                case 9 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 9);
    }

    public void reporteVehiculos() { reportesService.reporteVehiculos(); }
    public void reportePersonas()  { reportesService.reportePersonas(); }
    public void reporteTicket()    { reportesService.reporteTickets(); }
    public void reporteGeneral()   { reportesService.reporteGeneral(); }

    // ─────────────────────────────────────────────
    //  FILTROS NUEVOS
    // ─────────────────────────────────────────────

    public void ticketsPorFecha() {
        try {
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            String fecha = scanner.next();
            List<Ticket> lista = ticketService.consultarPorFecha(fecha);
            if (lista.isEmpty()) {
                System.out.println("No hay tickets para la fecha: " + fecha);
                return;
            }
            System.out.println("Tickets vendidos el " + fecha + ":");
            imprimirTickets(lista);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void ticketsPorTipoVehiculo() {
        try {
            System.out.println("Tipo de vehículo:");
            System.out.println("  1. Bus");
            System.out.println("  2. Buseta");
            System.out.println("  3. MicroBus");
            System.out.print("Seleccione (1-3): ");
            int opc = scanner.nextInt();

            String tipo;
            switch (opc) {
                case 1 -> tipo = "Bus";
                case 2 -> tipo = "Buseta";
                case 3 -> tipo = "MicroBus";
                default -> { System.out.println("Opción inválida."); return; }
            }

            List<Ticket> lista = ticketService.consultarPorTipoVehiculo(tipo);
            if (lista.isEmpty()) {
                System.out.println("No hay tickets para tipo de vehículo: " + tipo);
                return;
            }
            System.out.println("Tickets de vehículo tipo " + tipo + ":");
            imprimirTickets(lista);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void ticketsPorTipoPasajero() {
        try {
            System.out.println("Tipo de pasajero:");
            System.out.println("  1. Regular");
            System.out.println("  2. Estudiante");
            System.out.println("  3. Adulto Mayor");
            System.out.print("Seleccione (1-3): ");
            int opc = scanner.nextInt();

            TipoPasajero tipo;
            switch (opc) {
                case 1 -> tipo = TipoPasajero.REGULAR;
                case 2 -> tipo = TipoPasajero.ESTUDIANTE;
                case 3 -> tipo = TipoPasajero.ADULTO_MAYOR;
                default -> { System.out.println("Opción inválida."); return; }
            }

            List<Ticket> lista = ticketService.consultarPorTipoPasajero(tipo);
            if (lista.isEmpty()) {
                System.out.println("No hay tickets para tipo de pasajero: " + tipo);
                return;
            }
            System.out.println("Tickets de pasajero tipo " + tipo + ":");
            imprimirTickets(lista);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void resumenDelDia() {
        try {
            ticketService.resumenDelDia();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  Mostrar Info Tickets
    // ─────────────────────────────────────────────

    private void imprimirTickets(List<Ticket> lista) {
        double total = 0;
        for (Ticket t : lista) {
            System.out.println("--------------------------------------------");
            System.out.println("  ID Ticket : " + t.getIdTicket());
            System.out.println("  Cédula    : " + t.getCedulaPasajero());
            System.out.println("  Placa     : " + t.getPlacaVehiculo());
            System.out.println("  Fecha     : " + t.getFechaCompra());
            System.out.println("  Origen    : " + t.getOrigenRuta());
            System.out.println("  Destino   : " + t.getDestinoRuta());
            System.out.println("  Descuento : " + t.getTipoDescuento() + "%");
            System.out.printf("  Valor     : $%.2f%n", t.getValorFinal());
            total += t.getValorFinal();
        }
        System.out.println("════════════════════════════════════════════");
        System.out.println("  Total tickets   : " + lista.size());
        System.out.printf("  Total recaudado : $%.2f%n", total);
        System.out.println("════════════════════════════════════════════");
    }
}