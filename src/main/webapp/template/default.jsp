
<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Map" %>

<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    List<Dayoff> dayOffFuture = (List<Dayoff>) request.getAttribute("dayOffFuture");
    Map<String, Integer> mapData = (Map<String, Integer>) request.getAttribute("mapData");
%>

<section class="content-header">
    <div class="container-fluid">
        <div class="row mb-2">
            <div class="col-sm-6">
                <h1>Mon compte</h1>
            </div>
        </div>
    </div>
</section>

<section class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-5">
                <canvas id="doughnut-chart" width="200" height="130"></canvas>
            </div>

            <div class="row col-md-7">
                <%
                    if(dayOffFuture !=null && !dayOffFuture.isEmpty())
                    for(int i=0; i<dayOffFuture.size(); i++){
                %>
                <div class="row col-md-12">
                    <%="À partir du " + dateFormat.format(dayOffFuture.get(i).getDateStart()) + ", " +  dayOffFuture.get(i).getNbDays() + " jours de congés "+ dayOffFuture.get(i).getType().getName() + "."%>
                </div>
                    <%}
                    else {%>
                    <div class="row col-md-12">
                        Vous n'avez pas de prochains congés.
                    </div>
                    <%}%>

            </div>
        </div>
    </div>
</section>

<script>
    var labelsChart = [];
    var dataChart = [];
    <%
    for (Map.Entry<String,Integer> entry : mapData.entrySet())
        {%>
        labelsChart.push("<%=entry.getKey()%>");
        dataChart.push(<%=entry.getValue()%>);
    <%}%>
    var myDoughnutChart = new Chart(document.getElementById("doughnut-chart"), {
        type: 'doughnut',
        data: {
            labels: labelsChart,
            datasets: [
                {
                    label: "% de vos types de congés",
                    backgroundColor:["rgb(255, 99, 132)","rgb(54, 162, 235)","rgb(255, 205, 86)","rgb(7, 205, 86)","rgb(255, 7, 86)","rgb(25, 25, 86)","rgb(55, 89, 42)"],
                    data: dataChart
                }
            ]
        },
        options: {
            legend: {display: true},
            title: {
                display: true,
                text: 'Type de congés posés en jours.'
            }
        }
    });
</script>