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
    public void MenuVehiculo(){
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
        try {
            System.out.println("Tipo de vehiculo:");
            System.out.println("1. Bus      (45 puestos - $15.000)");
            System.out.println("2. Buseta   (19 puestos - $8.000)");
            System.out.println("3. MicroBus (25 puestos - $10.000)");
            System.out.print("Seleccione: ");
            int tipo = scanner.nextInt();

            System.out.print("Placa: ");
            String placa = scanner.next();
            System.out.print("Ruta: ");
            String ruta = scanner.next();
            System.out.print("Estado (true/false): ");
            boolean estado = scanner.nextBoolean();

            switch (tipo) {
                case 1 -> { busService.guardar(new Bus(placa, ruta, estado)); System.out.println("Bus registrado."); }
                case 2 -> { busetaService.guardar(new Buseta(placa, ruta, estado)); System.out.println("Buseta registrada."); }
                case 3 -> { microBusService.guardar(new MicroBus(placa, ruta, estado)); System.out.println("MicroBus registrado."); }
                default -> System.out.println("Tipo invalido.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }
    
    public void ListarVehiculo(){
        try {
            System.out.println("\n--- Buses ---");
            List<Bus> buses = busService.listar();
            if (buses.isEmpty()) System.out.println("No hay buses registrados.");
            else buses.forEach(System.out::println);

            System.out.println("\n--- Busetas ---");
            List<Buseta> busetas = busetaService.listar();
            if (busetas.isEmpty()) System.out.println("No hay busetas registradas.");
            else busetas.forEach(System.out::println);

            System.out.println("\n--- MicroBuses ---");
            List<MicroBus> micros = microBusService.listar();
            if (micros.isEmpty()) System.out.println("No hay microbuses registrados.");
            else micros.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    } 
    
    public void ActualizarVehiculo(){
       try {
            System.out.println("Tipo de vehiculo a actualizar:");
            System.out.println("1. Bus  2. Buseta  3. MicroBus");
            System.out.print("Seleccione: ");
            int tipo = scanner.nextInt();

            System.out.print("Placa a actualizar: ");
            String placa = scanner.next();
            System.out.print("Nueva Ruta: ");
            String ruta = scanner.next();
            System.out.print("Nuevo Estado (true/false): ");
            boolean estado = scanner.nextBoolean();

            switch (tipo) {
                case 1 -> { busService.actualizar(new Bus(placa, ruta, estado)); System.out.println("Bus actualizado."); }
                case 2 -> { busetaService.actualizar(new Buseta(placa, ruta, estado)); System.out.println("Buseta actualizada."); }
                case 3 -> { microBusService.actualizar(new MicroBus(placa, ruta, estado)); System.out.println("MicroBus actualizado."); }
                default -> System.out.println("Tipo invalido.");
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    } 
    
    public void EliminarVehiculo(){
        System.out.print("Placa a eliminar: ");
        String placa = scanner.next();
        try {
            busService.eliminar(placa);
            System.out.println("Bus eliminado.");
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    } 
} 
