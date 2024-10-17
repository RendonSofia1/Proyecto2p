<%-- 
    Document   : información
    Created on : 16-oct-2024, 19:44:36
    Author     : rendo
--%>

<%@page import="Model.Becario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Model.Vivienda"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container" style="margin-top: 20px">
            <%
                Becario becario = (Becario) request.getAttribute("becario");
            %>
            <div class="table-responsive">
                <h2>Información del becario</h2>
                <p>Curp:  <%= becario.getCurp()%></p>
                <p>Nombre: <%= becario.getNombre()%> </p>
                <p>Apellido Paterno: <%= becario.getApellido_pat()%> </p>
                <p>Apellido Materno: <%= becario.getApellido_mat()%> </p>
                <p>Fecha de nacimiento: <%= becario.getFecha_nacimiento()%></p>
                <p>Viviendas Registradas: </p>
                <table class="table table-bordered table-striped table-hover" >
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Calle</th>
                            <th scope="col">Colonia</th>
                            <th scope="col">Municipio</th>
                            <th scope="col" >CP</th>
                        </tr>
                    </thead>
                    <tbody >
                        <%
                            ArrayList<Vivienda> listaCursos = (ArrayList<Vivienda>) request.getAttribute("casas");

                            if (listaCursos != null && !listaCursos.isEmpty()) {
                                for (Vivienda est : listaCursos) {
                        %>
                        <tr>
                            <th scope="row"><%= est.getId()%></th>
                            <td><%= est.getCalle()%></td>
                            <td><%= est.getColonia()%></td>
                            <td><%= est.getMunicipio()%></td>
                            <td><%= est.getCp()%></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5">No hay viviendas registradas.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>  
            <a class="btn btn-outline-primary"  href="${pageContext.request.contextPath}/becario_servlet">
                <i class="bi bi-arrow-left"></i>
            </a>
        </div>
    </body>
</html>