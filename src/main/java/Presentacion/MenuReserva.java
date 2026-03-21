package Presentacion;

import java.util.Scanner;

public class MenuReserva {
    Scanner scanner = new Scanner(System.in);
    public void MenuConductor(){
        int opc;
            
        do{
            System.out.println("|============ MENU RESERVA =============|");
            System.out.println("| 1 | REGISTRAR RESERVA                 |");
            System.out.println("| 2 | CANCELAR RESERVA                  |");
            System.out.println("| 3 | CONVERTIR RESERVA A TICKET        |");
            System.out.println("| 4 | LISTAR RESERVAS ACTIVAS           |");
            System.out.println("| 5 | HISTORIAL DE RESERVAS POR USUARIO |");
            System.out.println("| 6 | SALIR                             |");
            System.out.println("|=======================================|");
            System.out.print("Ingrese una opcion (1 - 6)");
            opc = scanner.nextInt();
            
            switch (opc){
                case 1 -> RegistrarReserva();

                case 2 -> CancelarReserva();

                case 3 -> ConvertirReservaTicket();
                
                case 4 -> ListarReservasActivas();
                
                case 5 -> HistorialReservasUsuario();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
    
    public void RegistrarReserva(){
        
    }
    
    public void CancelarReserva(){
        
    }
            
    public void ConvertirReservaTicket(){
        
    }
            
    public void ListarReservasActivas(){
        
    }
            
    public void HistorialReservasUsuario(){
        
    }
}
