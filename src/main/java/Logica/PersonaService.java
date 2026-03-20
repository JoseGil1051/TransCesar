package Logica;

import Modelos.Conductor;
import Modelos.Pasajero;
import Modelos.Persona;
import Modelos.TipoPasajero;
import Persistencia.PersonaRepository;
import java.util.List;
import java.util.Optional;

public class PersonaService {

    private PersonaRepository repo = PersonaRepository.getInstancia();

    public boolean crearConductor(String nombre, int cedula, int numeroLicencia, String categoria) {
        return repo.crear(new Conductor(nombre, cedula, numeroLicencia, categoria));
    }

    public boolean crearPasajero(String nombre, int cedula, TipoPasajero tipo) { // ← CAMBIADO
        return repo.crear(new Pasajero(nombre, cedula, tipo));
    }

    public Optional<Persona> buscarPorCedula(int cedula) {
        return repo.buscarPorCedula(cedula);
    }

    public List<Persona> buscarPorNombre(String nombre) {
        return repo.buscarPorNombre(nombre);
    }

    public void listarTodos() {
        repo.listarTodos();
    }

    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        return repo.actualizarNombre(cedula, nuevoNombre);
    }

    public boolean actualizarConductor(int cedula, int nuevoNumLicencia, String nuevaCategoria) {
        return repo.actualizarConductor(cedula, nuevoNumLicencia, nuevaCategoria);
    }

    public boolean actualizarPasajero(int cedula, TipoPasajero nuevoTipo) { // ← CAMBIADO
        return repo.actualizarPasajero(cedula, nuevoTipo);
    }

    public boolean eliminar(int cedula) {
        return repo.eliminar(cedula);
    }
}