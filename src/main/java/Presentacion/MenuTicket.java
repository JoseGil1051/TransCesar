package Presentacion;

import java.util.Scanner;

public class MenuTicket {
    Scanner scanner = new Scanner(System.in);
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
        
    }
    
    public void CancelarTicket(){
        
    }
}

