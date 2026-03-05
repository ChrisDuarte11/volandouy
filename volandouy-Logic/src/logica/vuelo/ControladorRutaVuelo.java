package logica.vuelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTCiudad;
import datatypes.DTCostos;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.CategoriaRegistradaException;
import excepciones.ExisteParCiudadPaisException;
import excepciones.RutaNoExisteException;
import excepciones.RutaVueloExistenteException;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloRegistradoException;
import logica.enums.EstadoRuta;
import logica.fabrica.IRutaVuelo;
import logica.usuario.Aerolinea;
import logica.usuario.ManejadorUsuario;
import logica.usuario.Usuario;
import logica.vuelo.categoria.Categoria;
import logica.vuelo.ciudad.Ciudad;

public class ControladorRutaVuelo implements IRutaVuelo {

	public ControladorRutaVuelo() {}

	@Override
	public void agregarCiudad(DTCiudad nuevaCiudad) throws ExisteParCiudadPaisException { // en alta ciudad
		Set<String> ciudades = this.obtenerNombresCiudades(nuevaCiudad.getPais());

		for (String ciudad : ciudades) {
			if (ciudad.equals(nuevaCiudad.getNombre())) {
				throw new ExisteParCiudadPaisException();
			}
		}

		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		mRutaVuelo.agregarCiudad(new Ciudad(nuevaCiudad));
	}

	@Override
	public DTRutaVuelo buscarDTRutaVuelo(String rutavuelo) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		return mRutaVuelo.buscarDTRutaVuelo(rutavuelo);
	}

	@Override
	public boolean existeRutaNombre(String nombre) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		return mRutaVuelo.existeRuta(nombre);
	}

	@Override
	public void ingresarCategoriaNueva(String nombre) throws CategoriaRegistradaException {
		ManejadorRutaVuelo mRuta = ManejadorRutaVuelo.getInstance();
		if (mRuta.existeCategoria(nombre)) {
			throw new CategoriaRegistradaException("La categoría de nombre " + nombre + " ya existe.");
		}
		mRuta.agregarCategoria(new Categoria(nombre));
	}

	@Override
	public void ingresarDatosRuta(String nombre, String descripcion, LocalTime hora, DTCostos dtCostos, DTCiudad ciudadOrigen, DTCiudad ciudadDestino, LocalDate fechaAlta, Set<Categoria> categorias, String nickAerolinea, byte[] imagen, String descripcionCorta) throws RutaVueloExistenteException, UsuarioNoExisteException  {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		if (mRutaVuelo.existeRuta(nombre)) {
			throw new RutaVueloExistenteException("El nombre de la ruta de vuelo ya se encuentra registrado");
		} else {
			Costos costos = new Costos(dtCostos);
			Aerolinea aerolinea = (Aerolinea) mUsuario.obtenerUsuario(nickAerolinea);
			RutaVuelo ruta = new RutaVuelo(nombre, descripcion, hora, fechaAlta, categorias, mRutaVuelo.obtenerCiudad(ciudadDestino.getNombre(), ciudadDestino.getPais()), mRutaVuelo.obtenerCiudad(ciudadOrigen.getNombre(), ciudadOrigen.getPais()), costos, imagen, aerolinea, descripcionCorta);
			mRutaVuelo.agregarRutaVuelo(ruta);
			aerolinea.agregarRutaVuelo(ruta);
		}
	}

	@Override
	public void ingresarDatosVuelo(String nombre, int asientosMaxTurista, int asientosMaxEjecutiva, LocalTime duracion, LocalDate fecha, String nombreRuta, LocalDate fechaAlta, byte[] imagen) throws VueloRegistradoException {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		if (mRutaVuelo.existeVuelo(nombre)) {
        	throw new VueloRegistradoException("Ya está registrado un vuelo con ese nombre.");
		} else {		
			Vuelo vuelo = new Vuelo(nombre, asientosMaxEjecutiva, asientosMaxTurista, duracion, fecha, fechaAlta, imagen);
			mRutaVuelo.agregarVuelo(nombreRuta, vuelo);
		}
	}

