package org.erick.ManejodeSesiones.services.services.filtro;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.erick.ManejodeSesiones.services.services.services.LoginService;
import org.erick.ManejodeSesiones.services.services.services.LoginServiceSessionImplement;

import java.io.IOException;
import java.util.Optional;

@WebFilter({"/productos", "/agregar-carro"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)throws IOException, ServletException{
        LoginService service = new LoginServiceSessionImplement();
        Optional<String> username = service.getUserName((HttpServletRequest) req);
        //Realizo una condicional si esta presente el nombre del usuario
        if (username.isPresent()){
            filterChain.doFilter(req, resp);
        } else{
            ((HttpServletResponse)resp).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no estas autorizado para ingresar a esta pagina");
        }
    }
}
