package Presentacion;

import java.util.Scanner;

public class MenuConductor {
    Scanner scanner = new Scanner(System.in);
    public void MenuPasajeros(){
        int opc;
            
        do{
            System.out.println("|========= MENU CONDUCTOR =========|");
            System.out.println("| 1 | REGISTRAR CONDUCTOR           |");
            System.out.println("| 2 | LISTAR CONDUCTOR             |");
            System.out.println("| 3 | ACTUALIZAR CONDUCTOR          |");
            System.out.println("| 4 | ELIMINAR CONDUCTOR            |");
            System.out.println("| 5 | SALIR                          |");
            System.out.println("|====================================|");
            System.out.print("Ingrese una opcion (1 - 5)");
            opc = scanner.nextInt();
            
            switch (opc){
                case 1 -> RegistrarConductor();

                case 2 -> ListarConductor();

                case 3 -> ActualizarConductor();
                
                case 4 -> EliminarConductor();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
    
    public void RegistrarConductor(){
        
    }
    
    public void ListarConductor(){
        
    } 
    
    public void ActualizarConductor(){
        
    } 
    
    public void EliminarConductor(){
        
    } 
}
