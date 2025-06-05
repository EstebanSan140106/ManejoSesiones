package org.erick.ManejodeSesiones.services.services.services;

import org.erick.ManejodeSesiones.services.services.models.Productos;
import java.util.List;

public interface ProductoService {
    // Método para obtener la lista de productos
    List<Productos>listar();
}