package Logica;

import Modelos.Conductor;
import Modelos.Pasajero;
import Modelos.Persona;
import Modelos.TipoPasajero;
import Persistencia.PersonaRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public class PersonaService {

    private PersonaRepository repo = PersonaRepository.getInstancia();

    public boolean crearConductor(String nombre, int cedula, int numeroLicencia, String categoria) {
        return repo.crear(new Conductor(nombre, cedula, numeroLicencia, categoria));
    }

    public boolean crearPasajero(String nombre, int cedula, LocalDate fechaNacimiento, TipoPasajero tipoSolicitado) {
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        TipoPasajero tipoFinal;
        if (edad >= 60) {
            tipoFinal = TipoPasajero.ADULTO_MAYOR;
            System.out.println("Pasajero mayor de 60 años, se asigna ADULTO_MAYOR automáticamente.");
        } else {
            tipoFinal = tipoSolicitado;
        }

        return repo.crear(new Pasajero(nombre, cedula, tipoFinal, fechaNacimiento));
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

    public boolean actualizarPasajero(int cedula, TipoPasajero nuevoTipo) {
        return repo.actualizarPasajero(cedula, nuevoTipo);
    }

    public boolean eliminar(int cedula) {
        return repo.eliminar(cedula);
    }
}