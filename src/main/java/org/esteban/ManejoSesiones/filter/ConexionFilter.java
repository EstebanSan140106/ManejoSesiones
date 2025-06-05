package org.erick.ManejodeSesiones.services.services.filtro;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.erick.ManejodeSesiones.services.services.services.ServiceJdbcException;
import org.erick.ManejodeSesiones.services.services.util.Conexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*")
public class ConexionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)throws IOException, ServletException {
       try(Connection conn = Conexion.getConnection()) {
           if(conn.getAutoCommit()) {
               conn.setAutoCommit(false);
           }
           try{
               req.setAttribute("conn", conn);
               filterChain.doFilter(req, resp);
               conn.commit();
           }catch(SQLException | ServiceJdbcException e){
               conn.rollback();
               ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
               e.printStackTrace();
           }
       }catch(SQLException throwables){
           throwables.printStackTrace();
       }
    }
}
