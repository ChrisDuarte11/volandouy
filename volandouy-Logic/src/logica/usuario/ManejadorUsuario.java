package logica.usuario;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import logica.enums.EstadoRuta;
import logica.vuelo.RutaVuelo;

public class ManejadorUsuario {

	private static ManejadorUsuario instancia = null;
	public static ManejadorUsuario getInstance() {
		if (instancia == null) {
			instancia = new ManejadorUsuario();
		}
		return instancia;
	}
	private Map<String, Usuario> nickUsuarios;

	private Map<String, Usuario> correoUsuarios;

	private ManejadorUsuario() {
		this.nickUsuarios = new HashMap<String, Usuario>();
		this.correoUsuarios = new HashMap<String, Usuario>();
	}

	public void agregarRutaVuelo(String nickAerolinea, RutaVuelo rutaVuelo) {
		Aerolinea aerolinea = (Aerolinea) this.nickUsuarios.get(nickAerolinea);
		aerolinea.agregarRutaVuelo(rutaVuelo);
	}

	public void agregarUsuario(Usuario usuario) {
		this.nickUsuarios.put(usuario.getNickname(), usuario);
		this.correoUsuarios.put(usuario.getCorreo(), usuario);
	}

	public Aerolinea buscarAerolinea(String aerolinea) {
		return (Aerolinea) this.nickUsuarios.get(aerolinea);
	}

	public void clear() { // La usan los tests para tener las listas vacias
		this.nickUsuarios.clear();
		this.correoUsuarios.clear();
	}

	public void editarUsuario(DTUsuario dtUsuario) throws UsuarioNoExisteException {
		Usuario usuario = this.nickUsuarios.get(dtUsuario.getNickname());

		if (usuario == null)
			throw new UsuarioNoExisteException();

		if (usuario instanceof Cliente) {
			((Cliente) usuario).editar((DTCliente) dtUsuario);
		} else {
			((Aerolinea) usuario).editar((DTAerolinea) dtUsuario);
		}
	}

	public boolean existeUsuario(String nickCorreo) {
		return this.nickUsuarios.containsKey(nickCorreo) || this.correoUsuarios.containsKey(nickCorreo);
	}

	public Set<DTUsuario> getUsuarios() {
		Set<DTUsuario> dtUsr = new HashSet<DTUsuario>();
		for (Usuario usuario : this.nickUsuarios.values()) {
			dtUsr.add(usuario.getDT());
		}
		return dtUsr;
	}

	public Set<String> listarAerolineas() {
		Set<String> nicks = new HashSet<String>();
		for (Usuario usuario : this.nickUsuarios.values()) {
			if (usuario instanceof Aerolinea) {
				nicks.add(usuario.getNickname());
			}
		}
		return nicks;
	}

	public Set<String> listarClientes() {
		Set<String> nicks = new HashSet<String>();
		for (Usuario usuario : this.nickUsuarios.values()) {
			if (usuario instanceof Cliente) {
				nicks.add(usuario.getNickname());
			}
		}
		return nicks;

	}

	public Set<String> listarUsuarios() {
		return this.nickUsuarios.keySet();
	}

	public Cliente obtenerCliente(String nickCliente) throws UsuarioNoExisteException {
		Usuario cliente = this.nickUsuarios.get(nickCliente);
		if (cliente instanceof Cliente && cliente != null) {
			return (Cliente) this.nickUsuarios.get(nickCliente);
		}
		throw new UsuarioNoExisteException("El usuario no existe o no es un cliente");
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String nickAerolinea) throws UsuarioNoExisteException {
		Usuario usuario = this.nickUsuarios.get(nickAerolinea);
		if (usuario != null && usuario instanceof Aerolinea aerolinea) {
			return aerolinea.obtenerRutasVuelo();
		}

		throw new UsuarioNoExisteException("El usuario no existe o no es una aerolinea");
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String nickAerolinea, EstadoRuta estado1) throws UsuarioNoExisteException {
		Usuario usuario = this.nickUsuarios.get(nickAerolinea);
		if (usuario != null && usuario instanceof Aerolinea aerolinea) {
			return aerolinea.obtenerRutasVuelo(estado1);
		}

		throw new UsuarioNoExisteException("El usuario no existe o no es una aerolinea");
	}

	public Set<DTRutaVuelo> obtenerRutasAerolinea(String nickAerolinea, EstadoRuta estado1, EstadoRuta estado2) throws UsuarioNoExisteException {
		Usuario usuario = this.nickUsuarios.get(nickAerolinea);
		if (usuario != null && usuario instanceof Aerolinea aerolinea) {
			Set<DTRutaVuelo> set1 = new HashSet<DTRutaVuelo>();
			Set<DTRutaVuelo> set2 = new HashSet<DTRutaVuelo>();
			set1 = aerolinea.obtenerRutasVuelo(estado1);
			set2 = aerolinea.obtenerRutasVuelo(estado2);
			set1.addAll(set2);
			return set1;
		}

		throw new UsuarioNoExisteException("El usuario no existe o no es una aerolinea");
	}

	public Usuario obtenerUsuario(String nicknameEmail) throws UsuarioNoExisteException {
		Usuario usuario = this.nickUsuarios.get(nicknameEmail);
		if (usuario == null) {
			usuario = this.correoUsuarios.get(nicknameEmail);
			if (usuario == null) {
				throw new UsuarioNoExisteException();
			}
		}

		return usuario;
	}

}
