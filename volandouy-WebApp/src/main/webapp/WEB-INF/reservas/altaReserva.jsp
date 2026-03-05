<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="logica.usuario.Cliente" %>
<%@ page import="logica.fabrica.Fabrica" %>
<%@ page import="logica.fabrica.IUsuario" %>
<%@ page import="logica.fabrica.IRutaVuelo" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page import="datatypes.DTVuelo" %>
<%@ page import="datatypes.DTUsuario" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page import="datatypes.DTCostos" %>
<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Realizar Reserva - Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/altaVuelo.css">
</head>

<body>
   <jsp:include page="/WEB-INF/templates/header.jsp"/>
	<% Set<DTAerolinea> aerolineas = (Set<DTAerolinea>) request.getAttribute("aerolineas"); %>
	<% Set<DTRutaVuelo> rutas = (Set<DTRutaVuelo>) request.getAttribute("rutas"); %>
	<% Set<DTVuelo> vuelos = (Set<DTVuelo>) request.getAttribute("vuelos"); %>
	<% DTCliente cliente = (DTCliente) request.getSession().getAttribute("usuario_logueado"); %>
	<% if (request.getAttribute("errorReservaExistente") != null) { %>
		<script>alert("Ocurrio un error: El usuario ya tiene una reserva para este vuelo")</script>
	<% } %>
	<% if (request.getAttribute("errorAsientosInvalidos") != null) { %>
		<script>alert("Ocurrio un error: La cantidad de asientos reservados no esta disponible")</script>
	<% } %>
	<% if (request.getAttribute("errorPasajeros") != null) { %>
		<script>alert("Ocurrio un error: La cantidad de pasajeros es distinta a la cantidad de asientos seleccionada")</script>
	<% } %>
	<% if (request.getAttribute("error") != null) { %>
		<script>alert("Ocurrio un error: <%=(String) request.getAttribute("error")%>")</script>
	<% } %>
    <main class="flex-grow-1">
        <div class="container py-4">
            <div class="row">

            <jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>

				<div class="col-12 col-lg-10">
					<!-- Mensaje success: el vuelo fue creado exitosamente -->
					<%
						String confirmationMessage = (String) request.getAttribute("confirmationMessage");
						if (confirmationMessage != null) {
							%>
							<div class="alert alert-success alert-dismissible fade show" role="alert">
								<%=confirmationMessage%>
								<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
							</div>
							<%
						}
					%>


                    <form id="altaReservaForm" action="altaReserva" method="POST">
                        <div class="form-item">
                            <label for="aerolineas">Seleccionar aerolinea:</label>
                            <select name="aerolineas" id="aerolineasSelect" class="form-control" onchange="actualizarRutas()">
                            	<%for(DTAerolinea aero: aerolineas) {%>
                                	<option value="<%= aero.getNickname() %>"><%= aero.getNombre() %></option>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-item">
                            <label for="rutas de vuelo">Seleccionar ruta de vuelo:</label>
                            <select name="rutas" id="rutasSelect" class="form-control" onchange="actualizarVuelos(); actualizarCosto()">
                            	<%
                            		for(DTRutaVuelo ruta: rutas) {
                            			DTCostos costos = ruta.getCostos();
										%>
										<option class="<%=ruta.getNickAerolinea() %>" costo-turista="<%=costos.getCostoBaseTurista()%>" costo-ejecutivo="<%=costos.getCostoBaseEjecutivo()%>" costo-equipaje="<%=costos.getCostoEquipajeExtra()%>" value="<%=ruta.getNombre() %>"><%=ruta.getNombre() %></option>
                                		<%
                                	}
                                %>
                            </select>
							<div class="invalid-feedback">Seleccione una ruta de vuelo válida</div>
                        </div>

                        <div class="form-item">
                            <label for="vuelos">Seleccionar vuelo:</label>
                            <select name="vuelos" id="vuelosSelect" class="form-control">
                            	<%
                            		for (DTVuelo vuelo : vuelos) {
										%>
                                		<option class="<%= vuelo.getNombreRuta() %>" value="<%=vuelo.getNombre() %>"><%=vuelo.getNombre() %></option>
                                		<%
                                	}
                                %>
                            </select>
							<div class="invalid-feedback">Seleccione un vuelo válido</div>
                        </div>

                        <div class="form-item">
                            <label for="opciones">Tipo de asiento:</label>
                            <select name="tipo" id="opciones" onchange="actualizarCosto()" class="form-control">
                                <option value="turista">Turista</option>
                                <option value="ejecutivo">Ejecutivo</option>
                            </select>
                        </div>

                        <div class="form-item">
                            <label for="pasajes">Cantidad de pasajes:</label>
                            <input id="pasajesInput" type="number" min="1" name="pasajesInput" class="form-control" onchange="actualizarCosto()" required value="1">
                        </div>
                        
                        <div class="form-item">
                            <label for="equipajeExtra">Cantidad de equipaje extra:</label>
                            <input id="equipajeExtra" type="number" min="0" name="equipaje" class="form-control" onchange="actualizarCosto()" required value="0">
                        </div>
                        
                        <div class="form-item">
                            <label for="Pasajeros">Pasajeros:</label>
                            <div class="input-group">
                                <input id="pasajerosInput" type="text" name="search" class="form-control">
                                <span style="margin-left:4px"><button type="button" onclick="agregarPasajero()" class="btn btn-primary"><i class="fa fa-search"></i>+</button></span>
                             </div>
                        </div>
                        
                        <input name="pasajeros" type="text" id="pasajerosAgregados" class="form-control" readonly style="margin-bottom:4px">
                        <button type="button" onclick="limpiar()" class="btn btn-primary"><i class="fa fa-search"></i>Limpiar</button>
                        <br>
                        <br>
                        <div class="form-item">
                            <label for="costo">Costo:</label>
                            <input id="costo" type="text" class="form-control" disabled>
                        </div>

                        <div class="d-flex justify-content-end gap-1">
							<!-- Botón cancelar -->
                            <button type="reset" class="btn btn-secondary">Cancelar</button>

							<!-- Botón aceptar -->
                            <button type="submit" class="btn btn-primary">Aceptar</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
    <script type="text/javascript" src="resources/js/altaReserva.js"></script>

</body>

</html>


