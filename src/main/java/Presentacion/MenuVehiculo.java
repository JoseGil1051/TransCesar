package Presentacion;

import java.util.List;
import java.util.Scanner;

public class MenuVehiculo {
    Scanner scanner = new Scanner(System.in);

    public void MenuPasajeros(){
        int opc;
            
        do{
            System.out.println("|========= MENU VEHICULOS =========|");
            System.out.println("| 1 | REGISTRAR VEHICULOS           |");
            System.out.println("| 2 | LISTAR VEHICULOS             |");
            System.out.println("| 3 | ACTUALIZAR VEHICULOS          |");
            System.out.println("| 4 | ELIMINAR VEHICULOS            |");
            System.out.println("| 5 | SALIR                          |");
            System.out.println("|====================================|");
            System.out.print("Ingrese una opcion (1 - 5)");
            opc = scanner.nextInt();
            
            switch (opc){
                case 1 -> RegistrarVehiculo();

                case 2 -> ListarVehiculo();

                case 3 -> ActualizarVehiculo();
                
                case 4 -> EliminarVehiculo();

                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        }while (opc != 5);
    }
    
    public void RegistrarVehiculo(){
       
    }
    
    public void ListarVehiculo(){
        
    } 
    
    public void ActualizarVehiculo(){
        
    } 
    
    public void EliminarVehiculo(){
        
    } 
} 
