package logica.vuelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import datatypes.DTRutaVuelo;
import logica.enums.EstadoRuta;
import logica.usuario.Aerolinea;
import logica.vuelo.categoria.Categoria;
import logica.vuelo.ciudad.Ciudad;

public class RutaVuelo {

	private Set<Categoria> categorias;
	private Ciudad ciudadDestino;
	private Ciudad ciudadOrigen;
	private Costos costos;
	private String descripcion;
	private String descripcionCorta;
	private LocalDate fechaAlta;
	private LocalTime hora;
	private String nombre;
	private Set<Vuelo> vuelos;
	private byte[] imagen;
	private EstadoRuta estado;
	private Aerolinea aerolinea;

	public RutaVuelo(String nombre, String descripcion, LocalTime hora, LocalDate fechaAlta, Set<Categoria> categorias, Ciudad ciudadDestino, Ciudad ciudadOrigen, Costos costos, byte[] imagen, Aerolinea aerolinea, String descripcionCorta) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.hora = hora;
		this.fechaAlta = fechaAlta;
		this.categorias = categorias;
		this.ciudadDestino = ciudadDestino;
		this.ciudadOrigen = ciudadOrigen;
		this.costos = costos;
		this.vuelos = new HashSet<>();
		this.imagen = imagen;
		this.estado = EstadoRuta.INGRESADA;
		this.aerolinea = aerolinea;
		this.descripcionCorta = descripcionCorta;
	}

	public void agregarVuelo(Vuelo vuelo) {
		this.vuelos.add(vuelo);
	}

	public boolean existeVuelo(String nombre) {
		for (Vuelo vuelo : this.vuelos) {
			if (vuelo.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}

	public Set<Categoria> getCategorias() {
		return this.categorias;
	}

	public DTRutaVuelo getDT() {
		return new DTRutaVuelo(this.nombre, this.descripcion, this.descripcionCorta, this.hora, this.fechaAlta, this.categorias, this.ciudadOrigen.getDT(), this.ciudadDestino.getDT(), this.costos.getDT(), this.imagen, this.aerolinea.getNickname(), this.aerolinea.getNombre(), this.estado);
	}

	public EstadoRuta getEstado() {
		return this.estado;
	}

	public LocalTime getHora() {
		return this.hora;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Costos obtenerCostos() {
		return this.costos;
	}

	public Vuelo obtenerVuelo(String nombre) {
		for (Vuelo vuelo : this.vuelos) {
			if (vuelo.getNombre().equals(nombre)) {
				return vuelo;
			}
		}
		return null;
	}
	
	public Set<Vuelo> obtenerVuelos() {
		return vuelos;
	}

	public void setEstado(EstadoRuta estado) {
		this.estado = estado;
	}

}
