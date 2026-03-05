package com.volandouy.controllers;

import java.io.IOException;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
//import com.volando.controladores.ControladorUsuario;
//import com.volando.dtos.DTReserva;
//import com.volando.dtos.DTUsuario;
//import com.volando.exceptions.UsuarioNoExisteException;
import datatypes.DTVuelo;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@WebServlet("/consultarReserva")
public class ConsultaReservaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultaReservaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int identificador = Integer.parseInt(request.getParameter("id"));
        try {
        	Fabrica fabrica = Fabrica.getInstance();
    		IUsuario iUsuario = fabrica.getIUsuario();
    		IRutaVuelo iRutaVuelo = fabrica.getIRutaVuelo();
            DTReserva reserva = iUsuario.obtenerReserva(identificador);
            DTVuelo vuelo = iRutaVuelo.obtenerVuelo(reserva);
            DTRutaVuelo rutaVuelo = iRutaVuelo.obtenerRuta(vuelo);
            DTCliente cliente = iUsuario.obtenerClienteReserva(reserva);
            DTAerolinea aerolinea = iUsuario.obtenerAerolinea(rutaVuelo.getNombre());

            request.setAttribute("reserva", reserva);
            request.setAttribute("vuelo", vuelo);
            request.setAttribute("rutaVuelo", rutaVuelo);
            request.setAttribute("cliente", cliente);
            request.setAttribute("aerolinea", aerolinea);


            request.getRequestDispatcher("/WEB-INF/reservas/consultarReserva.jsp").forward(request, response);

        } catch (IOException | UsuarioNoExisteException | RutaNoExisteException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reserva no encontrada.");
        }
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		this.processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

}
