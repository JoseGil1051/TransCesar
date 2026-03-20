package Presentacion;

import Logica.TicketService;
import java.io.IOException;
import java.util.Scanner;

public class MenuTicket {
    Scanner scanner = new Scanner(System.in);
    TicketService service = new TicketService();
    public void MenuTicket(){
        int opc;

        do{
            System.out.println("|========= MENU TICKETS =========|");
            System.out.println("| 1 | COMPRAR TICKETS            |");
            System.out.println("| 2 | CANCELAR TICKETS           |");
            System.out.println("| 3 | SALIR                      |");
            System.out.println("|================================|");
            System.out.print("Ingrese una opcion (1 - 3)");
            opc = scanner.nextInt();

            switch (opc){
                case 1 -> ComprarTicket();

                case 2 -> CancelarTicket();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 3);        
    }  
    
    public void ComprarTicket(){
    try {
        System.out.print("Ingrese cedula del pasajero: ");
        int cedula = scanner.nextInt();

        System.out.print("Ingrese placa del vehiculo: ");
        String placa = scanner.next();

        System.out.print("Ingrese fecha de compra: ");
        String fecha = scanner.next();

        System.out.print("Ingrese origen: ");
        String origen = scanner.next();

        System.out.print("Ingrese destino: ");
        String destino = scanner.next();

        System.out.print("Ingrese valor final: ");
        double valor = scanner.nextDouble();

        System.out.print("Ingrese tipo de descuento: ");
        double descuento = scanner.nextDouble();

        service.registrar(cedula, placa, fecha, origen, destino, valor, descuento);

        System.out.println("Ticket registrado correctamente");

    } catch (IOException e) {
        System.out.println("Error al registrar ticket: " + e.getMessage());
    }
}
    
    public void CancelarTicket(){
    try {
        System.out.print("Ingrese id del ticket a cancelar: ");
        int id = scanner.nextInt();

        service.cancelar(id);

        System.out.println("Ticket cancelado correctamente");

    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
}

