package org.esteban.ManejoSesiones.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;


public class LoginServiceSessionImplement implements LoginService {
    @Override
    public Optional<String> getUserName(HttpServletRequest request) {
        //obtenemos sesion
        HttpSession session = request.getSession();
        //convierte los datos de la sesion en un string
        String username = (String)session.getAttribute("username");
        /*creo una condicion en la cual valido si al obtener el nombre del usuario es distinto de nulo
        - obtengo el username
        - caso contrario decuelvo la sesion vacia */
        if(username!=null){
            return Optional.of(username);
        }
        return Optional.empty();
    }

}