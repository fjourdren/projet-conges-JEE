<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>

<script>
    const FR = {
        "sEmptyTable": "Aucune donnée disponible dans le tableau",
        "sInfo": "Affichage de l'élément _START_ à _END_ sur _TOTAL_ éléments",
        "sInfoEmpty": "Affichage de l'élément 0 à 0 sur 0 élément",
        "sInfoFiltered": "(filtré à partir de _MAX_ éléments au total)",
        "sInfoPostFix": "",
        "sInfoThousands": ",",
        "sLengthMenu": "Afficher _MENU_ éléments",
        "sLoadingRecords": "Chargement...",
        "sProcessing": "Traitement...",
        "sSearch": "Rechercher :",
        "sZeroRecords": "Aucun élément correspondant trouvé",
        "oPaginate": {
            "sFirst": "Premier",
            "sLast": "Dernier",
            "sNext": "Suivant",
            "sPrevious": "Précédent"
        },
        "oAria": {
            "sSortAscending": ": activer pour trier la colonne par ordre croissant",
            "sSortDescending": ": activer pour trier la colonne par ordre décroissant"
        },
        "select": {
            "rows": {
                "_": "%d lignes sélectionnées",
                "0": "Aucune ligne sélectionnée",
                "1": "1 ligne sélectionnée"
            }
        }
    };

    $(document).ready(function () {
        $('#dataTableRead').DataTable({"language": FR});
    });
</script>

<%
    ArrayList<ArrayList<String>> datatableDataArray = (ArrayList<ArrayList<String>>) request.getAttribute("datatableDataArray");
    if (datatableDataArray != null && datatableDataArray.size() > 0) {
%>
<table id="dataTableRead" class="table table-bordred table-striped">
    <thead>
    <%
        ArrayList<String> datatableHeadArray = (ArrayList<String>) request.getAttribute("datatableHeadArray");
        for (String headName : datatableHeadArray) {
    %>
    <th><%= headName %>
    </th>
    <% } %>
    </thead>

    <tbody>
    <%
        for (ArrayList<String> subDatatableDataArray : datatableDataArray) {
    %>
    <tr>
        <%
            for (String data : subDatatableDataArray) {
        %>
        <td><%= data %>
        </td>
        <%
            }
        %>
    </tr>
    <%
        } %>
    </tbody>
</table>
<% } else { %>
<p>Aucune donnée.</p>
<% } %>
