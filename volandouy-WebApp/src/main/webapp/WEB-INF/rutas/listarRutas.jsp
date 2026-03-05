<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTRutaVuelo" %>
<%@page import="java.util.Set"%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Volando.uy</title>
</head>

<body>
    <jsp:include page="/WEB-INF/templates/header.jsp"/>
    <main class="flex-grow-1">
        <div class="container py-4">
            <div class="row">
                <aside class="col-md-3">
                    <div class="left-side-menu">
                        <jsp:include page="/WEB-INF/templates/sidemenu.jsp"/>
                    </div>
                </aside>

                <div class="col-md-9">
                    <div class="main">
                        <% 
                        Set<DTRutaVuelo> rutas = (Set<DTRutaVuelo>) request.getAttribute("rutas");
                        if (rutas != null && !rutas.isEmpty()) {
                            for (DTRutaVuelo ruta : rutas) {
                        %>
                        <div class="ruta mb-3 p-3 border rounded">
                        <p><strong>Nombre:</strong> <%= ruta.getNombre() %></p>
                            <p><strong>Origen:</strong> <%= ruta.getCiudadOrigen() %></p>
                            <p><strong>Destino:</strong> <%= ruta.getCiudadDestino() %></p>
                            <p><strong>Estado:</strong> Confirmada</p>
                            <p><a href="consultaVuelo?ruta=<%= ruta.getNombre() %>" class="text-decoration-none">Ver vuelos</a></p>
                        </div>
                        <% 
                            }
                        } else { 
                        %>
                        <p>No se encontraron rutas para esta aerolínea.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>