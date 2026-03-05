package excepciones;

@SuppressWarnings("serial")
public class ClienteTieneReservaEnVueloException extends Exception {

	public ClienteTieneReservaEnVueloException() {
		super("El cliente ya tiene una reserva en el vuelo ingresado");
	}

	public ClienteTieneReservaEnVueloException(String str) {
		super(str);
	}

}
