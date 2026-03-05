package com.volandouy.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.enums.EstadoRuta;
import logica.enums.TipoAsiento;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTCliente;
import datatypes.DTPasaje;
import datatypes.DTReserva;
import datatypes.DTUsuario;
import datatypes.DTRutaVuelo;
import datatypes.DTVuelo;
import excepciones.ClienteTieneReservaEnVueloException;
import excepciones.RutaNoExisteException;
import excepciones.ReservaCantidadAsientosInvalidos;
import excepciones.UsuarioNoExisteException;

@WebServlet("/altaReserva")
public class AltaReservaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AltaReservaServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (request.getParameter("aerolineas") == null || request.getParameter("rutas") == null || request.getParameter("vuelos") == null || request.getSession().getAttribute("usuario_logueado") == null) { 
    		this.doGet(request, response);
    		return;
    	}
    	
		/**
		 * Datos que ya tienen validación en front-end, pero que pueden terminar acá por alguna razón
		 */
    	try {
    		Integer.parseInt(request.getParameter("pasajesInput"));
    		Integer.parseInt(request.getParameter("equipaje"));
    	} catch (NumberFormatException e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    		this.doGet(request, response);
    		return;
    	}
    	
	    Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		try {
			int cantPasajeros = Integer.parseInt(request.getParameter("pasajesInput"));
			String pasajeros = request.getParameter("pasajeros");
			Set<DTPasaje> setPasajeros = new HashSet<>();

			if (pasajeros.length() > 0) {
				for (String pasajero: pasajeros.split(", ")) {
					String[] pas = pasajero.split(" ");
					String nombre = pasajero.split(" ")[0];
					String apellido = "";
					if (pas.length > 1) apellido = pasajero.split(" ")[1];
					setPasajeros.add(new DTPasaje(nombre, apellido));
				}
			}

			if (cantPasajeros - 1 != setPasajeros.size()) { // Puesto que no se crea el pasaje del usuario que hace la reserva
				request.setAttribute("errorPasajeros", "error");
				this.doGet(request, response);
				return;
			}

			TipoAsiento tipoAsiento = request.getParameter("tipo").equals("turista") ? TipoAsiento.TURISTA : TipoAsiento.EJECUTIVO;
			int cantEquipaje = Integer.parseInt(request.getParameter("equipaje"));
			String vuelo = request.getParameter("vuelos");
			String ruta = request.getParameter("rutas");
			iUsuario.ingresarDatosReserva(new DTReserva(LocalDate.now(), tipoAsiento, cantPasajeros, cantEquipaje, vuelo, setPasajeros), ((DTUsuario) request.getSession().getAttribute("usuario_logueado")).getNickname(), ruta);
			request.setAttribute("confirmationMessage", "La reserva ha sido creada con éxito");
		} catch (UsuarioNoExisteException e) {
			request.setAttribute("error", "El usuario no existe");
		} catch (ClienteTieneReservaEnVueloException e) {
			request.setAttribute("errorReservaExistente", "El usuario ya tiene una reserva para este vuelo");
		} catch (ReservaCantidadAsientosInvalidos e) {
			request.setAttribute("errorAsientosInvalidos", "Asientos Invalidos");
		}
		this.doGet(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DTUsuario usuario = (DTUsuario) request.getSession().getAttribute("usuario_logueado");
		
		if (usuario == null) {
			response.sendRedirect("login");
			return;
		}
		
		if (!(usuario instanceof DTCliente)) {
			response.sendRedirect("home");
			return;
		}
		
		try {
	    	Fabrica fabrica = Fabrica.getInstance();
			IUsuario iUsuario = fabrica.getIUsuario();
			IRutaVuelo iRutaVuelo = fabrica.getIRutaVuelo();

	        Set<DTRutaVuelo> rutas = new HashSet<>();
	        for (String aero: iUsuario.obtenerAerolineas()) {
	        	rutas.addAll(iRutaVuelo.obtenerRutasVuelo(aero));
	        }
	        rutas = rutas.stream().filter(ruta -> ruta.getEstado().equals(EstadoRuta.CONFIRMADA)).collect(Collectors.toSet());

	        Set<DTVuelo> vuelos = new HashSet<>();
	        for (DTRutaVuelo ruta: rutas) {
	        	vuelos.addAll(iRutaVuelo.obtenerVuelos(ruta));
	        }

	        request.setAttribute("aerolineas", iUsuario.obtenerDTAerolineas());
	        request.setAttribute("rutas", rutas);
	        request.setAttribute("vuelos", vuelos);
	        request.getRequestDispatcher("/WEB-INF/reservas/altaReserva.jsp").forward(request, response);
	    } catch (UsuarioNoExisteException | RutaNoExisteException e) {
	    	request.setAttribute("error", "error");
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
