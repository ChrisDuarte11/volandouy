package test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import datatypes.DTCiudad;
import datatypes.DTCostos;
import datatypes.DTPasaje;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.CategoriaRegistradaException;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.ExisteParCiudadPaisException;
import excepciones.ReservaCantidadAsientosInvalidos;
import excepciones.RutaNoExisteException;
import excepciones.RutaVueloExistenteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import excepciones.VueloRegistradoException;
import logica.enums.EstadoRuta;
import logica.enums.Pais;
import logica.enums.TipoAsiento;
import logica.enums.TipoDoc;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;
import logica.usuario.ManejadorUsuario;
import logica.vuelo.ManejadorRutaVuelo;

public class TestControladorRutaVuelo {

	private IRutaVuelo controladorRutaVuelo;
	private IUsuario controladorUsuario;

	@Before
	public void setUp() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorRutaVuelo = fabrica.getIRutaVuelo();
		controladorUsuario = fabrica.getIUsuario();
	}

	@After
	public void setDown() {
		ManejadorRutaVuelo.getInstance().clear();
		ManejadorUsuario.getInstance().clear();
	}

	/*@Test
	public void testObtenerReservas_RutaInvalida() {
		Assert.assertThrows(controladorRutaVuelo.obtenerReservas(null, null));
	}*/

	//ObtenerRutasVuelo
	@Test
	public void testObtenerRutasVuelo_Vacio() throws UsuarioRegistradoException, UsuarioNoExisteException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);
		Assert.assertEquals(0, controladorRutaVuelo.obtenerRutasVuelo("cuack").size());
	}

	@Test
	public void testObtenerRutasVuelo_AerolineaNoExiste() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorRutaVuelo.obtenerRutasVuelo(""));
	}

	@Test
	public void testObtenerRutasVuelo_NicknameInvalido() {
		Assert.assertThrows(IllegalArgumentException.class, () -> controladorRutaVuelo.obtenerRutasVuelo(null));
	}

	@Test
	public void testObtenerRutasVuelo_RutaValida() throws UsuarioNoExisteException, RutaVueloExistenteException, UsuarioRegistradoException, ExisteParCiudadPaisException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Nueva York", "Ciudad icónica con rascacielos, cultura diversa y atracciones famosas", "https://www.nyc.gov/", Pais.ESTADOS_UNIDOS_AMERICA.getPais(), "ohn F. Kennedy", LocalDate.of(2024, 8, 25));
		DTCiudad ciudadDestino = new DTCiudad("Río de Janeiro", " Ciudad costera de Brasil, famosa por sus playas y la estatua del Cristo Redentor.", "https://riotur.rio/es/bienvenido/", Pais.BRASIL.getPais(), "Galeão Antonio Carlos Jobim", LocalDate.of(2024, 7, 1));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "cuack", null, null);
		Assert.assertEquals(1, controladorRutaVuelo.obtenerRutasVuelo("cuack").size());
	}

	//ObtenerVuelos
	@Test
	public void testObtenerVuelos_Vacio() throws RutaNoExisteException, UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "cuack", null, null);
		Assert.assertEquals(0, controladorRutaVuelo.obtenerVuelos(new DTRutaVuelo("ruta")).size());
	}

	@Test
	public void testObtenerVuelos_RutaNoExiste() {
		Assert.assertThrows(RutaNoExisteException.class, () -> controladorRutaVuelo.obtenerVuelos(new DTRutaVuelo("ruta")));
	}

	@Test
	public void testObtenerVuelos_NicknameInvalido() {
		Assert.assertThrows(IllegalArgumentException.class, () -> controladorRutaVuelo.obtenerVuelos(null));
	}

	@Test
	public void testObtenerVuelos_VueloValido() throws RutaNoExisteException, UsuarioRegistradoException, RutaVueloExistenteException, VueloRegistradoException, UsuarioNoExisteException, ExisteParCiudadPaisException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Punta del Este", "Famoso balneario uruguayo, con playas, vida nocturna y lujosos resorts", "https://www.maldonado.gub.uy", Pais.URUGUAY.getPais(), "Laguna del Sauce", LocalDate.of(2024, 7, 15));
		DTCiudad ciudadDestino = new DTCiudad("Madrid", "Vibrante capital española con rica historia, cultura y arquitectura impresionante.", "https://www.turismomadrid.es/es/", Pais.ESPANA.getPais(), "Adolfo Suárez Madrid-Barajas.", LocalDate.of(2024, 8, 12));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "cuack", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, null, LocalDate.now(), "ruta", null, null);
		Assert.assertEquals(1, controladorRutaVuelo.obtenerVuelos(new DTRutaVuelo("ruta")).size());
	}

	//AgregarVuelo
	@Test
	public void testAgregarVuelo_VueloValido() throws UsuarioRegistradoException, RutaVueloExistenteException, VueloRegistradoException, UsuarioNoExisteException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "cuack", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, null, LocalDate.now(), "ruta", null, null);
		Assert.assertEquals("cuack", controladorRutaVuelo.obtenerVueloClase("ruta", "cuack").getNombre());
	}

	@Test
	public void testAgregarVuelo_VueloRepetido() throws UsuarioRegistradoException, RutaVueloExistenteException, VueloRegistradoException, UsuarioNoExisteException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "cuack", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, null, LocalDate.now(), "ruta", null, null);
		Assert.assertThrows(VueloRegistradoException.class, () -> controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, null, LocalDate.now(), "ruta", null, null));
	}

	//AltaCiudad
	@Test
	public void testAltaCiudad_CiudadValida() throws ExisteParCiudadPaisException {
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(new DTCiudad("montevideo", "descripcion", "web", "Uruguay", "aeropuerto", LocalDate.now()));
		DTCiudad ciudad = ManejadorRutaVuelo.getInstance().obtenerDTCiudadPais("montevideo", "Uruguay");
		Assert.assertEquals("montevideo", ciudad.getNombre());
		Assert.assertEquals("Uruguay", ciudad.getPais());
		Assert.assertEquals("web", ciudad.getWeb());
		Assert.assertEquals("descripcion", ciudad.getDescripcion());
		Assert.assertEquals("aeropuerto", ciudad.getAeropuerto());
	}

	@Test
	public void testAltaCiudad_CiudadNoValida() throws ExisteParCiudadPaisException {
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(new DTCiudad("montevideo", "descripcion", "web", "Uruguay", "aeropuerto", LocalDate.now()));
		Assert.assertThrows(ExisteParCiudadPaisException.class, () -> Fabrica.getInstance().getIRutaVuelo().agregarCiudad(new DTCiudad("montevideo", "descripcion", "web", "Uruguay", "aeropuerto", LocalDate.now())));
	}

	//IngresarCateogoria
	@Test
	public void testIngresarCategoria_CategoriaValida() throws CategoriaRegistradaException {
		controladorRutaVuelo.ingresarCategoriaNueva("Cat");
		Assert.assertTrue(controladorRutaVuelo.obtenerCategorias().contains("Cat"));
	}

	@Test
	public void testIngresarCategoria_CategoriaRepetida() throws CategoriaRegistradaException {
		controladorRutaVuelo.ingresarCategoriaNueva("Cat");
		Assert.assertThrows(CategoriaRegistradaException.class, () -> controladorRutaVuelo.ingresarCategoriaNueva("Cat"));
	}

	//IngresarReserva
	@Test
	public void testIngresarReserva_Valida() throws ExisteParCiudadPaisException, UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, VueloRegistradoException, ClienteTieneReservaEnVueloException, ReservaCantidadAsientosInvalidos {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		Fabrica.getInstance().getIUsuario().ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 1, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));
		controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes), "juan", "ruta");
		Assert.assertEquals(1, controladorRutaVuelo.obtenerReservas(new DTRutaVuelo("ruta"), new DTVuelo("cuack")).size());
	}

	@Test
	public void testIngresarReserva_Repetida() throws ExisteParCiudadPaisException, UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, VueloRegistradoException, ClienteTieneReservaEnVueloException, ReservaCantidadAsientosInvalidos {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		Fabrica.getInstance().getIUsuario().ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 1, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));
		controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes), "juan", "ruta");

		Assert.assertThrows(ClienteTieneReservaEnVueloException.class, () -> controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes), "juan", "ruta"));
	}

	//ObtenerCategorias
	@Test
	public void testObtenerCategoriasString_CategoriaValida() throws CategoriaRegistradaException {
		controladorRutaVuelo.ingresarCategoriaNueva("Cat");
		List<String> list = new ArrayList<String>();
		list.add("Cat");
		Assert.assertEquals(1, controladorRutaVuelo.obtenerCategorias(list).size());
	}

	//ExisteRutaNombre
	@Test
	public void testExisteRutaNombre_Existe() throws RutaVueloExistenteException, UsuarioNoExisteException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		Assert.assertTrue(controladorRutaVuelo.existeRutaNombre("ruta"));
	}

	@Test
	public void testExisteRutaNombre_NoExiste() throws RutaVueloExistenteException, UsuarioNoExisteException, UsuarioRegistradoException {
		Assert.assertFalse(controladorRutaVuelo.existeRutaNombre("ruta"));
	}

	//ObtenerRuta
	@Test
	public void obtenerRuta_RutaExistente() throws UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, VueloRegistradoException, ExisteParCiudadPaisException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Assert.assertEquals("ruta", controladorRutaVuelo.obtenerRuta(new DTVuelo("cuack")).getNombre());
	}

	@Test
	public void buscarDTRutaVuelo() throws RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);

		Assert.assertEquals("ruta", controladorRutaVuelo.buscarDTRutaVuelo("ruta").getNombre());
		Assert.assertEquals("descripcion", controladorRutaVuelo.buscarDTRutaVuelo("ruta").getDescripcion());
	}

	@Test
	public void obtenerReservas() throws UsuarioNoExisteException, ClienteTieneReservaEnVueloException, VueloRegistradoException, RutaVueloExistenteException, UsuarioRegistradoException, ReservaCantidadAsientosInvalidos {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		Fabrica.getInstance().getIUsuario().ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 1, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));
		controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.EJECUTIVO, 1, 0, "cuack", pasajes), "juan", "ruta");
		Assert.assertEquals(1, controladorRutaVuelo.obtenerReservas("ruta", "cuack").size());
	}

	@Test
	public void obtenerDTVuelos_RutaNula() {
		Assert.assertThrows(IllegalArgumentException.class, () -> controladorRutaVuelo.obtenerDTVuelos(null));
	}

	@Test
	public void obtenerDTVuelos_RutaErronea() {
		Assert.assertThrows(RutaNoExisteException.class, () -> controladorRutaVuelo.obtenerDTVuelos("miau"));
	}

	@Test
	public void obtenerDTVuelos() throws RutaNoExisteException, VueloRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, UsuarioRegistradoException, ExisteParCiudadPaisException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Assert.assertEquals(1, controladorRutaVuelo.obtenerDTVuelos("ruta").size());
	}

	@Test
	public void obtenerDTVuelo() throws VueloRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 0, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Assert.assertEquals("cuack", controladorRutaVuelo.obtenerDTVuelo("ruta", "cuack").getNombre());
		Assert.assertEquals("ruta", controladorRutaVuelo.obtenerDTVuelo("ruta", "cuack").getNombreRuta());
	}

	@Test
	public void aceptarORechazarRutaVuelo() throws RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.aceptarORechazarRutaVuelo("ruta", EstadoRuta.CONFIRMADA);
		Assert.assertEquals(EstadoRuta.CONFIRMADA, controladorRutaVuelo.obtenerRuta("ruta").getEstado());
	}

	@Test
	public void obtenerPaises() throws ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		Assert.assertEquals(2, controladorRutaVuelo.obtenerPaises().size());
	}

	@Test
	public void obtenerDTCiudadPais() throws ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		controladorRutaVuelo.agregarCiudad(ciudadOrigen);

		Assert.assertEquals(ciudadOrigen.getNombre(), controladorRutaVuelo.obtenerDTCiudadPais("Barcelona", "España").getNombre());
		Assert.assertEquals(ciudadOrigen.getPais(), controladorRutaVuelo.obtenerDTCiudadPais("Barcelona", "España").getPais());
	}

	@Test
	public void obtenerVuelo() throws UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, VueloRegistradoException, ClienteTieneReservaEnVueloException, ExisteParCiudadPaisException, ReservaCantidadAsientosInvalidos {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		Fabrica.getInstance().getIUsuario().ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosVuelo("cuack", 1, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));

		DTReserva reserva = new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes);
		controladorUsuario.ingresarDatosReserva(reserva, "juan", "ruta");
		Assert.assertEquals("cuack", controladorRutaVuelo.obtenerVuelo(reserva).getNombre());
	}

	@Test
	public void obtenerRutasVuelo() throws RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		controladorRutaVuelo.ingresarDatosRuta("rute", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadDestino, ciudadOrigen, LocalDate.now(), null, "aero", null, null);

		Assert.assertEquals(2, controladorRutaVuelo.obtenerRutasVuelo().size());
	}

	@Test
	public void ingresarDatosRuta_Repetida() throws RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException, UsuarioRegistradoException {
		Fabrica.getInstance().getIUsuario().ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		controladorRutaVuelo.agregarCiudad(ciudadOrigen);
		controladorRutaVuelo.agregarCiudad(ciudadDestino);

		controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);

		Assert.assertThrows(RutaVueloExistenteException.class, () -> controladorRutaVuelo.ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0,0,0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null));
	}

}
