package Persistencia;

import Modelos.Conductor;
import Modelos.Pasajero;
import Modelos.Persona;
import Modelos.TipoPasajero;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaRepository {

    private static final String ARCHIVO_CONDUCTORES = "conductores.txt";
    private static final String ARCHIVO_PASAJEROS   = "pasajeros.txt";

    private List<Persona> personas = new ArrayList<>();

    public PersonaRepository() {
        cargarDesdeArchivos();
    }

    // ─────────────────────────────────────────────
    //  CREATE
    // ─────────────────────────────────────────────

    public boolean crear(Persona persona) {
        if (buscarPorCedula(persona.getCedula()).isPresent()) {
            System.out.println("Ya existe una persona con cédula: " + persona.getCedula());
            return false;
        }
        personas.add(persona);
        guardarEnArchivo(persona);
        System.out.println("Persona registrada: " + persona.getNombre());
        return true;
    }

    // ─────────────────────────────────────────────
    //  READ
    // ─────────────────────────────────────────────

    public List<Persona> obtenerTodos() {
        return new ArrayList<>(personas);
    }

    public Optional<Persona> buscarPorCedula(int cedula) {
        return personas.stream()
                .filter(p -> p.getCedula() == cedula)
                .findFirst();
    }

    public List<Persona> buscarPorNombre(String nombre) {
        List<Persona> resultado = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public void listarTodos() {
        if (personas.isEmpty()) {
            System.out.println("No hay personas registradas.");
            return;
        }
        System.out.println("=== Lista de Personas (" + personas.size() + ") ===");
        for (Persona p : personas) {
            if (p instanceof Conductor) {
                Conductor c = (Conductor) p;
                System.out.println("[Conductor] " + c.getNombre()
                        + " | Cédula: " + c.getCedula()
                        + " | Licencia: " + c.getNumerodelicencia()
                        + " | Categoría: " + c.getCategorialicencia());
            } else if (p instanceof Pasajero) {
                Pasajero pa = (Pasajero) p;
                System.out.println("[Pasajero]  " + pa.getNombre()
                        + " | Cédula: " + pa.getCedula()
                        + " | Tipo: " + pa.getTipoPasajero()
                        + " | Descuento: " + (int)(pa.getTipoPasajero().getDescuento() * 100) + "%");
            } else {
                System.out.println("[Persona]   " + p.getNombre()
                        + " | Cédula: " + p.getCedula());
            }
        }
    }

    // ─────────────────────────────────────────────
    //  UPDATE
    // ─────────────────────────────────────────────

    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        Optional<Persona> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            opt.get().setNombre(nuevoNombre);
            reescribirArchivos();
            System.out.println("Nombre actualizado: " + opt.get().getNombre());
            return true;
        }
        System.out.println("No se encontró persona con cédula: " + cedula);
        return false;
    }

    public boolean actualizarConductor(int cedula, int nuevoNumLicencia, String nuevaCategoria) {
        Optional<Persona> opt = buscarPorCedula(cedula);
        if (opt.isPresent() && opt.get() instanceof Conductor) {
            Conductor c = (Conductor) opt.get();
            c.setNumerodelicencia(nuevoNumLicencia);
            c.setCategorialicencia(nuevaCategoria);
            reescribirArchivos();
            System.out.println("Conductor actualizado: cédula=" + cedula
                    + ", licencia=" + nuevoNumLicencia + ", categoría=" + nuevaCategoria);
            return true;
        }
        System.out.println("No se encontró Conductor con cédula: " + cedula);
        return false;
    }

    public boolean actualizarPasajero(int cedula, TipoPasajero nuevoTipo) {
        Optional<Persona> opt = buscarPorCedula(cedula);
        if (opt.isPresent() && opt.get() instanceof Pasajero) {
            Pasajero p = (Pasajero) opt.get();
            p.setTipoPasajero(nuevoTipo);
            reescribirArchivos();
            System.out.println("Pasajero actualizado: cédula=" + cedula
                    + ", nuevoTipo=" + nuevoTipo);
            return true;
        }
        System.out.println("No se encontró Pasajero con cédula: " + cedula);
        return false;
    }

    // ─────────────────────────────────────────────
    //  DELETE
    // ─────────────────────────────────────────────

    public boolean eliminar(int cedula) {
        Optional<Persona> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            personas.remove(opt.get());
            reescribirArchivos();
            System.out.println("Persona eliminada con cédula: " + cedula);
            return true;
        }
        System.out.println("No se encontró persona con cédula: " + cedula);
        return false;
    }

    // ─────────────────────────────────────────────
    //  PERSISTENCIA
    // ─────────────────────────────────────────────

    private void guardarEnArchivo(Persona persona) {
        String archivo;
        String linea;

        if (persona instanceof Conductor) {
            Conductor c = (Conductor) persona;
            archivo = ARCHIVO_CONDUCTORES;
            linea   = c.getNombre() + ","
                    + c.getCedula() + ","
                    + c.getNumerodelicencia() + ","
                    + c.getCategorialicencia();
        } else if (persona instanceof Pasajero) {
            Pasajero p = (Pasajero) persona;
            archivo = ARCHIVO_PASAJEROS;
            linea   = p.getNombre() + ","
                    + p.getCedula() + ","
                    + p.getTipoPasajero().name(); // ← CAMBIADO
        } else {
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar en archivo: " + e.getMessage());
        }
    }

    private void reescribirArchivos() {
        try (BufferedWriter bwC = new BufferedWriter(new FileWriter(ARCHIVO_CONDUCTORES, false));
             BufferedWriter bwP = new BufferedWriter(new FileWriter(ARCHIVO_PASAJEROS,   false))) {

            for (Persona persona : personas) {
                if (persona instanceof Conductor) {
                    Conductor c = (Conductor) persona;
                    bwC.write(c.getNombre() + ","
                            + c.getCedula() + ","
                            + c.getNumerodelicencia() + ","
                            + c.getCategorialicencia());
                    bwC.newLine();
                } else if (persona instanceof Pasajero) {
                    Pasajero p = (Pasajero) persona;
                    bwP.write(p.getNombre() + ","
                            + p.getCedula() + ","
                            + p.getTipoPasajero().name()); // ← CAMBIADO
                    bwP.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al reescribir archivos: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivos() {
        cargarConductores();
        cargarPasajeros();
        System.out.println("[INFO] Datos cargados: "
                + personas.size() + " persona(s) encontrada(s) en los archivos.");
    }

    private void cargarConductores() {
        File file = new File(ARCHIVO_CONDUCTORES);
        if (!file.exists()) { crearArchivoVacio(ARCHIVO_CONDUCTORES); return; }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",", 4);
                if (partes.length < 4) continue;
                try {
                    String nombre    = partes[0].trim();
                    int    cedula    = Integer.parseInt(partes[1].trim());
                    int    numLic    = Integer.parseInt(partes[2].trim());
                    String categoria = partes[3].trim();
                    personas.add(new Conductor(nombre, cedula, numLic, categoria));
                } catch (NumberFormatException e) {
                    System.out.println("Línea de conductor inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar conductores: " + e.getMessage());
        }
    }

    private void cargarPasajeros() {
        File file = new File(ARCHIVO_PASAJEROS);
        if (!file.exists()) { crearArchivoVacio(ARCHIVO_PASAJEROS); return; }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;
                String[] partes = linea.split(",", 3);
                if (partes.length < 3) continue;
                try {
                    String nombre = partes[0].trim();
                    int    cedula = Integer.parseInt(partes[1].trim());
                    TipoPasajero tipo = TipoPasajero.valueOf(partes[2].trim().toUpperCase()); // ← CAMBIADO
                    personas.add(new Pasajero(nombre, cedula, tipo));
                } catch (IllegalArgumentException e) {
                    System.out.println("Línea de pasajero inválida ignorada: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar pasajeros: " + e.getMessage());
        }
    }

    private void crearArchivoVacio(String nombreArchivo) {
        try { new File(nombreArchivo).createNewFile(); } catch (IOException e) { }
    }
}