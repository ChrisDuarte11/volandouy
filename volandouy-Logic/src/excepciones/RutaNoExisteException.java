package excepciones;

@SuppressWarnings("serial")
public class RutaNoExisteException extends Exception {

	public RutaNoExisteException() {
		super("La ruta seleccionada no existe");
	}

}
