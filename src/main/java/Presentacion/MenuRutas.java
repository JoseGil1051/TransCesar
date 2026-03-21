package Presentacion;

import java.util.Scanner;

public class MenuRutas {
    private final Scanner scanner = new Scanner(System.in);

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
        
    }
    
    public void listarRuta(){
        
    }
    
    public void actualizarRuta(){
        
    }
    
    public void eliminarRuta(){
        
    }
}
