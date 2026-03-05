package excepciones;

@SuppressWarnings("serial")
public class ExisteParCiudadPaisException extends Exception {

	public ExisteParCiudadPaisException() {
		super("Ya existe el par ciudad-pais");
	}

}
