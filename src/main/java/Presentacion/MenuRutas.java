package Presentacion;
import Modelos.Ruta;
import Logica.RutaService;

import java.util.Scanner;
import java.util.List;

public class MenuRutas {
    private final Scanner scanner = new Scanner(System.in);
    RutaService rutaService = new RutaService(); 

    public void MenuRutas() {
        int opc;
        do {
            System.out.println("|========= MENU TICKETS =========|");
            System.out.println("| 1 | REGISTRAR RUTA             |");
            System.out.println("| 2 | LISTAR RUTA                |");
            System.out.println("| 3 | ACTUALIZAR RUTA            |");
            System.out.println("| 4 | ELIMINAR RUTA              |");
            System.out.println("| 5 | REGRESAR                   |");
            System.out.println("|================================|");
            System.out.print("Ingrese una opcion (1 - 3): ");
            opc = scanner.nextInt();
            switch (opc) {
                case 1 -> registrarRuta();
                    
                case 2 -> listarRuta();
                
                case 3 -> actualizarRuta();
                
                case 4 -> eliminarRuta();
                    
                case 5 -> System.out.println("Volviendo al menú principal...");
                
                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 3);
    }
    
    public void registrarRuta(){
         System.out.print("Codigo de ruta: ");
          try {
        System.out.print("Codigo de ruta: ");
        String codigo = scanner.next();
        System.out.print("Ciudad origen: ");
        String origen = scanner.next();
        System.out.print("Ciudad destino: ");
        String destino = scanner.next();
        System.out.print("Distancia en km: ");
        double distancia = scanner.nextDouble();
        System.out.print("Tiempo estimado en minutos: ");
        int tiempo = scanner.nextInt();
        rutaService.guardar(new Ruta(codigo, origen, destino, distancia, tiempo));
        System.out.println("Ruta registrada.");
    } catch (Exception e) {
        System.out.println("Error al registrar: " + e.getMessage());
    }
        }
    
    
    public void listarRuta(){
         try {
            List<Ruta> rutas = rutaService.listar();
            if (rutas.isEmpty()) System.out.println("No hay rutas registradas.");
            else rutas.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
    }
    
    public void actualizarRuta(){
         try {
            System.out.print("Codigo de ruta a actualizar: ");
            String codigo = scanner.next();
            System.out.print("Nueva ciudad origen: ");
            String origen = scanner.next();
            System.out.print("Nueva ciudad destino: ");
            String destino = scanner.next();
            System.out.print("Nueva distancia en km: ");
            double distancia = scanner.nextDouble();
            System.out.print("Nuevo tiempo estimado en minutos: ");
            int tiempo = scanner.nextInt();

            rutaService.actualizar(new Ruta(codigo, origen, destino, distancia, tiempo));
            System.out.println("Ruta actualizada.");
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }
    
    public void eliminarRuta(){
        try {
            System.out.print("Codigo de ruta a eliminar: ");
            String codigo = scanner.next();
            rutaService.eliminar(codigo);
            System.out.println("Ruta eliminada.");
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
