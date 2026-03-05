package logica.usuario;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTPaquete;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.ReservaCantidadAsientosInvalidos;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import logica.enums.EstadoRuta;
import logica.enums.Pais;
import logica.enums.TipoAsiento;
import logica.enums.TipoDoc;
import logica.fabrica.IUsuario;
import logica.vuelo.Costos;
import logica.vuelo.ManejadorRutaVuelo;
import logica.vuelo.RutaVuelo;
import logica.vuelo.Vuelo;

public class ControladorUsuario implements IUsuario {

	public ControladorUsuario() {}

	@Override
	public void agregarRutaVuelo(String aerolinea, String rutaVuelo) {
		ManejadorRutaVuelo manejadorRV = ManejadorRutaVuelo.getInstance();
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		RutaVuelo rutaV = manejadorRV.obtenerRutaDeVuelo(rutaVuelo);
		manejadorU.agregarRutaVuelo(aerolinea, rutaV);
	}

	@Override
	public void editarUsuario(DTUsuario usuario) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		mUsuario.editarUsuario(usuario);
	}

	@Override
	public Set<DTUsuario> getUsuarios() {
		ManejadorUsuario manejadorU = ManejadorUsuario.getInstance();
		return manejadorU.getUsuarios();
	}

	@Override
	public void ingresarDatosAerolinea(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String descripcion, String web) throws UsuarioRegistradoException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();

		if (mUsuario.existeUsuario(nickname)) {
			throw new UsuarioRegistradoException("El nickname ya se encuentra registrado");
		}

		if (mUsuario.existeUsuario(correo)) {
			throw new UsuarioRegistradoException("El correo ya se encuentra registrado");
		}

		Usuario usuario = new Aerolinea(nickname, nombre, correo, password, imagen, fechaAlta, descripcion, web);
		mUsuario.agregarUsuario(usuario);
	}

	@Override
	public void ingresarDatosCliente(String nickname, String nombre, String correo, String password, byte[] imagen, LocalDate fechaAlta, String apellido, LocalDate fechaNac, TipoDoc tipoDoc, String numDoc, Pais nacionalidad) throws UsuarioRegistradoException {
		if (nickname == null) {
			throw new IllegalArgumentException("Nickname invalido");
		}

		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();

		if (mUsuario.existeUsuario(nickname)) {
			throw new UsuarioRegistradoException("El nickname ya se encuentra registrado");
		}

		if (mUsuario.existeUsuario(correo)) {
			throw new UsuarioRegistradoException("El correo ya se encuentra registrado");
		}

		Usuario usuario = new Cliente(nickname, nombre, correo, password, imagen, fechaAlta, apellido, fechaNac, tipoDoc, numDoc, nacionalidad);
		mUsuario.agregarUsuario(usuario);
	}

	@Override
	public void ingresarDatosReserva(DTReserva dtReserva, String nicknameCliente, String nombreRuta) throws UsuarioNoExisteException, ClienteTieneReservaEnVueloException, ReservaCantidadAsientosInvalidos {
		Cliente cliente = ManejadorUsuario.getInstance().obtenerCliente(nicknameCliente);
		if (cliente.tieneReservaEnVuelo(dtReserva.getVuelo())) {
			throw new ClienteTieneReservaEnVueloException();
		}

		RutaVuelo ruta = ManejadorRutaVuelo.getInstance().obtenerRuta(nombreRuta);
		Costos costosRuta = ruta.obtenerCostos();
		float costoReserva;
		Vuelo vuelo = ruta.obtenerVuelo(dtReserva.getVuelo());
		if (dtReserva.getTipoAsiento().equals(TipoAsiento.TURISTA)) {
			costoReserva = costosRuta.getCostoBaseTurista() * dtReserva.getCantPasajes() + costosRuta.getCostoEquipajeExtra() * dtReserva.getCantEquipajeExtra();
			if (dtReserva.getCantPasajes() > vuelo.getAsientosTurista()) throw new ReservaCantidadAsientosInvalidos("Se seleccionaron mas asientos que los disponibles.");
		} else { // TipoAsiento.EJECUTIVO
			costoReserva = costosRuta.getCostoBaseEjecutivo() * dtReserva.getCantPasajes() + costosRuta.getCostoEquipajeExtra() * dtReserva.getCantEquipajeExtra();
			if (dtReserva.getCantPasajes() > vuelo.getAsientosEjecutivo()) throw new ReservaCantidadAsientosInvalidos("Se seleccionaron mas asientos que los disponibles.");
		}
		Reserva reserva = new Reserva(dtReserva.getFecha(), dtReserva.getCantPasajes(), dtReserva.getCantEquipajeExtra(), costoReserva, dtReserva.getTipoAsiento(), dtReserva.getPasajes(), dtReserva.getVuelo());
		if (dtReserva.getTipoReserva() != null) reserva.setTipoReserva(dtReserva.getTipoReserva());
		cliente.agregarReserva(reserva);

		vuelo.agregarReserva(reserva);
		vuelo.actualizarAsientosDisponibles(dtReserva.getTipoAsiento(), dtReserva.getCantPasajes());
	}

	@Override
	public Set<String> listarClientes() {
		return ManejadorUsuario.getInstance().listarClientes();
	}

	@Override
	public Set<String> listarUsuarios() {
		return ManejadorUsuario.getInstance().listarUsuarios();
	}

	@Override
	public DTAerolinea obtenerAerolinea(String nombreRuta) throws UsuarioNoExisteException, RutaNoExisteException {
		for (String aero : ManejadorUsuario.getInstance().listarAerolineas()) {
			Aerolinea aerolinea = (Aerolinea) ManejadorUsuario.getInstance().obtenerUsuario(aero);
			for (DTRutaVuelo ruta : aerolinea.obtenerRutasVuelo()) {
				if (ruta.getNombre().equals(nombreRuta))
					return (DTAerolinea) aerolinea.getDT();
			}
		}
		throw new RutaNoExisteException();
	}

	@Override
	public Set<String> obtenerAerolineas() {
		return ManejadorUsuario.getInstance().listarAerolineas();
	}

	@Override
	public Cliente obtenerCliente(String Cliente) throws UsuarioNoExisteException {
		return ManejadorUsuario.getInstance().obtenerCliente(Cliente);
	}

	@Override
	public DTCliente obtenerClienteReserva(DTReserva reserva) throws UsuarioNoExisteException {
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		for (String nick: manejador.listarClientes()) {
			for (DTReserva res: manejador.obtenerCliente(nick).obtenerReservas()) {
				if (res.getId() == reserva.getId()) return (DTCliente) manejador.obtenerCliente(nick).getDT();
			}
		}
		return null;
	}

	@Override
	public Set<DTAerolinea> obtenerDTAerolineas(){
		Set<DTAerolinea> ret = new HashSet<>();
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		for (String nick: manejador.listarAerolineas()) {
			ret.add((DTAerolinea) manejador.buscarAerolinea(nick).getDT());
		}
		return ret;
	}

	@Override
	public Set<DTCliente> obtenerDTClientes() throws UsuarioNoExisteException{
		Set<DTCliente> ret = new HashSet<>();
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		for (String nick: manejador.listarClientes()) {
			ret.add((DTCliente) manejador.obtenerCliente(nick).getDT());
		}
		return ret;
	}

	@Override
	public Set<DTPaquete> obtenerPaquetes(String nickname) throws UsuarioNoExisteException {
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		Cliente cliente = (Cliente) manejador.obtenerUsuario(nickname);
		return cliente.obtenerPaquetes();
	}

	@Override
	public DTReserva obtenerReserva(int idReserva) throws UsuarioNoExisteException {
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Set<String> clientes = mUsuario.listarClientes();
		for (String cliente : clientes) {
			Set<DTReserva> reservas = mUsuario.obtenerCliente(cliente).obtenerReservas();
			for (DTReserva reserva : reservas) {
				if (reserva.getId() == idReserva) {
					return reserva;
				}
			}
		}
		return null;
	}

	@Override
	public DTReserva obtenerReserva(String nicknameUsuario, String nombreVuelo) throws UsuarioNoExisteException {
		Set<DTReserva> reservas = this.obtenerReservas(nicknameUsuario);
		for (DTReserva reserva : reservas) {
			if (reserva.getVuelo().equals(nombreVuelo)) {
				return reserva;
			}
		}
		return null;
	}

	@Override
	public Set<DTReserva> obtenerReservas(String nickname) throws UsuarioNoExisteException {
		if (nickname == null) {
			throw new IllegalArgumentException("El nickname seleccionado no es válido");
		}

		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		Cliente cliente = (Cliente) manejador.obtenerUsuario(nickname);
		if (cliente == null) {
			throw new UsuarioNoExisteException();
		}

		return cliente.obtenerReservas();
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea) throws UsuarioNoExisteException {
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		return manejador.obtenerRutasAerolinea(aerolinea);
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea, EstadoRuta estado1) throws UsuarioNoExisteException {
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		return manejador.obtenerRutasAerolinea(aerolinea, estado1);
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String aerolinea, EstadoRuta estado1, EstadoRuta estado2) throws UsuarioNoExisteException {
		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		return manejador.obtenerRutasAerolinea(aerolinea, estado1, estado2);
	}

	@Override
	public DTUsuario obtenerUsuario(String nicknameEmail) throws UsuarioNoExisteException {
		if (nicknameEmail == null) {
			throw new IllegalArgumentException("El nickname seleccionado no es válido");
		}

		ManejadorUsuario manejador = ManejadorUsuario.getInstance();
		Usuario usuario = manejador.obtenerUsuario(nicknameEmail);
		return usuario.getDT();
	}
}
