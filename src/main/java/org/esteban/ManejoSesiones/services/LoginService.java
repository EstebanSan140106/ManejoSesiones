package org.esteban.ManejoSesiones.services;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;


public interface LoginService {
        //devuelve un string
        Optional<String> getUserName(HttpServletRequest req);

}