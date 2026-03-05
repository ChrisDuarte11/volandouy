package com.volandouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;

import java.io.IOException;
import java.util.Set;

import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.RutaNoExisteException;

@WebServlet("/consultarRutaDeVuelo")
public class ConsultarRutaDeVueloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsultarRutaDeVueloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ruta = request.getParameter("ruta");
        try {
        	Fabrica fabrica = Fabrica.getInstance();
    		IRutaVuelo iRutaVuelo = fabrica.getIRutaVuelo();
    		DTRutaVuelo rutaDeVuelo = iRutaVuelo.buscarDTRutaVuelo(ruta);

    		request.setAttribute("rutaDeVuelo", rutaDeVuelo);
    		Set<DTVuelo> vuelos = iRutaVuelo.obtenerDTVuelos(ruta);
    		request.setAttribute("vuelos", vuelos);

            request.getRequestDispatcher("/WEB-INF/rutas/consultarRutaDeVuelo.jsp").forward(request, response);

        } catch (IOException | RutaNoExisteException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada.");
        }
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
