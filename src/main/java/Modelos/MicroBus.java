package Modelos;

public class MicroBus extends Vehiculo {
    private static final int CAPACIDAD_MICROBUS = 25;
    private static final double TARIFA_MICROBUS = 10000;

    public MicroBus(String placa, String ruta, boolean estado) {
        super(placa, ruta, estado, CAPACIDAD_MICROBUS, TARIFA_MICROBUS);
    }
}