package org.erick.ManejodeSesiones.services.services.services;

import com.google.protobuf.ServiceException;
import org.erick.ManejodeSesiones.services.services.models.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> Listar();
    Optional<Categoria> porId(Long id);

    void guardar(Categoria categoria);

    void activar(Long id);

    void desactivar(Long id);
    //implementar metodos guardar,activar y desactivar
}
