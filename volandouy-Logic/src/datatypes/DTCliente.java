package datatypes;

import java.time.LocalDate;

import logica.enums.Pais;
import logica.enums.TipoDoc;

public class DTCliente extends DTUsuario {

	private String apellido;
	private LocalDate fechaNacimiento;
	private TipoDoc tipoDoc;
	private Pais nacionalidad;
	private String numDoc;

	public DTCliente(String nickname, String nombre, String apellido) {
		super(nickname, nombre, null);
		this.apellido = apellido;
	}

	public DTCliente(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String apellido, LocalDate fechaNacimiento, TipoDoc tipoDoc, Pais nacionalidad, String numDoc) {
		super(nickname, nombre, correo, password, imagen, fechaAlta);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.tipoDoc = tipoDoc;
		this.nacionalidad = nacionalidad;
		this.numDoc = numDoc;
	}

	public Boolean equals(DTCliente dtCliente) {
		return this.getNickname().equals(dtCliente.getNickname()) && this.getNombre().equals(dtCliente.getNombre())
				&& this.getCorreo().equals(dtCliente.getCorreo()) && this.apellido.equals(dtCliente.getApellido())
				&& this.fechaNacimiento.equals(dtCliente.getFechaNacimiento())
				&& this.tipoDoc.equals(dtCliente.getTipoDoc()) && this.numDoc.equals(dtCliente.getNumDoc())
				&& this.nacionalidad.equals(dtCliente.getNacionalidad());
	}

	public String getApellido() {
		return this.apellido;
	}

	public LocalDate getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public Pais getNacionalidad() {
		return this.nacionalidad;
	}

	public String getNumDoc() {
		return this.numDoc;
	}

	public TipoDoc getTipoDoc() {
		return this.tipoDoc;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public void setNacionalidad(Pais nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}

	public void setTipoDoc(TipoDoc tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

}
