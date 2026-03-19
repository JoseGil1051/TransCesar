package Presentacion;

import java.util.Scanner;

public class MenuReportes {
    Scanner scanner = new Scanner(System.in);
    public void MenuReportes(){
        int opc;
            
        do{
            System.out.println("|========= MENU REPORTES =========|");
            System.out.println("| 1 | REPORTE VEHICULOS           |");
            System.out.println("| 2 | REPORTE PERSONAS            |");
            System.out.println("| 3 | REPORTE TICKETS             |");
            System.out.println("| 4 | REPORTE GENERAL             |");
            System.out.println("| 5 | SALIR                       |");
            System.out.println("|=================================|");
            System.out.print("Ingrese una opcion (1 - 5)");
            opc = scanner.nextInt();
            
            switch (opc){
                //case 1 -> ReporteVehiculo();

                //case 2 -> ReportePersona();

                //case 3 -> ReporteTicket();
                
                //case 4 -> ReporteGeneral();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
}