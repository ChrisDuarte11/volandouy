package datatypes;

public class DTPasaje {
    private String nombre;
    private String apellido;

    public DTPasaje(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    
    public String toString() {
    	return this.apellido + ", " + this.nombre;
    }

}
