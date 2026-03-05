<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es-UY" data-bs-theme="light">

<!-- Head -->
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
	<link rel="stylesheet" href="resources/css/signup.css">
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
				
				<h2 class="text-center mb-4">Inicia sesión</h2>
			
				<!-- Mensaje de error: el usuario no existe / la contraseña es incorrecta -->
				<%
					String errorMessage = (String) request.getAttribute("errorMessage");
					boolean nicknameEmailInvalido = false;
					boolean passwordInvalida = false;
					if (errorMessage != null) {
						nicknameEmailInvalido = errorMessage.contains("nicknameEmail");
						passwordInvalida = errorMessage.contains("password");
					}
					boolean fillNicknameEmail = !nicknameEmailInvalido && request.getParameter("nicknameEmail") != null;
				%>
				
				
				<!-- Login form -->

				<form action="login" method="POST">
					<div class="form-item">
						<label for="nicknameEmail" class="form-label">Nickname o email</label>
						<input type="text" class="form-control<%=nicknameEmailInvalido ? " is-invalid" : ""%>" id="nicknameEmail" name="nicknameEmail" value="<%=fillNicknameEmail ? request.getParameter("nicknameEmail") :  ""%>" required>
						<div class="invalid-feedback">El nickname o correo ingresado no está registrado</div>
					</div>

					<div class="form-item">
						<label for="password" class="form-label">Contraseña</label>
						<input type="password" class="form-control<%=passwordInvalida ? " is-invalid" : ""%>" id="password" name="password" required>
						<div class="invalid-feedback">La contraseña ingresada es incorrecta</div>
					</div>
					
					<!-- Botón login -->
					<button type="submit" class="btn btn-primary w-100 mt-2">Iniciar sesión</button>
					   
					<!-- No tiene una cuenta -->
					<p class="text-center mt-4 mb-0">¿No tienes una cuenta? <a href="signup" class="text-decoration-none">Registrate</a></p>
				</form>
			 </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
    <script src="resources/js/signup.js"></script>
</body>
</html>
