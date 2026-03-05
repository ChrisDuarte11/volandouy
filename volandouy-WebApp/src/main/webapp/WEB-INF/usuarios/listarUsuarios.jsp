<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Set" %>
<%@ page import="datatypes.DTAerolinea" %>
<%@ page import="datatypes.DTCliente" %>
<%@ page import="datatypes.DTUsuario" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Volando.uy</title>
    <link rel="stylesheet" href="resources/css/common.css">
    <link rel="stylesheet" href="resources/css/consultas.css">
</head>

<body>
	<jsp:include page="/WEB-INF/templates/header.jsp"/>
	<main class="flex-grow-1">
		<div class="container py-4">
			<div class="row">
				<!--  Left-side menu -->
				<jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>

				<!-- Users list -->
				<div class="col-12 col-lg-10 users-list">
					<%
						DTUsuario[] usuarios = (DTUsuario[]) request.getAttribute("usuarios");
						if (usuarios != null && usuarios.length != 0) {
							for (DTUsuario usuario : usuarios) {
								boolean esAerolinea = usuario instanceof DTAerolinea;
								%>
									<div class="user">
										<a href="usuarios?nickname=<%= usuario.getNickname() %>" class="image-link">
											<img class="user-img" src="data:image/*;base64, <%=usuario.getImagen() == null ? "resources/img/default-profile-picture.png" : Base64.getEncoder().encodeToString(usuario.getImagen())%>" alt="<%=usuario.getNickname()%>">
										</a>
										
										<div class="user-info">
											<div class="w-100">
												<div class="user-name"><%=usuario.getNombre()%> · <%=esAerolinea ? "Aerolínea" : "Cliente"%></div>

												<div class="user-nickname">
													<a href="usuarios?nickname=<%=usuario.getNickname()%>" class="text-decoration-none text-muted">@<%=usuario.getNickname()%></a>
												</div>

												<div class="user-description w-100"><%=esAerolinea ? ((DTAerolinea) usuario).getDescripcion() : ((DTCliente) usuario).getNacionalidad().getPais()%></div>
											</div>

											<%
												if (esAerolinea) {
													%>
													<div class="user-web"><p class="mb-0"><a href="<%=((DTAerolinea) usuario).getWeb()%>" class="text-decoration-none mt-auto"><%=((DTAerolinea) usuario).getWeb()%></a></p></div>
													<%
												}
											%>
										</div>
									</div>
								<%
							}
						} else {
							%>
							<p>No hay usuarios.</p>
							<%
						}
					%>
				</div>
			</div>
		</div>
	</main>

	<jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>