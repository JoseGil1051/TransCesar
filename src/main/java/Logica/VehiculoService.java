package Logica;

import java.util.List;
import Modelos.Vehiculo;
import Persistencia.VehiculoRepository;

public class VehiculoService {
    private VehiculoRepository repo = VehiculoRepository.getInstancia();

    public void guardar(Vehiculo v) throws Exception {
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
        if (repo.buscarPorPlaca(v.getPlaca()) == null) {
            throw new Exception("No existe un vehiculo con la placa: " + v.getPlaca());
        }
        repo.actualizar(v);
    }

    public void eliminar(String placa) throws Exception {
        if (repo.buscarPorPlaca(placa) == null) {
            throw new Exception("No existe un vehiculo con la placa: " + placa);
        }
        repo.eliminar(placa);
    }

    public boolean tieneCupos(String placa) throws Exception {
        Vehiculo v = repo.buscarPorPlaca(placa);
        if (v == null) throw new Exception("No existe un vehiculo con la placa: " + placa);
        return v.getCapacidad() > 0;
    }
}