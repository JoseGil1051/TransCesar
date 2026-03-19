package Presentacion;

import java.util.Scanner;

public class MenuPrincipal {
    Scanner scanner = new Scanner(System.in);
    
    public void IniciarMenuPrincipal(){
        int opc;
        do{
            System.out.println("|========= MENU PRINCIPAL =========|");
            System.out.println("| 1 | GESTION TICKETS              |");
            System.out.println("| 2 | GESTION PASAJEROS            |");
            System.out.println("| 3 | GESTION CONDUCTORES          |");
            System.out.println("| 4 | GESTION VEHICULOS            |");
            System.out.println("| 5 | GESTION REPORTES             |");
            System.out.println("| 6 | SALIR                        |");
            System.out.println("|==================================|");
            System.out.print("Ingrese una opcion (1 - 6)");
            opc = scanner.nextInt();

            switch (opc){
                //case 1 -> MenuTickets();

                //case 2 -> MenuPasajeros();

                //case 3 -> MenuConductores();
                
                //case 4 -> MenuReportes();
                
                //case 5 -> MenuVehiculos();

                //case 6 -> System.out.println("Gracias por utilizar el sistema academico, hasta la proxima...");

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 6);      
    }
}
