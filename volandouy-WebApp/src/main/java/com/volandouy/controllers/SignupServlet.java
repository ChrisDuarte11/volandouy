package com.volandouy.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.volandouy.models.EstadoSesion;
import com.volandouy.models.TipoUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import excepciones.UsuarioRegistradoException;
import logica.enums.Pais;
import logica.enums.TipoDoc;
import logica.fabrica.Fabrica;
import logica.fabrica.IUsuario;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024,
	maxFileSize = 1024 * 1024 * 2,
	maxRequestSize = 1024 * 1024 * 3
)
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public SignupServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tipoUsuario = (String) request.getParameter("tipoUsuario");
		String nickname = (String) request.getParameter("nickname");
		String email = (String) request.getParameter("email");
		String nombre = (String) request.getParameter("nombre");
		String password = (String) request.getParameter("password");
		Part imagenPerfil = request.getPart("imagenPerfil");
		byte[] imagenBytes = null;

		// Datos de aerolínea
		String descripcion = (String) request.getParameter("descripcion");
		String paginaWeb = (String) request.getParameter("paginaWeb");

		// Datos de cliente
		String apellido = (String) request.getParameter("apellido");
		String fechaNacimiento = (String) request.getParameter("fechaNacimiento");;
		String tipoDocumento = (String) request.getParameter("tipoDocumento");;
		String nroDocumento = (String) request.getParameter("nroDocumento");;
		String nacionalidad = (String) request.getParameter("nacionalidad");;

		if (imagenPerfil != null && imagenPerfil.getSize() > 0) {
			imagenBytes = this.imagenDePart(imagenPerfil);
		} else {
			imagenBytes = Files.readAllBytes(Paths.get(getServletContext().getRealPath("/resources/img/") + "default-profile-picture.png"));
		}

		EstadoSesion nuevoEstado;

		HttpSession sesion = request.getSession();

		try {
			IUsuario iUsuario = Fabrica.getInstance().getIUsuario();
			if (tipoUsuario.equals("aerolinea")) {
				iUsuario.ingresarDatosAerolinea(nickname, nombre, email, password, imagenBytes, LocalDate.now(), descripcion, paginaWeb);

				sesion.setAttribute("tipo_usuario", TipoUsuario.AEROLINEA);
			} else if (tipoUsuario.equals("cliente")) {
				TipoDoc tipoDoc;
				if (tipoDocumento.equals("cliente")) {
					tipoDoc = TipoDoc.CI;
				} else if (tipoDocumento == "dni") {
					tipoDoc = TipoDoc.DNI;
				} else {
					tipoDoc = TipoDoc.Pasaporte;
				}

				iUsuario.ingresarDatosCliente(nickname, nombre, email, password, imagenBytes, LocalDate.now(), apellido, LocalDate.parse(fechaNacimiento), tipoDoc, nroDocumento, Pais.desdeString(nacionalidad));

				sesion.setAttribute("tipo_usuario", TipoUsuario.CLIENTE);
			}

			nuevoEstado = EstadoSesion.LOGIN_OK;

			DTUsuario dtUsuario = null;
			try {
				dtUsuario = iUsuario.obtenerUsuario(nickname);
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			}
			sesion.setAttribute("usuario_logueado", dtUsuario);

		} catch (UsuarioRegistradoException e) {
			nuevoEstado = EstadoSesion.NO_LOGIN;
			request.setAttribute("errorMessage", e.getMessage());
		}

		sesion.setAttribute("estado_sesion", nuevoEstado);

		if (nuevoEstado == EstadoSesion.NO_LOGIN) {
			request.setAttribute("paises", Pais.values());
			request.getRequestDispatcher("WEB-INF/auth/signup.jsp").forward(request, response);
		} else {
			response.sendRedirect("home");
		}

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();

		// Verifica si quien está intentando crear una cuenta ya tiene una cuenta y está logueado
		if (sesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_OK) {
			response.sendRedirect("home");
		} else {
			request.setAttribute("paises", Pais.values());
			request.getRequestDispatcher("WEB-INF/auth/signup.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
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
		byte[] data = new byte[1024]; //4096

		try {
			while ((nRead = inputStream.read(data)) != -1) { // inputStream.read(data, 0, data.length))
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}

}
