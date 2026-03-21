package Logica;

import Modelos.Pasajero;
import Modelos.Reserva;
import Modelos.Reserva.estadoReserva;
import Modelos.Vehiculo;
import Persistencia.BusRepository;
import Persistencia.BusetaRepository;
import Persistencia.MicroBusRepository;
import Persistencia.PasajeroRepository;
import Persistencia.ReservaRepository;
import Persistencia.TicketRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ReservaService {

    private static ReservaService instancia;
    public static ReservaService getInstancia() {
        if (instancia == null) instancia = new ReservaService();
        return instancia;
    }

    private final ReservaRepository  repo          = ReservaRepository.getInstancia();
    private final PasajeroRepository pasajeroRepo  = PasajeroRepository.getInstancia();
    private final BusRepository      busRepo       = BusRepository.getInstancia();
    private final BusetaRepository   busetaRepo    = BusetaRepository.getInstancia();
    private final MicroBusRepository microRepo     = MicroBusRepository.getInstancia();
    private final TicketService      ticketService = TicketService.getInstancia();
    private final TicketRepository   ticketRepo    = TicketRepository.getInstancia();

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // ─────────────────────────────────────────────
    //  BUSCAR VEHICULO
    // ─────────────────────────────────────────────
    private Vehiculo buscarVehiculo(String placa) throws Exception {
        Vehiculo v = busRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        v = busetaRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        return microRepo.buscarPorPlaca(placa);
    }

    // ─────────────────────────────────────────────
    //  CREAR RESERVA
    // ─────────────────────────────────────────────
    public void crear(int cedulaPasajero, String placaVehiculo, String fechaViaje) throws Exception {

        // Validar que el pasajero exista
        Optional<Pasajero> optPasajero = pasajeroRepo.buscarPorCedula(cedulaPasajero);
        if (optPasajero.isEmpty())
            throw new Exception("No existe un pasajero con cedula: " + cedulaPasajero);

        // Validar que el vehiculo exista y este disponible
        Vehiculo vehiculo = buscarVehiculo(placaVehiculo);
        if (vehiculo == null)
            throw new Exception("No existe un vehiculo con placa: " + placaVehiculo);
        if (!vehiculo.isEstado())
            throw new Exception("El vehiculo no esta disponible.");

        // Contar reservas activas para ese vehiculo y fecha
        long reservasActivas = repo.listarActivas().stream()
                .filter(r -> r.getPlacaVehiculo().equalsIgnoreCase(placaVehiculo)
                          && r.getFechaViaje().equals(fechaViaje))
                .count();

        // Contar tickets vendidos para ese vehiculo y fecha
        long ticketsVendidos = ticketRepo.listar().stream()
                .filter(t -> t.getPlacaVehiculo().equalsIgnoreCase(placaVehiculo)
                          && t.getFechaCompra().equals(fechaViaje)
                          && t.isEstadoTicket())
                .count();

        // Validar capacidad: reservas activas + tickets vendidos no puede superar capacidad
        if ((reservasActivas + ticketsVendidos) >= vehiculo.getCapacidad())
            throw new Exception("El vehiculo no tiene cupos disponibles para esa fecha. "
                    + "Capacidad: " + vehiculo.getCapacidad()
                    + " | Reservas: " + reservasActivas
                    + " | Tickets: " + ticketsVendidos);

        // Validar que el pasajero no tenga reserva activa para ese vehiculo y fecha
        boolean yaReservado = repo.listarActivas().stream()
                .anyMatch(r -> r.getCedulaPasajero() == cedulaPasajero
                            && r.getPlacaVehiculo().equalsIgnoreCase(placaVehiculo)
                            && r.getFechaViaje().equals(fechaViaje));

        if (yaReservado)
            throw new Exception("El pasajero ya tiene una reserva activa para ese vehiculo en esa fecha.");

        // Crear la reserva
        int codigo = generarCodigo();
        String fechaCreacion = LocalDateTime.now().format(FORMATO);

        Reserva r = new Reserva(codigo, cedulaPasajero, placaVehiculo,
                                fechaCreacion, fechaViaje, estadoReserva.ACTIVA);
        repo.guardar(r);
        System.out.println("Reserva creada con codigo: " + codigo);
    }

    // ─────────────────────────────────────────────
    //  CANCELAR RESERVA
    // ─────────────────────────────────────────────
    public void cancelar(int codigo) throws Exception {
        Reserva r = repo.buscarPorCodigo(codigo);
        if (r == null)
            throw new Exception("No existe una reserva con codigo: " + codigo);
        if (r.getEstadoReserva() != estadoReserva.ACTIVA)
            throw new Exception("La reserva no esta activa.");

        r.setEstadoReserva(estadoReserva.CANCELADA);
        repo.actualizar(r);
        System.out.println("Reserva cancelada exitosamente.");
    }

    // ─────────────────────────────────────────────
    //  CONVERTIR A TICKET
    // ─────────────────────────────────────────────
    public void convertirATicket(int codigo, String origenRuta, String destinoRuta) throws Exception {
        Reserva r = repo.buscarPorCodigo(codigo);
        if (r == null)
            throw new Exception("No existe una reserva con codigo: " + codigo);
        if (r.getEstadoReserva() != estadoReserva.ACTIVA)
            throw new Exception("La reserva no esta activa.");

        // Aplica todas las reglas normales de venta: descuento por tipo y recargo festivo
        ticketService.registrar(
            r.getCedulaPasajero(),
            r.getPlacaVehiculo(),
            r.getFechaViaje(),
            origenRuta,
            destinoRuta
        );

        r.setEstadoReserva(estadoReserva.CONVERTIDA);
        repo.actualizar(r);
        System.out.println("Reserva convertida a ticket exitosamente.");
    }

    // ─────────────────────────────────────────────
    //  LISTAR ACTIVAS
    // ─────────────────────────────────────────────
    public List<Reserva> listarActivas() throws Exception {
        return repo.listarActivas();
    }

    // ─────────────────────────────────────────────
    //  HISTORIAL POR PASAJERO
    // ─────────────────────────────────────────────
    public List<Reserva> historialPorPasajero(int cedula) throws Exception {
        List<Reserva> historial = repo.listarPorPasajero(cedula);
        if (historial.isEmpty())
            throw new Exception("No hay reservas para el pasajero con cedula: " + cedula);
        return historial;
    }

    // ─────────────────────────────────────────────
    //  VERIFICAR VENCIDAS
    // ─────────────────────────────────────────────
    public int verificarVencidas() throws Exception {
        List<Reserva> activas = repo.listarActivas();
        LocalDateTime ahora = LocalDateTime.now();
        int canceladas = 0;

        for (Reserva r : activas) {
            LocalDateTime creacion = LocalDateTime.parse(r.getFechaCreacion(), FORMATO);
            if (creacion.plusHours(24).isBefore(ahora)) {
                r.setEstadoReserva(estadoReserva.CANCELADA);
                repo.actualizar(r);
                canceladas++;
            }
        }
        return canceladas;
    }

    // ─────────────────────────────────────────────
    //  GENERAR CODIGO
    // ─────────────────────────────────────────────
    private int generarCodigo() throws Exception {
        List<Reserva> lista = repo.listar();
        if (lista.isEmpty()) return 1;
        return lista.get(lista.size() - 1).getCodigoReserva() + 1;
    }
}