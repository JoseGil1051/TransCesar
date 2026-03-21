package Logica;

import java.util.List;
import Modelos.Bus;
import Persistencia.BusRepository;

public class BusService {
    private BusRepository repo = BusRepository.getInstancia();

    public void guardar(Bus v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) != null) {
            throw new Exception("Ya existe un Bus con la placa: " + v.getPlaca());
        }
        repo.guardar(v);
    }

    public List<Bus> listar() throws Exception {
        return repo.listar();
    }

    public Bus buscarPorPlaca(String placa) throws Exception {
        return repo.buscarPorPlaca(placa);
    }

    public void actualizar(Bus v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) == null) {
            throw new Exception("No existe un Bus con la placa: " + v.getPlaca());
        }
        repo.actualizar(v);
    }

    public void eliminar(String placa) throws Exception {
        if (repo.buscarPorPlaca(placa) == null) {
            throw new Exception("No existe un Bus con la placa: " + placa);
        }
        repo.eliminar(placa);
    }

    public boolean tieneCupos(String placa) throws Exception {
        Bus v = repo.buscarPorPlaca(placa);
        if (v == null) throw new Exception("No existe un Bus con la placa: " + placa);
        return v.getCapacidad() > 0;
    }
}