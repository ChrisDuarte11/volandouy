package com.volandouy.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTAerolinea;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import excepciones.VueloRegistradoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.enums.EstadoRuta;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 2,
	maxRequestSize = 1024 * 1024 * 3
)
@WebServlet("/altaVuelo")
public class altaVueloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public altaVueloServlet() {
		super();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rutaVuelo = (String) request.getParameter("rutaVuelo");
		String nombreVuelo = (String) request.getParameter("nombreVuelo");
		String cantidadTurista = (String) request.getParameter("cantidadTurista");
		String cantidadEjecutivo = (String) request.getParameter("cantidadEjecutivo");
		String fechaVuelo = (String) request.getParameter("fechaVuelo");
		String duracionVueloHoras = (String) request.getParameter("duracionVueloHoras");
		String duracionVueloMinutos = (String) request.getParameter("duracionVueloMinutos");
		Part imagenVuelo = request.getPart("imagenVuelo");
		byte[] imagenBytes = null;
		
		if (nombreVuelo == null) { // Caso en el que se intenta refrescar la página (se utiliza POST y no GET) y no hay un nombreVuelo
			this.cargarRutas(request);
			request.getRequestDispatcher("/WEB-INF/rutas/altaVuelo.jsp").forward(request, response);
			return;
		}
		
		if (imagenVuelo != null && imagenVuelo.getSize() > 0) {
			imagenBytes = this.imagenDePart(imagenVuelo);
		} else {
			imagenBytes = Files.readAllBytes(Paths.get(getServletContext().getRealPath("/resources/img/") + "default-flight-picture.png"));
		}
		
		if (duracionVueloHoras.length() == 1) {
			duracionVueloHoras = "0" + duracionVueloHoras;
		}
		if (duracionVueloMinutos.length() == 1) {
			duracionVueloMinutos = "0" + duracionVueloMinutos;
		}
		String duracionVuelo = duracionVueloHoras + ":" + duracionVueloMinutos;
		LocalDate fecha = LocalDate.parse(fechaVuelo);
		LocalTime duracion = LocalTime.parse(duracionVuelo);

		try {
			IRutaVuelo iRutaVuelo = Fabrica.getInstance().getIRutaVuelo();
			iRutaVuelo.ingresarDatosVuelo(nombreVuelo, Integer.parseInt(cantidadTurista), Integer.parseInt(cantidadEjecutivo), duracion, fecha, rutaVuelo, LocalDate.now(), imagenBytes);
			request.setAttribute("confirmationMessage", "El vuelo ha sido creado con éxito");
			this.doGet(request, response);
		} catch (VueloRegistradoException e) {
			request.setAttribute("errorMessage", "Ya existe un vuelo con ese nombre");
			this.cargarRutas(request);
			request.getRequestDispatcher("/WEB-INF/rutas/altaVuelo.jsp").forward(request, response);
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
		
		this.cargarRutas(request);

        request.getRequestDispatcher("/WEB-INF/rutas/altaVuelo.jsp").forward(request, response);
	}
	
	/**
	 * Carga las rutas de vuelo de la aerolínea logueada
	 * Utilizar únicamente si se sabe que hay un usuario logueado y es una aerolínea
	 * @param request
	 */
	private void cargarRutas(HttpServletRequest request) {
		IRutaVuelo iRutaVuelo = Fabrica.getInstance().getIRutaVuelo();
		DTUsuario usuario = (DTUsuario) request.getSession().getAttribute("usuario_logueado");
		try {
			Set<DTRutaVuelo> rutas = iRutaVuelo.obtenerRutasVuelo(usuario.getNickname());
			rutas = rutas.stream().filter(ruta -> ruta.getEstado().equals(EstadoRuta.CONFIRMADA)).collect(Collectors.toSet());
			request.setAttribute("rutas", rutas.toArray(DTRutaVuelo[]::new));
		} catch (UsuarioNoExisteException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
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
