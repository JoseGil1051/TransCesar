package Presentacion;

import java.util.Scanner;

public class MenuPrincipal {
    Scanner scanner = new Scanner(System.in);
    
    public void IniciarMenuPrincipal(){
        MenuTicket mTicket = new MenuTicket();
        MenuPasajero mPasajero = new MenuPasajero();
        MenuConductor mConductor = new MenuConductor();
        MenuVehiculo mVehiculo = new MenuVehiculo();
        MenuReportes mReportes = new MenuReportes();
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
            System.out.print("Ingrese una opcion (1 - 6): ");
            opc = scanner.nextInt();

            switch (opc){
                case 1 -> mTicket.MenuTicket();

                case 2 -> mPasajero.MenuPasajeros();

                case 3 -> mConductor.MenuConductor();
                
                case 4 -> mVehiculo.MenuVehiculo();
                
                case 5 -> mReportes.MenuReportes();

                case 6 -> System.out.println("Gracias por utilizar el sistema academico, hasta la proxima...");

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 6);      
    }
}
