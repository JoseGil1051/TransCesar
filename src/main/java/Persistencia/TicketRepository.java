package Persistencia;

import Modelos.Ticket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private final String archivo = "RegistroTicket.txt";
    
    private String toCSV(Ticket t) {
        return t.getIdTicket() + "," +
               t.getCedulaPasajero()  + "," +
               t.getPlacaVehiculo() + "," +
               t.getFechaCompra() + "," +
               t.getOrigenRuta() + "," +
               t.getDestinoRuta() + "," +
               t.getValorFinal() + "," +
               t.getTipoDescuento();
    }
    
    public void crearTicket(Ticket t) throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, true))) {
            pw.println(toCSV(t));
        }
    }
    
    public List<Ticket> listar() throws IOException {
        List<Ticket> lista = new ArrayList<>();
        File f = new File(archivo);
        if (!f.exists()) return lista;

        try (BufferedReader tk = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = tk.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] datos = linea.split(",");
                int idTicket = Integer.parseInt(datos[0]);
                int cedulaPasajero = Integer.parseInt(datos[1]);
                String placaVehiculo = datos[2];
                String fechaCompra = datos[3];
                String origenRuta = datos[4];
                String destinoRuta = datos[5];
                double valorFinal = Double.parseDouble(datos[3]);
                double tipoDescuento = Double.parseDouble(datos[4]);
                
                Ticket t = new Ticket(idTicket, cedulaPasajero, placaVehiculo, fechaCompra, origenRuta, destinoRuta, valorFinal, tipoDescuento) {};
                lista.add(t);
            }
        }
        return lista;
    }
    
    public Ticket buscarPorId(int id) throws IOException {
        for (Ticket t : listar()) {
            if (id == t.getIdTicket()) {
                return t;
            }
        }
        return null;
    }
    
    public void cancelar(int id) throws IOException {
        List<Ticket> lista = listar();
        try (PrintWriter tk = new PrintWriter(new FileWriter(archivo))) {
            for (Ticket t : lista) {
                if (id == t.getIdTicket()) {
                    t.setEstadoTicket(false);
                }
                tk.println(toCSV(t));
            }
        }
    }
    
}