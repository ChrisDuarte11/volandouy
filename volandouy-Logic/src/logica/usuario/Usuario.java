package logica.usuario;

import java.time.LocalDate;

import datatypes.DTUsuario;

public abstract class Usuario {

	private final String nickname;
	private String nombre;
	private final String correo;
	private String password;
	private byte[] imagen;
	private LocalDate fechaAlta;

	public Usuario(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta) {
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
		this.imagen = imagen;
		this.fechaAlta = fechaAlta;
	}

	public String getCorreo() {
		return this.correo;
	}

	public abstract DTUsuario getDT();

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

}
