package logica.usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTAerolinea;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import logica.enums.EstadoRuta;
import logica.vuelo.RutaVuelo;

public class Aerolinea extends Usuario {
	private String descripcion;
	private String web;
	private Set<RutaVuelo> rutasVuelo;

	public Aerolinea(String nick, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String descripcion, String web) {
		super(nick, nombre, correo, password, imagen, fechaAlta);
		this.descripcion = descripcion;
		this.web = web;
		this.rutasVuelo = new HashSet<RutaVuelo>();
	}

	public void agregarRutaVuelo(RutaVuelo rutaVuelo) {
		this.rutasVuelo.add(rutaVuelo);
	}

	public void editar(DTAerolinea dtAerolinea) {
		this.setNombre(dtAerolinea.getNombre());
		this.setPassword(dtAerolinea.getPassword());
		this.setImagen(dtAerolinea.getImagen());
		this.descripcion = dtAerolinea.getDescripcion();
		this.web = dtAerolinea.getWeb();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	@Override
	public DTUsuario getDT() {
		return new DTAerolinea(this.getNickname(), this.getNombre(), this.getCorreo(), this.getPassword(), this.getImagen(), this.getFechaAlta(), this.descripcion, this.web);
	}

	public String getWeb() {
		return this.web;
	}

	public Set<DTRutaVuelo> obtenerRutasVuelo() {
		return rutasVuelo.stream()
				.map(ruta -> ruta.getDT())
				.collect(Collectors.toSet());
	}

	public Set<DTRutaVuelo> obtenerRutasVuelo(EstadoRuta estado) {
		Set<DTRutaVuelo> dtRutas = new HashSet<DTRutaVuelo>();
		for (RutaVuelo ruta: this.rutasVuelo) {
			if (ruta.getEstado().equals(estado)) {
				dtRutas.add(ruta.getDT());
			}
		}
		return dtRutas;
	}


}
