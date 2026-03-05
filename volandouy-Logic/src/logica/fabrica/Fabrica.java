package logica.fabrica;

import logica.usuario.ControladorUsuario;
import logica.vuelo.ControladorRutaVuelo;

public class Fabrica {

	private static Fabrica instancia = null;

	private Fabrica() {}

	public static Fabrica getInstance() {
		if (instancia == null) {
			instancia = new Fabrica();
		}
		return instancia;
	}

	public IRutaVuelo getIRutaVuelo() {
		IRutaVuelo iRutaVuelo = new ControladorRutaVuelo();
		return iRutaVuelo;
	}

	public IUsuario getIUsuario() {
		IUsuario iUsuario = new ControladorUsuario();
		return iUsuario;
	}

}
