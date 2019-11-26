<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>

<nav class="navbar navbar-light bg-light">
    <span class="navbar-brand mb-0 h1">Liste des types de congés</span>
    <div>
        <a class="btn btn-primary my-2 my-sm-0" href="dayofftype-add-edit" role="button">Ajouter</a>
    </div>
</nav>
<br>

<jsp:include page="../components/datatable.jsp"/>