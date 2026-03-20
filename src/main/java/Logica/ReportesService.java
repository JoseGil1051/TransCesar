package Logica;

import Modelos.Conductor;
import Modelos.Pasajero;
import Modelos.Persona;
import Modelos.Ticket;
import Modelos.TipoPasajero;
import Modelos.Vehiculo;
import Persistencia.PersonaRepository;
import Persistencia.TicketRepository;
import Persistencia.VehiculoRepository;
import java.io.IOException;
import java.util.List;

public class ReportesService {

    private PersonaRepository personaRepo = PersonaRepository.getInstancia();
    private VehiculoRepository vehiculoRepo = VehiculoRepository.getInstancia();
    private TicketRepository ticketRepo = TicketRepository.getInstancia();

    // ─────────────────────────────────────────────
    //  REPORTE VEHÍCULOS
    // ─────────────────────────────────────────────

    public void reporteVehiculos() {
        try {
            List<Vehiculo> lista = vehiculoRepo.listar();
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║         REPORTE DE VEHÍCULOS             ║");
            System.out.println("╚══════════════════════════════════════════╝");

            if (lista.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
                return;
            }

            int activos   = 0;
            int inactivos = 0;

            for (Vehiculo v : lista) {
                System.out.println("--------------------------------------------");
                System.out.println("  Placa      : " + v.getPlaca());
                System.out.println("  Ruta       : " + v.getRuta());
                System.out.println("  Estado     : " + (v.isEstado() ? "Activo" : "Inactivo"));
                System.out.println("  Capacidad  : " + v.getCapacidad() + " pasajeros");
                System.out.println("  Tarifa     : $" + v.getTarifa());
                if (v.isEstado()) activos++; else inactivos++;
            }

            System.out.println("════════════════════════════════════════════");
            System.out.println("  Total vehículos : " + lista.size());
            System.out.println("  Activos         : " + activos);
            System.out.println("  Inactivos       : " + inactivos);
            System.out.println("════════════════════════════════════════════\n");

        } catch (IOException e) {
            System.out.println("Error al generar reporte de vehículos: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  REPORTE PERSONAS
    // ─────────────────────────────────────────────

    public void reportePersonas() {
        List<Persona> lista = personaRepo.obtenerTodos();

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          REPORTE DE PERSONAS             ║");
        System.out.println("╚══════════════════════════════════════════╝");

        if (lista.isEmpty()) {
            System.out.println("No hay personas registradas.");
            return;
        }

        int totalConductores = 0;
        int totalPasajeros   = 0;
        int regulares        = 0;
        int estudiantes      = 0;
        int adultosMayores   = 0;

        System.out.println("\n  --- CONDUCTORES ---");
        for (Persona p : lista) {
            if (p instanceof Conductor) {
                Conductor c = (Conductor) p;
                System.out.println("--------------------------------------------");
                System.out.println("  Nombre    : " + c.getNombre());
                System.out.println("  Cédula    : " + c.getCedula());
                System.out.println("  Licencia  : " + c.getNumerodelicencia());
                System.out.println("  Categoría : " + c.getCategorialicencia());
                totalConductores++;
            }
        }

        System.out.println("\n  --- PASAJEROS ---");
        for (Persona p : lista) {
            if (p instanceof Pasajero) {
                Pasajero pa = (Pasajero) p;
                System.out.println("--------------------------------------------");
                System.out.println("  Nombre    : " + pa.getNombre());
                System.out.println("  Cédula    : " + pa.getCedula());
                System.out.println("  Tipo      : " + pa.getTipoPasajero());
                System.out.println("  Descuento : " + (int)(pa.getTipoPasajero().getDescuento() * 100) + "%");
                totalPasajeros++;
                if (pa.getTipoPasajero() == TipoPasajero.REGULAR)       regulares++;
                else if (pa.getTipoPasajero() == TipoPasajero.ESTUDIANTE)    estudiantes++;
                else if (pa.getTipoPasajero() == TipoPasajero.ADULTO_MAYOR)  adultosMayores++;
            }
        }

        System.out.println("════════════════════════════════════════════");
        System.out.println("  Total personas   : " + lista.size());
        System.out.println("  Conductores      : " + totalConductores);
        System.out.println("  Pasajeros        : " + totalPasajeros);
        System.out.println("    - Regular      : " + regulares);
        System.out.println("    - Estudiante   : " + estudiantes);
        System.out.println("    - Adulto Mayor : " + adultosMayores);
        System.out.println("════════════════════════════════════════════\n");
    }

    // ─────────────────────────────────────────────
    //  REPORTE TICKETS
    // ─────────────────────────────────────────────

    public void reporteTickets() {
        try {
            List<Ticket> lista = ticketRepo.listar();

            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║          REPORTE DE TICKETS              ║");
            System.out.println("╚══════════════════════════════════════════╝");

            if (lista.isEmpty()) {
                System.out.println("No hay tickets registrados.");
                return;
            }

            double totalRecaudado = 0;
            double totalDescuentos = 0;

            for (Ticket t : lista) {
                System.out.println("--------------------------------------------");
                System.out.println("  ID Ticket      : " + t.getIdTicket());
                System.out.println("  Cédula Pasajero: " + t.getCedulaPasajero());
                System.out.println("  Placa Vehículo : " + t.getPlacaVehiculo());
                System.out.println("  Fecha Compra   : " + t.getFechaCompra());
                System.out.println("  Origen         : " + t.getOrigenRuta());
                System.out.println("  Destino        : " + t.getDestinoRuta());
                System.out.println("  Descuento      : " + t.getTipoDescuento() + "%");
                System.out.println("  Valor Final    : $" + t.getValorFinal());
                totalRecaudado  += t.getValorFinal();
                totalDescuentos += t.getTipoDescuento();
            }

            System.out.println("════════════════════════════════════════════");
            System.out.println("  Total tickets     : " + lista.size());
            System.out.printf("  Total recaudado   : $%.2f%n", totalRecaudado);
            System.out.printf("  Descuento promedio: %.1f%%%n",
                    lista.isEmpty() ? 0 : totalDescuentos / lista.size());
            System.out.println("════════════════════════════════════════════\n");

        } catch (IOException e) {
            System.out.println("Error al generar reporte de tickets: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  REPORTE GENERAL
    // ─────────────────────────────────────────────

    public void reporteGeneral() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║           REPORTE GENERAL                ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // — Personas —
        List<Persona> personas = personaRepo.obtenerTodos();
        long conductores = personas.stream().filter(p -> p instanceof Conductor).count();
        long pasajeros   = personas.stream().filter(p -> p instanceof Pasajero).count();

        System.out.println("\n  [PERSONAS]");
        System.out.println("  Total      : " + personas.size());
        System.out.println("  Conductores: " + conductores);
        System.out.println("  Pasajeros  : " + pasajeros);

        // — Vehículos —
        try {
            List<Vehiculo> vehiculos = vehiculoRepo.listar();
            long activos   = vehiculos.stream().filter(Vehiculo::isEstado).count();
            long inactivos = vehiculos.size() - activos;

            System.out.println("\n  [VEHÍCULOS]");
            System.out.println("  Total    : " + vehiculos.size());
            System.out.println("  Activos  : " + activos);
            System.out.println("  Inactivos: " + inactivos);
        } catch (IOException e) {
            System.out.println("  Error al leer vehículos: " + e.getMessage());
        }

        // — Tickets —
        try {
            List<Ticket> tickets = ticketRepo.listar();
            double totalRecaudado = tickets.stream()
                    .mapToDouble(Ticket::getValorFinal)
                    .sum();

            System.out.println("\n  [TICKETS]");
            System.out.println("  Total emitidos  : " + tickets.size());
            System.out.printf("  Total recaudado : $%.2f%n", totalRecaudado);
        } catch (IOException e) {
            System.out.println("  Error al leer tickets: " + e.getMessage());
        }

        System.out.println("\n════════════════════════════════════════════\n");
    }
}