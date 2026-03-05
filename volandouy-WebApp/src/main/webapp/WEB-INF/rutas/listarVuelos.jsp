<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTVuelo" %>
<%@page import="java.util.Set"%>
<%@page import="java.time.LocalTime"%>

<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Volando.uys</title>
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
                        Set<DTVuelo> vuelos = (Set<DTVuelo>) request.getAttribute("vuelos");
                        LocalTime hora = (LocalTime) request.getAttribute("horaRuta");
                        if (vuelos != null && !vuelos.isEmpty()) {
                            for (DTVuelo vuelo : vuelos) {
                        %>
                        <div class="vuelo mb-3 p-3 border rounded">
                            <p><strong>Nombre:</strong> <%= vuelo.getNombre() %></p>
                            <p><strong>Fecha:</strong> <%= vuelo.getFecha() %></p>
                            <p><strong>Hora:</strong> <%= hora %></p>
                            <p><a href="consultaVuelo?vuelo=<%= vuelo.getNombre() %>" class="text-decoration-none">Ver detalles</a></p>
                        </div>
                        <% 
                            }
                        } else { 
                        %>
                        <p>No se encontraron vuelos para esta ruta.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>