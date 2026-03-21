package Modelos;


public class Buseta extends Vehiculo {
    private static final int CAPACIDAD_BUSETA = 19;
    private static final double TARIFA_BUSETA = 8000;

    public Buseta(String placa, Ruta ruta, boolean estado) {
        super(placa, ruta, estado, CAPACIDAD_BUSETA, TARIFA_BUSETA);
    }
}