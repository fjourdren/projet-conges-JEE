<%
String type = (String) session.getAttribute("flashType");
String content = (String) session.getAttribute("flashMessage");

if(type != null && content != null) { 
%>

		<div class="col-md-9">
			<div class="row">
				<div class="col-centered col-md-12">
					<div class="alert alert-<%= type %>" role="alert">
						<%= content %>
					</div>
				</div>
			</div>
		 </div>
 
<% 
	session.setAttribute("flashType", null);
	session.setAttribute("flashMessage", null);
} 
%>