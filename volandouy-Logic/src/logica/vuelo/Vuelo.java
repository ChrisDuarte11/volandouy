package logica.vuelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import datatypes.DTReserva;
import datatypes.DTVuelo;
import logica.enums.TipoAsiento;
import logica.fabrica.Fabrica;
import logica.usuario.Reserva;

public class Vuelo {

	private String nombre;
	private int asientosMaxEjecutivo;
	private int asientosMaxTurista;
	private LocalTime duracion;
	private LocalDate fecha;
	//private LocalDate fechaAlta;
	private byte[] imagen;
	private Map<Integer, Reserva> reservas;
	//private Map<String, Cliente> clientes;

	public Vuelo(String nombre, int asientos_max_e, int asientos_max_t, LocalTime duracion, LocalDate fecha, LocalDate fechaAlta, byte[] imagen) {
		this.nombre = nombre;
		this.asientosMaxEjecutivo = asientos_max_e;
		this.asientosMaxTurista = asientos_max_t;
		this.duracion = duracion;
		this.fecha = fecha;
		//this.fechaAlta = fechaAlta;
		this.reservas = new HashMap<>();
		//this.clientes = new HashMap<>();
		this.imagen = imagen;
	}

	public void actualizarAsientosDisponibles(TipoAsiento tipoAsiento, int cantidad) {
		if (tipoAsiento.equals(TipoAsiento.TURISTA)) {
			this.asientosMaxTurista -= cantidad;
		} else {
			this.asientosMaxEjecutivo -= cantidad;
		}
	}

	public void agregarReserva(Reserva reserva) {
		this.reservas.put(reserva.getId(), reserva);
	}

	public DTVuelo getDT() {
		String nomRuta = Fabrica.getInstance().getIRutaVuelo().obtenerRuta(new DTVuelo(this.nombre)).getNombre();
		DTVuelo vuelo = new DTVuelo(this.nombre, this.asientosMaxEjecutivo, this.asientosMaxTurista, this.duracion, this.fecha, nomRuta, this.imagen);
		return vuelo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public Set<DTReserva> obtenerReservas(){
		Set<DTReserva> ret = new HashSet<>();
		for (Reserva res: reservas.values()) {
			ret.add(res.getDT());
		}
		return ret;
	}

	public boolean tieneReserva(DTReserva dtReserva) {
		return reservas.containsKey(dtReserva.getId());
	}

	public int getAsientosEjecutivo() {
		return this.asientosMaxEjecutivo;
	}

	public int getAsientosTurista() {
		return this.asientosMaxTurista;
	}


}
