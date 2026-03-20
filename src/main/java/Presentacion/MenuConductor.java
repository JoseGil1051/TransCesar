package Presentacion;

import java.util.Scanner;
import Modelos.TipoPasajero;
import Logica.ConductorService;
import Modelos.Conductor;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuConductor {
    Scanner scanner = new Scanner(System.in);
    ConductorService conductorService = new ConductorService();
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
        System.out.println("\n--- REGISTRAR CONDUCTOR ---");
        scanner.nextLine(); // limpiar buffer

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Cédula: ");
        int cedula = scanner.nextInt();

        System.out.print("Número de licencia: ");
        int numLicencia = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        System.out.println("Categoría de licencia:");
        System.out.println("  1. B1");
        System.out.println("  2. B2");
        System.out.println("  3. C1");
        System.out.println("  4. C2");
        System.out.print("Seleccione (1-4): ");
        int opcCategoria = scanner.nextInt();

        String categoria;
        switch (opcCategoria) {
            case 1 -> categoria = "B1";
            case 2 -> categoria = "B2";
            case 3 -> categoria = "C1";
            case 4 -> categoria = "C2";
            default -> {
                System.out.println("Categoría inválida, se asignará B1 por defecto.");
                categoria = "B1";
            }
        }

        boolean exito = conductorService.crear(nombre, cedula, numLicencia, categoria);
        if (exito) {
            System.out.println("Conductor registrado exitosamente.");
        } else {
            System.out.println("No se pudo registrar el conductor.");
        }
    }
    
    public void ListarConductor(){
        System.out.println("\n--- LISTA DE CONDUCTORES ---");
        conductorService.listarTodos();
    }

    public void BuscarConductor() {
        System.out.println("\n--- BUSCAR CONDUCTOR ---");
        System.out.println("Buscar por:");
        System.out.println("  1. Cédula");
        System.out.println("  2. Categoría de licencia");
        System.out.print("Seleccione (1-2): ");
        int opc = scanner.nextInt();

        switch (opc) {
            case 1 -> {
                System.out.print("Ingrese la cédula: ");
                int cedula = scanner.nextInt();
                Optional<Conductor> resultado = conductorService.buscarPorCedula(cedula);
                if (resultado.isPresent()) {
                    Conductor c = resultado.get();
                    System.out.println("Conductor encontrado:");
                    System.out.println("  Nombre    : " + c.getNombre());
                    System.out.println("  Cédula    : " + c.getCedula());
                    System.out.println("  Licencia  : " + c.getNumerodelicencia());
                    System.out.println("  Categoría : " + c.getCategorialicencia());
                } else {
                    System.out.println("No se encontró conductor con esa cédula.");
                }
            }
            case 2 -> {
                scanner.nextLine();
                System.out.print("Ingrese la categoría (B1, B2, C1, C2): ");
                String categoria = scanner.nextLine().toUpperCase();
                List<Conductor> lista = conductorService.buscarPorCategoria(categoria);
                if (lista.isEmpty()) {
                    System.out.println("No hay conductores con categoría: " + categoria);
                } else {
                    System.out.println("Conductores con categoría " + categoria + ":");
                    for (Conductor c : lista) {
                        System.out.println("  - " + c.getNombre()
                                + " | Cédula: " + c.getCedula()
                                + " | Licencia: " + c.getNumerodelicencia());
                    }
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    } 
    
    public void ActualizarConductor(){
        System.out.println("\n--- ACTUALIZAR CONDUCTOR ---");
        System.out.println("¿Qué desea actualizar?");
        System.out.println("  1. Nombre");
        System.out.println("  2. Licencia y categoría");
        System.out.print("Seleccione (1-2): ");
        int opc = scanner.nextInt();

        System.out.print("Ingrese la cédula del conductor: ");
        int cedula = scanner.nextInt();

        switch (opc) {
            case 1 -> {
                scanner.nextLine();
                System.out.print("Nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                boolean exito = conductorService.actualizarNombre(cedula, nuevoNombre);
                System.out.println(exito ? "Nombre actualizado." : "No se encontró el conductor.");
            }
            case 2 -> {
                System.out.print("Nuevo número de licencia: ");
                int nuevoNumLic = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Nueva categoría (B1, B2, C1, C2): ");
                String nuevaCategoria = scanner.nextLine().toUpperCase();
                boolean exito = conductorService.actualizarLicencia(cedula, nuevoNumLic, nuevaCategoria);
                System.out.println(exito ? "Licencia actualizada." : "No se encontró el conductor.");
            }
            default -> System.out.println("Opción inválida.");
        }
    } 
    
    public void EliminarConductor(){
         System.out.println("\n--- ELIMINAR CONDUCTOR ---");
        System.out.print("Ingrese la cédula del conductor a eliminar: ");
        int cedula = scanner.nextInt();

        System.out.print("¿Está seguro? (1 = Sí / 2 = No): ");
        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            boolean exito = conductorService.eliminar(cedula);
            System.out.println(exito ? "Conductor eliminado." : "No se encontró el conductor.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    } 

