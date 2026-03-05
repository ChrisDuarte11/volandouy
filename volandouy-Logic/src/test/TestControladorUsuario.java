package test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import datatypes.DTAerolinea;
import datatypes.DTCiudad;
import datatypes.DTCliente;
import datatypes.DTCostos;
import datatypes.DTPasaje;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
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
import logica.fabrica.IUsuario;
import logica.usuario.Cliente;
import logica.usuario.ManejadorUsuario;
import logica.vuelo.ManejadorRutaVuelo;

public class TestControladorUsuario {

	private IUsuario controladorUsuario;

	@Before
	public void setUp() {
		Fabrica fabrica = Fabrica.getInstance();
		controladorUsuario = fabrica.getIUsuario();
	}

	@After
	public void setDown() {
		ManejadorUsuario.getInstance().clear();
		ManejadorRutaVuelo.getInstance().clear();
	}

	//ListarUsuarios
	@Test
	public void testListarUsuariosVacio() {
		Assert.assertEquals(controladorUsuario.listarUsuarios().size(), 0);
	}

	@Test
	public void testListarUsuariosNoVacio() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertEquals(controladorUsuario.listarUsuarios().size(), 1);
	}

	//ListarClientes
		@Test
		public void testListarClientesVacio() {
			Assert.assertEquals(controladorUsuario.listarClientes().size(), 0);
		}

		@Test
		public void testListarClientesNoVacio() throws UsuarioRegistradoException {
			controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
			Assert.assertTrue(controladorUsuario.listarClientes().stream().anyMatch(s -> s.equals("juan")));
		}


	//ObtenerAerolineas
	@Test
	public void testObtenerAerolineasVacio() {
		Assert.assertEquals(controladorUsuario.obtenerAerolineas().size(), 0);
	}

	@Test
	public void testObtenerAerolineasNoVacio() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo", "desc", null, null, "web", null);
		Assert.assertEquals(controladorUsuario.obtenerAerolineas().size(), 1);
	}


	//ObtenerUsuario

	@Test
	public void testObtenerUsuarioNoExistente() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.obtenerUsuario("usuario que no existe"));
	}

	@Test
	public void testObtenerUsuarioClienteExistente() throws UsuarioNoExisteException, UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertTrue(controladorUsuario.obtenerUsuario("juan").getNickname().equals("juan"));
	}


	@Test
	public void testObtenerUsuarioAerolineaExistente() throws UsuarioNoExisteException, UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "Aerolinea Cuack", "cuack@gmail.com", "Aerolinea cuack descriptiva", null, null, "www.cuack.com", null);
		Assert.assertTrue(controladorUsuario.obtenerUsuario("cuack").getNickname().equals("cuack"));
	}

	@Test
	public void testObtenerUsuarioNullNickname() throws UsuarioNoExisteException {
		Assert.assertThrows(IllegalArgumentException.class, () -> controladorUsuario.obtenerUsuario(null));
	}

	//ObtenerCliente
	@Test
	public void testObtenerClienteNoExistente() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.obtenerCliente(null));
	}

	@Test
	public void testObtenerClienteClienteExistente() throws UsuarioNoExisteException, UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertTrue(controladorUsuario.obtenerCliente("juan").getNickname().equals("juan"));
	}

	//ObtenerAerolinea
	@Test
	public void testObtenerAerolineaRutaNoExistente() {
		Assert.assertThrows(RutaNoExisteException.class, () -> controladorUsuario.obtenerAerolinea(null));
	}

	@Test
	public void testObtenerAerolineaRutaExistenteEnAerolinea() throws UsuarioNoExisteException, UsuarioRegistradoException, RutaVueloExistenteException, RutaNoExisteException, ExisteParCiudadPaisException {
		controladorUsuario.ingresarDatosAerolinea("Aerolinea", "Aerolinea", "Aerolinea@gmail.com", "cuack", null, null, "cuack", null);

		DTCiudad ciudadOrigen = new DTCiudad("Montevideo", "Capital uruguaya, conocida por su Rambla, arquitectura colonial y vibrante vida cultural.", "https://montevideo.gub.uy", Pais.URUGUAY.getPais(), "Carrasco", LocalDate.of(2024, 4, 1));
		DTCiudad ciudadDestino = new DTCiudad("Múnich", "Ciudad alemana con rica historia, arquitectura barroca y vibrante vida cultural", "https://www.munich.travel/es", Pais.ALEMANIA.getPais(), "Aeropuerto de Múnich", LocalDate.of(2024, 6, 23));

		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadOrigen);
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadDestino);

		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "", LocalTime.now(), new DTCostos(0, 0, 0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "Aerolinea", null, null);
		Assert.assertTrue(controladorUsuario.obtenerAerolinea("ruta").getNickname().equals("Aerolinea"));
	}

	//IngresarDatosCliente
	@Test
	public void testIngresarDatosClienteNicknameRepetido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertThrows(UsuarioRegistradoException.class, () -> controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@hotmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN) );
	}

	@Test
	public void testIngresarDatosClienteCorreoRepetido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertThrows(UsuarioRegistradoException.class, () -> controladorUsuario.ingresarDatosCliente("pedro", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN) );
	}

	@Test
	public void testIngresarDatosClienteValido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertEquals(1, ManejadorUsuario.getInstance().listarUsuarios().size());
	}


	//IngresarDatosAerolinea
	@Test
	public void testIngresarDatosAerolineaNicknameRepetido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo", "desc", null, null, "web", null);
		Assert.assertThrows(UsuarioRegistradoException.class, () -> controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo1", "desc", null, null, "web", null));
	}

	@Test
	public void testIngresarDatosAerolineaCorreoRepetido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo", "desc", null, null, "web", null);
		Assert.assertThrows(UsuarioRegistradoException.class, () -> controladorUsuario.ingresarDatosAerolinea("nick", "nombre", "correo", "desc", null, null, "web", null));
	}

	@Test
	public void testIngresarDatosAerolineaValido() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo", "desc", null, null, "web", null);
		Assert.assertEquals(1, ManejadorUsuario.getInstance().listarAerolineas().size());
	}

	//ObtenerReservas
	@Test
	public void testObtenerReservasUsuarioNoExistente() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.obtenerReservas("Usuario que no existe"));
	}

	@Test
	public void testObtenerReservasUsuarioEsAerolinea() throws UsuarioRegistradoException {
		controladorUsuario.ingresarDatosAerolinea("cuack", "nombre", "correo", "desc", null, null, "web", null);
		Assert.assertThrows(ClassCastException.class, () -> controladorUsuario.obtenerReservas("cuack"));
	}

	@Test
	public void testObtenerReservasNullNickname() {
		Assert.assertThrows(IllegalArgumentException.class, () -> controladorUsuario.obtenerReservas(null));
	}

	@Test
	public void testObtenerReservasVacio() throws UsuarioNoExisteException, UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertEquals(0, controladorUsuario.obtenerReservas("juan").size());
	}

	/*@Test
	public void testObtenerReservas_Valido() throws UsuarioRegistradoException, UsuarioNoExisteException, RutaVueloExistenteException, VueloRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", LocalDate.now(), TipoDoc.CI, 12345678, Pais.AFGANISTAN);
		controladorUsuario.ingresarDatosAerolinea("cuack", "Cuack", "correo", "descripcion", "web");
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), 0, 0, 0, new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "cuack");
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosVuelo("vuelo", 0, 0, null, LocalDate.now(), "ruta", null);
		List<String> nombres = new ArrayList<>();
		nombres.add("pedro");
		List<String> apellidos = new ArrayList<>();
		nombres.add("perez");
		controladorUsuario.ingresarDatosReserva(TipoAsiento.Turista, 1, 0, nombres, apellidos, LocalDate.now(), "juan", "vuelo", "ruta");
		Assert.assertEquals(1, controladorUsuario.obtenerReservas("juan").size());
	}*/


	//ObtenerRutasAerolinea
	@Test
	public void testObtenerRutasAerolineaAerolineaNoExistente() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.obtenerRutasAerolinea(""));
	}

	@Test
	public void testObtenerRutasAerolineaAerolineaSinRutas() throws UsuarioNoExisteException, UsuarioRegistradoException, RutaVueloExistenteException, RutaNoExisteException {
		controladorUsuario.ingresarDatosAerolinea("Aerolinea", "Aerolinea", "Aerolinea@gmail.com", "cuack", null, null, "cuack", null);
		Assert.assertTrue(controladorUsuario.obtenerRutasAerolinea("Aerolinea").size() == 0);
	}

	@Test
	public void testObtenerRutasAerolineaConUnaRuta() throws UsuarioNoExisteException, UsuarioRegistradoException, RutaVueloExistenteException, RutaNoExisteException, ExisteParCiudadPaisException {
		controladorUsuario.ingresarDatosAerolinea("Aerolinea", "Aerolinea", "Aerolinea@gmail.com", "cuack", null, null, "cuack", null);

		DTCiudad ciudadOrigen = new DTCiudad("Ciudad de Panamá", "Moderno centro urbano con rascacielos, el Canal de Panamá y vibrante vida cultural.", "https://www.atp.gob.pa/", Pais.PANAMA.getPais(), "Tocumen", LocalDate.of(2024, 6, 25));
		DTCiudad ciudadDestino = new DTCiudad("Buenos Aires", "Vibrante capital argentina, conocida por su arquitectura, tango y vida cultural.", "https://turismo.buenosaires.gob.ar/es", Pais.ARGENTINA.getPais(), "Aeroparque Jorge Newbery", LocalDate.of(2024, 7, 5));

		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadOrigen);
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadDestino);

		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "", LocalTime.now(), new DTCostos(0, 0, 0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "Aerolinea", null, null);
		Assert.assertTrue(controladorUsuario.obtenerRutasAerolinea("Aerolinea").size() == 1);
	}

	//EditarUsuario
	@Test
	public void testEditarUsuarioUsuarioNoExistente() throws UsuarioRegistradoException, UsuarioNoExisteException {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.editarUsuario(new DTCliente("No existo", "nombre", "apellido")));
	}

	@Test
	public void testEditarUsuarioClienteValido() throws UsuarioRegistradoException, UsuarioNoExisteException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "pwd", null, LocalDate.now(), "Perez", null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		controladorUsuario.editarUsuario(new DTCliente("juan", "nombre", "juan@gmail.com", "pwd", null, LocalDate.now(), "apellido", null, TipoDoc.CI, Pais.ISLA_NAVIDAD, "10"));
		Cliente cli = controladorUsuario.obtenerCliente("juan");
		Assert.assertTrue(cli.getNombre().equals("nombre"));
		Assert.assertTrue(cli.getApellido().equals("apellido"));
		Assert.assertTrue(cli.getNacionalidad().equals(Pais.ISLA_NAVIDAD));
		Assert.assertTrue(cli.getNumDoc().equals("10"));
	}

	@Test
	public void testEditarUsuarioAerolineaValida() throws UsuarioRegistradoException, UsuarioNoExisteException, RutaNoExisteException {
		controladorUsuario.ingresarDatosAerolinea("Aerolinea", "Aerolinea", "Aerolinea@gmail.com", "cuack", null, null, "cuack", null);
		controladorUsuario.editarUsuario(new DTAerolinea("Aerolinea", "nombre", "Aerolinea@gmail.com", "pwd", null, null, "desc", "web"));
		DTAerolinea aero = (DTAerolinea) controladorUsuario.obtenerUsuario("Aerolinea");
		Assert.assertTrue(aero.getNombre().equals("nombre"));
		Assert.assertTrue(aero.getDescripcion().equals("desc"));
		Assert.assertTrue(aero.getWeb().equals("web"));
	}

	//ObtenerPaquetes
	@Test
	public void testObtenerPaquetesUsuarioNoExiste() {
		Assert.assertThrows(UsuarioNoExisteException.class, () -> controladorUsuario.obtenerPaquetes("cuack"));
	}

	@Test
	public void testObtenerPaquetesVacio() throws UsuarioNoExisteException, UsuarioRegistradoException {
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Assert.assertEquals(0, controladorUsuario.obtenerPaquetes("juan").size());
	}

	@Test
	public void agregarRutaAAerolinea() throws RutaVueloExistenteException, UsuarioNoExisteException, UsuarioRegistradoException, ExisteParCiudadPaisException {
		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadOrigen);
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadDestino);

		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);

		controladorUsuario.agregarRutaVuelo("aero", "ruta");
		Set<DTRutaVuelo> rutas = controladorUsuario.obtenerRutasAerolinea("aero");
		DTRutaVuelo[] rutasArray = rutas.toArray(new DTRutaVuelo[0]);
		Assert.assertTrue(rutas.size() == 1 && rutasArray[0].getNombre().equals("ruta"));
	}

	@Test
	public void getUsuarios() throws UsuarioRegistradoException {
		Assert.assertTrue(controladorUsuario.getUsuarios().size() == 0);

		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);

		Assert.assertTrue(controladorUsuario.getUsuarios().size() == 2);
	}

	@Test
	public void obtenerClienteReserva() throws UsuarioNoExisteException, ClienteTieneReservaEnVueloException, VueloRegistradoException, RutaVueloExistenteException, UsuarioRegistradoException, ReservaCantidadAsientosInvalidos {
		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosVuelo("cuack", 1, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));
		controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes), "juan", "ruta");

		Assert.assertEquals("Juan", controladorUsuario.obtenerClienteReserva(controladorUsuario.obtenerReserva("juan", "cuack")).getNombre());
	}

	@Test
	public void obtenerDTAerolineas() throws UsuarioRegistradoException {
		Assert.assertEquals(0, controladorUsuario.obtenerDTAerolineas().size());

		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosAerolinea("aeros", "Cuacks", "correos", "descripcion", null, null, "web", null);

		Assert.assertEquals(2, controladorUsuario.obtenerDTAerolineas().size());
	}

	@Test
	public void obtenerDTClientes() throws UsuarioRegistradoException, UsuarioNoExisteException {
		Assert.assertEquals(0, controladorUsuario.obtenerDTClientes().size());

		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		controladorUsuario.ingresarDatosCliente("juana", "Juana", "juana@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);

		Assert.assertEquals(2, controladorUsuario.obtenerDTClientes().size());
	}

	@Test
	public void obtenerReservaID() throws UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, ClienteTieneReservaEnVueloException, VueloRegistradoException, ReservaCantidadAsientosInvalidos {
		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), new DTCiudad(null, null, null, null, null, null), new DTCiudad(null, null, null, null, null, null), LocalDate.now(), null, "aero", null, null);
		
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosVuelo("cuack", 1, 0, LocalTime.now(), LocalDate.now(), "ruta", null, null);
		Set<DTPasaje> pasajes = new HashSet<>();
		pasajes.add(new DTPasaje("Juan", "Perez"));
		controladorUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), TipoAsiento.TURISTA, 1, 0, "cuack", pasajes), "juan", "ruta");

		Assert.assertEquals("cuack", controladorUsuario.obtenerReserva(controladorUsuario.obtenerReserva("juan", "cuack").getId()).getVuelo());
	}

	@Test
	public void obtenerRutasAerolinea() throws UsuarioRegistradoException, RutaVueloExistenteException, UsuarioNoExisteException, ExisteParCiudadPaisException {
		controladorUsuario.ingresarDatosAerolinea("aero", "Cuack", "correo", "descripcion", null, null, "web", null);
		controladorUsuario.ingresarDatosCliente("juan", "Juan", "juan@gmail.com", "Perez", null, LocalDate.now(), null, null, TipoDoc.CI, "12345678", Pais.AFGANISTAN);

		DTCiudad ciudadOrigen = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad ciudadDestino = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));

		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadOrigen);
		Fabrica.getInstance().getIRutaVuelo().agregarCiudad(ciudadDestino);

		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("ruta", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("rutas", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), ciudadDestino, ciudadOrigen, LocalDate.now(), null, "aero", null, null);
		Fabrica.getInstance().getIRutaVuelo().ingresarDatosRuta("rutaz", "descripcion", LocalTime.now(), new DTCostos(0, 0, 0), ciudadOrigen, ciudadDestino, LocalDate.now(), null, "aero", null, null);

		Fabrica.getInstance().getIRutaVuelo().aceptarORechazarRutaVuelo("ruta", EstadoRuta.CONFIRMADA);
		Fabrica.getInstance().getIRutaVuelo().aceptarORechazarRutaVuelo("rutas", EstadoRuta.RECHAZADA);

		Assert.assertEquals(1, controladorUsuario.obtenerRutasAerolinea("aero", EstadoRuta.INGRESADA).size());
		Assert.assertEquals(1, controladorUsuario.obtenerRutasAerolinea("aero", EstadoRuta.CONFIRMADA).size());
		Assert.assertEquals(1, controladorUsuario.obtenerRutasAerolinea("aero", EstadoRuta.RECHAZADA).size());

		Assert.assertEquals(2, controladorUsuario.obtenerRutasAerolinea("aero", EstadoRuta.CONFIRMADA, EstadoRuta.RECHAZADA).size());
		Assert.assertEquals(2, controladorUsuario.obtenerRutasAerolinea("aero", EstadoRuta.INGRESADA, EstadoRuta.CONFIRMADA).size());
	}

}

