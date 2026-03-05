package logica.paquete;

import java.util.HashSet;
import java.util.Set;

import datatypes.DTPaquete;
import logica.enums.TipoAsiento;
import logica.vuelo.RutaVuelo;

public class Paquete {

	private String nombre;
	private String descripcion;
	private int validezDias;
	private float descuento;
	private float costo;
	private Set<RutaPaquete> rutaPaquetes;

	public Paquete(String nombre, String descripcion, int validezDias, float descuento, float costo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.validezDias = validezDias;
		this.descuento = descuento;
		this.costo = costo;
		this.rutaPaquetes = new HashSet<>();
	}

	public void agregarRuta(RutaVuelo ruta, TipoAsiento tipo, int cantidad) {
		RutaPaquete rutaPaquete = new RutaPaquete(tipo, cantidad, ruta);
		this.rutaPaquetes.add(rutaPaquete);
	}

	public float getCosto() {
		return this.costo;
	}

	public DTPaquete getDT() {
		return new DTPaquete(this.nombre, this.descripcion, this.validezDias, this.descuento, this.costo);
	}

	public RutaPaquete getRutaPaquete(RutaVuelo rutaVuelo) {
		for (RutaPaquete rutaPaquete : this.rutaPaquetes) {
			if (rutaPaquete.getRutaVuelo().equals(rutaVuelo))
				return rutaPaquete;
		}
		return null;
	}

	public int getValidezDias() {
		return this.validezDias;
	}

}
