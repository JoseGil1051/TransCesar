package Modelos;

public class Reserva {    
    private enum estadoReserva {
        ACTIVA, 
        CONVERTIDA, 
        CANCELADA;
    }

    private int codigoReserva;
    private int cedulaPasajero;
    private String placaVehiculo;
    private String fechaCreacion;
    private String fechaViaje;
    private estadoReserva estadoReserva;

    public Reserva() {
    }

    public Reserva(int codigoReserva, int cedulaPasajero, String placaVehiculo, String fechaCreacion, String fechaViaje, estadoReserva estadoReserva) {
        this.codigoReserva = codigoReserva;
        this.cedulaPasajero = cedulaPasajero;
        this.placaVehiculo = placaVehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.estadoReserva = estadoReserva;
    }

    public int getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public int getCedulaPasajero() {
        return cedulaPasajero;
    }

    public void setCedulaPasajero(int cedulaPasajero) {
        this.cedulaPasajero = cedulaPasajero;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public estadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(estadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" + "codigoReserva=" + codigoReserva + ", cedulaPasajero=" + cedulaPasajero + ", placaVehiculo=" + placaVehiculo + ", fechaCreacion=" + fechaCreacion + ", fechaViaje=" + fechaViaje + ", estadoReserva=" + estadoReserva + '}';
    }
       
}