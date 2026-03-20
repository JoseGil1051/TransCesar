package Logica;

import Modelos.Pasajero;
import Modelos.TipoPasajero;
import Persistencia.PasajeroRepository;
import java.util.List;
import java.util.Optional;

public class PasajeroService {

    private PasajeroRepository repo = new PasajeroRepository();

    public boolean crear(String nombre, int cedula, TipoPasajero tipo) {
        return repo.crear(new Pasajero(nombre, cedula, tipo));
    }

    public Optional<Pasajero> buscarPorCedula(int cedula) {
        return repo.buscarPorCedula(cedula);
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

    /**
     * Calcula el precio final del ticket para un pasajero dado.
     */
    public double calcularPrecioTicket(int cedula, double precioBase) {
        return buscarPorCedula(cedula)
            .map(p -> p.calcularPrecioTicket(precioBase))
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe pasajero con cédula: " + cedula
            ));
    }
}
