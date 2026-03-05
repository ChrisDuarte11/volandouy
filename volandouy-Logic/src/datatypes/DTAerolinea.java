package datatypes;

import java.time.LocalDate;

public class DTAerolinea extends DTUsuario {

	private String descripcion;
	private String web;

	public DTAerolinea(String nickname, String nombre, String descripcion) {
		super(nickname, nombre, null);
		this.descripcion = descripcion;
	}

	public DTAerolinea(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String descripcion, String web) {
		super(nickname, nombre, correo, password, imagen, fechaAlta);
		this.descripcion = descripcion;
		this.web = web;
	}

	public Boolean equals(DTAerolinea dtAerolinea) {
		return this.getNickname() == dtAerolinea.getNickname() && this.getNombre() == dtAerolinea.getNombre() && this.getCorreo() == dtAerolinea.getCorreo() && this.descripcion == dtAerolinea.getDescripcion() && this.web == dtAerolinea.getWeb();
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public String getWeb() {
		return this.web;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}
