package Logica;

import java.util.List;
import Modelos.MicroBus;
import Persistencia.MicroBusRepository;

public class MicroBusService {
    private MicroBusRepository repo = MicroBusRepository.getInstancia();

    public void guardar(MicroBus v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) != null) {
            throw new Exception("Ya existe un MicroBus con la placa: " + v.getPlaca());
        }
        repo.guardar(v);
    }

    public List<MicroBus> listar() throws Exception {
        return repo.listar();
    }

    public MicroBus buscarPorPlaca(String placa) throws Exception {
        return repo.buscarPorPlaca(placa);
    }

    public void actualizar(MicroBus v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) == null) {
            throw new Exception("No existe un MicroBus con la placa: " + v.getPlaca());
        }
        repo.actualizar(v);
    }

    public void eliminar(String placa) throws Exception {
        if (repo.buscarPorPlaca(placa) == null) {
            throw new Exception("No existe un MicroBus con la placa: " + placa);
        }
        repo.eliminar(placa);
    }

    public boolean tieneCupos(String placa) throws Exception {
        MicroBus v = repo.buscarPorPlaca(placa);
        if (v == null) throw new Exception("No existe un MicroBus con la placa: " + placa);
        return v.getCapacidad() > 0;
    }
}