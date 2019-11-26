<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>

<nav class="navbar navbar-light bg-light">
    <span class="navbar-brand mb-0 h1">Liste des employés</span>
    <div>
        <a class="btn btn-primary my-2 my-sm-0" href="employees-add-edit" role="button">Ajouter</a>
    </div>
</nav>
<br>

<jsp:include page="../components/datatable.jsp"/>