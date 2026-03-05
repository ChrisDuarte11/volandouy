<%@ page import="java.util.Base64" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/WEB-INF/error-pages/500.jsp" %>
<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html lang="es-UY" data-bs-theme="light">

<!-- Head -->
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
    <link rel="stylesheet" href="resources/css/common.css">
    <link rel="stylesheet" href="resources/css/altaVuelo.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="/WEB-INF/templates/header.jsp"/>

<main class="flex-grow-1">
	<div class="container py-4">
		<div class="row">
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
				
				<%
					String rutaVuelo = "";
					String cantidadTurista = "0";
					String cantidadEjecutivo = "0";
					String fechaVuelo = "";
					String duracionVueloHoras = "0";
					String duracionVueloMinutos = "0";
					if (errorMessage != null) {
						rutaVuelo = (String) request.getParameter("rutaVuelo");
						cantidadTurista = (String) request.getParameter("cantidadTurista");
						cantidadEjecutivo = (String) request.getParameter("cantidadEjecutivo");
						fechaVuelo = (String) request.getParameter("fechaVuelo");
						duracionVueloHoras = (String) request.getParameter("duracionVueloHoras");
						duracionVueloMinutos = (String) request.getParameter("duracionVueloMinutos");
					}
				%>

				<% DTRutaVuelo[] rutas = (DTRutaVuelo[]) request.getAttribute("rutas"); %>
				<form id="altaVueloForm" action="altaVuelo" method="POST" enctype="multipart/form-data">
					<%
						
					%>
				
					<label for="rutaVuelo" class="form-label">Ruta de vuelo:</label>
					<div class="form-item">
						<select class="form-select" id="rutaVuelo" name="rutaVuelo" required>
							<%
								for (DTRutaVuelo ruta : rutas) {
									%>
									<option value="<%=ruta.getNombre()%>" <%=(rutaVuelo != null && rutaVuelo.equals(ruta.getNombre())) ? " selected" : ""%>><%=ruta.getNombre()%></option>
									<%
								}
							%>
						</select>
						<div class="invalid-feedback">Selecciona una ruta</div>
					</div>

					<label for="nombreVuelo" class="form-label">Nombre:</label>
					<div class="form-item">
						<input type="text" class="form-control<%=nombreInvalido ? " is-invalid" : ""%>" id="nombreVuelo" name="nombreVuelo" required>
						<div class="invalid-feedback">Ya existe un vuelo con ese nombre</div>
						<!--<div class="invalid-feedback">El nombre ya está en uso</div>-->
					</div>

					<label for="cantidadTurista" class="form-label d-block">Cantidad de asientos tipo turista:</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-chair"></i></span> 
						<input type="number" min="0" class="form-control" id="cantidadTurista" name="cantidadTurista" value="<%=cantidadTurista%>" style="border-top-right-radius: 6px; border-bottom-right-radius: 6px;" required>
						<div class="invalid-feedback">Ambas cantidades no pueden ser 0</div>
					</div>

					<label for="cantidadEjecutivo" class="form-label">Cantidad de asientos tipo ejecutivo:</label>
					<div class="input-group">
						<span class="input-group-text"><i class="fas fa-chair"></i></span> 
						<input type="number" min="0" class="form-control" id="cantidadEjecutivo" name="cantidadEjecutivo" value="<%=cantidadEjecutivo%>" style="border-top-right-radius: 6px; border-bottom-right-radius: 6px;" required>
						<div class="invalid-feedback">Ambas cantidades no pueden ser 0</div>
					</div>

					<label for="fechaVuelo" class="form-label">Fecha de vuelo:</label>
					<div class="form-item">
						<input type="date" class="form-control" id="fechaVuelo" name="fechaVuelo" value="<%=fechaVuelo%>" required>
						<div class="invalid-feedback">Ingrese la fecha válida</div>
					 </div>

					<label for="duracion" class="form-label">Duración del vuelo:</label>
					<div class="form-group">
						<div class="d-flex gap-2">
							<div class="input-group">
								<input type="number" class="form-control" id="duracionVueloHoras" name="duracionVueloHoras" min="0" value="<%=duracionVueloHoras%>"required>
								<span class="input-group-text" style="border-top-right-radius: 6px; border-bottom-right-radius: 6px;">horas</span> 
								<div class="invalid-feedback">Ingrese una duración válida</div>
							</div>
							<div class="input-group">
								<input type="number" class="form-control" id="duracionVueloMinutos" name="duracionVueloMinutos" min="0" max="59" value="<%=duracionVueloMinutos%>" style="max-height: 38px;" required>
								<span class="input-group-text" style="max-height: 38px;">minutos</span> 
							</div>
						</div>
					</div>
					
					<div class="form-item">
                        <label for="imagenVuelo" class="form-label">Imagen del vuelo</label>
                        <input type="file" class="form-control" id="imagenVuelo" name="imagenVuelo" accept="image/*">
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

	<script src="resources/js/altaVuelo.js"></script>
    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>
</html>









