<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.EmployeeType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
	DayoffType dayoffType = (DayoffType) request.getAttribute("dayoffType");
    Map<DayoffType, Float> dayoffTypeMap = (Map<DayoffType, Float>) request.getAttribute("dayoffTypeMap");
%>

<div class="col-md-9">
	<div class="row">
		<nav class="navbar navbar-light bg-light">
			<% if (dayoffType == null || dayoffType.getId() == -1) { %>
		    <span class="navbar-brand mb-0">Ajouter un type de congés</span>
		    <% } else { %>
		    <span class="navbar-brand mb-0">Modifier un type de congés</span>
		    <% } %>
		</nav>
	</div>
</div>


<div class="col-md-9">
	<div class="row">
		<form action="congesTypes-add-edit" method="POST" id="edit-form">
		
		    <input type="hidden" name="id" id="object-id" value="<%= dayoffType.getId() %>">
		    <div style="margin: 16px">
		        <div class="card">
		            <div style="padding: 16px">
		                <div class="form-group row">
		                    <label for="last-name-input" class="col-sm-2 col-form-label">Nom</label>
		                    <div class="col-sm-10">
		                        <input type="text" class="form-control" name="last-name" id="last-name-input" required
		                               value="<%= dayoffType.getName() %>">
		                    </div>
		                </div>
		                <div class="form-group row">
		                    <label for="first-name-input" class="col-sm-2 col-form-label">Nombre de jour par défaut</label>
		                    <div class="col-sm-10">
		                        <input type="text" class="form-control" name="first-name" id="first-name-input" required
		                               value="<%= dayoffType.getDefaultNbDays() %>">
		                    </div>
		                </div>
		                
		            </div>
		        </div>
		        
		    </div>
		    
		    
		    <div class="row">
				<div class="center-button">
					<button class="btn btn-success" type="submit" style="margin:5px;">Enregistrer</button>
					<a class="btn btn-primary" style="margin:5px;" href="employees" role="button">Annuler</a>
				</div>
			</div>
		
		
		</form>
	</div>
</div>