//	@Override
//	public Set<String> listarClientes(DTRutaVuelo ruta, DTVuelo vuelo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Set<String> obtenerCategorias() {
		return ManejadorRutaVuelo.getInstance().obtenerCategorias();
	}

	@Override
	public Set<Categoria> obtenerCategorias(List<String> categorias) {
		Set<Categoria> cat = new HashSet<>();
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		for (String c : categorias) {
			Categoria categoria = mRutaVuelo.obtenerCategoria(c);
			if (categoria != null)
				cat.add(categoria);
		}
		return cat;
	}

	@Override
	public Set<Ciudad> obtenerCiudades() {
		return ManejadorRutaVuelo.getInstance().obtenerCiudades();
	}

	@Override
	public DTCiudad obtenerDTCiudadPais(String nombreCiudad, String nombrePais) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		return mRutaVuelo.obtenerDTCiudadPais(nombreCiudad, nombrePais);
	}

	@Override
	public Set<String> obtenerNombresCiudades(String pais) {
		Set<String> nombres = new HashSet<>();
		Set<Ciudad> ciudades = this.obtenerCiudades();
		for (Ciudad ciudad : ciudades) {
			if (ciudad.getPais().getPais() == pais) {
				nombres.add(ciudad.getNombre());
			}
		}
		return nombres;
	}

	@Override
	public Set<String> obtenerPaises() {
		Set<String> paises = new HashSet<>();
		Set<Ciudad> ciudades = this.obtenerCiudades();
		for (Ciudad ciudad : ciudades) {
			paises.add(ciudad.getPais().getPais());
		}
		return paises;
	}

	@Override
	public Set<DTReserva> obtenerReservas(DTRutaVuelo rutaVuelo, DTVuelo dtVuelo) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		RutaVuelo ruta = mRutaVuelo.obtenerRuta(rutaVuelo.getNombre());
		Vuelo vuelo = ruta.obtenerVuelo(dtVuelo.getNombre());
		return vuelo.obtenerReservas();
	}
	
	@Override
	public Set<DTReserva> obtenerReservas(String nombreRuta, String nombreVuelo) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		RutaVuelo ruta = mRutaVuelo.obtenerRuta(nombreRuta);
		Vuelo vuelo = ruta.obtenerVuelo(nombreVuelo);
		return vuelo.obtenerReservas();
	}

	@Override
	public DTRutaVuelo obtenerRuta(DTVuelo dtVuelo) {
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		Set<RutaVuelo> rutas = mRutaVuelo.obtenerRutas();
		for (RutaVuelo ruta : rutas) {
			if (ruta.existeVuelo(dtVuelo.getNombre())) {
				return ruta.getDT();
			}
		}
		return null;
	}

	@Override
	public RutaVuelo obtenerRuta(String ruta) {
		return ManejadorRutaVuelo.getInstance().obtenerRuta(ruta);
	}

	@Override
	public Set<String> obtenerRutasVuelo() {
		return ManejadorRutaVuelo.getInstance().listarRutaVuelo();
	}

	@Override
	public Set<DTRutaVuelo> obtenerRutasVuelo(String nickname) throws UsuarioNoExisteException {
		if (nickname == null) {
			throw new IllegalArgumentException("Nickname inválido");
		}
		ManejadorUsuario mUsuario = ManejadorUsuario.getInstance();
		Usuario usuario = mUsuario.obtenerUsuario(nickname);
		return ((Aerolinea) usuario).obtenerRutasVuelo();
	}

	@Override
	public DTVuelo obtenerVuelo(DTReserva reserva) {
		return ManejadorRutaVuelo.getInstance().obtenerVuelo(reserva).getDT();
	}

//	@Override
//	public DTVuelo obtenerVuelo(String nickCliente, DTReserva reserva) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Vuelo obtenerVueloClase(String rutaVuelo, String vuelo) { // en reservar vuelo
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		return mRutaVuelo.obtenerVuelo(rutaVuelo, vuelo);
	}

	@Override
	public Set<DTVuelo> obtenerVuelos(DTRutaVuelo rutaVuelo) throws RutaNoExisteException {
		if (rutaVuelo == null) {
			throw new IllegalArgumentException("La ruta seleccionada es inválida");
		}

		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		RutaVuelo ruta = mRutaVuelo.obtenerRuta(rutaVuelo.getNombre());

		if (ruta == null) {
			throw new RutaNoExisteException();
		}

		return ruta.obtenerVuelos().stream().map(vuelo -> vuelo.getDT()).collect(Collectors.toSet());
	}

	@Override
	public Set<DTVuelo> obtenerDTVuelos(String rutaVuelo) throws RutaNoExisteException {
		if (rutaVuelo == null) {
			throw new IllegalArgumentException("La ruta seleccionada es inválida");
		}

		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		RutaVuelo ruta = mRutaVuelo.obtenerRuta(rutaVuelo);

		if (ruta == null) {
			throw new RutaNoExisteException();
		}

		return ruta.obtenerVuelos().stream().map(vuelo -> vuelo.getDT()).collect(Collectors.toSet());
	}

	@Override
	public DTVuelo obtenerDTVuelo(String rutaVuelo, String vuelo){
		ManejadorRutaVuelo mRutaVuelo = ManejadorRutaVuelo.getInstance();
		 return mRutaVuelo.obtenerVuelo(rutaVuelo, vuelo).getDT();
	}

	@Override
	public void aceptarORechazarRutaVuelo(String nombreRuta, EstadoRuta estado) {
		ManejadorRutaVuelo.getInstance().obtenerRuta(nombreRuta).setEstado(estado);
	}

}
