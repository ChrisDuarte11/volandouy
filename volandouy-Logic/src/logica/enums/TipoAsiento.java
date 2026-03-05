package logica.enums;

public enum TipoAsiento {

	TURISTA("Turista"),
	EJECUTIVO("Ejecutivo");

	private String tipo;

	TipoAsiento(String tipo){
		this.tipo = tipo;
	}

	public String getTipo() {
		return this.tipo;
	}
	
	public static TipoAsiento desdeString(String nombreTipo) {
		for (TipoAsiento tipoAsiento : TipoAsiento.values()) {
			if (tipoAsiento.getTipo().equals(nombreTipo)) {
				return tipoAsiento;
			}
		}
		throw new IllegalArgumentException("No existe un tipo de asiento con el nombre ingresado: " + nombreTipo);
	}
	
}
