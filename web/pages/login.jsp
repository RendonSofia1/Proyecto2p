<%-- 
    Document   : login
    Created on : 16-oct-2024, 2:06:09
    Author     : rendo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String mensaje = (String) request.getAttribute("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Formulario Becario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            // Verifica si hay un mensaje y muestra la alerta
            <% if (mensaje != null) {%>
            alert("<%= mensaje%>");
            <% }%>
        </script>
    </head>
    <body>
        <div class="container mt-5" style="width: 50%">
            <h2>Iniciar Sesión</h2>
            <form method="post" action="${pageContext.request.contextPath}/login_servlet">

                <div class="mb-4">
                    <label for="curp" class="form-label">CURP</label>
                    <input type="text" class="form-control" id="curp" name="curp" maxlength="18"  pattern=".{18}" style="text-transform: uppercase;" required>
                    <div class="form-text">Debe contener exactamente 18 caracteres.</div>
                </div>


                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" maxlength="8" required>
                    <div class="form-text">Máximo 8 caracteres.</div>
                </div>

                <button type="submit" class="btn btn-primary">Ingresar</button>
            </form>
        </div>
    </body>
</html>
