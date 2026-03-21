package Logica;

import Modelos.Pasajero;
import Modelos.Ticket;
import Modelos.Vehiculo;
import Persistencia.BusRepository;
import Persistencia.BusetaRepository;
import Persistencia.MicroBusRepository;
import Persistencia.PasajeroRepository;
import Persistencia.TicketRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TicketService {

    private final TicketRepository repo = TicketRepository.getInstancia();
    private final PasajeroRepository pasajeroRepo = PasajeroRepository.getInstancia();
    private final BusRepository busRepo = BusRepository.getInstancia();
    private final BusetaRepository busetaRepo = BusetaRepository.getInstancia();
    private final MicroBusRepository microRepo = MicroBusRepository.getInstancia();

    // ─────────────────────────────────────────────
    //  FESTIVOS (formato MM-DD)
    // ─────────────────────────────────────────────

    private static final Set<String> FESTIVOS = new HashSet<>(Arrays.asList(
        "01-01", // Año Nuevo
        "01-06", // Reyes Magos
        "03-24", // Día de San José (2025 - festivo movible, ajustar por año)
        "04-17", // Jueves Santo (2025)
        "04-18", // Viernes Santo (2025)
        "05-01", // Día del Trabajo
        "05-26", // Ascensión del Señor (2025 - movible)
        "06-16", // Corpus Christi (2025 - movible)
        "06-23", // Sagrado Corazón (2025 - movible)
        "06-30", // San Pedro y San Pablo (movible)
        "07-04", // -
        "07-20", // Día de la Independencia
        "08-07", // Batalla de Boyacá
        "08-18", // Asunción de la Virgen (2025 - movible)
        "10-13", // Día de la Raza (2025 - movible)
        "11-03", // Todos los Santos (2025 - movible)
        "11-17", // Independencia de Cartagena (2025 - movible)
        "12-08", // Inmaculada Concepción
        "12-25"  // Navidad
    ));

    private static final int  MAX_TICKETS_POR_DIA = 3;
    private static final double RECARGO_FESTIVO   = 0.20;

    // ─────────────────────────────────────────────
    //  BUSCAR VEHÍCULO EN LOS TRES REPOSITORIOS
    // ─────────────────────────────────────────────

    private Vehiculo buscarVehiculo(String placa) throws Exception {
        Vehiculo v = busRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        v = busetaRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        return microRepo.buscarPorPlaca(placa);
    }

    // ─────────────────────────────────────────────
    //  VALIDAR LÍMITE DE TICKETS POR DÍA
    // ─────────────────────────────────────────────

    /**
     * Cuenta cuántos tickets tiene el pasajero en la fecha indicada.
     * La fecha debe tener formato YYYY-MM-DD.
     */
    private int contarTicketsDelDia(int cedulaPasajero, String fecha) throws IOException {
        List<Ticket> todos = repo.listar();
        return (int) todos.stream()
                .filter(t -> t.getCedulaPasajero() == cedulaPasajero
                          && t.getFechaCompra().equals(fecha)
                          && t.isEstadoTicket()) // solo tickets activos
                .count();
    }

    // ─────────────────────────────────────────────
    //  VERIFICAR SI LA FECHA ES FESTIVO
    // ─────────────────────────────────────────────

    /**
     * Recibe fecha en formato YYYY-MM-DD y verifica si MM-DD está en la lista.
     */
    private boolean esFestivo(String fecha) {
        if (fecha == null || fecha.length() < 10) return false;
        String mesDia = fecha.substring(5); // extrae MM-DD
        return FESTIVOS.contains(mesDia);
    }

    // ─────────────────────────────────────────────
    //  REGISTRAR TICKET
    // ─────────────────────────────────────────────

    public void registrar(int cedulaPasajero, String placaVehiculo,
                          String fechaCompra, String origenRuta,
                          String destinoRuta) throws Exception {

        // 1. Validar que el pasajero esté registrado
        Optional<Pasajero> optPasajero = pasajeroRepo.buscarPorCedula(cedulaPasajero);
        if (optPasajero.isEmpty()) {
            throw new Exception("No existe un pasajero registrado con cédula: " + cedulaPasajero);
        }
        Pasajero pasajero = optPasajero.get();

        // 2. Validar que el vehículo esté registrado
        Vehiculo vehiculo = buscarVehiculo(placaVehiculo);
        if (vehiculo == null) {
            throw new Exception("No existe un vehículo registrado con placa: " + placaVehiculo);
        }

        // 3. Validar que el vehículo esté disponible
        if (!vehiculo.isEstado()) {
            throw new Exception("El vehículo con placa " + placaVehiculo + " no está disponible.");
        }

        // 4. Validar cupos disponibles
        if (vehiculo.getCapacidad() <= 0) {
            throw new Exception("El vehículo con placa " + placaVehiculo
                    + " no tiene cupos disponibles.");
        }

        // 5. Validar límite de tickets por día
        int ticketsHoy = contarTicketsDelDia(cedulaPasajero, fechaCompra);
        if (ticketsHoy >= MAX_TICKETS_POR_DIA) {
            throw new Exception(
                "El pasajero con cédula " + cedulaPasajero
                + " ya tiene " + ticketsHoy + " ticket(s) para la fecha "
                + fechaCompra + ". No se permiten más de "
                + MAX_TICKETS_POR_DIA + " tickets por día.");
        }

        // 6. Calcular tarifa base, aplicar recargo festivo si aplica
        double tarifaBase = vehiculo.getTarifa();
        if (esFestivo(fechaCompra)) {
            tarifaBase = tarifaBase * (1 + RECARGO_FESTIVO);
            System.out.println("  [INFO] Fecha festiva detectada. "
                    + "Se aplica recargo del 20%. Tarifa base ajustada: $" + tarifaBase);
        }

        // 7. Calcular descuento según tipo de pasajero (polimorfismo)
        double descuento  = pasajero.getTipoPasajero().getDescuento();
        double valorFinal = pasajero.calcularPrecioTicket(tarifaBase);

        // 8. Generar y guardar el ticket
        int id = generarId();
        Ticket t = new Ticket(id, cedulaPasajero, placaVehiculo,
                              fechaCompra, origenRuta, destinoRuta,
                              valorFinal, descuento);
        repo.crearTicket(t);

        // 9. Informar resumen al usuario
        System.out.println("  Tarifa base    : $" + vehiculo.getTarifa()
                + (esFestivo(fechaCompra) ? " (+20% festivo)" : ""));
        System.out.println("  Descuento      : " + (int)(descuento * 100) + "%");
        System.out.println("  Valor final    : $" + valorFinal);
        System.out.println("  Tickets hoy    : " + (ticketsHoy + 1)
                + "/" + MAX_TICKETS_POR_DIA);
    }

    // ─────────────────────────────────────────────
    //  CANCELAR
    // ─────────────────────────────────────────────

    public void cancelar(int idTicket) throws Exception {
        if (repo.buscarPorId(idTicket) == null) {
            throw new Exception("No existe un ticket con id: " + idTicket);
        }
        repo.cancelar(idTicket);
    }

    // ─────────────────────────────────────────────
    //  GENERAR ID
    // ─────────────────────────────────────────────

    public int generarId() throws IOException {
        List<Ticket> lista = repo.listar();
        if (lista.isEmpty()) return 1;
        return lista.get(lista.size() - 1).getIdTicket() + 1;
    }
}
