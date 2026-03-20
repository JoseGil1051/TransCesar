package Presentacion;

import java.util.Scanner;
import java.util.List;
import Modelos.Bus;
import Modelos.Buseta;
import Modelos.MicroBus;
import Modelos.Vehiculo;
import Logica.BusService;
import Logica.BusetaService;
import Logica.MicroBusService;
import Logica.VehiculoService;

public class MenuVehiculo {
    Scanner scanner = new Scanner(System.in);
     BusService busService = new BusService();
    BusetaService busetaService = new BusetaService();
    MicroBusService microBusService = new MicroBusService();
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
       System.out.print("Placa a actualizar: ");
        String placa = scanner.next();
        System.out.print("Nueva Ruta: ");
        String ruta = scanner.next();
        System.out.print("Nuevo Estado (true/false): ");
        boolean estado = scanner.nextBoolean();
        System.out.print("Nueva Capacidad: ");
        int capacidad = scanner.nextInt();
        System.out.print("Nueva Tarifa: ");
        double tarifa = scanner.nextDouble();
        try {
            busService.actualizar(new Bus(placa, ruta, estado, capacidad, tarifa));
            System.out.println("Bus actualizado.");
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }
    
    public void ListarVehiculo(){
        
    } 
    
    public void ActualizarVehiculo(){
        
    } 
    
    public void EliminarVehiculo(){
        
    } 
} 
