package org.erick.ManejodeSesiones.services.services.controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.erick.ManejodeSesiones.services.services.services.LoginService;
import org.erick.ManejodeSesiones.services.services.services.LoginServiceSessionImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/logout")
public class Logout  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //creamos el objeto de tipo session
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String>userNameOptional = auth.getUserName(req);
        if(userNameOptional.isPresent()){
            HttpSession session = req.getSession();
            //cerramnos ls session
            session.invalidate();
        }
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("</head>");
        }
        resp.sendRedirect(req.getContextPath()+ "/login.html");
    }
}
