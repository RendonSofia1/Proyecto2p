/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Config.ConnectionBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author rendo
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login_servlet"})
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        // Eliminar la cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("curp".equals(cookie.getName())) {
                    cookie.setMaxAge(0); // Eliminar la cookie
                    cookie.setPath("/"); // Asegurar que se elimine en todo el contexto
                    response.addCookie(cookie); // Agregar la cookie eliminada a la respuesta
                    break;
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String curp = request.getParameter("curp");
        String password = request.getParameter("password");

        try {
            ConnectionBD conexion = new ConnectionBD();
            conn = conexion.getConnectionBD();
            String sql = "SELECT password FROM becario WHERE curp = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, curp);

            rs = ps.executeQuery();

            if (rs.next()) {
                String hashPassword = rs.getString("password");
                System.out.println("Password hash recuperado: " + hashPassword);
                if (BCrypt.checkpw(password, hashPassword)) {
                    //Crear una sesion
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(60 * 2); //2 min
                    session.setAttribute("curp", curp);
                    //Crear una cookie
                    Cookie cookie = new Cookie("curp", curp);
                    cookie.setMaxAge(60 * 15); // vida de 15 min
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    // Redirigir al servlet becario_servlet pasando el curp como parámetro
                    response.sendRedirect(request.getContextPath() + "/becario_servlet" );
                } else {
                    request.setAttribute("mensaje", "Credenciales incorrectas");
                    request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("mensaje", "Usuario no encontrado");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Error en post: " + e);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
