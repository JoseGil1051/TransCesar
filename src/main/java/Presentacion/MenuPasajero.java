package Presentacion;

import Logica.PasajeroService;
import Modelos.Pasajero;
import Modelos.TipoPasajero;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuPasajero {

    Scanner scanner = new Scanner(System.in);
    PasajeroService pasajeroService = new PasajeroService();

    public void MenuPasajeros() {
        int opc;

        do {
            System.out.println("|========= MENU PASAJEROS =========|");
            System.out.println("| 1 | REGISTRAR PASAJEROS          |");
            System.out.println("| 2 | LISTAR PASAJEROS             |");
            System.out.println("| 3 | ACTUALIZAR PASAJEROS         |");
            System.out.println("| 4 | ELIMINAR PASAJEROS           |");
            System.out.println("| 5 | SALIR                        |");
            System.out.println("|==================================|");
            System.out.print("Ingrese una opcion (1 - 5): ");
            opc = scanner.nextInt();

            switch (opc) {
                case 1 -> RegistrarPasajero();
                case 2 -> ListarPasajero();
                case 3 -> ActualizarPasajero();
                case 4 -> EliminarPasajero();
                case 5 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion incorrecta, ingrese nuevamente...");
            }
        } while (opc != 5);
    }

    public void RegistrarPasajero() {
        System.out.println("\n--- REGISTRAR PASAJERO ---");
        scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Cédula: ");
        int cedula = scanner.nextInt();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String fechaStr = scanner.next();
        LocalDate fechaNacimiento = LocalDate.parse(fechaStr);

        int edad = pasajeroService.calcularEdad(fechaNacimiento);

        TipoPasajero tipo;
        if (edad >= 60) {
            tipo = TipoPasajero.ADULTO_MAYOR;
            System.out.println("Pasajero mayor de 60 años, se asigna ADULTO_MAYOR automáticamente (30% descuento).");
        } else {
            System.out.println("Tipo de pasajero:");
            System.out.println("  1. Regular     (sin descuento)");
            System.out.println("  2. Estudiante  (15% descuento)");
            System.out.print("Seleccione (1-2): ");
            int opcTipo = scanner.nextInt();
            switch (opcTipo) {
                case 2 -> tipo = TipoPasajero.ESTUDIANTE;
                default -> tipo = TipoPasajero.REGULAR;
            }
        }

        boolean exito = pasajeroService.crear(nombre, cedula, fechaNacimiento, tipo);
        System.out.println(exito ? "Pasajero registrado exitosamente." : "No se pudo registrar el pasajero.");
    }

    public void ListarPasajero() {
        System.out.println("\n--- LISTA DE PASAJEROS ---");
        pasajeroService.listarTodos();
    }

    public void BuscarPasajero() {
        System.out.println("\n--- BUSCAR PASAJERO ---");
        System.out.println("Buscar por:");
        System.out.println("  1. Cédula");
        System.out.println("  2. Tipo de pasajero");
        System.out.print("Seleccione (1-2): ");
        int opc = scanner.nextInt();

        switch (opc) {
            case 1 -> {
                System.out.print("Ingrese la cédula: ");
                int cedula = scanner.nextInt();
                Optional<Pasajero> resultado = pasajeroService.buscarPorCedula(cedula);
                if (resultado.isPresent()) {
                    Pasajero p = resultado.get();
                    System.out.println("Pasajero encontrado:");
                    System.out.println("  Nombre          : " + p.getNombre());
                    System.out.println("  Cédula          : " + p.getCedula());
                    System.out.println("  Tipo            : " + p.getTipoPasajero());
                    System.out.println("  Descuento       : " + (int)(p.getTipoPasajero().getDescuento() * 100) + "%");
                    System.out.println("  Fecha Nacimiento: " + p.getFechaNacimiento());
                } else {
                    System.out.println("No se encontró pasajero con esa cédula.");
                }
            }
            case 2 -> {
                System.out.println("Tipos disponibles:");
                System.out.println("  1. Regular");
                System.out.println("  2. Estudiante");
                System.out.println("  3. Adulto Mayor");
                System.out.print("Seleccione (1-3): ");
                int opcTipo = scanner.nextInt();

                TipoPasajero tipo;
                switch (opcTipo) {
                    case 1 -> tipo = TipoPasajero.REGULAR;
                    case 2 -> tipo = TipoPasajero.ESTUDIANTE;
                    case 3 -> tipo = TipoPasajero.ADULTO_MAYOR;
                    default -> {
                        System.out.println("Tipo inválido.");
                        return;
                    }
                }

                List<Pasajero> lista = pasajeroService.buscarPorTipo(tipo);
                if (lista.isEmpty()) {
                    System.out.println("No hay pasajeros de tipo: " + tipo);
                } else {
                    System.out.println("Pasajeros de tipo " + tipo + ":");
                    for (Pasajero p : lista) {
                        System.out.println("  - " + p.getNombre()
                                + " | Cédula: " + p.getCedula()
                                + " | Descuento: " + (int)(p.getTipoPasajero().getDescuento() * 100) + "%");
                    }
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    public void ActualizarPasajero() {
        System.out.println("\n--- ACTUALIZAR PASAJERO ---");
        System.out.println("¿Qué desea actualizar?");
        System.out.println("  1. Nombre");
        System.out.println("  2. Tipo de pasajero");
        System.out.print("Seleccione (1-2): ");
        int opc = scanner.nextInt();

        System.out.print("Ingrese la cédula del pasajero: ");
        int cedula = scanner.nextInt();

        switch (opc) {
            case 1 -> {
                scanner.nextLine();
                System.out.print("Nuevo nombre: ");
                String nuevoNombre = scanner.nextLine();
                boolean exito = pasajeroService.actualizarNombre(cedula, nuevoNombre);
                System.out.println(exito ? "Nombre actualizado." : "No se encontró el pasajero.");
            }
            case 2 -> {
                System.out.println("Nuevo tipo:");
                System.out.println("  1. Regular     (sin descuento)");
                System.out.println("  2. Estudiante  (15% descuento)");
                System.out.println("  3. Adulto Mayor (30% descuento)");
                System.out.print("Seleccione (1-3): ");
                int opcTipo = scanner.nextInt();

                TipoPasajero nuevoTipo;
                switch (opcTipo) {
                    case 1 -> nuevoTipo = TipoPasajero.REGULAR;
                    case 2 -> nuevoTipo = TipoPasajero.ESTUDIANTE;
                    case 3 -> nuevoTipo = TipoPasajero.ADULTO_MAYOR;
                    default -> {
                        System.out.println("Tipo inválido, operación cancelada.");
                        return;
                    }
                }

                boolean exito = pasajeroService.actualizarTipo(cedula, nuevoTipo);
                System.out.println(exito ? "Tipo actualizado." : "No se encontró el pasajero.");
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    public void EliminarPasajero() {
        System.out.println("\n--- ELIMINAR PASAJERO ---");
        System.out.print("Ingrese la cédula del pasajero a eliminar: ");
        int cedula = scanner.nextInt();

        System.out.print("¿Está seguro? (1 = Sí / 2 = No): ");
        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            boolean exito = pasajeroService.eliminar(cedula);
            System.out.println(exito ? "Pasajero eliminado." : "No se encontró el pasajero.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}