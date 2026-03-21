/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author HP
 */
public class Ruta {
    private String codigoRuta;
    private String ciudadOrigen;
    private String ciudadDestino;
    private double distanciaKm;
    private int tiempoEstimadoMinutos;

    public Ruta() {
    }

    public Ruta(String codigoRuta, String ciudadOrigen, String ciudadDestino, double distanciaKm, int tiempoEstimadoMinutos) {
        this.codigoRuta = codigoRuta;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.distanciaKm = distanciaKm;
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }

    /**
     * @return the codigoRuta
     */
    public String getCodigoRuta() {
        return codigoRuta;
    }

    /**
     * @param codigoRuta the codigoRuta to set
     */
    public void setCodigoRuta(String codigoRuta) {
        this.codigoRuta = codigoRuta;
    }

    /**
     * @return the ciudadOrigen
     */
    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    /**
     * @param ciudadOrigen the ciudadOrigen to set
     */
    public void setCiudadOrigen(String ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    /**
     * @return the ciudadDestino
     */
    public String getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * @param ciudadDestino the ciudadDestino to set
     */
    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    /**
     * @return the distanciaKm
     */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * @param distanciaKm the distanciaKm to set
     */
    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    /**
     * @return the tiempoEstimadoMinutos
     */
    public int getTiempoEstimadoMinutos() {
        return tiempoEstimadoMinutos;
    }

    /**
     * @param tiempoEstimadoMinutos the tiempoEstimadoMinutos to set
     */
    public void setTiempoEstimadoMinutos(int tiempoEstimadoMinutos) {
        this.tiempoEstimadoMinutos = tiempoEstimadoMinutos;
    }
    
    @Override
public String toString() {
    return "Codigo: "    + codigoRuta           + "\n" +
           "Origen: "    + ciudadOrigen          + "\n" +
           "Destino: "   + ciudadDestino         + "\n" +
           "Distancia: " + distanciaKm + " km\n" +
           "Tiempo: "    + tiempoEstimadoMinutos + " minutos";
}
    

}
