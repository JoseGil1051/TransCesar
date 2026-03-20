package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.MicroBus;

public class MicroBusRepository {

    private final String archivo = "RegistroMicro.txt";

    private String toCSV(MicroBus v) {
        return v.getPlaca() + "," +
               v.getRuta()  + "," +
               v.isEstado() + "," +
               v.getCapacidad() + "," +
               v.getTarifa();
    }

    public void guardar(MicroBus v) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(v));
        pw.close();
    }

    public List<MicroBus> listar() throws Exception {
        List<MicroBus> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos  = linea.split(",");
            String placa    = datos[0];
            String ruta     = datos[1];
            boolean estado  = Boolean.parseBoolean(datos[2]);
            int capacidad   = Integer.parseInt(datos[3]);
            double tarifa   = Double.parseDouble(datos[4]);

            MicroBus v = new MicroBus(placa, ruta, estado, capacidad, tarifa);
            lista.add(v);
        }
        br.close();
        return lista;
    }

    public MicroBus buscarPorPlaca(String placaBuscada) throws Exception {
        for (MicroBus v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) {
                return v;
            }
        }
        return null;
    }

    public void actualizar(MicroBus actualizado) throws Exception {
        List<MicroBus> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (MicroBus v : lista) {
            if (v.getPlaca().equalsIgnoreCase(actualizado.getPlaca())) {
                pw.println(toCSV(actualizado));
            } else {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }

    public void eliminar(String placa) throws Exception {
        List<MicroBus> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (MicroBus v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }
}