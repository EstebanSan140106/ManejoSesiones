<%--
  Created by IntelliJ IDEA.
  User: pepew
  Date: 29/5/2025
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%--
  Este JSP muestra un listado de categorías obtenidas del servidor
  y permite activar/desactivar categorías, así como agregar nuevas.
--%>
<%--
  Este JSP muestra un formulario para crear, editar o confirmar la activación de una categoría.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="org.esteban.ManejoSesiones.models.Categoria" %>

<%
    // Obtenemos la categoría desde el atributo de la solicitud
    Categoria categoria = (Categoria) request.getAttribute("categoria");
    // Obtenemos la acción enviada desde el servlet (ej. "editar", "activar")
    String accion = (String) request.getAttribute("accion");

    // Si la categoría es nula (ej. para un nuevo registro), creamos una instancia vacía
    if (categoria == null) {
        categoria = new Categoria();
    }

    // Verificamos si es un formulario de edición (si tiene ID válido)
    boolean esEdicion = categoria.getIdCategoria() != null && categoria.getIdCategoria() > 0;
    // Verificamos si es una acción de activación
    boolean esActivacion = "activar".equalsIgnoreCase(accion) && esEdicion;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%-- Título dinámico de la página --%>
    <title><%= esActivacion ? "Activar Categoría" : (esEdicion ? "Editar Categoría" : "Nueva Categoría") %></title>

    <%-- Aquí puedes incluir tu CSS si lo tienes (ej. formularioCategoria.css) --%>
    <%-- <link rel="stylesheet" href="<%= request.getContextPath() %>/css/formularioCategoria.css"> --%>

    <%-- Script para validación en el cliente --%>
    <script>
        function validarFormulario() {
            // Solo valida campos si NO es una acción de activación (donde los campos pueden ser de solo lectura)
            if (!<%= esActivacion %>) {
                const nombre = document.getElementById("nombre").value.trim();
                const descripcion = document.getElementById("descripcion").value.trim();

                if (nombre === "") {
                    alert("El campo 'Nombre' no puede estar vacío.");
                    return false;
                }
                if (descripcion === "") {
                    alert("El campo 'Descripción' no puede estar vacío.");
                    return false;
                }
            }
            return true; // Permite el envío del formulario
        }
    </script>
</head>

<body>

<%-- Encabezado principal del formulario --%>
<h1><%= esActivacion ? "Confirmar Activación de Categoría" : (esEdicion ? "Editar Categoría" : "Nueva Categoría") %></h1>

<%-- Formulario que se envía vía POST al servlet que maneja '/categoria/form' --%>
<form action="<%= request.getContextPath() %>/categoria/form" method="post" onsubmit="return validarFormulario();">

    <%-- Campo oculto para el ID de la categoría (0 para nueva, ID para edición/activación) --%>
    <input type="hidden" name="idCategoria" value="<%= categoria.getIdCategoria() != null ? categoria.getIdCategoria() : 0 %>" />

    <%-- Campo oculto para la acción si es una confirmación de activación --%>
    <% if (esActivacion) { %>
    <input type="hidden" name="accion" value="activarConfirmar" />
    <% } %>

    <%-- Campo de texto para el nombre de la categoría --%>
    <label for="nombre">Nombre:</label><br>
    <%-- Si es activación, el campo es de solo lectura --%>
    <input type="text" id="nombre" name="nombre" value="<%= categoria.getNombre() != null ? categoria.getNombre() : "" %>" <%= esActivacion ? "readonly" : "required" %> /><br><br>

    <%-- Área de texto para la descripción de la categoría --%>
    <label for="descripcion">Descripción:</label><br>
    <%-- Si es activación, el campo es de solo lectura --%>
    <textarea id="descripcion" name="descripcion" <%= esActivacion ? "readonly" : "required" %>><%= categoria.getDescripcion() != null ? categoria.getDescripcion() : "" %></textarea><br><br>

    <%-- Campo para la condición (activo/inactivo) --%>
    <%-- Solo visible y editable si NO es una acción de activación --%>
    <% if (!esActivacion) { %>
    <label for="condicion">Activo:</label><br>
    <input type="checkbox" id="condicion" name="condicion" <%= categoria.getCondicion() == 1 ? "checked" : "" %> /><br><br>
    <% } else { %>
    <%-- Si es activación, muestra el estado actual y un campo oculto para forzar la activación --%>
    <p>Estado actual: **<%= categoria.getCondicion() == 1 ? "Activo" : "Inactivo" %>**</p>
    <p>¿Desea cambiar el estado a **Activo**?</p>
    <input type="hidden" name="condicion" value="on" /> <%-- Fuerza la condición a "on" (activo) --%>
    <% } %>

    <%-- Botón de envío que cambia su texto --%>
    <button type="submit">
        <%= esActivacion ? "Confirmar Activación" : (esEdicion ? "Actualizar" : "Guardar") %>
    </button>

    <%-- Enlace para volver al listado de categorías --%>
    <a href="<%= request.getContextPath() %>/categoria">Cancelar</a>
</form>

</body>
</html>
