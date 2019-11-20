<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    List<Dayoff> dayoffs = (List<Dayoff>) request.getAttribute("dayoffs");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
%>

<style>
    #calendar {
        max-width: 900px;
        margin: 40px auto;
    }
</style>

<div id='calendar'></div>

<script>
    var resources = [
        <% for (Employee employee : employees) { %>
        {
            id: "<%= employee.getId() %>",
            title: "<%= employee.getLastName() + " " + employee.getFirstName() %>"
        },
        <% } %>
    ];

    var events = [
        <% for (Dayoff dayoff : dayoffs) { %>
        {
            resourceId: "<%= dayoff.getEmployee().getId() %>",
            title: "TODO",
            start: "<%= dateFormat.format(dayoff.getDateStart()) %>",
            end: "<%= dateFormat.format(dayoff.getDateEnd()) %>"
        },
        <% } %>
    ];
</script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var calendarEl = document.getElementById('calendar');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
            plugins: ['interaction', 'resourceTimeline'],
            timeZone: 'UTC',
            defaultView: 'resourceTimelineMonth',
            aspectRatio: 1.5,
            weekends: false,
            editable: false,
            locale: 'fr',
            resourceLabelText: 'Employés',
            resources: resources,
            events: events
        });

        calendar.render();
    });
</script>