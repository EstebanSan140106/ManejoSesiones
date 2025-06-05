package org.erick.ManejodeSesiones.services.services.repositorio;

import org.erick.ManejodeSesiones.services.services.models.Articulo;
import org.erick.ManejodeSesiones.services.services.models.Categoria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositorioJdbcImplement implements Repositorio<Articulo> {

    //Creamos una variable de tipo conexion
    private Connection conn;
    //Creamos el contructor don recibimos la conexión e inicializamos la conexion a la BBDD

    public ArticuloRepositorioJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Articulo> listar() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select a.*,c.nombre as categoria FROM articulo as a inner join categoria as c ON " +
                     " (a.idcategoria=c.idcategoria)")) {
            while (rs.next()){
                Articulo articulo = getArticulo(rs);
                articulos.add(articulo);
            }

        }
        return articulos;
    }



    @Override
    public Articulo porId(Long id) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Articulo articulo) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    private static Articulo getArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setIdArticulo(rs.getLong("idarticulo"));
        Categoria c = new Categoria();
        c.setIdCategoria(rs.getLong("idcategoria"));
        c.setNombre(rs.getString("nombre"));
        articulo.setCategoria(c);
        articulo.setCodigo(rs.getString("codigo"));
        articulo.setNombre(rs.getString("categoria"));
        articulo.setStock(rs.getInt("stock"));
        articulo.setDescripcion(rs.getString("descripcion"));
        articulo.setImagen(rs.getString("imagen"));
        articulo.setCondicion(rs.getInt("condicion"));
        return articulo;
    }
}
