package Presentacion;

import Logica.ReservaService;
import Modelos.Reserva;
import java.util.List;
import java.util.Scanner;

public class MenuReserva {

    Scanner scanner = new Scanner(System.in);
    ReservaService reservaService = ReservaService.getInstancia();

    public void MenuReserva() {
        int opc;

        do {
            System.out.println("|============ MENU RESERVA =============|");
            System.out.println("| 1 | REGISTRAR RESERVA                 |");
            System.out.println("| 2 | CANCELAR RESERVA                  |");
            System.out.println("| 3 | CONVERTIR RESERVA A TICKET        |");
            System.out.println("| 4 | LISTAR RESERVAS ACTIVAS           |");
            System.out.println("| 5 | HISTORIAL DE RESERVAS POR PASAJERO|");
            System.out.println("| 6 | SALIR                             |");
            System.out.println("|=======================================|");
            System.out.print("Ingrese una opcion (1 - 6): ");
            opc = scanner.nextInt();

            switch (opc) {
                case 1 -> RegistrarReserva();
                case 2 -> CancelarReserva();
                case 3 -> ConvertirReservaTicket();
                case 4 -> ListarReservasActivas();
                case 5 -> HistorialReservasUsuario();
                case 6 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 6);
    }

    // ─────────────────────────────────────────────
    //  REGISTRAR
    // ─────────────────────────────────────────────

    public void RegistrarReserva() {
        try {
            System.out.println("\n--- REGISTRAR RESERVA ---");

            System.out.print("Cedula del pasajero: ");
            int cedula = scanner.nextInt();

            System.out.print("Placa del vehiculo: ");
            String placa = scanner.next();

            System.out.print("Fecha del viaje (YYYY-MM-DD): ");
            String fechaViaje = scanner.next();

            reservaService.crear(cedula, placa, fechaViaje);

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║       RESERVA REGISTRADA             ║");
            System.out.println("╚══════════════════════════════════════╝");
        } catch (Exception e) {
            System.out.println("Error al registrar reserva: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  CANCELAR
    // ─────────────────────────────────────────────

    public void CancelarReserva() {
        try {
            System.out.println("\n--- CANCELAR RESERVA ---");

            System.out.print("Codigo de la reserva a cancelar: ");
            int codigo = scanner.nextInt();

            System.out.print("¿Está seguro? (1 = Si / 2 = No): ");
            int confirmar = scanner.nextInt();

            if (confirmar == 1) {
                reservaService.cancelar(codigo);
                System.out.println("Reserva cancelada exitosamente.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Error al cancelar reserva: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  CONVERTIR A TICKET
    // ─────────────────────────────────────────────

    public void ConvertirReservaTicket() {
        try {
            System.out.println("\n--- CONVERTIR RESERVA A TICKET ---");

            System.out.print("Codigo de la reserva: ");
            int codigo = scanner.nextInt();

            System.out.print("Origen del viaje: ");
            String origen = scanner.next();

            System.out.print("Destino del viaje: ");
            String destino = scanner.next();

            reservaService.convertirATicket(codigo, origen, destino);

            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║     RESERVA CONVERTIDA A TICKET      ║");
            System.out.println("╚══════════════════════════════════════╝");
        } catch (Exception e) {
            System.out.println("Error al convertir reserva: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  LISTAR ACTIVAS
    // ─────────────────────────────────────────────

    public void ListarReservasActivas() {
        try {
            System.out.println("\n--- RESERVAS ACTIVAS ---");
            List<Reserva> lista = reservaService.listarActivas();

            if (lista.isEmpty()) {
                System.out.println("No hay reservas activas.");
                return;
            }

            System.out.println("Total reservas activas: " + lista.size());
            for (Reserva r : lista) {
                imprimirReserva(r);
            }
        } catch (Exception e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  HISTORIAL POR USUARIO
    // ─────────────────────────────────────────────

    public void HistorialReservasUsuario() {
        try {
            System.out.println("\n--- HISTORIAL DE RESERVAS POR USUARIO ---");

            System.out.print("Cedula del pasajero: ");
            int cedula = scanner.nextInt();

            List<Reserva> historial = reservaService.historialPorPasajero(cedula);

            System.out.println("Historial de reservas para cedula " + cedula + ":");
            System.out.println("Total: " + historial.size() + " reserva(s)");

            for (Reserva r : historial) {
                imprimirReserva(r);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener historial: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  IMPRIMIR RESERVA
    // ─────────────────────────────────────────────

    private void imprimirReserva(Reserva r) {
        System.out.println("--------------------------------------------");
        System.out.println("  Codigo    : " + r.getCodigoReserva());
        System.out.println("  Cedula    : " + r.getCedulaPasajero());
        System.out.println("  Placa     : " + r.getPlacaVehiculo());
        System.out.println("  Creacion  : " + r.getFechaCreacion());
        System.out.println("  Viaje     : " + r.getFechaViaje());
        System.out.println("  Estado    : " + r.getEstadoReserva());
        System.out.println("--------------------------------------------");
    }
}