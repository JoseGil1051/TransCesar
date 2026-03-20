package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Bus;

public class BusRepository {

    private final String archivo = "RegistroBus.txt";
    
    private static BusRepository instancia;
    public static BusRepository getInstancia() {
        if (instancia == null) instancia = new BusRepository();
        return instancia;
    }

    private String toCSV(Bus v) {
        return v.getPlaca() + "," +
               v.getRuta() + "," +
               v.isEstado();
    }

    public void guardar(Bus v) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(v));
        pw.close();
    }

    public List<Bus> listar() throws Exception {
        List<Bus> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(",");
            String placa   = datos[0];
            String ruta    = datos[1];
            boolean estado = Boolean.parseBoolean(datos[2]);

            Bus v = new Bus(placa, ruta, estado);
            lista.add(v);
        }
        br.close();
        return lista;
    }

    public Bus buscarPorPlaca(String placaBuscada) throws Exception {
        for (Bus v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) return v;
        }
        return null;
    }

    public void actualizar(Bus actualizado) throws Exception {
        List<Bus> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (Bus v : lista) {
            if (v.getPlaca().equalsIgnoreCase(actualizado.getPlaca())) {
                pw.println(toCSV(actualizado));
            } else {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }

    public void eliminar(String placa) throws Exception {
        List<Bus> lista = listar();
        File temp = new File("RegistroBus_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (Bus v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }
}