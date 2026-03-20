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
import java.util.List;
import java.util.Optional;

public class TicketService {

    private final TicketRepository repo = TicketRepository.getInstancia();
    private final PasajeroRepository pasajeroRepo = PasajeroRepository.getInstancia();
    private final BusRepository      busRepo    = BusRepository.getInstancia();
    private final BusetaRepository   busetaRepo = BusetaRepository.getInstancia();
    private final MicroBusRepository microRepo  = MicroBusRepository.getInstancia();

    /**
     * Busca el vehículo en los tres repositorios.
     * Retorna null si no lo encuentra en ninguno.
     */
    private Vehiculo buscarVehiculo(String placa) throws Exception {
        Vehiculo v = busRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        v = busetaRepo.buscarPorPlaca(placa);
        if (v != null) return v;
        return microRepo.buscarPorPlaca(placa);
    }

    /**
     * Registra un ticket aplicando validaciones y calculando el descuento
     * automáticamente según el tipo de pasajero (polimorfismo).
     */
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
            throw new Exception("El vehículo con placa " + placaVehiculo + " no tiene cupos disponibles.");
        }

        // 5. Calcular descuento y valor final usando polimorfismo
        double descuento   = pasajero.getTipoPasajero().getDescuento();
        double valorFinal  = pasajero.calcularPrecioTicket(vehiculo.getTarifa());

        // 6. Generar y guardar el ticket
        int id = generarId();
        Ticket t = new Ticket(id, cedulaPasajero, placaVehiculo,
                              fechaCompra, origenRuta, destinoRuta,
                              valorFinal, descuento);
        repo.crearTicket(t);
    }

    public void cancelar(int idTicket) throws Exception {
        if (repo.buscarPorId(idTicket) == null) {
            throw new Exception("No existe un ticket con id: " + idTicket);
        }
        repo.cancelar(idTicket);
    }

    public int generarId() throws IOException {
        List<Ticket> lista = repo.listar();
        if (lista.isEmpty()) return 1;
        // Corregido: retorna el último ID + 1
        return lista.get(lista.size() - 1).getIdTicket() + 1;
    }
}