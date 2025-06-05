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
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, org.erick.ManejodeSesiones.services.*" %>
<%@ page import="org.erick.ManejodeSesiones.services.services.models.Categoria" %>
<%
    // Obtiene la lista de categorías del atributo de la solicitud (request)
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
    // Obtiene el nombre de usuario del atributo de la solicitud (request)
    String username = (String) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado Categoria</title>
    <link rel="stylesheet" href="bootstrap.min.css">
</head>
<body>
<%-- Muestra un mensaje de bienvenida si el nombre de usuario no es nulo y no está vacío --%>
<% if (username != null && !username.isEmpty()) { %>
<div style="color:blue;">Hola <%= username %>, bienvenido a la aplicación</div>
<% } %>

<h1>Listado Categoria</h1>
<table border="1">
    <thead>
    <tr>
        <th>Id Categoria</th>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Condición</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%-- Itera sobre cada categoría en la lista para mostrarla en la tabla --%>
    <% for (Categoria cat : categorias) { %>
    <tr>
        <td><%= cat.getIdCategoria() %></td>
        <td><%= cat.getNombre() %></td>
        <td><%= cat.getDescripcion() %></td>
        <td><%= cat.getCondicion() == 1 ? "Activo" : "Inactivo" %></td>
        <td>
            <%-- Enlace para editar la categoría, pasando el ID --%>
            <a href="<%= request.getContextPath() %>/categoria/editar?id=<%= cat.getIdCategoria() %>">Editar</a> |
            <%-- Lógica condicional para mostrar el enlace de Desactivar o Activar --%>
            <% if (cat.getCondicion() == 1) { %>
            <%-- Si la categoría está activa (condicion == 1), muestra el enlace para Desactivar y un enlace no-op para Activar --%>
            <a href="<%= request.getContextPath() %>/categoria/desactivar?id=<%= cat.getIdCategoria() %>">Desactivar</a> | <a href="#">Activar</a>
            <% } else { %>
            <%-- Si la categoría está inactiva (condicion != 1), muestra un enlace no-op para Desactivar y el enlace para Activar --%>
            <a href="#">Desactivar</a> | <a href="<%= request.getContextPath() %>/categoria/activar?id=<%= cat.getIdCategoria() %>">Activar</a>
            <% } %>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<h2>Agregar Nueva Categoría</h2>
<%-- Formulario para agregar una nueva categoría --%>
<form action="<%= request.getContextPath() %>/categoria/guardar" method="post">
    <label for="nombre">Nombre:</label>
    <input type="text" name="nombre" id="nombre" required><br>

    <label for="descripcion">Descripción:</label>
    <textarea name="descripcion" id="descripcion" required></textarea><br>

    <button type="submit">Guardar Categoría</button>
</form>
</body>
</html>

