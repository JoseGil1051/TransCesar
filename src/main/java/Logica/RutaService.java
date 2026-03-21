package Logica;

import java.util.List;
import Modelos.Ruta;
import Persistencia.RutaRepository;

public class RutaService {
    private RutaRepository repo = RutaRepository.getInstancia();

    public void guardar(Ruta r) throws Exception {
        if (repo.buscarPorCodigo(r.getCodigoRuta()) != null) {
            throw new Exception("Ya existe una ruta con el codigo: " + r.getCodigoRuta());
        }
        repo.guardar(r);
    }

    public List<Ruta> listar() throws Exception {
        return repo.listar();
    }

    public Ruta buscarPorCodigo(String codigo) throws Exception {
        return repo.buscarPorCodigo(codigo);
    }

    public void actualizar(Ruta r) throws Exception {
        if (repo.buscarPorCodigo(r.getCodigoRuta()) == null) {
            throw new Exception("No existe una ruta con el codigo: " + r.getCodigoRuta());
        }
        repo.actualizar(r);
    }

    public void eliminar(String codigo) throws Exception {
        if (repo.buscarPorCodigo(codigo) == null) {
            throw new Exception("No existe una ruta con el codigo: " + codigo);
        }
        repo.eliminar(codigo);
    }
}