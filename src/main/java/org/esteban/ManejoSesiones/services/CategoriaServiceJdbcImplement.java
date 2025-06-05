package org.erick.ManejodeSesiones.services.services.services;

import com.google.protobuf.ServiceException;
import org.erick.ManejodeSesiones.services.services.models.Categoria;
import org.erick.ManejodeSesiones.services.services.repositorio.CategoriaRepositorioJdbcImplement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CategoriaServiceJdbcImplement implements CategoriaService {

    //creamos una varbiable de tipo CategoriaRepositoryImplement
    //creamos una variable de tipo Connection
    private CategoriaRepositorioJdbcImplement repositorioJdbc;

    public CategoriaServiceJdbcImplement(Connection conn) {
        this.repositorioJdbc = new CategoriaRepositorioJdbcImplement(conn);
    }

    @Override
    public List<Categoria> Listar() {
        try {
            return repositorioJdbc.listar();
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porId(Long id) {
        try {
            return Optional.ofNullable(repositorioJdbc.porId(id));
        } catch (SQLException throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Categoria categoria) {

    }

    @Override
    public void activar(Long id) {

    }

    @Override
    public void desactivar(Long id) {

    }
}