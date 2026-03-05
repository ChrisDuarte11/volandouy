<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="datatypes.DTCiudad" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page import="datatypes.DTVuelo" %>
<%@ page import="datatypes.DTUsuario" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="/WEB-INF/error-pages/500.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Crear ruta de vuelo - Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/altaVuelo.css">
</head>

<body>
<jsp:include page="/WEB-INF/templates/header.jsp"/>
<% DTCiudad[] ciudades = (DTCiudad[]) request.getAttribute("ciudades"); %>
<% String[] paises = (String[]) request.getAttribute("paises"); %>
<% String[] categorias = (String[]) request.getAttribute("categorias"); %>
	
<main class="flex-grow-1">
	<div class="container py-4">
		<div class="row">
			<!-- Left-side menu -->
			<jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>

			<div class="col-12 col-lg-10">
				<!-- Mensaje de error: el vuelo ya existe -->
				<%
					String errorMessage = (String) request.getAttribute("errorMessage");
					boolean nombreInvalido = false;
					if (errorMessage != null) {
						nombreInvalido = true;
						%>
						<div class="alert alert-danger alert-dismissible fade show" role="alert">
							<%=errorMessage%>
							<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
						</div>
						<%
					}
				%>
				
				<!-- Mensaje success: la ruta fue creada exitosamente -->
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

				<%
					String descripcion = "";
					String descripcionCorta = "";
					String horaRuta = "";
					String costoTurista = "0";
					String costoEjecutivo = "0";
					String costoEquipaje = "0";
					String paisOrigen = "";
					String ciudadOrigen = "";
					String paisDestino = "";
					String ciudadDestino = "";
					String[] categoriasRuta = new String[0];
					if (errorMessage != null) {
						descripcion = request.getParameter("descripcion");
						descripcionCorta = request.getParameter("descripcionCorta");
						horaRuta = request.getParameter("horaRuta");
						costoTurista = request.getParameter("costoTurista");
						costoEjecutivo = request.getParameter("costoEjecutivo");
						costoEquipaje = request.getParameter("costoEquipaje");
						paisOrigen = request.getParameter("paisOrigen");
						ciudadOrigen = request.getParameter("ciudadOrigen");
						paisDestino = request.getParameter("paisDestino");
						ciudadDestino = request.getParameter("ciudadDestino");
						categoriasRuta = (String[]) request.getParameterValues("categoriasRuta");
					}
				%>

				<!-- Form -->
				<form id="altaRutaForm" action="altaRutaVuelo" method="POST" enctype="multipart/form-data">
					<div class="form-item">
						<label for="nombre" class="form-label">Nombre:</label>
						<input type="text" id="nombre" name="nombre" class="form-control<%=nombreInvalido ? " is-invalid" : ""%>" required>
						<div class="invalid-feedback">Ya existe una ruta con ese nombre</div>
					</div>

					<div class="form-item">
						 <label for="descripcion" class="form-label">Descripción:</label>
						<textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%=descripcion%></textarea>
						<div class="invalid-feedback">Ingresa una descripción</div>
					</div>

					<div class="form-item">
						<label for="descripcion" class="form-label">Descripción corta:</label>
						<textarea class="form-control" id="descripcionCorta" name="descripcionCorta" rows="3" required><%=descripcionCorta%></textarea>
						<div class="invalid-feedback">Ingresa una descripción corta</div>
					</div>

					<div class="form-item">
						<label for="hora" class="form-label">Hora:</label>
						<input type="time" class="form-control" id="horaRuta" name="horaRuta" value="<%=horaRuta%>" required>
						<div class="invalid-feedback">Ingrese la duracion del vuelo</div>
					</div>

					<div class="form-item">
						<label for="costoturista" class="form-label">Costo turista:</label>
						<input type="number" id="costoTurista" name="costoTurista" min="0" class="form-control" value="<%=costoTurista%>" required>
						<div class="invalid-feedback">Ingrese el costo de tipo turista</div>
					</div>

					<div class="form-item">
						<label for="costoejecutivoa" class="form-label">Costo ejecutivo:</label>
						<input type="number" id="costoEjecutivo" name="costoEjecutivo" min="0" class="form-control" value="<%=costoEjecutivo%>" required>
						<div class="invalid-feedback">Ingrese el costo de tipo ejecutivo</div>
					</div>

					<div class="form-item">
						<label for="costoequipaje" class="form-label">Costo equipaje extra:</label>
						<input type="number" id="costoEquipaje" name="costoEquipaje" min="0" class="form-control" value="<%=costoEquipaje%>" required>
						<div class="invalid-feedback">Ingrese el costo de equipaje extra</div>
					</div>

					<div class="form-item">
						<label for="origen">País de origen:</label>
						<select id="paisOrigen" name="paisOrigen" class="form-control" onchange="actualizar(true)">
							<%
								for (String pais : paises) {
									%>
									<option value="<%=pais%>" <%=(paisOrigen != null && paisOrigen.equals(pais)) ? " selected" : ""%>><%=pais %></option>
									<%
								}
							%>
						</select>
					</div>

					<div class="form-item">
						<label for="origen">Ciudad de origen:</label>
						<select id="ciudadOrigen" name="ciudadOrigen" class="form-control">
							 <%
							 	for (DTCiudad ciudad : ciudades) {
							 		%>
									<option pais="<%=ciudad.getPais()%>" value="<%=ciudad.getNombre()%>" <%=(ciudadOrigen != null && ciudadOrigen.equals(ciudad.getNombre())) ? " selected" : ""%>><%=ciudad.getNombre()%></option>
						 			<%
						 		}
						 	%>
						</select>
						<div class="invalid-feedback">La ciudad de origen y de destino no pueden ser iguales</div>
					</div>

					<div class="form-item">
						<label for="destino">País de destino:</label>
						<select id="paisDestino" name="paisDestino" class="form-control" onchange="actualizar(false)">
							 <%
							 	for (String pais : paises) {
							 		%>
									<option value="<%= pais%>" <%=(paisDestino != null && paisDestino.equals(pais)) ? " selected" : ""%>><%= pais %></option>
						 			<%
						 		}
						 	%>
						</select>
					</div>

					<div class="form-item">
						<label for="destino">Ciudad de destino:</label>
						<select id="ciudadDestino" name="ciudadDestino" class="form-control">
							<%
								for (DTCiudad ciudad : ciudades) {
									%>
									<option pais="<%=ciudad.getPais()%>" value="<%=ciudad.getNombre()%>" <%=(ciudadDestino != null && ciudadDestino.equals(ciudad.getNombre())) ? " selected" : ""%>><%=ciudad.getNombre() %></option>
						 			<%
						 		}
						 	%>
						</select>
						<div class="invalid-feedback">La ciudad de origen y de destino no pueden ser iguales</div>
					</div>

					<div class="form-item">
						<label for="destino">Seleccionar categorías:</label>
						<select id="categoriasRuta" name="categoriasRuta" class="form-control" multiple>
							<%
								Set<String> categoriasSet = Arrays.stream(categoriasRuta).collect(Collectors.toSet());
								for (String cat : categorias) {
									%>
									<option value="<%=cat%>" <%=(categoriasSet.contains(cat)) ? " selected" : ""%>><%=cat%></option>
									<%
								}
							%>
						</select>
					</div>
					
					<div class="form-item">
                        <label for="imagenRuta" class="form-label">Imagen de la ruta de vuelo</label>
                        <input type="file" class="form-control" id="imagenRuta" name="imagenRuta" accept="image/*">
                    </div>

				 	<br>

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
<script type="text/javascript" src="resources/js/altaRutaVuelo.js"></script>
</body>
</html>