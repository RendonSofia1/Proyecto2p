/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Config.ConnectionBD;
import Model.Becario;
import Model.Vivienda;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rendo
 */
@WebServlet(name = "ViviendaServlet", urlPatterns = {"/vivienda_servlet"})
public class ViviendaServlet extends HttpServlet {

    Connection conn;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViviendaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViviendaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String curp = null;
        // Obtener la sesión existente (sin crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Leer el curp almacenado en la sesión
            curp = (String) session.getAttribute("curp");
        }

        ConnectionBD conexion = new ConnectionBD();
        List<Vivienda> listaCasas = new ArrayList<>();
        Becario user = new Becario();

        try {
            // Primera consulta: Obtener los datos de la vivienda
            String sqlVivienda = "SELECT * FROM vivienda WHERE curp = ?";
            conn = conexion.getConnectionBD();
            PreparedStatement psVivienda = conn.prepareStatement(sqlVivienda);
            psVivienda.setString(1, curp);
            ResultSet rsVivienda = psVivienda.executeQuery();

            if (rsVivienda.next()) {
                Vivienda vivienda = new Vivienda();
                vivienda.setId(Integer.parseInt(rsVivienda.getString("id")));
                vivienda.setCalle(rsVivienda.getString("calle"));
                vivienda.setColonia(rsVivienda.getString("colonia"));
                vivienda.setMunicipio(rsVivienda.getString("municipio"));
                vivienda.setCp(rsVivienda.getString("cp"));

                System.out.println(vivienda.toString());
                listaCasas.add(vivienda);
            }

            // Segunda consulta: Obtener los datos del becario
            String sqlBecario = "SELECT * FROM becario WHERE curp = ?";
            PreparedStatement psBecario = conn.prepareStatement(sqlBecario);
            psBecario.setString(1, curp);
            ResultSet rsBecario = psBecario.executeQuery();

            if (rsBecario.next()) {
                user.setCurp(rsBecario.getString("curp"));
                user.setFecha_nacimiento(rsBecario.getDate("fecha_nacimiento"));
                user.setNombre(rsBecario.getString("nombre"));
                user.setApellido_pat(rsBecario.getString("apellido_pat"));
                user.setApellido_mat(rsBecario.getString("apellido_mat"));
                user.setGenero(rsBecario.getString("genero").charAt(0));
                user.setUrl_foto(rsBecario.getString("url_foto"));

                System.out.println(user.toString());
            }

            // Si se encontraron los datos de vivienda y becario, enviarlos a la JSP
            if (!listaCasas.isEmpty() && user != null) {
                request.setAttribute("casas", listaCasas);
                request.setAttribute("becario", user);
                request.getRequestDispatcher("/pages/informacion.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron datos con la curp: " + curp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        ConnectionBD conexion = new ConnectionBD();

        // Obtener los parámetros del formulario 
        String calle = request.getParameter("calle");
        String colonia = request.getParameter("colonia");
        String municipio = request.getParameter("municipio");
        String cp = request.getParameter("cp");

        //obetener curp de httpSession
        HttpSession session = request.getSession(false);
        String curp = (String) session.getAttribute("curp");

        try {
            // Crear la consulta SQL para insertar el usuario 
            String sql = "INSERT INTO vivienda (calle, colonia, municipio, cp, curp) VALUES (?, ?, ?, ?, ?)";
            conn = conexion.getConnectionBD();
            ps = conn.prepareStatement(sql);
            ps.setString(1, calle);
            ps.setString(2, colonia);
            ps.setString(3, municipio);
            ps.setString(4, cp);
            ps.setString(5, curp);

            // Ejecutar la consulta 
            int filasInsertadas = ps.executeUpdate();
            if (filasInsertadas > 0) {
                // Si se insertó correctamente, mandar a inicio
                request.setAttribute("mensaje", "Vivienda registrada exitosamente.");
                request.getRequestDispatcher("/pages/registro_vivienda.jsp").forward(request, response);
            } else {
                // Si falló, redirigir a una página de error 
                request.setAttribute("mensaje", "Error al registrar vivienda.");
                request.getRequestDispatcher("/pages/registro_vivienda.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("/pages/registro_vivienda.jsp").forward(request, response);
        } finally {
            // Cerrar los recursos 
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
