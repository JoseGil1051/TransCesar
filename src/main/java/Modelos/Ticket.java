package Modelos;

public class Ticket {
    private int IdTicket;
    private int cedulaPasajero;
    private String placaVehiculo;
    private String fechaCompra;
    private String origenRuta;
    private String destinoRuta;
    private double valorFinal;
    private double tipoDescuento;
    private boolean estadoTicket;

    public Ticket() {
    }

    public Ticket(int IdTicket, int cedulaPasajero, String placaVehiculo, String fechaCompra, String origenRuta, String destinoRuta, double valorFinal, double tipoDescuento) {
        this.IdTicket = IdTicket;
        this.cedulaPasajero = cedulaPasajero;
        this.placaVehiculo = placaVehiculo;
        this.fechaCompra = fechaCompra;
        this.origenRuta = origenRuta;
        this.destinoRuta = destinoRuta;
        this.valorFinal = valorFinal;
        this.tipoDescuento = tipoDescuento;
        this.estadoTicket = true;
    }

    public int getIdTicket() {
        return IdTicket;
    }

    public void setIdTicket(int IdTicket) {
        this.IdTicket = IdTicket;
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

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getOrigenRuta() {
        return origenRuta;
    }

    public void setOrigenRuta(String origenRuta) {
        this.origenRuta = origenRuta;
    }

    public String getDestinoRuta() {
        return destinoRuta;
    }

    public void setDestinoRuta(String destinoRuta) {
        this.destinoRuta = destinoRuta;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public double getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(double tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }
    
    public boolean isEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(boolean estadoTicket) {
        this.estadoTicket = estadoTicket;
    }

    @Override
    public String toString() {
        return "Ticket{" + "IdTicket=" + IdTicket + ", cedulaPasajero=" + cedulaPasajero + ", placaVehiculo=" + placaVehiculo + ", fechaCompra=" + fechaCompra + ", origenRuta=" + origenRuta + ", destinoRuta=" + destinoRuta + ", valorFinal=" + valorFinal + ", tipoDescuento=" + tipoDescuento + ", Estado=" + isEstadoTicket() + '}';
    }  

}
