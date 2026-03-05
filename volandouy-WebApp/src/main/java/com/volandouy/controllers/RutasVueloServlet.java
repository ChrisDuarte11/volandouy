package com.volandouy.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import datatypes.DTRutaVuelo;
import datatypes.DTUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.enums.EstadoRuta;
import logica.vuelo.ManejadorRutaVuelo;
import logica.vuelo.RutaVuelo;
import logica.vuelo.categoria.Categoria;

/**
 * Utilizado para:
 * 	- Cargar categorías en header y menú izquerdo
 * 	- Cargar TODAS las rutas de vuelo en home.jsp
 * 		- Todavía no carga rutas de vuelo según su categoría
 */
@WebServlet("/rutas")
public class RutasVueloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public RutasVueloServlet() {
        super();
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.cargarCategorias(request);

    	Set<RutaVuelo> rutas = ManejadorRutaVuelo.getInstance().obtenerRutas();

    	if (request.getParameter("categoria") != null) {
    		String categoria = request.getParameter("categoria");
    		if (rutas != null) {
    			Categoria categoriaBuscada = new Categoria(categoria);
    			Iterator<RutaVuelo> iterator = rutas.iterator();
    			while (iterator.hasNext()) {
    				RutaVuelo ruta = iterator.next();
					if (!ruta.getCategorias().contains(categoriaBuscada)) {
						iterator.remove();
					}
				}
    		}
    	}
    	DTRutaVuelo[] listaRutas = rutas.stream().map(RutaVuelo::getDT).filter(ruta -> ruta.getEstado().equals(EstadoRuta.CONFIRMADA) || (DTUsuario) request.getSession().getAttribute("usuario_logueado") != null && ruta.getNickAerolinea().equals(((DTUsuario) request.getSession().getAttribute("usuario_logueado")).getNickname())).toArray(DTRutaVuelo[]::new);

    	request.setAttribute("rutas", listaRutas);
    	request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }

    private void cargarCategorias(HttpServletRequest request) {
    	Set<String> categorias = ManejadorRutaVuelo.getInstance().obtenerCategorias();
    	String[] listaCategorias = categorias.toArray(String[]::new);
    	request.getSession().setAttribute("categorias", listaCategorias);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

}
