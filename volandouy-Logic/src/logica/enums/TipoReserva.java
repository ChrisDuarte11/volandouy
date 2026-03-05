package logica.enums;

public enum TipoReserva {

	GENERAL("General"),
	PAQUETE("Paquete");

	private String tipo;

	TipoReserva(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return this.tipo;
	}

	public static TipoReserva desdeString(String nombreTipo) {
		for (TipoReserva tipoReserva : TipoReserva.values()) {
			if (tipoReserva.getTipo().equals(nombreTipo)) {
				return tipoReserva;
			}
		}
		throw new IllegalArgumentException("No existe un tipo de reserva con el nombre ingresado: " + nombreTipo);
	}

}
