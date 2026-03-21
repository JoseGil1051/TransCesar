package Logica;

import Modelos.Pasajero;
import Modelos.TipoPasajero;
import Persistencia.PasajeroRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

public class PasajeroService {

    private PasajeroRepository repo = PasajeroRepository.getInstancia();

    // ── NUEVO: calcula edad ──
    public int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // ── MODIFICADO: ahora recibe fechaNacimiento y tipoSolicitado ──
    public boolean crear(String nombre, int cedula, LocalDate fechaNacimiento, TipoPasajero tipoSolicitado) {
        int edad = calcularEdad(fechaNacimiento);

        TipoPasajero tipoFinal;
        if (edad >= 60) {
            tipoFinal = TipoPasajero.ADULTO_MAYOR;
            System.out.println("Pasajero mayor de 60 años, se asigna ADULTO_MAYOR automáticamente (30% descuento).");
        } else {
            tipoFinal = tipoSolicitado;
        }

        return repo.crear(new Pasajero(nombre, cedula, tipoFinal, fechaNacimiento));
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