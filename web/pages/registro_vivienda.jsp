<%-- 
    Document   : registro_vivienda
    Created on : 16-oct-2024, 19:08:14
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
            <h2>Registro de Vivienda</h2>
            <form method="post" action="${pageContext.request.contextPath}/vivienda_servlet">

                <div class="mb-3">
                    <label for="calle" class="form-label">Calle</label>
                    <input type="text" class="form-control" id="calle" name="calle" maxlength="50" required>
                </div>

                <div class="mb-3">
                    <label for="colonia" class="form-label">Colonia</label>
                    <input type="text" class="form-control" id="colonia" name="colonia" maxlength="50" required>
                </div>

                <div class="mb-3">
                    <label for="municipio" class="form-label">Municipio</label>
                    <input type="text" class="form-control" id="municipio" name="municipio" maxlength="50" required>
                </div>

                <div class="mb-3">
                    <label for="cp" class="form-label">Código Postal</label>
                    <input type="number" class="form-control" id="cp" name="cp" maxlength="5" pattern=".{5}"  required>
                    <div class="form-text">Debe contener exactamente 5 caracteres.</div>
                </div>

                <div class="mb-3 d-flex"  style="justify-content: space-between" >
                    <button type="submit" class="btn btn-primary">Registrar</button>
                    <a  class="btn btn-outline-dark"  href="/proyecto2p/becario_servlet" role="button">Volver    </a>
                </div>
            </form>
        </div>
    </body>
</html>

