package Persistencia;

import Modelos.Ticket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    private final String archivo = "RegistroTicket.txt";

    private static TicketRepository instancia;
    public static TicketRepository getInstancia() {
        if (instancia == null) instancia = new TicketRepository();
        return instancia;
    }

    private String toCSV(Ticket t) {
        return t.getIdTicket()       + "," +
               t.getCedulaPasajero() + "," +
               t.getPlacaVehiculo()  + "," +
               t.getFechaCompra()    + "," +
               t.getOrigenRuta()     + "," +
               t.getDestinoRuta()    + "," +
               t.getValorFinal()     + "," +
               t.getTipoDescuento();
    }

    public void crearTicket(Ticket t) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(toCSV(t));
        }
    }

    public List<Ticket> listar() throws IOException {
        List<Ticket> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");
                if (datos.length < 8) continue;
                int    idTicket       = Integer.parseInt(datos[0]);
                int    cedulaPasajero = Integer.parseInt(datos[1]);
                String placaVehiculo  = datos[2];
                String fechaCompra    = datos[3];
                String origenRuta     = datos[4];
                String destinoRuta    = datos[5];
                double valorFinal     = Double.parseDouble(datos[6]);
                double tipoDescuento  = Double.parseDouble(datos[7]);
                lista.add(new Ticket(idTicket, cedulaPasajero, placaVehiculo,
                                     fechaCompra, origenRuta, destinoRuta,
                                     valorFinal, tipoDescuento));
            }
        }
        return lista;
    }

    public Ticket buscarPorId(int id) throws IOException {
        for (Ticket t : listar()) {
            if (id == t.getIdTicket()) return t;
        }
        return null;
    }

    public void cancelar(int id) throws IOException {
        List<Ticket> lista = listar();
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Ticket t : lista) {
                if (id == t.getIdTicket()) t.setEstadoTicket(false);
                pw.println(toCSV(t));
            }
        }
    }

    // ─────────────────────────────────────────────
    //  FILTROS — NUEVOS
    // ─────────────────────────────────────────────

    public List<Ticket> buscarPorFecha(String fecha) throws IOException {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : listar()) {
            if (t.getFechaCompra().equals(fecha)) resultado.add(t);
        }
        return resultado;
    }

    public List<Ticket> buscarPorPlaca(String placa) throws IOException {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : listar()) {
            if (t.getPlacaVehiculo().equalsIgnoreCase(placa)) resultado.add(t);
        }
        return resultado;
    }

    public List<Ticket> buscarPorDescuento(double descuento) throws IOException {
        List<Ticket> resultado = new ArrayList<>();
        for (Ticket t : listar()) {
            if (t.getTipoDescuento() == descuento) resultado.add(t);
        }
        return resultado;
    }
}