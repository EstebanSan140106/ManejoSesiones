package org.erick.ManejodeSesiones.services.services.repositorio;

import java.sql.SQLException;
import java.util.List;

/*
*<T> Es un parametro generico que permite que la interfaz sea utilizada
*como se desee o cualquier tipo de objeto(entidad) que se desea manejar */
public interface Repositorio <T>{

    List<T> listar() throws SQLException;

    T porId(Long id) throws SQLException;

    void guardar (T t) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
