package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Vehiculo;

public class VehiculoRepository {

    private final String archivo = "RegistroVehiculos.txt";
    
    private static VehiculoRepository instancia;
    public static VehiculoRepository getInstancia() {
        if (instancia == null) instancia = new VehiculoRepository();
        return instancia;
    }

    //  Convierte un Vehiculo a línea CSV
    private String toCSV(Vehiculo v) {
        return v.getPlaca() + "," +
               v.getRuta()  + "," +
               v.isEstado() + "," +
               v.getCapacidad() + "," +
               v.getTarifa();
    }

    //  GUARDAR
    public void guardar(Vehiculo v) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(v));
        pw.close();
    }

    //  LISTAR
    public List<Vehiculo> listar() throws IOException {
        List<Vehiculo> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos   = linea.split(",");
            String placa     = datos[0];
            String ruta      = datos[1];
            boolean estado   = Boolean.parseBoolean(datos[2]);
            int capacidad    = Integer.parseInt(datos[3]);
            double tarifa    = Double.parseDouble(datos[4]);

            Vehiculo v = new Vehiculo(placa, ruta, estado, capacidad, tarifa) {};
            lista.add(v);
        }
        br.close();
        return lista;
    }

    //  BUSCAR POR PLACA
    public Vehiculo buscarPorPlaca(String placaBuscada) throws IOException {
        for (Vehiculo v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) {
                return v;
            }
        }
        return null;
    }

    //  ACTUALIZAR
    public void actualizar(Vehiculo actualizado) throws IOException {
        List<Vehiculo> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (Vehiculo v : lista) {
            if (v.getPlaca().equalsIgnoreCase(actualizado.getPlaca())) {
                pw.println(toCSV(actualizado));
            } else {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }

    //  ELIMINAR
    public void eliminar(String placa) throws IOException {
        List<Vehiculo> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (Vehiculo v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }
}