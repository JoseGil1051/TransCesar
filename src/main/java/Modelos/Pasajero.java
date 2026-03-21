package Modelos;

import java.time.LocalDate;

public class Pasajero extends Persona {

    private TipoPasajero tipoPasajero;
    private LocalDate fechaNacimiento; // ← NUEVO

    public Pasajero() {}

    // ← Constructor actualizado con fechaNacimiento
    public Pasajero(String nombre, int cedula, TipoPasajero tipoPasajero, LocalDate fechaNacimiento) {
        super(nombre, cedula);
        this.tipoPasajero = tipoPasajero;
        this.fechaNacimiento = fechaNacimiento;
    }

    public TipoPasajero getTipoPasajero() { return tipoPasajero; }
    public void setTipoPasajero(TipoPasajero tipoPasajero) { this.tipoPasajero = tipoPasajero; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public double calcularPrecioTicket(double precioBase) {
        return tipoPasajero.aplicarDescuento(precioBase);
    }
}