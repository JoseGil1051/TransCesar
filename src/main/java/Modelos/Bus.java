package Modelos;



public class Bus extends Vehiculo{
    private static final int CAPACIDAD_BUS = 45;
    private static final double TARIFA_BUS = 15000;

    public Bus(String placa, String ruta, boolean estado) {
        super(placa, ruta, estado, CAPACIDAD_BUS, TARIFA_BUS);
    }
}