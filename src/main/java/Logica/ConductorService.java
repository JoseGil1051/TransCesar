package Logica;

import Modelos.Conductor;
import Persistencia.ConductorRepository;
import java.util.List;
import java.util.Optional;

public class ConductorService {

    private ConductorRepository repo = new ConductorRepository();

    public boolean crear(String nombre, int cedula, int numeroLicencia, String categoria) {
        // La validación de categoría ya ocurre dentro del constructor de Conductor
        return repo.crear(new Conductor(nombre, cedula, numeroLicencia, categoria));
    }

    /**
     * Verifica si un conductor puede ser asignado a un vehículo.
     * Lanza excepción si no tiene licencia registrada.
     */
    public void validarAsignacion(int cedula) {
        Conductor conductor = repo.buscarPorCedula(cedula)
            .orElseThrow(() -> new IllegalArgumentException(
                "No existe un conductor con cédula: " + cedula
            ));

        if (!conductor.tieneLicenciaRegistrada()) {
            throw new IllegalStateException(
                "El conductor con cédula " + cedula +
                " no puede ser asignado: no tiene licencia registrada."
            );
        }
    }

    public List<Conductor> obtenerTodos() {
        return repo.obtenerTodos();
    }

    public Optional<Conductor> buscarPorCedula(int cedula) {
        return repo.buscarPorCedula(cedula);
    }

    public List<Conductor> buscarPorCategoria(String categoria) {
        return repo.buscarPorCategoria(categoria);
    }

    public void listarTodos() {
        repo.listarTodos();
    }

    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        return repo.actualizarNombre(cedula, nuevoNombre);
    }

    public boolean actualizarLicencia(int cedula, int nuevoNumLicencia, String nuevaCategoria) {
        return repo.actualizarLicencia(cedula, nuevoNumLicencia, nuevaCategoria);
    }

    public boolean eliminar(int cedula) {
        return repo.eliminar(cedula);
    }
}