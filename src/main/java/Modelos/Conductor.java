
package Modelos;

import java.util.List;

public class Conductor extends Persona{
    private static final List<String> CATEGORIAS_VALIDAS = List.of("B1", "B2", "C1", "C2");

    private int numerodelicencia;
    private String categorialicencia;
    
    public Conductor() {
    }

    public Conductor(String nombre, int cedula, int numerodelicencia, String categorialicencia) {
        super(nombre, cedula);
        this.numerodelicencia = numerodelicencia;
        setCategorialicencia(categorialicencia); // usa el setter con validación
    }

    public int getNumerodelicencia() {
        return numerodelicencia;
    }

    public void setNumerodelicencia(int numerodelicencia) {
        this.numerodelicencia = numerodelicencia;
    }

    public String getCategorialicencia() {
        return categorialicencia;
    }

    public void setCategorialicencia(String categorialicencia) {
        if (categorialicencia == null || !CATEGORIAS_VALIDAS.contains(categorialicencia.toUpperCase())) {
            throw new IllegalArgumentException(
                "Categoría inválida: '" + categorialicencia + "'. Debe ser B1, B2, C1 o C2."
            );
        }
        this.categorialicencia = categorialicencia.toUpperCase();
    }

    // Método clave: verifica si tiene licencia registrada
    public boolean tieneLicenciaRegistrada() {
        return numerodelicencia > 0 && categorialicencia != null && !categorialicencia.isBlank();
    }

}
