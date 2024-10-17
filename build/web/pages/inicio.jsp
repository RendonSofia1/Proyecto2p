<%-- 
    Document   : inicio
    Created on : 16-oct-2024, 3:45:47
    Author     : rendo
--%>

<%@page import="Model.Becario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //obtener la sesion actual
    HttpSession session_web = request.getSession(false);

    // Obtener la duración de la sesión en minutos (desde web.xml o configuración)
    int sessionTimeout = session_web.getMaxInactiveInterval(); // Tiempo en segundos
    int remainingTime = sessionTimeout; // Lo convertimos en segundos para usarlo en el temporizador

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            let remainingTime = <%= remainingTime%>; // Tiempo en segundos desde el servidor
            let timer; // Variable para el setInterval

            function startTimer() {
                const timerElement = document.getElementById('timer');
                timer = setInterval(function () {
                    let minutes = Math.floor(remainingTime / 60);
                    let seconds = remainingTime % 60;

                    // Formatear los números para que siempre tengan 2 dígitos
                    minutes = minutes < 10 ? '0' + minutes : minutes;
                    seconds = seconds < 10 ? '0' + seconds : seconds;

                    // Mostrar el tiempo restante en el elemento HTML
                    timerElement.textContent = minutes + ":" + seconds;

                    // Reducir el tiempo en 1 segundo
                    if (--remainingTime < 0) {
                        clearInterval(timer);
                        alert("Tu sesión ha expirado.");
                        window.location.href = '<%= request.getContextPath()%>/pages/login.jsp'; // Redirige al login
                    }
                }, 1000); // Actualiza cada segundo
            }

            function resetTimer() {
                remainingTime = <%= sessionTimeout%>; // Reiniciar el tiempo restante
                clearInterval(timer); // Limpiar el temporizador anterior
                timerActive = false; // Reiniciar el control de temporizador
                startTimer(); // Iniciar un nuevo temporizador
            }

            // Escuchar eventos de interacción del usuario
            document.addEventListener('click', resetTimer); // Para clics
            document.addEventListener('keypress', resetTimer); // Para pulsaciones de teclas
            document.addEventListener('mousemove', resetTimer); // Para movimiento del ratón

            window.onload = startTimer;
        </script>

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Proyecto</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <div class="d-flex align-items-center">
                        <span class="text-white me-3">Tiempo restante de la sesión: <span  id="timer"><%= remainingTime%></span></span>
                        <a href="${pageContext.request.contextPath}/login_servlet" class="btn btn-danger" >Cerrar Sesión</a>
                    </div>
                </div>
            </div>
        </nav>

        <%
            Becario becario = (Becario) request.getAttribute("becario");
        %>

        <div class="container mt-5">
            <div class="row">
                <div class="col text-center d-flex flex-column justify-content-center" >
                    <h1>¡Bienvenido, <%= becario.getNombre()%> <%= becario.getApellido_pat()%>  <%= becario.getApellido_mat()%>!</h1>
                    <p class="lead">Que gusto verte aquí.</p>
                    <p>Explora la aplicación y descubre todas las funcionalidades que tenemos para ti.</p>
                    <a class="btn btn-outline-primary mt-3"  href="/proyecto2p/pages/registro_vivienda.jsp" role="button">Registrar Vivienda</a>
                    <a class="btn btn-outline-success mt-3"  href="/proyecto2p/vivienda_servlet" role="button">Más información</a>
                </div>
                <div class="col d-flex align-items-center p-3">
                    <div class="p-3" style="border: solid 3px aquamarine; width: 100%; text-align: center; ">
                        <%
                            if (becario.getUrl_foto() != null) {
                        %>
                        <img src="<%= request.getContextPath() + "/" + becario.getUrl_foto() %>" alt="Foto del becario" width="500">
                        <%
                        } else {
                        %>
                        <h3>Sube tu foto!!</h3>
                        <form action="${pageContext.request.contextPath}/becario_servlet" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="_method" value="PUT" />
                            <div class="mb-3">
                                <label for="image" class="form-label"></label>
                                <input type="file" name="image" id="image" class="form-control" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Subir</button>
                        </form>
                        <%
                            }
                        %>
                    </div>
                </div>  
            </div>
        </div>

    </body>
</html>
