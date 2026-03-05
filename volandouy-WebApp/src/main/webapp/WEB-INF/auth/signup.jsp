<%@ page import="logica.enums.Pais" %>
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

                <h2 class="text-center mb-4">Crea tu cuenta</h2>       
                
                <!-- Mensaje de error: el usuario ya existe -->
                <%
                	String errorMessage = (String) request.getAttribute("errorMessage");
					boolean nicknameInvalido = false;
					boolean correoInvalido = false;
                	if (errorMessage != null) {
                		nicknameInvalido = errorMessage.contains("nickname");
                		correoInvalido = errorMessage.contains("correo");
                		%>
                		<div class="alert alert-danger alert-dismissible fade show" role="alert">
							<%=request.getAttribute("errorMessage")%>
							<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
						</div>
						<%
					}
                %>

                <!-- Sign-up form -->
                <form id="signupForm" action="signup" method="POST" enctype="multipart/form-data">
                	<%
                		String tipoUsuario = (String) request.getParameter("tipoUsuario");
                		String aerolineaSelected = "";
                		String clienteSelected = "";
                		if (tipoUsuario != null) {
                			if (tipoUsuario.equals("aerolinea")) {
                				aerolineaSelected = " selected";
                			} else {
                				clienteSelected = " selected";
                			}
                		}
                	%>
                    <div class="form-item">
                        <label for="tipoUsuario" class="form-label">Tipo de usuario</label>
                        <select class="form-select" id="tipoUsuario" name="tipoUsuario" required>
                            <option value="aerolinea" <%=aerolineaSelected%>>Aerolínea</option>
                            <option value="cliente" <%=clienteSelected%>>Cliente</option>
                        </select>
                        <div class="invalid-feedback">Selecciona un tipo de usuario</div>
                    </div>

                    <div class="form-item">
                        <label for="nickname" class="form-label">Nickname</label>
                        <input type="text" class="form-control<%=nicknameInvalido ? " is-invalid" : ""%>" id="nickname" name="nickname" value="<%=request.getParameter("nickname") != null ? request.getParameter("nickname") : ""%>" required>
                        <div class="invalid-feedback">El nickname ya está en uso</div>
                    </div>                       

                    <div class="form-item">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control<%=correoInvalido ? " is-invalid" : ""%>" id="email" name="email" value="<%=request.getParameter("email") != null ? request.getParameter("email") : ""%>" required>
                        <div class="invalid-feedback">El email ya está en uso</div>
                    </div>

                    <div class="form-item">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" value="<%=request.getParameter("nombre") != null ? request.getParameter("nombre") : ""%>" required>
                        <div class="invalid-feedback">Ingresa un nombre</div>
                    </div>

                    <div class="form-item">
                        <label for="password" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" value="<%=request.getParameter("password") != null ? request.getParameter("password") : ""%>" required>
                        <div class="form-text">Tu contraseña debe tener entre 8 y 20 caracteres, contener letras y números, y no debe contener espacios, caracteres especiales ni emoji.</div>
                        <div class="invalid-feedback">Ingresa una contraseña</div>
                    </div>

                    <div class="form-item">
                        <label for="passwordConfirm" class="form-label">Repetir contraseña</label>
                        <input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" value="<%=request.getParameter("passwordConfirm") != null ? request.getParameter("passwordConfirm") : ""%>" required>
                        <div class="invalid-feedback">Las contraseñas no coinciden</div>
                    </div>

                    <div id="clienteFields">
                    	<div class="form-item">
                    		<label for="apellido" class="form-label">Apellido</label>
                    		<input type="text" class="form-control" id="apellido" name="apellido" value="<%=request.getParameter("apellido") != null ? request.getParameter("apellido") : ""%>" required>
                    	</div>
                    	
                    
                        <div class="form-item">
                            <label for="fechaNacimiento" class="form-label">Fecha de nacimiento</label>
                            <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" value="<%=request.getParameter("fechaNacimiento") != null ? request.getParameter("fechaNacimiento") : ""%>" required>
                            <div class="invalid-feedback">Ingresa una fecha de nacimiento</div>
                        </div>

						<%
							String tipoDocumento = (String) request.getParameter("tipoDocumento");
							String ciSelected = "";
							String dniSelected = "";
							String pasaporteSelected = "";
							if (tipoDocumento != null) {
								if (tipoDocumento.equals("ci")) {
									ciSelected = " selected";
								} else if (tipoDocumento.equals("dni")) {
									dniSelected = " selected";
								} else {
									pasaporteSelected = " selected";
								}
							}
						%>
                        <div class="form-item">
                            <label for="tipoDocumento" class="form-label">Tipo de documento</label>
                            <select class="form-select" id="tipoDocumento" name="tipoDocumento" required>
                                <option value="ci" <%=ciSelected%>>C.I.</option>
                                <option value="dni" <%=dniSelected%>>D.N.I.</option>
                                <option value="pasaporte" <%=pasaporteSelected%>>Pasaporte</option>
                            </select>
                            <div class="invalid-feedback">Selecciona un tipo de documento</div>
                        </div>

                        <div class="form-item">
                            <label for="nroDocumento" class="form-label">Número de documento</label>
                            <input type="text" class="form-control" id="nroDocumento" name="nroDocumento" value="<%=request.getParameter("nroDocumento") != null ? request.getParameter("nroDocumento") : ""%>" required>
                            <div class="invalid-feedback">Ingresa un número de documento</div>
                        </div>

                        <div class="form-item">
                            <label for="nacionalidad" class="form-label">Nacionalidad</label>
                            <select class="form-select" id="nacionalidad" name="nacionalidad" required>
                            	<%
                            		String nacionalidad = (String) request.getParameter("nacionalidad");
                            	
                            		Pais[] paises = (Pais[]) request.getAttribute("paises");
                            		for (Pais pais : paises) {
                            			%>
                            			<option value="<%=pais.getCodigo()%>" <%=(nacionalidad != null && nacionalidad.equals(pais.getCodigo())) ? " selected" : ""%>><%=pais.getPais()%></option>
                            			<%
                            		}
                            	%>
                            </select>
                            <div class="invalid-feedback">Selecciona una nacionalidad</div>
                        </div>
                    </div>

                    <div id="aerolineaFields">
                        <div class="form-item">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%=request.getParameter("descripcion") != null ? request.getParameter("descripcion") : ""%></textarea>
                            <div class="invalid-feedback">Ingresa una descripción</div>
                        </div>

                        <div class="form-item">
                            <label for="paginaWeb" class="form-label">Página web</label>
                            <input type="url" class="form-control" id="paginaWeb" name="paginaWeb" value="<%=request.getParameter("paginaWeb") != null ? request.getParameter("paginaWeb") : ""%>" required>
                            <div class="invalid-feedback">Ingresa una página web válida</div>
                        </div>
                    </div>

                    <div class="form-item">
                        <label for="imagenPerfil" class="form-label">Imagen de perfil</label>
                        <input type="file" class="form-control" id="imagenPerfil" name="imagenPerfil" accept="image/*">
                    </div>

                    <!-- Botón registrarse -->
                    <button type="submit" class="btn btn-primary w-100 mt-2">Registrarme</button>

                    <!-- Ya tiene una cuenta -->
                    <p class="text-center mt-4 mb-0">¿Ya tienes una cuenta? <a href="login" class="text-decoration-none">Inicia sesión</a></p>
                </form>
            </div>
        </div>
    </main>

    <jsp:include page="/WEB-INF/templates/footer.jsp"/>
    <script src="resources/js/signup.js"></script>
</body>
</html>
