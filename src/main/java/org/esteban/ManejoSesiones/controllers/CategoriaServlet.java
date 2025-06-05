package org.erick.ManejodeSesiones.services.services.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.erick.ManejodeSesiones.services.services.models.Categoria;
import org.erick.ManejodeSesiones.services.services.services.CategoriaService;
import org.erick.ManejodeSesiones.services.services.services.CategoriaServiceJdbcImplement;
import org.erick.ManejodeSesiones.services.services.services.LoginService;
import org.erick.ManejodeSesiones.services.services.services.LoginServiceSessionImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@WebServlet("/categoria")
public class CategoriaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUserName(req);

        String accion = req.getParameter("accion");
        Long id = 0L;

        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException ignored) {}

        try {
            if ("editar".equalsIgnoreCase(accion) && id > 0) {
                // Reenvía al servlet CategoriaForm para manejar la edición
                req.getRequestDispatcher("/categoria/form?idCategoria=" + id).forward(req, resp);
                return; // Importante: salir del método después de un forward
            } else if ("activar".equalsIgnoreCase(accion) && id > 0) {
                // *** CAMBIO CLAVE AQUÍ: Reenviar al servlet CategoriaForm para manejar la activación en un formulario ***
                // Pasa el ID y la acción para que el formulario sepa qué hacer
                req.getRequestDispatcher("/categoria/form?accion=activar&idCategoria=" + id).forward(req, resp);
                return; // Importante: salir del método después de un forward
            } else if ("desactivar".equalsIgnoreCase(accion) && id > 0) {
                // Desactivar sigue siendo una acción directa con redirección
                service.desactivar(id);
                resp.sendRedirect(req.getContextPath() + "/categoria");
                return; // Importante: salir del método después de una redirección
            }

            // Si no se realizó una acción de editar, activar o desactivar,
            // entonces se muestra el listado normal.
            List<Categoria> categorias = service.Listar();
            req.setAttribute("categorias", categorias);

            String username = usernameOptional.orElse("");
            req.setAttribute("username", username);

            req.getRequestDispatcher("/categoriaListar.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Error en la acción de categoría", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");

        Long idCategoria;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L;
        }

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        try {
            service.guardar(categoria);
        } catch (Exception e) {
            throw new ServletException("Error al guardar la categoría", e);
        }

        // Redireccionar a la lista para evitar reposteo (POST-Redirect-GET)
        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}
