package Presentacion;

import Logica.ReportesService;
import java.util.Scanner;

public class MenuReportes {
    Scanner scanner = new Scanner(System.in);
    ReportesService reportesService = ReportesService.getInstancia();
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
                case 1 -> reporteVehiculos();

                case 2 -> reportePersonas();

                case 3 -> reporteTicket();
                
                case 4 -> reporteGeneral();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
    
     public void reporteVehiculos() {
        reportesService.reporteVehiculos();
    }

    public void reportePersonas() {
        reportesService.reportePersonas();
    }

    public void reporteTicket() {
        reportesService.reporteTickets();
    }

    public void reporteGeneral() {
        reportesService.reporteGeneral();
    }
    
    
    
    
}