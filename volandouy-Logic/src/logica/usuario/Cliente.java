package logica.usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTCliente;
import datatypes.DTPaquete;
import datatypes.DTReserva;
import datatypes.DTUsuario;
import logica.enums.Pais;
import logica.enums.TipoDoc;
import logica.paquete.ClientePaquete;
import logica.vuelo.RutaVuelo;

public class Cliente extends Usuario {
	private String apellido;
	private LocalDate fechaNacimiento;
	private Pais nacionalidad;
	private TipoDoc tipoDocumento;
	private String numeroDocumento;
	private Set<Reserva> reservas;
	private Set<ClientePaquete> clientePaquetes;

	public Cliente(String nick, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String apellido, LocalDate fechaNacimiento, TipoDoc tipo_doc, String num_doc, Pais nacionalidad) {
		super(nick, nombre, correo, password, imagen, fechaAlta);
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.tipoDocumento = tipo_doc;
		this.numeroDocumento = num_doc;
		this.reservas = new HashSet<>();
		this.clientePaquetes = new HashSet<>();
	}

	public String getApellido() {
		return this.apellido;
	}

	public LocalDate getFechaNac() {
		return this.fechaNacimiento;
	}

	public Pais getNacionalidad() {
		return this.nacionalidad;
	}

	public TipoDoc getTipoDoc() {
		return this.tipoDocumento;
	}
	public String getNumDoc() {
		return this.numeroDocumento;
	}

	public void agregarReserva(Reserva reserva) {
		this.reservas.add(reserva);
	}

	public boolean tieneReservaEnVuelo(String nombreVuelo) {
		for (Reserva reserva : reservas) {
			if (reserva.getVuelo().equals(nombreVuelo)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public DTUsuario getDT() {
		return new DTCliente(this.getNickname(), this.getNombre(), this.getCorreo(), this.getPassword(), this.getImagen(), this.getFechaAlta(), this.apellido, this.fechaNacimiento, this.tipoDocumento, this.nacionalidad, this.numeroDocumento);
	}

	public Set<DTReserva> obtenerReservas() {
		return reservas.stream().map(reserva -> reserva.getDT()).collect(Collectors.toSet());
	}

	public Set<DTPaquete> obtenerPaquetes() {
		return clientePaquetes.stream().map(clientePaquete -> clientePaquete.getDTPaquete()).collect(Collectors.toSet());
	}

	public void editar(DTCliente dtCliente) {
		this.setNombre(dtCliente.getNombre());
		this.setPassword(dtCliente.getPassword());
		this.setImagen(dtCliente.getImagen());
		this.apellido = dtCliente.getApellido();
		this.fechaNacimiento = dtCliente.getFechaNacimiento();
		this.nacionalidad = dtCliente.getNacionalidad();
		this.tipoDocumento = dtCliente.getTipoDoc();
		this.numeroDocumento = dtCliente.getNumDoc();
	}

	public ClientePaquete paqueteVigenteParaRutaAsiento(RutaVuelo rutaVuelo, String tipoAsiento, LocalDate fecha) {
		for (ClientePaquete cliPaq: clientePaquetes) {
			if (cliPaq.getVencimiento().isAfter(fecha) && cliPaq.getPaquete().getRutaPaquete(rutaVuelo) != null && cliPaq.getPaquete().getRutaPaquete(rutaVuelo).getTipoAsiento().getTipo().equals(tipoAsiento)) {
				return cliPaq;
			}
		}
		return null;
	}

}
