/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Config.ConnectionBD;
import Model.Becario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rendo
 */
@WebServlet(name = "BecarioServlet", urlPatterns = {"/becario_servlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, //2MB
        maxFileSize = 1024 * 1024 * 10, //10MB
        maxRequestSize = 1024 * 1024 * 50)  //50MB
public class BecarioServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "images";

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
            out.println("<title>Servlet BecarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BecarioServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("CURP: " + curp);
        ConnectionBD conexion = new ConnectionBD();

        try {
            String sql = "SELECT * FROM becario WHERE curp = ?";
            conn = conexion.getConnectionBD();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, curp);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Becario user = new Becario();
                user.setCurp(rs.getString("curp"));
                user.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido_pat(rs.getString("apellido_pat"));
                user.setApellido_mat(rs.getString("apellido_mat"));
                user.setGenero(rs.getString("genero").charAt(0));
                user.setUrl_foto(rs.getString("url_foto"));

                System.out.println(user.toString());

                request.setAttribute("becario", user);

                request.getRequestDispatcher("/pages/inicio.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el becario con curp: " + curp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Detectar si se está simulando un PUT
        String method = request.getParameter("_method");
        if (method != null && method.equalsIgnoreCase("PUT")) {
            doPut(request, response);  // Llamar a doPut si el método es PUT
        } else {
            // Procesar como un POST regular

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            ConnectionBD conexion = new ConnectionBD();

            // Obtener los parámetros del formulario 
            String curp = request.getParameter("curp");
            String nombre = request.getParameter("nombre");
            String ap_pat = request.getParameter("apellido_pat");
            String ap_mat = request.getParameter("apellido_mat");
            String genero = request.getParameter("genero");
            String password = request.getParameter("password");

            // Extraer la fecha de nacimiento (posiciones 4 a 9)
            String fechaNacimiento = curp.substring(4, 10);
            // Formatear la fecha a YYYY-MM-DD
            String anio = "20" + fechaNacimiento.substring(0, 2); // Obtener el año (agregando 20 al inicio)
            String mes = fechaNacimiento.substring(2, 4); // Obtener el mes
            String dia = fechaNacimiento.substring(4, 6); // Obtener el día
            // Formar la fecha completa
            String fechan = anio + "-" + mes + "-" + dia;
            Date fecha_nacimiento = Date.valueOf(fechan);
            //Hashear la contraseña usando bycript
            String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            try {
                // Crear la consulta SQL para insertar el usuario 
                String sql = "INSERT INTO becario (curp, fecha_nacimiento, nombre, apellido_pat, apellido_mat, genero, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                conn = conexion.getConnectionBD();
                ps = conn.prepareStatement(sql);
                ps.setString(1, curp);
                ps.setDate(2, fecha_nacimiento);
                ps.setString(3, nombre);
                ps.setString(4, ap_pat);
                ps.setString(5, ap_mat);
                ps.setString(6, genero);
                ps.setString(7, hashPassword);

                // Ejecutar la consulta 
                int filasInsertadas = ps.executeUpdate();
                if (filasInsertadas > 0) {
                    // Si se insertó correctamente, mandar a login
                    request.setAttribute("mensaje", "Usuario registrado exitosamente.");
                    request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                } else {
                    // Si falló, redirigir a una página de error 
                    request.setAttribute("mensaje", "Error al registrar usuario.");
                    request.getRequestDispatcher("/pages/registro.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("mensaje", "Ocurrió un error: " + e.getMessage());
                request.getRequestDispatcher("/pages/registro.jsp").forward(request, response);
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

    }
    // </editor-fold>

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Ejecutando doPut");

        // Obtener la ruta absoluta de la carpeta images
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        //Crear la carpeta images si no existe
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        //Obtener la imagen subida
        Part part = request.getPart("image");   
        String fileName = getFileName(part);

        //Guardar la imagen en el servidor (en la carpeta "images")
        String filePath = uploadFilePath + File.separator + fileName;
        part.write(filePath);

        // Obtener la ruta relativa para almacenar en la base de datos
        String relativePath = UPLOAD_DIR + File.separator + fileName;

        // Actualizar la ruta de la imagen en la base de datos
        HttpSession session = request.getSession(false);
        String curp = (String) session.getAttribute("curp");
        updateBecarioImagePath(curp, relativePath);

         //Redirigir a la página de bienvenida después de subir la imagen
        response.sendRedirect(request.getContextPath() + "/becario_servlet");
    }

    //  Método para obtener el nombre del archivo obtenido
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }

    private void updateBecarioImagePath(String curp, String imagePath) {
        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();
            String sql = "UPDATE becario SET url_foto = ? WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, imagePath);
            ps.setString(2, curp);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Ruta de imagen actualizada para el becario con curp: " + curp);
            } else {
                System.out.println("No se pudo actualizar la ruta de la imagen.");
            }

            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
