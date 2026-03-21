package Logica;

import Modelos.Pasajero;
import Modelos.Ticket;
import Modelos.TipoPasajero;
import Modelos.Vehiculo;
import Persistencia.BusRepository;
import Persistencia.BusetaRepository;
import Persistencia.MicroBusRepository;
import Persistencia.PasajeroRepository;
import Persistencia.TicketRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TicketService {

    private static TicketService instancia;
    public static TicketService getInstancia() {
        if (instancia == null) instancia = new TicketService();
        return instancia;
    }

    private final TicketRepository   repo        = TicketRepository.getInstancia();
    private final PasajeroRepository pasajeroRepo = PasajeroRepository.getInstancia();
    private final BusRepository      busRepo      = BusRepository.getInstancia();
    private final BusetaRepository   busetaRepo   = BusetaRepository.getInstancia();
    private final MicroBusRepository microRepo    = MicroBusRepository.getInstancia();

    // ─────────────────────────────────────────────
    //  FESTIVOS
    // ─────────────────────────────────────────────

    private static final Set<String> FESTIVOS = new HashSet<>(Arrays.asList(
        "01-01", "01-06", "03-24", "04-17", "04-18", "05-01",
        "05-26", "06-16", "06-23", "06-30", "07-20", "08-07",
        "08-18", "10-13", "11-03", "11-17", "12-08", "12-25"
    ));

    private static final int    MAX_TICKETS_POR_DIA = 3;
    private static final double RECARGO_FESTIVO      = 0.20;

    // ─────────────────────────────────────────────
    //  BUSCAR VEHÍCULO
    // ─────────────────────────────────────────────

    private Vehiculo buscarVehiculo(String placa) throws Exception {
        Vehiculo v = busRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        v = busetaRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        return microRepo.buscarPorPlaca(placa);
    }

    // ─────────────────────────────────────────────
    //  VALIDACIONES INTERNAS
    // ─────────────────────────────────────────────

    private int contarTicketsDelDia(int cedulaPasajero, String fecha) throws IOException {
        return (int) repo.listar().stream()
                .filter(t -> t.getCedulaPasajero() == cedulaPasajero
                          && t.getFechaCompra().equals(fecha)
                          && t.isEstadoTicket())
                .count();
    }

    private boolean esFestivo(String fecha) {
        if (fecha == null || fecha.length() < 10) return false;
        return FESTIVOS.contains(fecha.substring(5));
    }

    // ─────────────────────────────────────────────
    //  REGISTRAR
    // ─────────────────────────────────────────────

    public void registrar(int cedulaPasajero, String placaVehiculo,
                          String fechaCompra, String origenRuta,
                          String destinoRuta) throws Exception {

        Optional<Pasajero> optPasajero = pasajeroRepo.buscarPorCedula(cedulaPasajero);
        if (optPasajero.isEmpty())
            throw new Exception("No existe un pasajero registrado con cédula: " + cedulaPasajero);
        Pasajero pasajero = optPasajero.get();

        Vehiculo vehiculo = buscarVehiculo(placaVehiculo);
        if (vehiculo == null)
            throw new Exception("No existe un vehículo registrado con placa: " + placaVehiculo);
        if (!vehiculo.isEstado())
            throw new Exception("El vehículo con placa " + placaVehiculo + " no está disponible.");
        if (vehiculo.getCapacidad() <= 0)
            throw new Exception("El vehículo con placa " + placaVehiculo + " no tiene cupos disponibles.");

        int ticketsHoy = contarTicketsDelDia(cedulaPasajero, fechaCompra);
        if (ticketsHoy >= MAX_TICKETS_POR_DIA)
            throw new Exception("El pasajero ya tiene " + ticketsHoy
                    + " ticket(s) para esa fecha. Máximo " + MAX_TICKETS_POR_DIA + ".");

        double tarifaBase = vehiculo.getTarifa();
        if (esFestivo(fechaCompra)) {
            tarifaBase = tarifaBase * (1 + RECARGO_FESTIVO);
            System.out.println("  [INFO] Fecha festiva. Recargo del 20%. Tarifa ajustada: $" + tarifaBase);
        }

        double descuento  = pasajero.getTipoPasajero().getDescuento();
        double valorFinal = pasajero.calcularPrecioTicket(tarifaBase);

        int id = generarId();
        Ticket t = new Ticket(id, cedulaPasajero, placaVehiculo,
                              fechaCompra, origenRuta, destinoRuta,
                              valorFinal, descuento);
        repo.crearTicket(t);

        System.out.println("  Tarifa base : $" + vehiculo.getTarifa()
                + (esFestivo(fechaCompra) ? " (+20% festivo)" : ""));
        System.out.println("  Descuento   : " + (int)(descuento * 100) + "%");
        System.out.println("  Valor final : $" + valorFinal);
        System.out.println("  Tickets hoy : " + (ticketsHoy + 1) + "/" + MAX_TICKETS_POR_DIA);
    }

    // ─────────────────────────────────────────────
    //  CANCELAR
    // ─────────────────────────────────────────────

    public void cancelar(int idTicket) throws Exception {
        if (repo.buscarPorId(idTicket) == null)
            throw new Exception("No existe un ticket con id: " + idTicket);
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

    // ─────────────────────────────────────────────
    //  CONSULTAS PARA REPORTES — NUEVOS
    // ─────────────────────────────────────────────

    public List<Ticket> consultarPorFecha(String fecha) throws IOException {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : repo.listar()) {
            if (t.getFechaCompra().equals(fecha)) resultado.add(t);
        }
        return resultado;
    }

    public List<Ticket> consultarPorTipoVehiculo(String tipoVehiculo) throws Exception {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : repo.listar()) {
            Vehiculo v = buscarVehiculo(t.getPlacaVehiculo());
            if (v != null && v.getClass().getSimpleName()
                    .equalsIgnoreCase(tipoVehiculo)) {
                resultado.add(t);
            }
        }
        return resultado;
    }

    public List<Ticket> consultarPorTipoPasajero(TipoPasajero tipo) throws IOException {
        double descuento = tipo.getDescuento();
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : repo.listar()) {
            if (t.getTipoDescuento() == descuento) resultado.add(t);
        }
        return resultado;
    }

    public void resumenDelDia() throws IOException {
        String hoy = LocalDate.now().toString();
        List<Ticket> lista = consultarPorFecha(hoy);
        double totalRecaudado = lista.stream()
                .mapToDouble(Ticket::getValorFinal).sum();
        System.out.println("════════════════════════════════════════════");
        System.out.println("  RESUMEN DEL DÍA: " + hoy);
        System.out.println("  Total tickets vendidos : " + lista.size());
        System.out.printf("  Total recaudado        : $%.2f%n", totalRecaudado);
        System.out.println("════════════════════════════════════════════");
    }
}