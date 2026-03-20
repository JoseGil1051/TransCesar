/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import Modelos.Ticket;
import Persistencia.TicketRepository;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author gilga
 */
public class TicketService {
    TicketRepository Repo = new TicketRepository();
    
    public void registrar(int cedulaPasajero, String placaVehiculo, String fechaCompra, String origenRuta, String destinoRuta, double valorFinal, double tipoDescuento) throws IOException {
        int id = generarId();
        Ticket t = new Ticket(id, cedulaPasajero, placaVehiculo, fechaCompra, origenRuta, destinoRuta, valorFinal, tipoDescuento);

        Repo.crearTicket(t);
    }
    
    public void cancelar(int idTicket) throws Exception {
        // Validar que el vehiculo exista antes de eliminar
        if (Repo.buscarPorId(idTicket) == null) {
            throw new Exception("No existe un ticket con id: " + idTicket);
        }
        Repo.cancelar(idTicket);
    }
    
    public int generarId() throws IOException {
        List<Ticket> lista = Repo.listar();

        int ultimoId = 1;

        if (!lista.isEmpty()) {
            ultimoId = lista.get(lista.size() - 1).getIdTicket();
        }

        return ultimoId;
    }
}
