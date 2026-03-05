package com.volandouy.controllers;

import java.io.IOException;

import com.volandouy.models.EstadoSesion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

    public static void initSession(HttpServletRequest request) {
    	HttpSession sesion = request.getSession();
    	if (sesion.getAttribute("estado_sesion") == null) {
    		sesion.setAttribute("estado_sesion", EstadoSesion.NO_LOGIN);
    	}
    	
    }
   
    public static EstadoSesion getEstado(HttpServletRequest request) {
    	return (EstadoSesion) request.getSession().getAttribute("estado_sesion");
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	initSession(request);
    	
    	switch (getEstado(request)) {
    		case NO_LOGIN:
    			request.getRequestDispatcher("rutas").forward(request, response);
    			break;
    		case LOGIN_ERROR:
    			request.getRequestDispatcher("WEB-INF/auth/login.jsp").forward(request, response);
    			break;
    		case LOGIN_OK:
    			request.getRequestDispatcher("rutas").forward(request, response);
    			break;
    	}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
