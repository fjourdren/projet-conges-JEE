<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		
		<jsp:include page="components/head.jsp" />
	</head>
	<body>

		<jsp:include page="components/menu.jsp" />

        <div class="container">
        
        	<jsp:include page="components/flashMessages.jsp" />
            
            <%
	            // router - components
	            switch((String) request.getAttribute("componentNeeded")) {
					case "login":
						%> <jsp:include page="login.jsp" /> <%
						break;
					case "demo":
						%> <jsp:include page="demo.jsp" /> <%
						break;
					default:
						
				}
			%>
       

            <jsp:include page="components/footer.jsp" />
        
        </div>
	</body>
</html>