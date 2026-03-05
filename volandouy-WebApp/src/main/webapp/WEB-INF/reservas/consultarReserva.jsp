<%@ page import="java.util.Base64" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="datatypes.DTPasaje" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page import="datatypes.DTVuelo" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Consulta de Reserva - Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/consultaReservaVuelo.css">
</head>

<body>
   <jsp:include page="/WEB-INF/templates/header.jsp"/>
	<% DTReserva res = (DTReserva) request.getAttribute("reserva"); %>
	<% DTAerolinea aerolinea = (DTAerolinea) request.getAttribute("aerolinea"); %>
	<% DTRutaVuelo rutaVuelo = (DTRutaVuelo) request.getAttribute("rutaVuelo"); %>
	<% DTVuelo vuelo = (DTVuelo) request.getAttribute("vuelo"); %>
	<% DTCliente cliente = (DTCliente) request.getAttribute("cliente"); %>
    <main class="flex-grow-1">
        <div class="container py-4">
            <div class="row">
            	<jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>
				<div class="col-12 col-lg-10">
                        <div class="main">
                            <div class="datos">
                                <h1>Reserva</h1>
                                <p class="gray"><strong>Tipo Asiento:</strong> <%= res.getTipoAsiento() %></p>
                                <p><strong>Cantidad Pasajeros: </strong> <%= res.getCantPasajes() %></p>
                                <p class="gray"><strong>Cantidad Equipaje Extra:</strong> <%= res.getCantEquipajeExtra() %></p>
                                <p><strong>Fecha Reserva: </strong> <%= res.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %> </p>
                                <p><strong>Costo: </strong> <%= res.getCosto() %> </p>
                                <p><strong>Tipo Reserva: </strong> <%= res.getTipoReserva() %></p>
                            </div>
                            <div class="pasajeros">
                                <h3>Pasajeros</h3>
                                <% for(DTPasaje pasajero: res.getPasajes()){ %>
                                <div class="pasajeros"><%= pasajero.getNombre() + " " + pasajero.getApellido() %></div>
                                <% } %>
                            </div>
                            <div class="col">
                                <h3>Aerolinea</h3>
                                <div class="row" id="row1">
                                    <img src="data:image/*;base64, <%=aerolinea.getImagen() == null ? "" : Base64.getEncoder().encodeToString(aerolinea.getImagen()) %>" alt="Foto Aerolinea">
                                    <a href="usuarios?nickname=<%=aerolinea.getNickname() %>"><%= aerolinea.getNombre() %></a>
                                </div>
                                <h3>Ruta</h3>
                                <div class="row" id="row2">
                                    <img src="data:image/*;base64, <%=rutaVuelo.getImagen() == null ? "" : Base64.getEncoder().encodeToString(rutaVuelo.getImagen()) %>" alt="Foto Ruta">
                                    <a href="consultarRutaDeVuelo?ruta=<%=rutaVuelo.getNombre() %>"><%= rutaVuelo.getNombre() %></a>
                                </div>
                                <h3>Vuelo</h3>
                                <div class="row" id="row3">
                                    <img src="data:image/*;base64, <%=vuelo.getImagen() == null ? "" : Base64.getEncoder().encodeToString(vuelo.getImagen()) %>" alt="Foto Vuelo">
                                    <a href="consultaVuelo?vuelo=<%=vuelo.getNombre()%>&aerolinea=<%=aerolinea.getNickname()%>&ruta=<%=rutaVuelo.getNombre()%>"><%= vuelo.getNombre() %></a>
                                </div>
                                <h3>Cliente</h3>
                                <div class="row" id="row4">
                                    <img src="data:image/*;base64, <%=cliente.getImagen() == null ? "" : Base64.getEncoder().encodeToString(cliente.getImagen()) %>" alt="Foto Cliente">
                                    <a href="usuarios?nickname=<%=cliente.getNickname() %>"><%= cliente.getNickname() %></a>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>


