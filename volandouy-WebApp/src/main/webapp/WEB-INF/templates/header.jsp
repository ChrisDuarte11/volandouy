<%@ page import="com.volandouy.models.EstadoSesion" %>
<%@ page import="com.volandouy.models.TipoUsuario" %>
<%@ page import="datatypes.DTUsuario" %>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header class="bg-white border-bottom">
    <nav id="navbar" class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
        <div class="container">
            <a class="navbar-brand text-primary" href="home">Volando.uy</a>

            <!-- Offcanvas -->
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" aria-label="Toggle navigation"><!-- aria-expanded="false"-->
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Menú</h5>
                    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                <div class="offcanvas-body d-flex flex-lg-column">
                    <div class="d-flex flex-column flex-lg-row w-100">
                        <ul class="navbar-nav left-menu mb-2 mb-lg-0">
                        	<%
                        		EstadoSesion estadoSesion = (EstadoSesion) session.getAttribute("estado_sesion");
                        		if (estadoSesion == EstadoSesion.LOGIN_OK) {
                        			if (session.getAttribute("tipo_usuario") == TipoUsuario.AEROLINEA) {
                        				%>
										<li class="nav-item dropdown d-lg-none">
											<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMiPerfil" role="button" data-bs-toggle="dropdown" aria-expanded="false">
												Mi perfil
											</a>
											<ul class="dropdown-menu" aria-labelledby="navbarDropdownMiPerfil">
												<li><a class="dropdown-item" href="/ServidorWeb/altaRutaVuelo">Nueva ruta</a></li>
												<li><a class="dropdown-item" href="/ServidorWeb/altaVuelo">Nuevo vuelo</a></li>
											</ul>
										</li>
										<%
									} else {
										%>
										<li class="nav-item dropdown d-lg-none">
											<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMiPerfil" role="button" data-bs-toggle="dropdown" aria-expanded="false">
												Mi perfil
											</a>
											<ul class="dropdown-menu" aria-labelledby="navbarDropdownMiPerfil">
												<li><a class="dropdown-item" href="/ServidorWeb/altaReserva">Reservar vuelo</a></li>
												<li><a class="dropdown-item" href="#">Comprar paquete</a></li>
											</ul>
										</li>
										<%
									}
                        		}
                        	%>

                            <li class="nav-item">
                                <a class="nav-link" href="usuarios">Usuarios</a>
                            </li>

                            <li class="nav-item dropdown d-lg-none">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownCategorias" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Categorías
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdownCategorias">
                                	<%
										String[] categorias = (String[]) request.getSession().getAttribute("categorias");
										if (categorias != null) {
											for (String categoria : categorias) {
												%>
												<li><a class="dropdown-item" href="rutas?categoria=<%=categoria%>"> <%=categoria%> </a></li>
												<%
											}
										}
									%>
                                </ul>
                            </li>
                        </ul>
                        <div class="search-form-container my-2 my-lg-0 mt-auto">
                            <form class="d-flex search-form">
                                <input class="form-control me-2" type="search" placeholder="Origen, destino, aerolínea, paquete" aria-label="Search">
                                <button class="btn btn-outline-primary" type="submit">Buscar</button>
                            </form>
                        </div>

                        <%
                        	if (estadoSesion != EstadoSesion.LOGIN_OK) {
                        		%>
								<a class="btn btn-primary d-lg-none" href="signup">Registrarme</a>
								<%
							}
						%>
                    </div>
                </div>
            </div>

			<%
				if (estadoSesion == EstadoSesion.LOGIN_OK) {
					DTUsuario usuario = (DTUsuario) session.getAttribute("usuario_logueado");
					%>
					<div class="dropdown">
                        <button class="btn btn-link dropdown-toggle" type="button" id="profileDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="data:image/*;base64, <%=Base64.getEncoder().encodeToString(usuario.getImagen())%>" alt="<%=usuario.getNickname()%>" class="profile-picture">
                        </button>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                            <li class="profile-header">
                                <img src="data:image/*;base64, <%=Base64.getEncoder().encodeToString(usuario.getImagen())%>" alt="<%=usuario.getNickname()%>" class="profile-picture">
                                <span class="profile-name"><%=usuario.getNickname()%></span>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="usuarios?nickname=<%=usuario.getNickname()%>">Mi perfil</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="logout">Cerrar sesión</a></li>
                        </ul>
                    </div>
                    <%
				} else {
					%>
					<ul class="navbar-nav right-menu ms-auto">
						<li class="nav-item d-none d-lg-block">
							<a href="login" class="btn btn-outline-primary mx-2">Ingresar</a>
						</li>
						<li class="nav-item d-none d-lg-block">
							<a class="btn btn-primary" href="signup">Registrarme</a>
						</li>
						<li class="nav-item d-lg-none">
							<a href="login" class="btn btn-outline-primary btn-sm">Ingresar</a>
						</li>
					</ul>
					<%
				}
			%>
        </div>
    </nav>
</header>