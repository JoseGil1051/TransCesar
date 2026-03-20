/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Modelos;

/**
 *
 * @author HP
 */
public abstract class Vehiculo {

    private String placa;
    private String ruta;
    private boolean estado;
    private int capacidad;
    private double tarifa;

    public Vehiculo() {
    }

    public Vehiculo(String placa, String ruta, boolean estado, int capacidad, double tarifa) {
        this.placa = placa;
        this.ruta = ruta;
        this.estado = estado;
        this.capacidad = capacidad;
        this.tarifa = tarifa;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the tarifa
     */
    public double getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public String toString(){
        return "Placa: "+placa+
                "Ruta: "+ruta+
                "Estado: "+estado+
                "Capacidad: "+capacidad+
                "Tarifa: "+tarifa;
    }

       
}

