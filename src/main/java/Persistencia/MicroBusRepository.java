package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.MicroBus;
import Modelos.Ruta;

public class MicroBusRepository {

    private final String archivo = "RegistroMicroBus.txt";
    private RutaRepository rutaRepo = new RutaRepository();

    private String toCSV(MicroBus v) {
        return v.getPlaca() + "," +
               v.getRuta().getCodigoRuta() + "," +
               v.isEstado();
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
            String[] datos = linea.split(",");
            String placa   = datos[0];
            Ruta ruta      = rutaRepo.buscarPorCodigo(datos[1]);
            boolean estado = Boolean.parseBoolean(datos[2]);

            MicroBus v = new MicroBus(placa, ruta, estado);
            lista.add(v);
        }
        br.close();
        return lista;
    }

    public MicroBus buscarPorPlaca(String placaBuscada) throws Exception {
        for (MicroBus v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) return v;
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
        File temp = new File("RegistroMicroBus_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (MicroBus v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }
}