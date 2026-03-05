<%@ page import="datatypes.DTVuelo" %>
<%@page import="java.util.Set"%>
<%@page import="java.time.LocalDate"%>
<%@page import="logica.usuario.Usuario"%>
<%@page import="logica.usuario.Aerolinea"%>
<%@page import="logica.usuario.Cliente"%>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="datatypes.DTUsuario" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/templates/head.jsp"/>
        <link rel="stylesheet" href="resources/css/home.css">
        <link rel="stylesheet" href="resources/css/consultaVuelo.css">
        <title>Volando.uy</title>
    </head>
    <body>
     	<jsp:include page="/WEB-INF/templates/header.jsp"/>
        <main class="flex-grow-1">
            <div class="container py-4">
                <div class="row">
                    <jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>
                    <div class="col-md-9">
                        <div class="main">
                            <div class="foto">
                            <% DTVuelo vuelo = (DTVuelo) request.getAttribute("vuelo"); %>

                                <img class="user-img" style="width: 300px; height: auto;" src="data:image/*;base64, <%=vuelo.getImagen() == null ? "resources/img/default-flight-picture.png" : Base64.getEncoder().encodeToString(vuelo.getImagen())%>" alt="<%=vuelo.getNombre()%>">
                            </div>
                            <div class="nombre">

                                <h1><%= vuelo.getNombre() %></h1>
                            </div>
                            <div class="datos">
                                <p><strong>Nombre: </strong><%= vuelo.getNombre() %></p>
                                <p><strong>Fecha: </strong><%= vuelo.getFecha() %></p>
                                <p><strong>Duración: </strong><%= vuelo.getDuracion() %></p>
                                <p><strong>Asientos turista: </strong><%= vuelo.getAsientosMaxTurista() %></p>
                                <p><strong>Asientos ejecutivo: </strong><%= vuelo.getAsientosMaxEjecutivo() %></p>
                            </div>

                            <div class="reserva">
                                <h3>Reservas:</h3>
                                <% DTUsuario usuario = (DTUsuario) request.getAttribute("usuario");  %>
                                <% if (usuario instanceof DTAerolinea) {
                                	  Set<DTReserva> reservas = (Set<DTReserva>) request.getAttribute("reservas");
                                	 if (reservas != null ) { %>
                                    <ul>
                                    <%
                                    	for (DTReserva reserva : reservas) { %>
                                        <li>Reserva: <a href="consultarReserva?id=<%= reserva.getId() %>"><%= reserva.getId() %></a></li>
                                    <% } %>
                                   <% } %>
                                    </ul>
                                <% } else if (usuario instanceof DTCliente){
                                			DTReserva reservasCliente = (DTReserva) request.getAttribute("reservaCliente");
                                			if(reservasCliente != null) { %>
                                    <p><a href="consultarReserva?id=<%= reservasCliente.getId() %>">Ver mi reserva</a></p>
                                <% }
                                			} else { %>
                                    <p>No hay reservas asociadas a este vuelo.</p>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="/WEB-INF/templates/footer.jsp"/>

    </body>
</html>