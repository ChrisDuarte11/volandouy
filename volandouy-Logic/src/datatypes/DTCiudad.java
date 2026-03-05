package datatypes;

import java.time.LocalDate;

public class DTCiudad {

	private String aeropuerto;
	private String descripcion;
	private LocalDate fechaAlta;
	private String nombre;
	private String pais;
	private String web;

	public DTCiudad(String nombre, String descripcion, String web, String pais, String aeropuerto, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.web = web;
		this.pais = pais;
		this.aeropuerto = aeropuerto;
		this.fechaAlta = fechaAlta;
	}

	public String getAeropuerto() {
		return this.aeropuerto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getPais() {
		return this.pais;
	}

	public String getWeb() {
		return this.web;
	}

	public void setAeropuerto(String aeropuerto) {
		this.aeropuerto = aeropuerto;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}
