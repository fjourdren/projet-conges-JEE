<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>DayoffManager</title>

		<jsp:include page="components/head.jsp" />
	</head>
	<body style="width: 100vw; height: 100vh">

		<jsp:include page="components/menu.jsp" />

      <!--  <div class="container"> -->
		<div style="margin: 16px">

        <%--	<jsp:include page="components/leftMenu.jsp" /> --%>

        	<jsp:include page="components/flashMessages.jsp" />

            <%
	            // router - components
	            switch((String) request.getAttribute("componentNeeded")) {
					case "login":
						%> <jsp:include page="login.jsp" /> <%
						break;
					case "employeesRender":
						%> <jsp:include page="employees/render.jsp" /> <%
						break;
					case "employeesEditAdd":
						%> <jsp:include page="employees/edit-add.jsp" /> <%
						break;
					case "dayoffTypeRender":
						%> <jsp:include page="DayoffType/render.jsp" /> <%
						break;
					case "dayoffTypeEditAdd":
						%> <jsp:include page="DayoffType/edit-add.jsp" /> <%
						break;
					case "changePassword":
						%> <jsp:include page="changePassword.jsp" /> <%
						break;
					case "dayoffTimeline":
						%> <jsp:include page="dayoff-timeline.jsp" /> <%
						break;
					case "dayoffRender":
						%> <jsp:include page="Dayoff/render.jsp" /> <%
						break;
					case "dayoffTraiterDemande":
						%> <jsp:include page="Dayoff/traiter-demande.jsp" /> <%
						break;
					case "manageMyDayoffs":
						%> <jsp:include page="manage-my-dayoffs.jsp" /> <%
						break;
					case "stat-overview":
                        %> <jsp:include page="stat-overview.jsp" /> <%
                        break;
                    default:
						%> <jsp:include page="default.jsp" /> <%
						break;
				}
			%>


            <jsp:include page="components/footer.jsp" />

		</div>
      <!--  </div> -->
	</body>
</html>