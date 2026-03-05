<%@ page import="java.util.Base64" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/WEB-INF/error-pages/500.jsp" %>
<!DOCTYPE html>
<html lang="es-UY" data-bs-theme="light">

<!-- Head -->
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
	<link rel="stylesheet" href="resources/css/home.css">
</head>

<body>
	<!-- Header -->
	<jsp:include page="/WEB-INF/templates/header.jsp"/>

	<main class="flex-grow-1">
		<div class="container py-4">
			<div class="row">
				<!-- Left-side menu -->
				<jsp:include page="/WEB-INF/templates/sidemenu.jsp"></jsp:include>

				<!-- Flight route list -->
				<div class="col-12 col-lg-10 flight-route-list">

					<%
						DTRutaVuelo[] rutas = (DTRutaVuelo[]) request.getAttribute("rutas");
						for (DTRutaVuelo ruta : rutas) { %>
							<div class="flight-route">
								<a href="consultarRutaDeVuelo?ruta=<%=ruta.getNombre()%>"><img class="flight-img" src="data:image/*;base64, <%=ruta.getImagen() == null ? "" : Base64.getEncoder().encodeToString(ruta.getImagen())%>" alt="<%=ruta.getNombre()%>"></a>
								<div class="flight-info">
									<div class="flight-title"><%=ruta.getCiudadOrigen().getNombre()%> - <%=ruta.getCiudadDestino().getNombre()%> (<%=ruta.getNombre()%>)</div>
									<a href="usuarios?nickname=<%=ruta.getNickAerolinea()%>" class="text-decoration-none"><div class="airline-name"><%=ruta.getNombreAerolinea()%></div></a>
									<div class="flight-description"><%=ruta.getDescripcion()%></div>
									<a href="consultarRutaDeVuelo?ruta=<%=ruta.getNombre()%>" class="btn btn-primary">Ver vuelos</a>
								</div>
							</div>

					<% }
					%>

				</div>

			</div>
		</div>
	</main>

	<!-- Footer -->
	<jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>
</html>