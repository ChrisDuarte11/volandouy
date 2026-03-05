package excepciones;

@SuppressWarnings("serial")
public class CargarDatosErrorException extends Exception {
	
	public CargarDatosErrorException() {
		super("Los datos de prueba NO han podido ser cargados");
	}
	
	public CargarDatosErrorException(String str) {
		super(str);
	}

}
