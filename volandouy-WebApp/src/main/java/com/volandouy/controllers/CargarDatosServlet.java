package com.volandouy.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import datatypes.DTCiudad;
import datatypes.DTCostos;
import datatypes.DTPasaje;
import datatypes.DTReserva;
import excepciones.CategoriaRegistradaException;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.ExisteParCiudadPaisException;
import excepciones.RutaVueloExistenteException;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import excepciones.VueloRegistradoException;
import excepciones.ReservaCantidadAsientosInvalidos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.enums.EstadoRuta;
import logica.enums.Pais;
import logica.enums.TipoAsiento;
import logica.enums.TipoReserva;
import logica.enums.TipoDoc;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@WebServlet("/cargar_datos")
public class CargarDatosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IUsuario iUsuario;
	private IRutaVuelo iRutaVuelo;
	private String path;
	private String pathDefault;
	private Map<String, DTCiudad> ciudadMap = new HashMap<>();

    public CargarDatosServlet() {
        super();
        this.iUsuario = Fabrica.getInstance().getIUsuario();
        this.iRutaVuelo = Fabrica.getInstance().getIRutaVuelo();
        this.ciudadesMap();
    }

    public void init() {
    	this.path = getServletContext().getRealPath("/WEB-INF/datos-prueba/");
    	this.pathDefault = getServletContext().getRealPath("/resources/img/");
    }

    private void cargarCategorias() {
		try (CSVReader readerCategorias = new CSVReaderBuilder(new FileReader(path + "2024Categorias.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> categorias = readerCategorias.readAll();

			for (String[] linea : categorias) {
				this.iRutaVuelo.ingresarCategoriaNueva(linea[1]);
			}
		} catch (IOException | CsvException | CategoriaRegistradaException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarCiudades() {
		try (CSVReader readerCiudades = new CSVReaderBuilder(new FileReader(path + "2024Ciudades.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> ciudades = readerCiudades.readAll();

			for (String[] linea : ciudades) {
				this.iRutaVuelo.agregarCiudad(this.ciudadMap.get(linea[0]));
			}
		} catch (IOException | CsvException | ExisteParCiudadPaisException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarReservas() {
		try (CSVReader readerReservas = new CSVReaderBuilder(new FileReader(path + "2024Reservas.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> reservas = readerReservas.readAll();

			for (String[] linea : reservas) {
				String[] fecha = linea[8].split("/");

				// Cargo los pasajeros
				Set<DTPasaje> pasajeros = this.cargarPasajes(linea[0]);
				LocalDate fechaReserva = LocalDate.of(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
				TipoAsiento tipoAsiento = TipoAsiento.desdeString(linea[5]);
				int cantPasajes = Integer.parseInt(linea[6]);
				int cantEquipajeExtra = Integer.parseInt(linea[7]);
				String nombreVuelo = this.getVueloDeRef(linea[3]);
				DTReserva reserva = new DTReserva(fechaReserva, tipoAsiento, cantPasajes, cantEquipajeExtra, nombreVuelo, pasajeros);
				if (!linea[10].equals("General")) reserva.setTipoReserva(TipoReserva.PAQUETE);
				this.iUsuario.ingresarDatosReserva(reserva, this.getNickDeRef(linea[4]), this.getRutaDeRef(linea[2]));
			}
		} catch (IOException | CsvException | UsuarioNoExisteException | ClienteTieneReservaEnVueloException | ReservaCantidadAsientosInvalidos e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarRutas() {
		try {
			CSVReader readerRutas = new CSVReaderBuilder(new FileReader(path + "2024RutasVuelos.csv"))
							.withSkipLines(1)
							.withCSVParser(new com.opencsv.CSVParserBuilder()
								.withSeparator(';')
								.build())
							.build();
			List<String[]> rutas = readerRutas.readAll();

			for (String[] linea : rutas) {
				// Cargo las categorías
				List<String> categorias = new ArrayList<String>();
				String[] catRef = linea[11].split(",");
				for (String categoria : catRef) {
					categorias.add(this.getCategoriaDeRef(categoria.trim()));
				}

				String[] fecha = linea[10].split("/");

				byte[] defaultImage = Files.readAllBytes(Paths.get(this.pathDefault + "default-flight-route-picture.jpg"));
				byte[] imagen = linea[14].equals("(Sin Imagen)") ? defaultImage : Files.readAllBytes(Paths.get(this.path + "img/" + linea[14]));
				iRutaVuelo.ingresarDatosRuta(linea[2], linea[3], LocalTime.parse(linea[4]), new DTCostos(Float.parseFloat(linea[6]), Float.parseFloat(linea[5]), Float.parseFloat(linea[7])), this.ciudadMap.get(linea[8]), this.ciudadMap.get(linea[9]), LocalDate.of(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])), iRutaVuelo.obtenerCategorias(categorias), this.getNickDeRef(linea[1]), imagen, linea[13]);
				if (!linea[12].equals("Ingresada")) {
					iRutaVuelo.aceptarORechazarRutaVuelo(linea[2], linea[12].equals("Confirmada") ? EstadoRuta.CONFIRMADA : EstadoRuta.RECHAZADA);
				}

			}

		} catch (IOException | NumberFormatException | RutaVueloExistenteException | UsuarioNoExisteException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarUsuarios() {
		try {
			this.iUsuario.ingresarDatosAerolinea("aerolineas", "Aerolíneas Argentinas", "servicioalcliente@aerolineas.com.uy", "zaq1xsw2", Files.readAllBytes(Paths.get(this.path + "img/US01.png")), LocalDate.of(2024, 01, 01), "Aerolínea nacional de Argentina que ofrece vuelos directos entre múltiples destinos.", "https://www.aerolineas.com.ar");
			this.iUsuario.ingresarDatosAerolinea("aireuropa", "Air Europa", "reservas@aireuropa.com.uy", "cde3vfr4", Files.readAllBytes(Paths.get(this.path + "img/US02.png")), LocalDate.of(2024, 01, 01), "Aerolínea española que ofrece vuelos varios destinos en Europa y América.", "https://www.aireuropa.com");
			this.iUsuario.ingresarDatosCliente("anarod87", "Ana", "arodriguez87@netuy.com", "bgt5nhy6", Files.readAllBytes(Paths.get(this.path + "img/US03.jpg")), LocalDate.of(2024, 01, 01), "Rodríguez", LocalDate.of(1987, 2, 18), TipoDoc.Pasaporte, "B2345678", Pais.URUGUAY);
			this.iUsuario.ingresarDatosCliente("claire93d", "Claire", "claire.db@frmail.fr", "mju76yhn", Files.readAllBytes(Paths.get(this.path + "img/US04.jpeg")), LocalDate.of(2024, 01, 01), "Rinaldi", LocalDate.of(1993, 8, 22), TipoDoc.Pasaporte, "20VF756483", Pais.ITALIA);
			this.iUsuario.ingresarDatosAerolinea("copaair", "Copa Airlines", "contacto@copaair.com.uy", "2wsx3edc", Files.readAllBytes(Paths.get(this.path + "img/US05.png")), LocalDate.of(2024, 01, 01), "Aerolínea panameña con conexiones a varios destinos en América y el Caribe", "https://www.copaair.com");
			this.iUsuario.ingresarDatosCliente("csexto", "Cristian", "csexto@adinet.com.uy", "4rfvbgt5", Files.readAllBytes(Paths.get(this.path + "img/US06.jpeg")), LocalDate.of(2024, 01, 01), "Sexto", LocalDate.of(1987, 3, 26), TipoDoc.CI, "45871265", Pais.URUGUAY);
			this.iUsuario.ingresarDatosCliente("ejstar", "Emily", "emily.j@hotmail.com", "lkjoiu987", Files.readAllBytes(Paths.get(this.path + "img/US07.jpeg")), LocalDate.of(2024, 01, 01), "Johnson", LocalDate.of(1985, 6, 24), TipoDoc.Pasaporte, "485719842", Pais.ESTADOS_UNIDOS_AMERICA);
			this.iUsuario.ingresarDatosCliente("hernacar", "Carlos", "carlosh89@mxmail.com", "poi098lkj", Files.readAllBytes(Paths.get(this.path + "img/US08.jpg")), LocalDate.of(2024, 01, 01), "Hernández", LocalDate.of(1988, 9, 15), TipoDoc.Pasaporte, "GZ1234567", Pais.MEXICO);
			this.iUsuario.ingresarDatosAerolinea("iberia", "Iberia", "atencionclientes@iberia.com.uy", "qwer1234", Files.readAllBytes(Paths.get(this.path + "img/US09.png")), LocalDate.of(2024, 01, 01), "Aerolínea española que te conecta con Europa y otros destinos internacionales.", "https://www.iberia.com");
			this.iUsuario.ingresarDatosCliente("jackwil", "Jack", "jack.w.90@mail.br", "asdfzxcv", Files.readAllBytes(Paths.get(this.path + "img/US10.jpeg")), LocalDate.of(2024, 01, 01), "Oliveira", LocalDate.of(1990, 12, 10), TipoDoc.Pasaporte, "N98123456", Pais.BRASIL);
			this.iUsuario.ingresarDatosCliente("juanitop", "Juan", "juanito.uy@correo.com", "cde34rfv", Files.readAllBytes(Paths.get(this.path + "img/US11.jpeg")), LocalDate.of(2024, 01, 01), "Pérez", LocalDate.of(1990, 3, 12), TipoDoc.CI, "39142842", Pais.URUGUAY);
			this.iUsuario.ingresarDatosAerolinea("latam", "LATAM Airlines", "info@latam.com.uy", "mki8nju7", Files.readAllBytes(Paths.get(this.path + "img/US12.png")), LocalDate.of(2024, 01, 01), "Ofrecemos vuelos nacionales e internacionales", "https://www.latam.com");
			this.iUsuario.ingresarDatosCliente("liamth", "Liam", "liam.t.ca@mailbox.com", "bhu7vgy7", Files.readAllBytes(Paths.get(this.path + "img/US13.jpeg")), LocalDate.of(2024, 01, 01), "Thompson", LocalDate.of(1992, 11, 30), TipoDoc.Pasaporte, "AJ7684359", Pais.CANADA);
			this.iUsuario.ingresarDatosCliente("marsil14", "Martín", "m.silva94@webmail.uy", "vgy6cft5", Files.readAllBytes(Paths.get(this.path + "img/US14.jpeg")), LocalDate.of(2024, 01, 01), "Silva", LocalDate.of(1994, 1, 14), TipoDoc.Pasaporte, "C3456789", Pais.URUGUAY);
			this.iUsuario.ingresarDatosCliente("martig", "Marta", "marta.garcia95@webmail.es", "cft5xdr4", Files.readAllBytes(Paths.get(this.path + "img/US15.jpeg")), LocalDate.of(2024, 01, 01), "García", LocalDate.of(1995, 7, 5), TipoDoc.Pasaporte, "X1245786L", Pais.ESPANA);
			this.iUsuario.ingresarDatosCliente("sofi89", "Sofía", "sofia.lopez@correouruguay.com", "xde2zsw1", Files.readAllBytes(Paths.get(this.path + "img/US16.jpeg")), LocalDate.of(2024, 01, 01), "López", LocalDate.of(1989, 4, 25), TipoDoc.Pasaporte, "A9876543", Pais.URUGUAY);
			this.iUsuario.ingresarDatosAerolinea("zfly", "ZuluFly", "info@zfly.com", "r45tgvcf", Files.readAllBytes(Paths.get(this.path + "img/US17.png")), LocalDate.of(2024, 01, 01), "Viajes exlcusivos entre los destinos más solicitados", "http://www.zfly.com");
		} catch (IOException | UsuarioRegistradoException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cargarVuelos() {
		try (CSVReader readerVuelos = new CSVReaderBuilder(new FileReader(path + "2024Vuelos.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> vuelos = readerVuelos.readAll();

			for (String[] linea : vuelos) {
				String[] fecha = linea[4].split("/");
				String[] fechaAlta = linea[8].split("/");

				byte[] defaultImage = Files.readAllBytes(Paths.get(this.pathDefault + "default-flight-picture.png"));
				byte[] imagen = linea[9].equals("(Sin Imagen)") ? defaultImage : Files.readAllBytes(Paths.get(this.path + "img/" + linea[9]));

				iRutaVuelo.ingresarDatosVuelo(linea[3], Integer.parseInt(linea[6]), Integer.parseInt(linea[7]), LocalTime.parse(linea[5]), LocalDate.of(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0])), this.getRutaDeRef(linea[2]), LocalDate.of(Integer.parseInt(fechaAlta[2]), Integer.parseInt(fechaAlta[1]), Integer.parseInt(fechaAlta[0])), imagen);
			}
		} catch (IOException | CsvException | VueloRegistradoException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void ciudadesMap() {
		// Solución temporal a paises como enum
		DTCiudad CI01 = new DTCiudad("Montevideo", "Capital uruguaya, conocida por su Rambla, arquitectura colonial y vibrante vida cultural.", "https://montevideo.gub.uy", Pais.URUGUAY.getPais(), "Carrasco", LocalDate.of(2024, 4, 1));
		DTCiudad CI02 = new DTCiudad("Múnich", "Ciudad alemana con rica historia, arquitectura barroca y vibrante vida cultural", "https://www.munich.travel/es", Pais.ALEMANIA.getPais(), "Aeropuerto de Múnich", LocalDate.of(2024, 6, 23));
		DTCiudad CI03 = new DTCiudad("Ciudad de Panamá", "Moderno centro urbano con rascacielos, el Canal de Panamá y vibrante vida cultural.", "https://www.atp.gob.pa/", Pais.PANAMA.getPais(), "Tocumen", LocalDate.of(2024, 6, 25));
		DTCiudad CI04 = new DTCiudad("Buenos Aires", "Vibrante capital argentina, conocida por su arquitectura, tango y vida cultural.", "https://turismo.buenosaires.gob.ar/es", Pais.ARGENTINA.getPais(), "Aeroparque Jorge Newbery", LocalDate.of(2024, 7, 5));
		DTCiudad CI05 = new DTCiudad("Barcelona", "Ciudad catalana con arquitectura modernista, playas y vibrante vida cultural.", "https://ajuntament.barcelona.cat", Pais.ESPANA.getPais(), "Josep Tarradellas Barcelona–El Prat ", LocalDate.of(2024, 7, 5));
		DTCiudad CI06 = new DTCiudad("Santiago de Chile", "Capital chilena con moderna arquitectura, cerros y rica vida cultural", "https://disfrutasantiago.cl", Pais.CHILE.getPais(), "Arturo Merino Benítez", LocalDate.of(2024, 7, 6));
		DTCiudad CI07 = new DTCiudad("Punta del Este", "Famoso balneario uruguayo, con playas, vida nocturna y lujosos resorts", "https://www.maldonado.gub.uy", Pais.URUGUAY.getPais(), "Laguna del Sauce", LocalDate.of(2024, 7, 15));
		DTCiudad CI08 = new DTCiudad("Madrid", "Vibrante capital española con rica historia, cultura y arquitectura impresionante.", "https://www.turismomadrid.es/es/", Pais.ESPANA.getPais(), "Adolfo Suárez Madrid-Barajas.", LocalDate.of(2024, 8, 12));
		DTCiudad CI09 = new DTCiudad("Nueva York", "Ciudad icónica con rascacielos, cultura diversa y atracciones famosas", "https://www.nyc.gov/", Pais.ESTADOS_UNIDOS_AMERICA.getPais(), "ohn F. Kennedy", LocalDate.of(2024, 8, 25));
		DTCiudad CI10 = new DTCiudad("Río de Janeiro", " Ciudad costera de Brasil, famosa por sus playas y la estatua del Cristo Redentor.", "https://riotur.rio/es/bienvenido/", Pais.BRASIL.getPais(), "Galeão Antonio Carlos Jobim", LocalDate.of(2024, 7, 1));
		DTCiudad CI11 = new DTCiudad("Sevilla", "Sevilla, es un destino turístico fascinante que ofrece una rica mezcla de historia, cultura y belleza.", "https://visitasevilla.es/", Pais.ESPANA.getPais(), "Sevilla-San Pablo", LocalDate.of(2024, 2, 29));

		this.ciudadMap.put("CI01", CI01);
		this.ciudadMap.put("CI02", CI02);
		this.ciudadMap.put("CI03", CI03);
		this.ciudadMap.put("CI04", CI04);
		this.ciudadMap.put("CI05", CI05);
		this.ciudadMap.put("CI06", CI06);
		this.ciudadMap.put("CI07", CI07);
		this.ciudadMap.put("CI08", CI08);
		this.ciudadMap.put("CI09", CI09);
		this.ciudadMap.put("CI10", CI10);
		this.ciudadMap.put("CI11", CI11);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.cargarUsuarios();
		this.cargarCategorias();
		this.cargarCiudades();
		this.cargarRutas();
		this.cargarVuelos();
		this.cargarReservas();

		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String getCategoriaDeRef(String ref) {
		try (CSVReader readerCategorias = new CSVReaderBuilder(new FileReader(path + "2024Categorias.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> categorias = readerCategorias.readAll();

			for (String[] linea : categorias) {
				if (linea[0].equals(ref)) {
					return linea[1];
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private String getNickDeRef(String ref) {
		try (CSVReader readerUsuarios = new CSVReaderBuilder(new FileReader(path + "2024Usuarios.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> usuarios = readerUsuarios.readAll();

			for (String[] linea : usuarios) {
				if (linea[0].equals(ref)) {
					return linea[2];
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private String getRutaDeRef(String ref) {
		try (CSVReader readerRutas = new CSVReaderBuilder(new FileReader(path + "2024RutasVuelos.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> rutas = readerRutas.readAll();

			for (String[] linea : rutas) {
				if (linea[0].equals(ref)) {
					return linea[2];
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private String getVueloDeRef(String ref) {
		try (CSVReader readerVuelos = new CSVReaderBuilder(new FileReader(path + "2024Vuelos.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> vuelos = readerVuelos.readAll();

			for (String[] linea : vuelos) {
				if (linea[0].equals(ref)) {
					return linea[3];
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	private Set<DTPasaje> cargarPasajes(String ref) {
		Set<DTPasaje> nombres = new HashSet<DTPasaje>();
		try (CSVReader readerPasajes = new CSVReaderBuilder(new FileReader(path + "2024Pasajes.csv"))
				.withSkipLines(1)
				.withCSVParser(new com.opencsv.CSVParserBuilder()
					.withSeparator(';')
					.build())
				.build()) {
			List<String[]> pasajes = readerPasajes.readAll();

			for (String[] linea : pasajes) {
				if (linea[1].equals(ref)) {
					nombres.add(new DTPasaje(linea[2], linea[3]));
				}
			}
		} catch (IOException | CsvException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Cargar datos", JOptionPane.ERROR_MESSAGE);
		}
		return nombres;
	}

}
