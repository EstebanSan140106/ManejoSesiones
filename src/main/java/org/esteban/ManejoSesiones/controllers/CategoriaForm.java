package org.esteban.ManejoSesiones.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.esteban.ManejoSesiones.services.CategoriaServiceJdbcImplement;
import org.esteban.ManejoSesiones.services.CategoriaService;
import org.esteban.ManejoSesiones.models.Categoria;
import org.esteban.ManejoSesiones.services.LoginServiceSessionImplement;
import org.esteban.ManejoSesiones.services.LoginService;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/categoria/form")
public class CategoriaForm extends HttpServlet {

    // Método GET: Carga el formulario con una categoría específica si se está editando o activando
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos la conexión a la base de datos que viene del filtro
        Connection conn = (Connection) req.getAttribute("conn");

        // Instanciamos el servicio de categorías con la conexión
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        // Instanciamos el servicio de login para obtener el nombre de usuario
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUserName(req);
        String username = usernameOptional.orElse(""); // Extrae el String, o una cadena vacía ""
        req.setAttribute("username", username); // Pasa el String real al JSP

        Long id = 0L;
        // Obtener la acción de la URL (ej. "editar", "activar")
        String accion = req.getParameter("accion");

        // Intentamos obtener el parámetro 'idCategoria' de la URL y convertirlo a Long
        try {
            id = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            // Si no es válido o no viene, lo dejamos en 0 (nuevo registro)
            id = 0L;
        }

        // Creamos una categoría vacía por defecto
        Categoria categoria = new Categoria();

        // Si el ID es mayor que cero, estamos editando o activando una categoría existente
        if (id > 0) {
            // Buscamos la categoría por ID
            Optional<Categoria> optionalCategoria = service.porId(id);
            // Si existe, la asignamos
            if (optionalCategoria.isPresent()) {
                categoria = optionalCategoria.get();
            }
        }

        // Enviamos la categoría al JSP como atributo "categoria"
        req.setAttribute("categoria", categoria);
        // Enviamos la acción al JSP para que pueda adaptar su contenido
        req.setAttribute("accion", accion);

        // Redirigimos al formulario JSP
        getServletContext().getRequestDispatcher("/formularioCategoria.jsp").forward(req, resp);
    }

    // Método POST: Guarda o actualiza la categoría, o confirma la activación
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenemos la conexión a la base de datos
        Connection conn = (Connection) req.getAttribute("conn");

        // Instanciamos el servicio
        CategoriaService service = new CategoriaServiceJdbcImplement(conn);

        // Obtenemos la acción del formulario (puede ser "activarConfirmar" o nulo para guardar/actualizar)
        String accion = req.getParameter("accion");

        Long idCategoria = null;
        try {
            idCategoria = Long.parseLong(req.getParameter("idCategoria"));
        } catch (NumberFormatException e) {
            idCategoria = 0L; // Si no viene o no es número, asumimos que es una nueva categoría
        }

        // --- Lógica para confirmar la activación ---
        if ("activarConfirmar".equalsIgnoreCase(accion) && idCategoria > 0) {
            try {
                service.activar(idCategoria); // Llama al método activar del servicio
                resp.sendRedirect(req.getContextPath() + "/categoria"); // Redirecciona al listado
                return; // Sale del método después de la redirección
            } catch (Exception e) {
                throw new ServletException("Error al confirmar la activación de la categoría", e);
            }
        }

        // --- Lógica normal para guardar o actualizar la categoría ---
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        // Obtener el parámetro de condición (de un checkbox, por ejemplo)
        String condicionParam = req.getParameter("condicion");

        // Creamos y llenamos el objeto Categoria
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria != 0 ? idCategoria : null); // null si es nueva
        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
        // Establecer la condición de la categoría (1 si el checkbox está marcado, 0 si no)
        categoria.setCondicion("on".equalsIgnoreCase(condicionParam) ? 1 : 0);

        try {
            // Guardamos la categoría (insertar o actualizar)
            service.guardar(categoria);
        } catch (Exception e) {
            throw new ServletException("Error al guardar la categoría", e);
        }

        // Redireccionamos al listado para evitar reenvío de formulario (POST-Redirect-GET)
        resp.sendRedirect(req.getContextPath() + "/categoria");
    }
}
