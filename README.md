# volandouy

##  Descripción
VolandoUY es una aplicación web desarrollada en Java EE (Jakarta EE) que simula un sistema de gestión de aerolíneas y reservas de vuelos.

El sistema permite administrar usuarios, rutas de vuelo, vuelos y reservas mediante una arquitectura en capas que separa la lógica de negocio de la capa de presentación web.

##  Funcionalidades

### Gestión de usuarios
- Registro de nuevos usuarios
- Inicio y cierre de sesión
- Consulta de perfiles de usuario

### Gestión de rutas de vuelo
- Alta de nuevas rutas de vuelo
- Consulta de rutas disponibles

### Gestión de vuelos
- Creación de vuelos asociados a las rutas de vuelo
- Consulta de información de vuelos

### Gestión de reservas
- Alta de reservas de vuelos
- Consulta de reservas realizadas

## Cómo ejecutar el proyecto

1. Clonar el repositorio:
    git clone https://github.com/ChrisDuarte11/volandouy.git

2. Importar el proyecto en Eclipse como Existing Projects into Workspace
   
3. Vincular volandouy-Logic en Deployment Assembly de volandouy-WebApp 

4. Configurar Apache Tomcat 10.1 como servidor

5. Ejecutar el proyecto en el servidor

6. Abrir en el navegador:
    http://localhost:8080/volandouyWebApp/home


