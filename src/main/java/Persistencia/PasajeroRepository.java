package Persistencia;

import Modelos.Pasajero;
import Modelos.TipoPasajero;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PasajeroRepository {

    private static final String ARCHIVO = "pasajeros.txt";
    private static final String REGISTRO = "RegistroPersonas.txt";

    // ── SINGLETON ──────────────────────────────────
    private static PasajeroRepository instancia;

    public static PasajeroRepository getInstancia() {
        if (instancia == null) {
            instancia = new PasajeroRepository();
        }
        return instancia;
    }
    // ───────────────────────────────────────────────

    private List<Pasajero> pasajeros = new ArrayList<>();

    private PasajeroRepository() {
        cargarDesdeArchivo();
    }

    // ─────────────────────────────────────────────
    //  CREATE
    // ─────────────────────────────────────────────

    public boolean crear(Pasajero pasajero) {
        if (buscarPorCedula(pasajero.getCedula()).isPresent()) {
            System.out.println("Ya existe un pasajero con cédula: " + pasajero.getCedula());
            return false;
        }
        pasajeros.add(pasajero);
        guardarEnArchivo(pasajero);
        registrarEnRegistroGeneral(pasajero);
        System.out.println("Pasajero registrado: " + pasajero.getNombre());
        return true;
    }

    // ─────────────────────────────────────────────
    //  READ
    // ─────────────────────────────────────────────

    public Optional<Pasajero> buscarPorCedula(int cedula) {
        return pasajeros.stream()
                .filter(p -> p.getCedula() == cedula)
                .findFirst();
    }

    public List<Pasajero> buscarPorTipo(TipoPasajero tipo) {
        return pasajeros.stream()
                .filter(p -> p.getTipoPasajero() == tipo)
                .collect(java.util.stream.Collectors.toList());
    }

    public void listarTodos() {
        if (pasajeros.isEmpty()) {
            System.out.println("No hay pasajeros registrados.");
            return;
        }
        System.out.println("=== Lista de Pasajeros (" + pasajeros.size() + ") ===");
        for (Pasajero p : pasajeros) {
            System.out.println("[Pasajero] " + p.getNombre()
                    + " | Cédula: " + p.getCedula()
                    + " | Tipo: " + p.getTipoPasajero()
                    + " | Descuento: " + (int)(p.getTipoPasajero().getDescuento() * 100) + "%"
                    + " | Fecha Nac: " + p.getFechaNacimiento());
        }
    }

    // ─────────────────────────────────────────────
    //  UPDATE
    // ─────────────────────────────────────────────

    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        Optional<Pasajero> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            opt.get().setNombre(nuevoNombre);
            reescribirArchivo();
            System.out.println("Nombre actualizado: " + opt.get().getNombre());
            return true;
        }
        System.out.println("No se encontró pasajero con cédula: " + cedula);
        return false;
    }

    public boolean actualizarTipo(int cedula, TipoPasajero nuevoTipo) {
        Optional<Pasajero> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            opt.get().setTipoPasajero(nuevoTipo);
            reescribirArchivo();
            System.out.println("Tipo actualizado para cédula: " + cedula);
            return true;
        }
        System.out.println("No se encontró pasajero con cédula: " + cedula);
        return false;
    }

    // ─────────────────────────────────────────────
    //  DELETE
    // ─────────────────────────────────────────────

    public boolean eliminar(int cedula) {
        Optional<Pasajero> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            pasajeros.remove(opt.get());
            reescribirArchivo();
            System.out.println("Pasajero eliminado con cédula: " + cedula);
            return true;
        }
        System.out.println("No se encontró pasajero con cédula: " + cedula);
        return false;
    }

    // ─────────────────────────────────────────────
    //  PERSISTENCIA
    // ─────────────────────────────────────────────

    private void guardarEnArchivo(Pasajero p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(p.getNombre() + "," + p.getCedula() + ","
                    + p.getTipoPasajero().name() + ","
                    + p.getFechaNacimiento());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar pasajero: " + e.getMessage());
        }
    }

    private void registrarEnRegistroGeneral(Pasajero p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REGISTRO, true))) {
            bw.write("PASAJERO," + p.getNombre() + "," + p.getCedula() + ","
                    + p.getTipoPasajero().name() + ","
                    + p.getFechaNacimiento());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al registrar en registro general: " + e.getMessage());
        }
    }

    private void reescribirArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Pasajero p : pasajeros) {
                bw.write(p.getNombre() + "," + p.getCedula() + ","
                        + p.getTipoPasajero().name() + ","
                        + p.getFechaNacimiento());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir archivo: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { }
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",", 4);
                if (partes.length < 4) continue;
                try {
                    String nombre     = partes[0].trim();
                    int cedula        = Integer.parseInt(partes[1].trim());
                    TipoPasajero tipo = TipoPasajero.valueOf(partes[2].trim().toUpperCase());
                    LocalDate fecha   = LocalDate.parse(partes[3].trim());
                    pasajeros.add(new Pasajero(nombre, cedula, tipo, fecha));
                } catch (IllegalArgumentException e) {
                    System.out.println("Línea inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar pasajeros: " + e.getMessage());
        }
        System.out.println("[INFO] Pasajeros cargados: " + pasajeros.size());
    }
}