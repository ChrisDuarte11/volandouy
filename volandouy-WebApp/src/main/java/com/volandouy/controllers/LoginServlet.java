package com.volandouy.controllers;

import java.io.IOException;

import com.volandouy.models.EstadoSesion;
import com.volandouy.models.TipoUsuario;

import datatypes.DTAerolinea;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.fabrica.Fabrica;
import logica.fabrica.IUsuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nicknameEmail = (String) request.getParameter("nicknameEmail");
		String password = (String) request.getParameter("password");
		IUsuario iUsuario = Fabrica.getInstance().getIUsuario();

		if (nicknameEmail == null) {
			request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
			return;
		}

		DTUsuario usuario = null;
		try {
			usuario = iUsuario.obtenerUsuario(nicknameEmail);
		} catch (UsuarioNoExisteException e) {
			request.setAttribute("errorMessage", "nicknameEmail");
			request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
			return;
		}
    		
		if (!usuario.getPassword().equals(password)) {
			request.setAttribute("errorMessage", "password");
			request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
			return;
		}

		HttpSession sesion = request.getSession();

		if (usuario instanceof DTAerolinea) {
			sesion.setAttribute("tipo_usuario", TipoUsuario.AEROLINEA);
		} else {
			sesion.setAttribute("tipo_usuario", TipoUsuario.CLIENTE);
		}

		sesion.setAttribute("usuario_logueado", usuario);
		sesion.setAttribute("estado_sesion", EstadoSesion.LOGIN_OK);
		response.sendRedirect("home");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesion = request.getSession();
		
		// Verifica si quien está intentando loguearse ya tiene una cuenta y está logueado
		if (sesion.getAttribute("estado_sesion") == EstadoSesion.LOGIN_OK) {
			response.sendRedirect("home");
		} else {
			request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
