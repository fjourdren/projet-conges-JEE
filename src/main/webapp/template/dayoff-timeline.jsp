<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%
    List<Employee> employees = (List<Employee>) request.getAttribute("employees");
    List<Dayoff> dayoffs = (List<Dayoff>) request.getAttribute("dayoffs");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'");
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
        <% DateFormat simpleDayFormat = new SimpleDateFormat("yyyy-MM-dd"); %>
        <% for (Dayoff dayoff : dayoffs) { %>
        <%
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("Du ");
        titleBuilder.append(simpleDayFormat.format(dayoff.getDateStart()));
        titleBuilder.append(dayoff.getDateStart().getHours() == 12 ? " après-midi" : " matin" );
        titleBuilder.append(" au ");
        titleBuilder.append(simpleDayFormat.format(dayoff.getDateEnd()));
        titleBuilder.append(dayoff.getDateEnd().getHours() == 12 ? " après-midi" : " matin" );

        String eventColor = "blue";
        switch (dayoff.getStatus()){
            case WAITING:{
                eventColor = "lightgray";
                break;
            }
            case ACCEPTED:{
                eventColor = "lightgreen";
                break;
            }
            case REFUSED:{
                eventColor = "lightcoral";
                break;
            }
        }
        %>

        {
            resourceId: "<%= dayoff.getEmployee().getId() %>",
            title: "<%= titleBuilder.toString() %>",
            start: "<%= dateFormat.format(dayoff.getDateStart()) %>",
            end: "<%= dateFormat.format(dayoff.getDateEnd()) %>",
            color: "<%= eventColor %>",
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