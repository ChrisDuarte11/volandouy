<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
	<meta charset="UTF-8">
	<title>Volando.uy - 404</title>
</head>

<body>
    <header class="bg-white border-bottom">
        <nav id="navbar" class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
            <div class="container justify-content-center">
                <a class="navbar-brand text-primary" href="home">Volando.uy</a>
            </div>
        </nav>
    </header>
    <main>
        <div class="container py-4">
            <div class="form-container">
				<div class="text-center mb-4">
                	<h2>404 - La pagina no existe.</h2>
                	<br>
                	<button type="reset" class="btn btn-outline-primary"><a href="home">Volver a home</a></button>
                </div>
            </div>
        </div>
    </main>
</body>
</html>