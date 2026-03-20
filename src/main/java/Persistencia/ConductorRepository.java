package Persistencia;

import Modelos.Conductor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConductorRepository {

    private static final String ARCHIVO = "conductores.txt";
    private static final String REGISTRO = "RegistroPersonas.txt";
    
    private static ConductorRepository instancia;
    public static ConductorRepository getInstancia() {
        if (instancia == null) instancia = new ConductorRepository();
        return instancia;
    }

    private List<Conductor> conductores = new ArrayList<>();

    public ConductorRepository() {
        cargarDesdeArchivo();
    }

  
    public boolean crear(Conductor conductor) {
        if (buscarPorCedula(conductor.getCedula()).isPresent()) {
            System.out.println(" Ya existe un conductor con cédula: " + conductor.getCedula());
            return false;
        }
        conductores.add(conductor);
        guardarEnArchivo(conductor);
        registrarEnRegistroGeneral(conductor);
        System.out.println(" Conductor registrado: " + conductor.getNombre());
        return true;
    }


    public List<Conductor> obtenerTodos() {
        return new ArrayList<>(conductores);
    }

    public Optional<Conductor> buscarPorCedula(int cedula) {
        return conductores.stream()
                .filter(c -> c.getCedula() == cedula)
                .findFirst();
    }

    public List<Conductor> buscarPorCategoria(String categoria) {
        List<Conductor> resultado = new ArrayList<>();
        for (Conductor c : conductores) {
            if (c.getCategorialicencia().equalsIgnoreCase(categoria)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public void listarTodos() {
        if (conductores.isEmpty()) {
            System.out.println("No hay conductores registrados.");
            return;
        }
        System.out.println("=== Lista de Conductores (" + conductores.size() + ") ===");
        for (Conductor c : conductores) {
            System.out.println("[Conductor] " + c.getNombre()
                    + " | Cédula: " + c.getCedula()
                    + " | Licencia: " + c.getNumerodelicencia()
                    + " | Categoría: " + c.getCategorialicencia());
        }
    }

 

    public boolean actualizarNombre(int cedula, String nuevoNombre) {
        Optional<Conductor> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            opt.get().setNombre(nuevoNombre);
            reescribirArchivo();
            System.out.println(" Nombre actualizado: " + opt.get().getNombre());
            return true;
        }
        System.out.println(" No se encontró conductor con cédula: " + cedula);
        return false;
    }

    public boolean actualizarLicencia(int cedula, int nuevoNumLicencia, String nuevaCategoria) {
        Optional<Conductor> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            opt.get().setNumerodelicencia(nuevoNumLicencia);
            opt.get().setCategorialicencia(nuevaCategoria);
            reescribirArchivo();
            System.out.println(" Licencia actualizada para cédula: " + cedula);
            return true;
        }
        System.out.println(" No se encontró conductor con cédula: " + cedula);
        return false;
    }

    

    public boolean eliminar(int cedula) {
        Optional<Conductor> opt = buscarPorCedula(cedula);
        if (opt.isPresent()) {
            conductores.remove(opt.get());
            reescribirArchivo();
            System.out.println(" Conductor eliminado con cédula: " + cedula);
            return true;
        }
        System.out.println(" No se encontró conductor con cédula: " + cedula);
        return false;
    }

   
    private void guardarEnArchivo(Conductor c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(c.getNombre() + "," + c.getCedula() + ","
                    + c.getNumerodelicencia() + "," + c.getCategorialicencia());
            bw.newLine();
        } catch (IOException e) {
        }
    }

    private void registrarEnRegistroGeneral(Conductor c) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REGISTRO, true))) {
            bw.write("CONDUCTOR," + c.getNombre() + "," + c.getCedula() + ","
                    + c.getNumerodelicencia() + "," + c.getCategorialicencia());
            bw.newLine();
        } catch (IOException e) {
        }
    }

    private void reescribirArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false))) {
            for (Conductor c : conductores) {
                bw.write(c.getNombre() + "," + c.getCedula() + ","
                        + c.getNumerodelicencia() + "," + c.getCategorialicencia());
                bw.newLine();
            }
        } catch (IOException e) {
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
                String[] p = linea.split(",", 4);
                if (p.length < 4) continue;
                conductores.add(new Conductor(p[0],
                        Integer.parseInt(p[1].trim()),
                        Integer.parseInt(p[2].trim()),
                        p[3]));
            }
        } catch (IOException e) {
        }
        System.out.println("[INFO] Conductores cargados: " + conductores.size());
    }
}