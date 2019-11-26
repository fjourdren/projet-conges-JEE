<%
    String type = (String) session.getAttribute("flashType");
    String content = (String) session.getAttribute("flashMessage");

    if (type != null && content != null) {
%>

<div class="alert alert-<%= type %>" role="alert">
    <%= content %>
</div>

<%
        session.setAttribute("flashType", null);
        session.setAttribute("flashMessage", null);
    }
%>