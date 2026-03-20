
package Modelos;

public class Persona {
    protected String nombre;
    protected int cedula;
    
    public Persona() {
    }

    public Persona(String nombre, int cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    public int getCedula() {
        return cedula;
    }

    
    public void setCedula(int cedula) {
        this.cedula = cedula;
    }    

    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", cedula=" + cedula + '}';
    }
}
