<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTUsuario" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="datatypes.DTReserva" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@page import="java.util.Set"%>
<%@page import= "logica.fabrica.IRutaVuelo"%>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/consultarUsuario.css">
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
                        <%
                        DTUsuario usuario = (DTUsuario) request.getAttribute("usuario");
                        %>

                        	<img class="user-img" style="width: 300px; height: auto;" src="data:image/*;base64, <%=usuario.getImagen() == null ? "" : Base64.getEncoder().encodeToString(usuario.getImagen())%>" alt="<%=usuario.getNickname()%>">

                        </div>
                        <div class="nombre">
                            <h1><strong><%= usuario.getNombre() %></strong></h1>
                            <h2><%= usuario.getCorreo() %></h2>
                            <br>
                        </div>
                        <div class="datos">
                            <div class="tab">
                                <button class="tablinks" onclick="openTab(event, 'Perfil')">Perfil</button>

                                <%-- Mostrar las pestañas de Reservas y Paquetes si es un cliente --%>
                                <%
                                    if (usuario instanceof DTCliente) {
                                %>
                                    <button class="tablinks" onclick="openTab(event, 'Reservas')">Reservas</button>
                                    <button class="tablinks" onclick="openTab(event, 'Paquetes')">Paquetes</button>
                                <% } %>

                                <%-- Mostrar las pestañas de Rutas si es una aerolínea --%>
                                <%
                                    if (usuario instanceof DTAerolinea) {
                                %>
                                    <button class="tablinks" onclick="openTab(event, 'Rutas')">Rutas de Vuelo</button>
                                <% } %>
                            </div>
                            <div class="tabs">
                                <div id="Perfil" class="tabcontent">
                                <%
                                    if (usuario instanceof DTCliente) {
										DTCliente u = (DTCliente) usuario;
                                %>
                                    <p class="gray"><strong>Nickname: </strong> <%= usuario.getNickname() %> </p>
                                    <p><strong>Nombre: </strong> <%= usuario.getNombre() %> </p>
                                    <p><strong>Apellido: </strong> <%= u.getApellido() %> </p>
                                    <p class="gray"><strong>Email: </strong> <%= usuario.getCorreo() %> </p>
                                    <p><strong>Fecha de Nacimiento: </strong> <%= u.getFechaNacimiento() %> </p>
                                    <p><strong>Tipo de Documento: </strong> <%= u.getTipoDoc() %> </p>
                                    <p><strong>Numero de Documento: </strong> <%= u.getNumDoc() %> </p>
                                    <p><strong>Nacionalidad: </strong> <%= u.getNacionalidad() %> </p>
                                    <%if ((Boolean) request.getAttribute("esPropioPerfil")) { %><a href="">Editar Perfil</a> <% } %>
                                </div>
								<% } %>

								<%
                                    if (usuario instanceof DTAerolinea) {
                                    	DTAerolinea u = (DTAerolinea) usuario;
                                %>
                                    <p><strong>Nickname: </strong> <%= usuario.getNickname() %> </p>
                                    <p><strong>Nombre: </strong> <%= usuario.getNombre() %> </p>
                                    <p><strong>Email: </strong> <%= usuario.getCorreo() %> </p>
                                    <p><strong>Descipcion: </strong> <%= u.getDescripcion() %> </p>
                                    <p><strong>Sitio web: </strong> <%= u.getWeb() %> </p>
                                    <%if ((Boolean) request.getAttribute("esPropioPerfil")) { %> <a href="">Editar Perfil</a> <% } %>
                            </div>
								<% } %>

                                <%-- Mostrar reservas si es cliente --%>
                                <%
                                    if (usuario instanceof DTCliente) {
                                        Set<DTReserva> reservas = (Set<DTReserva>) request.getAttribute("reservas");
                                %>
                                    <div id="Reservas" class="tabcontent">
                                        <% if (reservas != null && !reservas.isEmpty()) { %>
                                            <h3>Reservas de Vuelo</h3>
                                            <% for (DTReserva reserva : reservas) {
                                            	%>
                                                <div class="reserva">
                                                   <%-- no existe reserva.getAerolinea <p><strong>Aerolínea: </strong> <%= .getAerolinea() %></p> --%>
                                                    <%-- no existe reserva.getRutaVuelo  <p><strong>Ruta de Vuelo: </strong> <a href="consultarRuta?Vuelo=<%= .getRutaVuelo() %>"><%= .getRutaVuelo() %></a></p> --%>
                                                    <p><strong>Vuelo: </strong> <%= reserva.getVuelo() %></p>
                                                    <p><strong>Tipo de Reserva: </strong> <%= reserva.getTipoReserva() %></p>
                                                    <p><a href="consultarReserva?id=<%= reserva.getId() %>">Más información</a></p>
                                                </div>
                                            <% } %>
                                        <% } else { %>
                                            <p>No tiene reservas de vuelo.</p>
                                        <% } %>
                                    </div>
                                <% } %>

                                <%-- Mostrar paquetes si es cliente --%>
                                <%
                                    if (usuario instanceof DTCliente) {
                                %>
                                    <div id="Paquetes" class="tabcontent">
                                    	<p>No tiene paquetes.</p>
                                    </div>
                                <% } %>

                                <%-- Mostrar rutas de vuelo si es aerolínea --%>
                                <%
                                    if (usuario instanceof DTAerolinea) {
                                        Set<DTRutaVuelo> rutasConfirmadas = (Set<DTRutaVuelo>) request.getAttribute("rutasConfirmadas");
                                %>
                                   <div id="Rutas" class="tabcontent">
                                        <% if (rutasConfirmadas != null && !rutasConfirmadas.isEmpty()) { %>
                                            <h3>Rutas Vuelo</h3>
                                            <% for (DTRutaVuelo ruta : rutasConfirmadas) {
                                            	%>
                                                <div class="ruta">
                                                    <p><strong>Descripcion Corta: </strong> <a href="consultarRutaDeVuelo?ruta=<%= ruta.getNombre() %>"><%= ruta.getDescripcionCorta() %></a></p>
                                                    <p><strong>Descripcion: </strong> <%= ruta.getDescripcion() %></p>
                                                    <p><strong>Hora: </strong> <%= ruta.getHora() %></p>
                                                    <p><strong>Estado: </strong> <%= ruta.getEstado() %></p>
                                                </div>
                                            <% } %>
                                        <% } else if((Boolean) request.getAttribute("esPropioPerfil") && (Set<DTRutaVuelo>) request.getAttribute("rutasExtra") != null && !((Set<DTRutaVuelo>) request.getAttribute("rutasExtra")).isEmpty()) {%>
                                        <% } else {%>
                                        	<p>No tiene rutas de vuelo.</p>
                                        <% } %>

                                            <%-- Mostrar rutas ingresadas y rechazadas si es su propio perfil y aerolínea --%>
		                                <%
		                                if ((usuario instanceof DTAerolinea) && ((Boolean) request.getAttribute("esPropioPerfil"))) {
	                                        Set<DTRutaVuelo> rutasExtra = (Set<DTRutaVuelo>) request.getAttribute("rutasExtra");
	                                %>
	                                        <% if (rutasExtra != null && !rutasExtra.isEmpty()) {
	                                            for (DTRutaVuelo ruta : rutasExtra) { %>
	                                                <div class="ruta">
	                                                    <p><strong>Descripcion Corta: </strong> <a href="consultarRutaDeVuelo?ruta=<%= ruta.getNombre() %>"><%= ruta.getDescripcionCorta() %></a></p>
	                                                    <p><strong>Descripcion: </strong> <%= ruta.getDescripcion() %></p>
	                                                    <p><strong>Hora: </strong> <%= ruta.getHora() %></p>
	                                                    <p><strong>Estado: </strong> <%= ruta.getEstado() %></p>
	                                                </div>
	                                            <% }
	                                         }
	                                 } %>
                                     </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </main>
    <script src="resources/js/consultarUsuario.js"></script>
    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>