package logica.usuario;

import datatypes.DTPasaje;

public class Pasaje {

	private String nombre;
	private String apellido;

	public Pasaje(DTPasaje pasaje) {
		this.nombre = pasaje.getNombre();
		this.apellido = pasaje.getApellido();
	}

	public Pasaje(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public String getApellido() {
		return this.apellido;
	}

	public DTPasaje getDT() {
		return new DTPasaje(this.nombre, this.apellido);
	}

	public String getNombre() {
		return this.nombre;
	}

}
