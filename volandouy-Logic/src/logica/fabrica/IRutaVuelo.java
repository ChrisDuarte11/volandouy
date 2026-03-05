package logica.fabrica;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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
import logica.vuelo.RutaVuelo;
import logica.vuelo.Vuelo;
import logica.vuelo.categoria.Categoria;
import logica.vuelo.ciudad.Ciudad;

public interface IRutaVuelo {

	public abstract void agregarCiudad(DTCiudad ciudad) throws ExisteParCiudadPaisException; // en alta ciudad

	public abstract DTRutaVuelo buscarDTRutaVuelo(String rutavuelo);

	public abstract boolean existeRutaNombre(String nombre);

	public abstract void ingresarCategoriaNueva(String nombre) throws CategoriaRegistradaException;

	public abstract void ingresarDatosRuta(String nombre, String descripcion, LocalTime hora, DTCostos costos, DTCiudad ciudadOrigen, DTCiudad ciudadDestino, LocalDate fecha, Set<Categoria> categorias, String nickAerolinea, byte[] imagen, String descripcionCorta) throws RutaVueloExistenteException, UsuarioNoExisteException;

	public abstract void ingresarDatosVuelo(String nombre, int asientosMaxTurista, int asientosMaxEjecutiva, LocalTime duracion, LocalDate fecha, String nombreRuta, LocalDate fechaAlta, byte[] imagen) throws VueloRegistradoException;

	public abstract Set<String> obtenerCategorias();

	public abstract Set<Categoria> obtenerCategorias(List<String> categorias);

	public abstract Set<Ciudad> obtenerCiudades();

	public abstract DTCiudad obtenerDTCiudadPais(String nombreCiudad, String nombrePais);

	public abstract Set<String> obtenerNombresCiudades(String Pais);

	public abstract Set<String> obtenerPaises();

	public abstract Set<DTReserva> obtenerReservas(DTRutaVuelo rutaVuelo, DTVuelo vuelo);

	public abstract Set<DTReserva> obtenerReservas(String nombreRuta, String nombreVuelo);

	public abstract DTRutaVuelo obtenerRuta(DTVuelo dtVuelo);

	public abstract RutaVuelo obtenerRuta(String ruta);

	public abstract Set<String> obtenerRutasVuelo();

	public abstract Set<DTRutaVuelo> obtenerRutasVuelo(String nickname) throws UsuarioNoExisteException;

	public abstract DTVuelo obtenerVuelo(DTReserva reserva);

	public abstract Vuelo obtenerVueloClase(String rutavuelo, String vuelo); // en reservar vuelo

	public abstract Set<DTVuelo> obtenerVuelos(DTRutaVuelo rutaVuelo) throws RutaNoExisteException;

	public abstract Set<DTVuelo> obtenerDTVuelos(String rutaVuelo) throws RutaNoExisteException;

	public abstract DTVuelo obtenerDTVuelo(String rutaVuelo, String vuelo);

	void aceptarORechazarRutaVuelo(String nombreRuta, EstadoRuta estado);

}
