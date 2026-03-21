/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.time.LocalDate;

/**
 *
 * @author Nacho
 */
public class Pasajero extends Persona{
    private TipoPasajero tipoPasajero;
    private LocalDate fechaNacimiento;
    
    public Pasajero() {
    }

    public Pasajero(String nombre, int cedula, TipoPasajero tipoPasajero) {
        super(nombre, cedula);
        this.tipoPasajero = tipoPasajero;
    }

    public TipoPasajero getTipoPasajero() {
        return tipoPasajero;
    }

    public void setTipoPasajero(TipoPasajero tipoPasajero) {
        this.tipoPasajero = tipoPasajero;
    }

    /**
     * Calcula el precio final del ticket según el tipo de pasajero.
     */
    public double calcularPrecioTicket(double precioBase) {
        return tipoPasajero.aplicarDescuento(precioBase);
    }

    /**
     * Representación para guardar en pasajeros.txt
     */
    @Override
    public String toString() {
        return getNombre() + "," + getCedula() + "," + tipoPasajero.name();
    }
}
