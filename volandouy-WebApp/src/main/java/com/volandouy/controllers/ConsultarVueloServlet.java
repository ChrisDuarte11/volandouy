package com.volandouy.controllers;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;

import datatypes.DTAerolinea;
import datatypes.DTCliente;
import datatypes.DTReserva;
import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import datatypes.DTVuelo;
import excepciones.RutaNoExisteException;
import excepciones.UsuarioNoExisteException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.enums.EstadoRuta;
import logica.fabrica.Fabrica;
import logica.fabrica.IRutaVuelo;
import logica.fabrica.IUsuario;

@WebServlet("/consultaVuelo")
public class ConsultarVueloServlet extends HttpServlet {

    public ConsultarVueloServlet() {
        super();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    	Fabrica fabrica = Fabrica.getInstance();
		IUsuario iUsuario = fabrica.getIUsuario();
		IRutaVuelo iRutaVuelo = fabrica.getIRutaVuelo();

        String aerolinea = request.getParameter("aerolinea");
        String ruta = request.getParameter("ruta");
        String vuelo = request.getParameter("vuelo");

        if (aerolinea == null) {
            // Si no se ha seleccionado aerolínea, listar todas
            Set<DTAerolinea> aerolineas =  iUsuario.obtenerDTAerolineas();
            request.setAttribute("aerolineas", aerolineas);
            request.getRequestDispatcher("/WEB-INF/usuarios/listarAerolineas.jsp").forward(request, response);

        } else if (ruta == null) {
            //Listar rutas confirmadas de la aerolínea seleccionada
            Set<DTRutaVuelo> rutasConfirmadas = null;
			try {
				rutasConfirmadas = iUsuario.obtenerRutasAerolinea(aerolinea, EstadoRuta.CONFIRMADA);
			} catch (UsuarioNoExisteException e) {
				e.printStackTrace();
			}
            request.setAttribute("rutas", rutasConfirmadas);
            request.setAttribute("aerolinea", aerolinea); //no se usa?
            request.getRequestDispatcher("/WEB-INF/rutas/listarRutas.jsp").forward(request, response);

        } else if (vuelo == null) {
            //Listar vuelos asociados a la ruta seleccionada
            Set<DTVuelo> vuelos = null;
			try {
				vuelos = iRutaVuelo.obtenerDTVuelos(ruta);
			} catch (RutaNoExisteException e) {
				e.printStackTrace();
			}
            LocalTime horaRuta = iRutaVuelo.obtenerRuta(ruta).getHora();
            request.setAttribute("vuelos", vuelos);
            request.setAttribute("horaRuta", horaRuta);
            request.setAttribute("ruta", ruta);
            request.getRequestDispatcher("/WEB-INF/rutas/listarVuelos.jsp").forward(request, response);

        } else {
            //Mostrar detalles del vuelo

            DTVuelo vueloSeleccionado = iRutaVuelo.obtenerDTVuelo(ruta, vuelo);
            request.setAttribute("vuelo", vueloSeleccionado);

            DTUsuario usuarioActual = (DTUsuario) request.getSession().getAttribute("usuario_logueado");
            request.setAttribute("usuario", usuarioActual);
            if (usuarioActual instanceof DTAerolinea && usuarioActual.getNickname().equals(aerolinea)) {
                // Si es la aerolínea, mostrar reservas del vuelo
                Set<DTReserva> reservas = iRutaVuelo.obtenerReservas(ruta, vuelo);
                System.out.println(reservas.size());
                request.setAttribute("reservas", reservas);
            } else if (usuarioActual instanceof DTCliente) {
                // Si es un cliente con reserva, mostrar su reserva
                DTReserva reservaCliente = null;
				try {
					reservaCliente = (DTReserva) iUsuario.obtenerReserva(usuarioActual.getNickname(), vuelo);
				} catch (UsuarioNoExisteException e) {
					e.printStackTrace();
				}
                request.setAttribute("reservaCliente", reservaCliente);
            }

            request.getRequestDispatcher("/WEB-INF/rutas/consultaVuelo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}