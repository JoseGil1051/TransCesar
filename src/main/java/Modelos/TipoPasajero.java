package Modelos;


public enum TipoPasajero {
    REGULAR(0.0),
    ESTUDIANTE(0.15),
    ADULTO_MAYOR(0.30);

    private final double descuento;

    TipoPasajero(double descuento) {
        this.descuento = descuento;
    }

    public double getDescuento() {
        return descuento;
    }

    public double aplicarDescuento(double precioOriginal) {
        return precioOriginal * (1 - descuento);
    }
}
