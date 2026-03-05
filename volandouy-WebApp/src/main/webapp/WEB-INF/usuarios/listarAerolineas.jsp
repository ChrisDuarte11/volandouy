<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTAerolinea" %>
<%@page import="java.util.Set"%>
<!DOCTYPE html>
<html>

<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
    <title>Volando.uy</title>
    <link rel="stylesheet" href="resources/css/home.css">
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
                        Set<DTAerolinea> aerolineas = (Set<DTAerolinea>) request.getAttribute("aerolineas");
                        if (aerolineas != null && !aerolineas.isEmpty()) {
                            for (DTAerolinea aerolinea: aerolineas) {
                        %>
                        <div class="usuario mb-3 p-3 border rounded">
   							 <p><strong>Nickname:</strong> <%= aerolinea.getNickname() %></p>
   							 <p><strong>Nombre:</strong> <%= aerolinea.getNombre() %></p>
        						<p><strong>Tipo:</strong> Aerolínea</p>
   						     <p><a href="usuarios?nickname=<%= aerolinea.getNickname() %>" class="text-decoration-none">Más</a></p>
  						</div>
  						<% } %>
                        <%
                        } else {
                        %>
                        <p>No hay aerolineas.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
</body>

</html>