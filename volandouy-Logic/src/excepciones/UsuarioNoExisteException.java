package excepciones;

@SuppressWarnings("serial")
public class UsuarioNoExisteException extends Exception {

	public UsuarioNoExisteException() {
		super("El usuario de nickname ingresado no existe");
	}

	public UsuarioNoExisteException(String str) {
		super(str);
	}

}
