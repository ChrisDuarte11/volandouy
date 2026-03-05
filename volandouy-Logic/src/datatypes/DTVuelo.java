package datatypes;

import java.time.LocalDate;
import java.time.LocalTime;

public class DTVuelo {

	private String nombre;
	private LocalDate fecha;
	private LocalTime duracion;
	private int asientosMaxEjecutivo;
	private int asientosMaxTurista;
	private String nombreRuta;
	private byte[] imagen;

	public DTVuelo() {}

	public DTVuelo(String nombre) {
		this.nombre = nombre;
	}

	public DTVuelo(String nombre, int asientosMaxEjecutivo, int asientosMaxTurista, LocalTime duracion, LocalDate fecha, String nombreRuta, byte[] imagen) {
		this.nombre = nombre;
		this.asientosMaxEjecutivo = asientosMaxEjecutivo;
		this.asientosMaxTurista = asientosMaxTurista;
		this.duracion = duracion;
		this.fecha = fecha;
		this.nombreRuta = nombreRuta;
		this.imagen = imagen;
	}

	public int getAsientosMaxEjecutivo() {
		return this.asientosMaxEjecutivo;
	}

	public int getAsientosMaxTurista() {
		return this.asientosMaxTurista;
	}

	public LocalTime getDuracion() {
		return this.duracion;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String toString() {
		return this.nombre + ", Fecha: " + this.fecha.toString();
	}

	public String getNombreRuta() {
		return this.nombreRuta;
	}
	
	public byte[] getImagen() {
		return this.imagen;
	}
}
