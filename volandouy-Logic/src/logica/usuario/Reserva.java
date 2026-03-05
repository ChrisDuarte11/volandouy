package logica.usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import datatypes.DTPasaje;
import datatypes.DTReserva;
import logica.enums.TipoAsiento;
import logica.enums.TipoReserva;

public class Reserva {

	private static int nextId = 0;
	private int cantEquipajeExtra;
	private int cantPasajes;
	private float costo;
	private LocalDate fecha;
	private int idReserva;
	private String nombreVuelo;
	private Set<Pasaje> pasajeros;
	private TipoAsiento tipoAsiento;

	private TipoReserva tipoReserva;

	public Reserva() {
		idReserva = nextId++;
	}

	public Reserva(LocalDate fecha, int cantPasajes, int cantEquipajeExtra, float costo, TipoAsiento tipoAsiento, Set<DTPasaje> pasajeros, String nombreVuelo) {
		this.fecha = fecha;
		this.cantPasajes = cantPasajes;
		this.cantEquipajeExtra = cantEquipajeExtra;
		this.costo = costo;
		this.tipoAsiento = tipoAsiento;
		this.nombreVuelo = nombreVuelo;
		this.tipoReserva = TipoReserva.GENERAL;
		idReserva = nextId++;

		this.pasajeros = new HashSet<>();
		for (DTPasaje pasaje : pasajeros) {
			this.pasajeros.add(new Pasaje(pasaje));
		}
	}

	public Reserva(LocalDate fecha, int cantPasajes, int cantEquipajeExtra, float costo, TipoAsiento tipoAsiento, Set<DTPasaje> pasajeros, String nombreVuelo, TipoReserva tipoReserva) {
		this(fecha, cantPasajes, cantEquipajeExtra, costo, tipoAsiento, pasajeros, nombreVuelo);
		this.tipoReserva = tipoReserva;
	}

	public DTReserva getDT() {
		Set<DTPasaje> dtPasajeros = new HashSet<>();
		for (Pasaje pasajero : this.pasajeros) {
			dtPasajeros.add(pasajero.getDT());
		}

		return new DTReserva(this.idReserva, this.fecha, this.tipoAsiento, this.cantPasajes, this.cantEquipajeExtra, this.costo, nombreVuelo, dtPasajeros, tipoReserva);
	}

	public Integer getId() {
		return this.idReserva;
	}

	public String getVuelo() {
		return this.nombreVuelo;
	}

	public void setTipoReserva(TipoReserva tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

}
