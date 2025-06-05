package org.esteban.ManejoSesiones.repository;

import org.esteban.ManejoSesiones.models.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación del repositorio utilizando JDBC para la entidad Categoria
public class CategoriaRepositorioJdbcImplement implements Repositorio<Categoria> {

    // Variable para almacenar la conexión a la base de datos
    private Connection conn;

    // Constructor que recibe una conexión y la asigna a la variable local
    public CategoriaRepositorioJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    // Método que devuelve una lista de todas las categorías
    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        // Ejecuta una consulta SQL para obtener todos los registros de la tabla 'categoria'
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM categoria")) {
            while (rs.next()) {
                // Se obtiene una instancia de Categoria desde el ResultSet
                Categoria c = getCategoria(rs);
                categorias.add(c);
            }
        }
        return categorias;
    }

    // Método que busca una categoría por su ID
    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        // Prepara la consulta SQL para buscar por ID
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM categoria WHERE id = ?")) {
            stmt.setLong(1, id); // Asigna el ID al parámetro de la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Si se encuentra un resultado, se construye la instancia de Categoria
                categoria = getCategoria(rs);
            }
        }
        return categoria;
    }

    // Método para insertar o actualizar una categoría en la base de datos
    @Override
    public void guardar(Categoria categoria) throws SQLException {
        String sql;
        // Verifica si la categoría ya tiene un ID para decidir si se actualiza o se inserta
        if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
            sql = "update categoria set nombre=?, descripcion=? where idCategoria=?";
        } else {
            sql = "insert into categoria(nombre, descripcion, condicion)values(?,?,1)";
        }

        // Prepara la sentencia SQL para insertar o actualizar
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());       // Asigna el nombre
            stmt.setString(2, categoria.getDescripcion());  // Asigna la descripción
            // En caso de actualización, también se establece el ID
            if (categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0) {
                stmt.setLong(3, categoria.getIdCategoria());
            }
            stmt.executeUpdate(); // Ejecuta la operación en la base de datos
        }
    }

    // Método para eliminar una categoría (aún no implementado)
    @Override
    public void eliminar(Long id) throws SQLException {
        // Falta implementar
    }

    // Método auxiliar que construye una instancia de Categoria a partir de un ResultSet
    private static Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setNombre(rs.getString("nombre"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setCondicion(rs.getInt("condicion"));
        c.setIdCategoria(rs.getLong("idCategoria"));
        return c;
    }

    public void actualizar(Categoria categoria) {
    }
}
