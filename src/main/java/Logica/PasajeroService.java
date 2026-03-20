package Logica;

import Modelos.Pasajero;
import Modelos.TipoPasajero;
import Persistencia.PasajeroRepository;
import java.util.List;
import java.util.Optional;

public class PasajeroService {

    // ── usa la instancia compartida ──
    private PasajeroRepository repo = PasajeroRepository.getInstancia();

    public boolean crear(String nombre, int cedula, TipoPasajero tipo) {
        return repo.crear(new Pasajero(nombre, cedula, tipo));
    }
    public Optional<Pasajero> buscarPorCedula(int cedula) {
        return repo.buscarPorCedula(cedula);
    }
    public List<Pasajero> buscarPorTipo(TipoPasajero tipo) {
        return repo.buscarPorTipo(tipo);
    }
    public void listarTodos() {
        repo.listarTodos();
    }
    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        return repo.actualizarNombre(cedula, nuevoNombre);
    }
    public boolean actualizarTipo(int cedula, TipoPasajero nuevoTipo) {
        return repo.actualizarTipo(cedula, nuevoTipo);
    }
    public boolean eliminar(int cedula) {
        return repo.eliminar(cedula);
    }
    public double calcularPrecioTicket(int cedula, double precioBase) {
        return buscarPorCedula(cedula)
            .map(p -> p.calcularPrecioTicket(precioBase))
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe pasajero con cédula: " + cedula));
    }
}