package datatypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import logica.enums.TipoAsiento;
import logica.enums.TipoReserva;

public class DTReserva {

	private int cantEquipajeExtra;
	private int cantPasajes;
	private float costo;
	private LocalDate fecha;
	private int idReserva;
	private String nombreVuelo;
	private Set<DTPasaje> pasajeros;
	private TipoAsiento tipoAsiento;

	private TipoReserva tipoReserva;

	public DTReserva(int idReserva, LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int cantEquipajeExtra, float costo, String nombreVuelo, Set<DTPasaje> pasajeros) {
		this.idReserva = idReserva;
		this.fecha = fecha;
		this.cantPasajes = cantPasajes;
		this.cantEquipajeExtra = cantEquipajeExtra;
		this.costo = costo;
		this.nombreVuelo = nombreVuelo;
		this.tipoAsiento = tipoAsiento;
		this.pasajeros = new HashSet<>(pasajeros);
		this.tipoReserva = TipoReserva.GENERAL;
	}

	public DTReserva(LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes, int cantEquipajeExtra, String nombreVuelo, Set<DTPasaje> pasajeros) {
		this.fecha = fecha;
		this.tipoAsiento = tipoAsiento;
		this.cantPasajes = cantPasajes;
		this.cantEquipajeExtra = cantEquipajeExtra;
		this.nombreVuelo = nombreVuelo;
		this.pasajeros = new HashSet<>(pasajeros);
		this.tipoReserva = TipoReserva.GENERAL;
	}

	public DTReserva(int idReserva, LocalDate fecha, TipoAsiento tipoAsiento, int cantPasajes,
			int cantEquipajeExtra, float costo, String nombreVuelo, Set<DTPasaje> dtPasajeros,
			TipoReserva tipoReserva) {
		this(idReserva, fecha, tipoAsiento, cantPasajes, cantEquipajeExtra, costo, nombreVuelo, dtPasajeros);
		this.tipoReserva = tipoReserva;
	}

	public int getCantEquipajeExtra() {
		return this.cantEquipajeExtra;
	}

	public int getCantPasajes() {
		return this.cantPasajes;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public int getId() {
		return this.idReserva;
	}

	public String getVuelo() {
		return this.nombreVuelo;
	}

	public void setId(int idReserva) {
		this.idReserva = idReserva;
	}

	public String toString() {
		return this.fecha.toString() + ", Vuelo: " + this.nombreVuelo + ", Cantidad de pasajes: " + Integer.toString(this.cantPasajes) + ", Costo total: " + Float.toString(this.costo);
	}

	public TipoAsiento getTipoAsiento() {
		return this.tipoAsiento;
	}

	public Set<DTPasaje> getPasajes() {
		return this.pasajeros;
	}

	public float getCosto() {
		return this.costo;
	}

	public TipoReserva getTipoReserva() {
		return this.tipoReserva;
	}

	public void setTipoReserva(TipoReserva tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

}
