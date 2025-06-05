    package org.esteban.ManejoSesiones.util;

    import java.sql.Connection;
    import java.sql.SQLException;

    public class TestConexion {
        public static void main(String[] args) {
            try (Connection conn = Conexion.getConnection()) {
                System.out.println("¡Conexión exitosa!");
            } catch (SQLException e) {
                System.out.println("Error al conectar:");
                e.printStackTrace();
            }
        }
    }