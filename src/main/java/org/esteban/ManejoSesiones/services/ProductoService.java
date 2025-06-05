package org.esteban.ManejoSesiones.services;

import org.esteban.ManejoSesiones.models.Productos;

import java.util.List;

public interface ProductoService {
    // Método para obtener la lista de productos
    List<Productos>listar();
}