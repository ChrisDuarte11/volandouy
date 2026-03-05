<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Set" %>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@ page import="datatypes.DTVuelo" %>
<%@ page import="logica.vuelo.categoria.Categoria" %>
<%@ page import="logica.fabrica.IRutaVuelo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Consulta de Ruta de Vuelo - Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
    <link rel="stylesheet" href="resources/css/consultarRutaDeVuelo.css">
</head>

<body>
   <jsp:include page="/WEB-INF/templates/header.jsp"/>
   <% DTRutaVuelo ruta = (DTRutaVuelo) request.getAttribute("rutaDeVuelo"); %>
   <% Set<DTVuelo> vuelos = (Set<DTVuelo>) request.getAttribute("vuelos"); %>
    <main class="flex-grow-1">
        <div class="container py-4">
            <div class="row">
                <jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>

				<div class="col-md-9">
            		<div class="main">
                		<div class="foto">
                			<img src="data:image/*;base64, <%=ruta.getImagen() == null ? "" : Base64.getEncoder().encodeToString(ruta.getImagen())%>" alt="Foto Ruta">
                		</div>
                		<div class="nombre">
                    		<h1><strong><%= ruta.getNombre() %></strong></h1>
                    		<h4><%= ruta.getDescripcionCorta() %></h4>
                		</div>
                		<div class="datos">
                    		<p class="gray"><strong>Descripcion: </strong><%= ruta.getDescripcion() %></p>
                    		<p><strong>Ciudad origen: </strong><%= ruta.getCiudadOrigen().getNombre() %></p>
                    		<p class="gray"><strong>Ciudad destino: </strong><%= ruta.getCiudadDestino().getNombre() %></p>
                    		<p><strong>Hora: </strong><%= ruta.getHora() %></p>
                    		<p><strong>Costo turista (USD): </strong><%= ruta.getCostos().getCostoBaseTurista() %></p>
                    		<p><strong>Costo ejecutivo (USD): </strong><%= ruta.getCostos().getCostoBaseEjecutivo() %></p>
                    		<p><strong>Costo equipaje extra(USD): </strong><%= ruta.getCostos().getCostoEquipajeExtra() %></p>
                    		<p><strong>Fecha de alta: </strong><%= ruta.getFechaAlta() %></p>
                    		<p><strong>Estado: </strong><%= ruta.getEstado() %></p><!--falta-->
                    		<br>
                    		<p>
                    			<strong>Categorias: </strong>
                    			<%
                    				Set<Categoria> categorias = (Set<Categoria>) ruta.getCategorias();
                    				for (Categoria cat : categorias) {
                    					%>
										<div><%= cat.getNombre() %>.</div>
                               			<%
                               		}
                               	%>
                            </p>
                		</div>
                		<div class="col">
                		    <div class="vuelos">
                        		<h2>Vuelos</h2>
                        			<% for(DTVuelo vuelo: vuelos){ %>
                        			<div>
                                		<a href="consultaVuelo?vuelo=<%= vuelo.getNombre() %>&ruta=<%= ruta.getNombre()%>&aerolinea=<%= ruta.getNickAerolinea() %>"><%= vuelo.toString() %></a>
                                	</div>
                                	<% } %>
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


