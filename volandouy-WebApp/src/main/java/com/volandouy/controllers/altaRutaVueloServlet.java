package com.volandouy.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import datatypes.DTAerolinea;
import datatypes.DTCiudad;
import datatypes.DTCostos;
import datatypes.DTUsuario;
import excepciones.RutaVueloExistenteException;
import excepciones.UsuarioNoExisteException;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.vuelo.ciudad.Ciudad;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 2,
	maxRequestSize = 1024 * 1024 * 3
)
@WebServlet("/altaRutaVuelo")
public class altaRutaVueloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public altaRutaVueloServlet() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreRuta = (String) request.getParameter("nombre");
		String descripcionRuta = (String) request.getParameter("descripcion");
		String descripcionCorta = (String) request.getParameter("descripcionCorta");
		String horaRuta = (String) request.getParameter("horaRuta");
		String costoTurista = (String) request.getParameter("costoTurista");
		String costoEjecutivo = (String) request.getParameter("costoEjecutivo");
		String costoEquipaje = (String) request.getParameter("costoEquipaje");
		String paisOrigen = (String) request.getParameter("paisOrigen");
		String ciudadOrigen = (String) request.getParameter("ciudadOrigen");
		String paisDestino = (String) request.getParameter("paisDestino");
		String ciudadDestino = (String) request.getParameter("ciudadDestino");
		String[] categoriasRuta = (String[]) request.getParameterValues("categoriasRuta");
		Part imagenRuta = request.getPart("imagenRuta");
		byte[] imagenBytes = null;

		LocalTime hora = null;

		/**
		 * Datos que ya tienen validación en front-end, pero que pueden terminar acá por alguna razón
		 */
		try {
			hora = LocalTime.parse(horaRuta);
			Float.parseFloat(costoTurista);
			Float.parseFloat(costoEjecutivo);
			Float.parseFloat(costoEquipaje);
		} catch (DateTimeParseException | NumberFormatException | NullPointerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			this.doGet(request, response);
			return;
		}

		if (imagenRuta != null && imagenRuta.getSize() > 0) {
			imagenBytes = this.imagenDePart(imagenRuta);
		} else {
			imagenBytes = Files.readAllBytes(Paths.get(getServletContext().getRealPath("/resources/img/") + "default-flight-route-picture.jpg"));
		}
		
		try {
			IRutaVuelo iRutaVuelo = Fabrica.getInstance().getIRutaVuelo();
			DTCostos costos = new DTCostos(Float.parseFloat(costoEjecutivo), Float.parseFloat(costoTurista), Float.parseFloat(costoEquipaje));
			DTCiudad origen = iRutaVuelo.obtenerDTCiudadPais(ciudadOrigen, paisOrigen);
			DTCiudad destino = iRutaVuelo.obtenerDTCiudadPais(ciudadDestino, paisDestino);
			String nickAerolinea = ((DTUsuario) request.getSession().getAttribute("usuario_logueado")).getNickname();

			try {
				List<String> catStrings = new ArrayList<>();
				if (categoriasRuta != null && categoriasRuta.length > 0) catStrings = Arrays.asList(categoriasRuta);
				iRutaVuelo.ingresarDatosRuta(nombreRuta, descripcionRuta, hora, costos, origen, destino, LocalDate.now(), iRutaVuelo.obtenerCategorias(catStrings), nickAerolinea, imagenBytes, descripcionCorta);
				request.setAttribute("confirmationMessage", "La ruta ha sido creada con éxito");
				this.doGet(request, response);
			} catch (UsuarioNoExisteException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} catch (NumberFormatException | NullPointerException | RutaVueloExistenteException e) {
			request.setAttribute("errorMessage", "Ya existe una ruta con ese nombre");
			this.doGet(request, response);
			return;
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DTUsuario usuario = (DTUsuario) request.getSession().getAttribute("usuario_logueado");
		
		if (usuario == null) {
			response.sendRedirect("login");
			return;
		}
		
		if (!(usuario instanceof DTAerolinea)) {
			response.sendRedirect("home");
			return;
		}
		
		IRutaVuelo iRutaVuelo = Fabrica.getInstance().getIRutaVuelo();
		DTCiudad[] ciudades = iRutaVuelo.obtenerCiudades().stream().map(Ciudad::getDT).toArray(DTCiudad[]::new);
		String[] paises = iRutaVuelo.obtenerPaises().toArray(String[]::new);
		String[] categorias = (String[]) request.getSession().getAttribute("categorias");
		
		request.setAttribute("ciudades", ciudades);
		request.setAttribute("paises", paises);
		request.setAttribute("categorias", categorias);
		request.getRequestDispatcher("/WEB-INF/rutas/altaRutaVuelo.jsp").forward(request, response);
	}
	
	private byte[] imagenDePart(Part imagen) {
		InputStream inputStream = null;
		try {
			inputStream = imagen.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];

		try {
			while ((nRead = inputStream.read(data)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}

}
