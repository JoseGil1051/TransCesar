package Persistencia;

import Modelos.Reserva;
import Modelos.Reserva.estadoReserva;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {

    private static ReservaRepository instancia;
    public static ReservaRepository getInstancia() {
        if (instancia == null) instancia = new ReservaRepository();
        return instancia;
    }

    private final String archivo = "reservas.txt";

    private String toCSV(Reserva r) {
        return r.getCodigoReserva()  + ";" +
               r.getCedulaPasajero() + ";" +
               r.getPlacaVehiculo()  + ";" +
               r.getFechaCreacion()  + ";" +
               r.getFechaViaje()     + ";" +
               r.getEstadoReserva();
    }

    public void guardar(Reserva r) throws Exception {
        PrintWriter pw = new PrintWriter(new FileWriter(archivo, true));
        pw.println(toCSV(r));
        pw.close();
    }

    public List<Reserva> listar() throws Exception {
        List<Reserva> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) continue;
            String[] datos = linea.split(";");
            if (datos.length < 6) continue;
            Reserva reserva = new Reserva(
                Integer.parseInt(datos[0]),
                Integer.parseInt(datos[1]),
                datos[2],
                datos[3],
                datos[4],
                estadoReserva.valueOf(datos[5])  // convierte "ACTIVA" al enum
            );
            lista.add(reserva);
        }
        br.close();
        return lista;
    }

    public Reserva buscarPorCodigo(int codigo) throws Exception {
        for (Reserva r : listar()) {
            if (r.getCodigoReserva() == codigo) return r;
        }
        return null;
    }

    public void actualizar(Reserva actualizada) throws Exception {
        List<Reserva> lista = listar();
        File temp = new File("reservas_temp.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(temp));
        for (Reserva r : lista) {
            if (r.getCodigoReserva() == actualizada.getCodigoReserva()) {
                pw.println(toCSV(actualizada));
            } else {
                pw.println(toCSV(r));
            }
        }
        pw.close();
        new File(archivo).delete();
        temp.renameTo(new File(archivo));
    }

    public List<Reserva> listarPorPasajero(int cedula) throws Exception {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : listar()) {
            if (r.getCedulaPasajero() == cedula) lista.add(r);
        }
        return lista;
    }

    public List<Reserva> listarActivas() throws Exception {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : listar()) {
            if (r.getEstadoReserva() == estadoReserva.ACTIVA) lista.add(r);
        }
        return lista;
    }
}