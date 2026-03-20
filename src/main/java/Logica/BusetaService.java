package Logica;
import java.util.List;
import Modelos.Buseta;
import Persistencia.BusetaRepository;

public class BusetaService {
    private BusetaRepository repo = BusetaRepository.getInstancia();

    public void guardar(Buseta v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) != null) {
            throw new Exception("Ya existe una Buseta con la placa: " + v.getPlaca());
        }
        repo.guardar(v);
    }

    public List<Buseta> listar() throws Exception {
        return repo.listar();
    }

    public Buseta buscarPorPlaca(String placa) throws Exception {
        return repo.buscarPorPlaca(placa);
    }

    public void actualizar(Buseta v) throws Exception {
        if (repo.buscarPorPlaca(v.getPlaca()) == null) {
            throw new Exception("No existe una Buseta con la placa: " + v.getPlaca());
        }
        repo.actualizar(v);
    }

    public void eliminar(String placa) throws Exception {
        if (repo.buscarPorPlaca(placa) == null) {
            throw new Exception("No existe una Buseta con la placa: " + placa);
        }
        repo.eliminar(placa);
    }

    public boolean tieneCupos(String placa) throws Exception {
        Buseta v = repo.buscarPorPlaca(placa);
        if (v == null) {
            throw new Exception("No existe una Buseta con la placa: " + placa);
        }
        return v.getCapacidad() > 0;
    }
}