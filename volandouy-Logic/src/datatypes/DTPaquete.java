package datatypes;

public class DTPaquete {
	private String nombre;
    private String descripcion;
    private int validez_dias;
    private float descuento;
    private float costo;

    public DTPaquete(String nombre, String descripcion, int validez_dias, float descuento, float costo) {
    	this.nombre = nombre;
    	this.descripcion = descripcion;
    	this.validez_dias = validez_dias;
    	this.descuento = descuento;
    	this.costo = costo;
    }

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getValidez_dias() {
		return validez_dias;
	}

	public float getDescuento() {
		return descuento;
	}

	public float getCosto() {
		return costo;
	}

}
