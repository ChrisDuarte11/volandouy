package logica.paquete;

import logica.enums.TipoAsiento;
import logica.vuelo.RutaVuelo;

public class RutaPaquete {

	private Integer cantidadRutas;
	private RutaVuelo rutaVuelo;
	private TipoAsiento tipoAsiento;

	public RutaPaquete(TipoAsiento tipoAsiento, Integer cantidadRutas, RutaVuelo rutaVuelo) {
		this.tipoAsiento = tipoAsiento;
		this.cantidadRutas = cantidadRutas;
		this.rutaVuelo = rutaVuelo;
	}

	public Integer getCantidadRutas() {
		return this.cantidadRutas;
	}

	public RutaVuelo getRutaVuelo() {
		return this.rutaVuelo;
	}

	public TipoAsiento getTipoAsiento() {
		return this.tipoAsiento;
	}

}
