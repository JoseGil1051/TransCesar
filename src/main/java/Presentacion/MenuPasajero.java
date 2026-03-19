package Presentacion;

import java.util.Scanner;

public class MenuPasajero{
    Scanner scanner = new Scanner(System.in);
    public void MenuPasajeros(){
        int opc;
            
        do{
            System.out.println("|========= MENU PASAJEROS =========|");
            System.out.println("| 1 | REGISTRAR PASAJEROS           |");
            System.out.println("| 2 | LISTAR PASAJEROS             |");
            System.out.println("| 3 | ACTUALIZAR PASAJEROS          |");
            System.out.println("| 4 | ELIMINAR PASAJEROS            |");
            System.out.println("| 5 | SALIR                          |");
            System.out.println("|====================================|");
            System.out.print("Ingrese una opcion (1 - 5)");
            opc = scanner.nextInt();
            
            switch (opc){
                case 1 -> RegistrarPasajero();

                case 2 -> ListarPasajero();

                case 3 -> ActualizarPasajero();
                
                case 4 -> EliminarPasajero();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
    
    public void RegistrarPasajero(){
        
    }
    
    public void ListarPasajero(){
        
    } 
    
    public void ActualizarPasajero(){
        
    } 
    
    public void EliminarPasajero(){
        
    } 
}
