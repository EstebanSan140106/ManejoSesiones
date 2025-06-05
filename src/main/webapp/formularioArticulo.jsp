<%--
  Created by IntelliJ IDEA.
  User: pepew
  Date: 4/6/2025
  Time: 6:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*, org.erick.ManejodeSesiones.services.*" %>
<%@ page import="org.erick.ManejodeSesiones.services.services.models.Categoria" %>
<%@ page import="org.erick.ManejodeSesiones.services.services.models.Articulo" %>
<% Articulo articulo = (Articulo) request.getAttribute("articulo");
    Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
%>
<html>
<head>
    <title>Formulario Artículo</title>
    <link rel="stylesheet" href="bootstrap.min.css">
</head>
<body>
<h1>Formulario Artículo</h1>
<div>
    <form action="<%=request.getContextPath()%>/articulo/form" method="post">
        <div>
            <label for="codigo">Código</label><br>
            <input type="text" id="codigo" name="codigo" value="<%=articulo != null && articulo.getCodigo() != null ? articulo.getCodigo() : ""%>"><br>
            <span style="color:red;"><%= (errores != null && errores.get("codigo") != null) ? errores.get("codigo") : "" %></span>
        </div>

        <div>
            <label for="nombre">Nombre</label><br>
            <input type="text" id="nombre" name="nombre" value="<%=articulo != null && articulo.getNombre() != null ? articulo.getNombre() : ""%>"><br>
            <span style="color:red;"><%= (errores != null && errores.get("nombre") != null) ? errores.get("nombre") : "" %></span>
        </div>

        <div>
            <label for="stock">Stock</label><br>
            <input type="text" id="stock" name="stock" value="<%=articulo != null ? articulo.getStock() : ""%>"><br>
            <span style="color:red;"><%= (errores != null && errores.get("stock") != null) ? errores.get("stock") : "" %></span>
        </div>

        <div>
            <label for="descripcion">Descripción</label><br>
            <input type="text" id="descripcion" name="descripcion" value="<%=articulo != null && articulo.getDescripcion() != null ? articulo.getDescripcion() : ""%>"><br>
            <span style="color:red;"><%= (errores != null && errores.get("descripcion") != null) ? errores.get("descripcion") : "" %></span>
        </div>

        <div>
            <label for="imagen">Imagen</label><br>
            <input type="text" id="imagen" name="imagen" value="<%=articulo != null && articulo.getImagen() != null ? articulo.getImagen() : ""%>"><br>
            <span style="color:red;"><%= (errores != null && errores.get("imagen") != null) ? errores.get("imagen") : "" %></span>
        </div>

        <div>
            <input type="submit" value="<%=(articulo != null && articulo.getIdarticulo() != null && articulo.getIdarticulo() > 0) ? "Editar" : "Crear"%>">
        </div>
    </form>
</div>
</body>
</html>