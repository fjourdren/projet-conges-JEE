<%@ page import="java.util.List, java.util.ArrayList" %>

<script>
$(document).ready(function() {
    $('#dataTableRead').DataTable();
});
</script>

<div class="col-md-9">
	<div class="row">
		<div class="col-centered col-md-12">
			<% 
			ArrayList<ArrayList<String>> datatableDataArray = (ArrayList<ArrayList<String>>) request.getAttribute("datatableDataArray");
			if(datatableDataArray != null && datatableDataArray.size() > 0) { 
			%>
			<table id="dataTableRead" class="table table-bordred table-striped">
				<thead>
					<% 
					ArrayList<String> datatableHeadArray = (ArrayList<String>) request.getAttribute("datatableHeadArray");
					for (String headName: datatableHeadArray) { 
					%>
					    <th><%= headName %></th>
					<% } %>
				</thead>
				
				<tbody>
					<% 
					for (ArrayList<String> subDatatableDataArray: datatableDataArray) { 
						%>
						<tr>
						<%
						for (String data: subDatatableDataArray) { 
					%>
					    <td><%= data %></td>
					<% 
						} 
						%>
						</tr>
						<%
					} %>
				</tbody>  
			</table>
			<% } else { %>
			<p>Aucune donn�e.</p>
			<% } %>
		</div>
	</div>
 </div>