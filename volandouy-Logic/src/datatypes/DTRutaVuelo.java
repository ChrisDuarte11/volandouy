package datatypes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import logica.enums.EstadoRuta;
import logica.vuelo.categoria.Categoria;

public class DTRutaVuelo {

	private Set<Categoria> categorias;
	private DTCiudad ciudadDestino;
	private DTCiudad ciudadOrigen;
	private DTCostos costos;
	private String descripcion;
	private String descripcionCorta;
	private LocalDate fechaAlta;
	private LocalTime hora;
	private String nombre;
	private byte[] imagen;
	private String nickAerolinea;
	private String nombreAerolinea;
	private EstadoRuta estado;

	public DTRutaVuelo(String nombre) {
		this.nombre = nombre;
	}

	public DTRutaVuelo(String nombre, String descripcion, LocalTime hora, LocalDate fechaAlta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.hora = hora;
		this.fechaAlta = fechaAlta;
	}

	public DTRutaVuelo(String nombre, String descripcion, String descripcionCorta , LocalTime hora, LocalDate fechaAlta, Set<Categoria> categorias, DTCiudad ciudadOrigen, DTCiudad ciudadDestino, DTCostos costos, byte[] imagen, String nickAerolinea, String nombreAerolinea, EstadoRuta estado) {
		this(nombre, descripcion, hora, fechaAlta);
		this.descripcionCorta = descripcionCorta;
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.costos = costos;
		this.imagen = imagen;
		this.nickAerolinea = nickAerolinea;
		this.nombreAerolinea = nombreAerolinea;
		this.categorias = categorias;
		this.estado = estado;
	}

	public DTCiudad getCiudadDestino() {
		return ciudadDestino;
	}

	public DTCiudad getCiudadOrigen() {
		return this.ciudadOrigen;
	}

	public DTCostos getCostos() {
		return this.costos;
	}

	public String getDescripcion() {
		return this.descripcion;
	}
	
	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}

	public LocalTime getHora() {
		return this.hora;
	}

	public byte[] getImagen() {
		return this.imagen;
	}
	
	public String getNickAerolinea() {
		return this.nickAerolinea;
	}
	
	public Set<Categoria> getCategorias() {
		return this.categorias;
	}
	
	public EstadoRuta getEstado() {
		return this.estado;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public String getNombreAerolinea() {
		return this.nombreAerolinea;
	}
	
	public void setCiudadDestino(DTCiudad ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public void setCostos(DTCostos costos) {
		this.costos = costos;
	}

	@Override
	public String toString() {
		return this.nombre + ", Origen: " + this.ciudadOrigen.getNombre() + ", Destino: " + this.ciudadDestino.getNombre() + ", Hora: " + this.hora.toString();
	}

}

