package datatypes;

import java.time.LocalDate;

public class DTUsuario {

	private final String nickname;
	private String nombre;
	private final String correo;
	private String password;
	private byte[] imagen;
	private LocalDate fechaAlta;

	public DTUsuario(String nickname, String nombre, String correo) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
	}
	
	public DTUsuario(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.imagen = imagen;
		this.fechaAlta = fechaAlta;
	}

	public String getCorreo() {
		return correo;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	
	public byte[] getImagen() {
		return this.imagen;
	}

	public String getNickname() {
		return nickname;
	}

	public String getNombre() {
		return nombre;
	}

	public String getPassword() {
		return this.password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

