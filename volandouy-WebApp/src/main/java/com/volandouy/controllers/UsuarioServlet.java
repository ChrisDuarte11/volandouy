package com.volandouy.controllers;

import java.io.IOException;
import java.util.Set;

import datatypes.DTAerolinea;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import excepciones.UsuarioNoExisteException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.enums.EstadoRuta;
import logica.fabrica.Fabrica;
import logica.fabrica.IUsuario;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UsuarioServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String nickname = request.getParameter("nickname");
 		IUsuario iUsuario = Fabrica.getInstance().getIUsuario();

 		if (nickname == null) { // Caso 1: Listar todos los usuarios
            Set<DTUsuario> usuarios = iUsuario.getUsuarios();
            request.setAttribute("usuarios", usuarios.toArray(DTUsuario[]::new));
            request.getRequestDispatcher("/WEB-INF/usuarios/listarUsuarios.jsp").forward(request, response);
        } else { // Caso 2: Consulta de usuario
            try {
                DTUsuario usuario = iUsuario.obtenerUsuario(nickname);
                request.setAttribute("usuario", usuario);

                if (usuario instanceof DTAerolinea) { // Si es aerolínea
                    Set<DTRutaVuelo> rutasConfirmadas = iUsuario.obtenerRutasAerolinea(usuario.getNickname(), EstadoRuta.CONFIRMADA);
                    request.setAttribute("rutasConfirmadas", rutasConfirmadas);
                    System.out.println(rutasConfirmadas.size());
                    if (isPropioPerfil(usuario.getNickname(), request)) {
                        Set<DTRutaVuelo> rutasExtra = iUsuario.obtenerRutasAerolinea(usuario.getNickname(), EstadoRuta.INGRESADA, EstadoRuta.RECHAZADA);
                        request.setAttribute("rutasExtra", rutasExtra);
                    }
                } else {
                    // Si es cliente
                	if (isPropioPerfil(usuario.getNickname(), request)) {
	                    Set<DTReserva> reservas = iUsuario.obtenerReservas(usuario.getNickname());
	                    request.setAttribute("reservas", reservas);
                	}
                }

                // Redirigir al perfil del usuario
                request.getRequestDispatcher("/WEB-INF/usuarios/consultaDeUsuario.jsp").forward(request, response);

            } catch (UsuarioNoExisteException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
            }
        }

    }

    protected Boolean isPropioPerfil(String nickUsuario, HttpServletRequest request)  {
    	DTUsuario usuarioAutenticado = (DTUsuario) request.getSession().getAttribute("usuario_logueado");
        Boolean usr =  usuarioAutenticado != null && usuarioAutenticado.getNickname().equals(nickUsuario);
        request.setAttribute("esPropioPerfil", usr);
        return usr;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
