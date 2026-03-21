package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Ruta;

public class RutaRepository {

    private final String archivo = "RegistroRutas.txt";

    private String toCSV(Ruta r) {
        return r.getCodigoRuta()            + "," +
               r.getCiudadOrigen()          + "," +
               r.getCiudadDestino()         + "," +
               r.getDistanciaKm()           + "," +
               r.getTiempoEstimadoMinutos();
    }

    public void guardar(Ruta r) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(r));
        pw.close();
    }

    public List<Ruta> listar() throws Exception {
        List<Ruta> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(",");
            Ruta r = new Ruta(
                datos[0],
                datos[1],
                datos[2],
                Double.parseDouble(datos[3]),
                Integer.parseInt(datos[4])
            );
            lista.add(r);
        }
        br.close();
        return lista;
    }

    public Ruta buscarPorCodigo(String codigo) throws Exception {
        for (Ruta r : listar()) {
            if (r.getCodigoRuta().equalsIgnoreCase(codigo)) return r;
        }
        return null;
    }

    public void actualizar(Ruta actualizada) throws Exception {
        List<Ruta> lista = listar();
        PrintWriter pw = new PrintWriter(new FileWriter(archivo));
        for (Ruta r : lista) {
            if (r.getCodigoRuta().equalsIgnoreCase(actualizada.getCodigoRuta())) {
                pw.println(toCSV(actualizada));
            } else {
                pw.println(toCSV(r));
            }
        }
        pw.close();
    }

    public void eliminar(String codigo) throws Exception {
        List<Ruta> lista = listar();
        File temp = new File("RegistroRutas_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (Ruta r : lista) {
            if (!r.getCodigoRuta().equalsIgnoreCase(codigo)) {
                pw.println(toCSV(r));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }
}