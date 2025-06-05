<%--
  Created by IntelliJ IDEA.
  User: pepew
  Date: 4/6/2025
  Time: 6:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, org.esteban.ManejoSesiones.*" %>
<%@ page import="org.esteban.ManejoSesiones.models.Categoria" %>
<%@ page import="org.esteban.ManejoSesiones.models.Articulo" %>
<%List<Articulo> articulos = (List<Articulo>) request.getAttribute("articulos");
    Optional<String> username = (Optional<String>) request.getAttribute("username");
%>
<html>
<head>
    <title>Listado Artículos</title>
</head>
<body>
<h1>Listado Artículos</h1>
<%
    if (username.isPresent()) { %>
<div style="color: blue;">Hola, <%=username.get()%> bienvenido</div>
<div><a href="<%=request.getContextPath()%>/articulo/form">Añadir Artículo</a></div>
<% } %>

<table border="1">
    <thead>
    <tr>
        <th>ID ARTICULO</th>
        <th>ID CATEGORIA</th>
        <th>CODIGO</th>
        <th>NOMBRE</th>
        <th>STOCK</th>
        <th>DESCRIPCION</th>
        <th>IMAGEN</th>
        <th>CONDICION</th>
        <th>ACCION</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Articulo art : articulos) { %>
    <tr>
        <td><%=art.getIdArticulo()%></td>
        <td><%=art.getCategoria()%></td>
        <td><%=art.getCodigo()%></td>
        <td><%=art.getNombre()%></td>
        <td><%=art.getStock()%></td>
        <td><%=art.getDescripcion()%></td>
        <td><%=art.getImagen()%></td>
        <td><%=art.getCondicion()%></td>
        <% if (username.isPresent()) { %>
        <td>
            <a href="<%=request.getContextPath()%>/articulo/form?id=<%=art.getIdArticulo()%>">Editar</a>
            <a href="<%=request.getContextPath()%>/articulo/desactivar?id=<%=art.getIdArticulo()%>">Desactivar</a>
        </td>
        <% } %>
    </tr>
    <% } %>
    </tbody>
</table>
<a href="<%=request.getContextPath()%>/articulo/reactivar">Reactivar Artículos</a>
<br>
<br>
<div>
    <a href="<%=request.getContextPath()%>/index.html">Inicio</a>
</div>
</body>
</html>
</body>
</html>
