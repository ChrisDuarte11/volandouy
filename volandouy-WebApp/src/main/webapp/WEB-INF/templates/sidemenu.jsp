<%@ page import="com.volandouy.models.EstadoSesion" %>
<%@ page import="com.volandouy.models.TipoUsuario" %>
<%@ page import="java.util.Collection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="col-0 col-lg-2 d-none d-lg-block">
	<%
		if (session.getAttribute("estado_sesion") == EstadoSesion.LOGIN_OK) {
			if (session.getAttribute("tipo_usuario") == TipoUsuario.AEROLINEA) {
				%>
				<div class="left-side-menu">
					<h2>Mi perfil</h2>
					<ul>
						<li><a href="/volandouyWebApp/altaRutaVuelo">Nueva ruta</a></li>
						<li><a href="/volandouyWebApp/altaVuelo">Nuevo vuelo</a></li>
					</ul>
				</div>
				<%
			} else {
				%>
				<div class="left-side-menu">
					<h2>Mi perfil</h2>
					<ul>
						<li><a href="/volandouyWebApp/altaReserva">Reservar vuelo</a></li>
						<li><a href="#">Comprar paquete</a></li>
					</ul>
				</div>
				<%
			}
		}
	%>

	<div class="left-side-menu">
		<h2>Categorías</h2>
		<ul>
			<%
				String[] categorias = (String[]) request.getSession().getAttribute("categorias");
				if (categorias != null) {
					for (String categoria : categorias) { %>
						<li><a href="rutas?categoria=<%=categoria%>"> <%=categoria%> </a></li>
						<%
					}
				}
			%>
		</ul>
	</div>
</aside>