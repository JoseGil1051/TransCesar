/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;
import Modelos.Vehiculo;
import Persistencia.VehiculoRepository;
import java.util.List;
/**
 *
 * @author HP
 */
public class VehiculoService {
   
    private VehiculoRepository repo = VehiculoRepository.getInstancia();

    public void guardar(Vehiculo v) throws Exception {
        // Validar que no exista un vehiculo con la misma placa
        if (repo.buscarPorPlaca(v.getPlaca()) != null) {
            throw new Exception("Ya existe un vehiculo con la placa: " + v.getPlaca());
        }
        repo.guardar(v);
    }

    public List<Vehiculo> listar() throws Exception {
        return repo.listar();
    }

    public Vehiculo buscarPorPlaca(String placa) throws Exception {
        return repo.buscarPorPlaca(placa);
    }

    public void actualizar(Vehiculo v) throws Exception {
        // Validar que el vehiculo exista antes de actualizar
        if (repo.buscarPorPlaca(v.getPlaca()) == null) {
            throw new Exception("No existe un vehiculo con la placa: " + v.getPlaca());
        }
        repo.actualizar(v);
    }

    public void eliminar(String placa) throws Exception {
        // Validar que el vehiculo exista antes de eliminar
        if (repo.buscarPorPlaca(placa) == null) {
            throw new Exception("No existe un vehiculo con la placa: " + placa);
        }
        repo.eliminar(placa);
    }

    // Validar que el vehiculo tenga cupos disponibles antes de vender ticket
    public boolean tieneCupos(String placa) throws Exception {
        Vehiculo v = repo.buscarPorPlaca(placa);
        if (v == null) {
            throw new Exception("No existe un vehiculo con la placa: " + placa);
        }
        return v.getCapacidad() > 0;
    }
}

