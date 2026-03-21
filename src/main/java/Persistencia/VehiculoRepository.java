package Persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Modelos.Vehiculo;
import Modelos.Ruta;

public class VehiculoRepository {

    private static VehiculoRepository instancia;
    public static VehiculoRepository getInstancia() {
        if (instancia == null) instancia = new VehiculoRepository();
        return instancia;
    }

    private final String archivo = "RegistroVehiculos.txt";
    private RutaRepository rutaRepo = RutaRepository.getInstancia();

    private String toCSV(Vehiculo v) {
        return v.getPlaca() + "," +
               v.getRuta().getCodigoRuta() + "," +
               v.isEstado() + "," +
               v.getCapacidad() + "," +
               v.getTarifa();
    }

    public void guardar(Vehiculo v) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(v));
        pw.close();
    }

    public List<Vehiculo> listar() throws Exception {
        List<Vehiculo> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos  = linea.split(",");
            String placa    = datos[0];
            Ruta ruta       = rutaRepo.buscarPorCodigo(datos[1]);
            boolean estado  = Boolean.parseBoolean(datos[2]);
            int capacidad   = Integer.parseInt(datos[3]);
            double tarifa   = Double.parseDouble(datos[4]);

            Vehiculo v = new Vehiculo(placa, ruta, estado, capacidad, tarifa) {};
            lista.add(v);
        }
        br.close();
        return lista;
    }

    public Vehiculo buscarPorPlaca(String placaBuscada) throws Exception {
        for (Vehiculo v : listar()) {
            if (v.getPlaca().equalsIgnoreCase(placaBuscada)) return v;
        }
        return null;
    }

    public void actualizar(Vehiculo actualizado) throws Exception {
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

    public void eliminar(String placa) throws Exception {
        List<Vehiculo> lista = listar();
        File temp = new File("RegistroVehiculos_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (Vehiculo v : lista) {
            if (!v.getPlaca().equalsIgnoreCase(placa)) {
                pw.println(toCSV(v));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }
}