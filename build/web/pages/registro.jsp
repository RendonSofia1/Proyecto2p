<%-- 
    Document   : registro
    Created on : 16-oct-2024, 2:46:50
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
            <h2>Registro de Becario</h2>
            <form method="post" action="${pageContext.request.contextPath}/becario_servlet">

                <div class="mb-4">
                    <label for="curp" class="form-label">CURP</label>
                    <input type="text" class="form-control" id="curp" name="curp" maxlength="18"  pattern=".{18}"  required>
                    <div class="form-text">Debe contener exactamente 18 caracteres.</div>
                </div>

                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" maxlength="20" required>
                </div>

                <div class="mb-3">
                    <label for="apellido_pat" class="form-label">Apellido Paterno</label>
                    <input type="text" class="form-control" id="apellido_pat" name="apellido_pat" maxlength="20" required>
                </div>

                <div class="mb-3">
                    <label for="apellido_mat" class="form-label">Apellido Materno</label>
                    <input type="text" class="form-control" id="apellido_mat" name="apellido_mat" maxlength="20" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Género</label>
                    <div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="genero" id="masculino" value="M" required>
                            <label class="form-check-label" for="masculino">M</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="genero" id="femenino" value="F" required>
                            <label class="form-check-label" for="femenino">F</label>
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="password" name="password" maxlength="8" required>
                    <div class="form-text">Máximo 8 caracteres.</div>
                </div>

                <button type="submit" class="btn btn-primary">Registrar</button>
            </form>
        </div>
    </body>
</html>

