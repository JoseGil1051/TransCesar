package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Buseta;

public class BusetaRepository {

    private final String archivo = "RegistroBuseta.txt";

    private String toCSV(Buseta v) {
        return v.getPlaca() + "," +
               v.getRuta() + "," +
               v.isEstado();
    }

    public void guardar(Buseta v) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(v));
        pw.close();
    }

    public List<Buseta> listar() throws Exception {
        List<Buseta> lista = new ArrayList<>();
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

            Buseta v = new Buseta(placa, ruta, estado);
            lista.add(v);
        }
        br.close();
        return lista;
    }

    public Buseta buscarPorPlaca(String placaBuscada) throws Exception {
        for (Buseta v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) return v;
        }
        return null;
    }

    public void actualizar(Buseta actualizado) throws Exception {
        List<Buseta> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (Buseta v : lista) {
            if (v.getPlaca().equalsIgnoreCase(actualizado.getPlaca())) {
                pw.println(toCSV(actualizado));
            } else {
                pw.println(toCSV(v));
            }
        }
        pw.close();
    }

    public void eliminar(String placa) throws Exception {
        List<Buseta> lista = listar();
        File temp = new File("RegistroBuseta.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (Buseta v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }
}