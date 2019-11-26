<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Arrays" %>
<%
    int[] dataMonth  = (int[]) request.getAttribute("dataMonth");
    List<Dayoff> allDayoff = (List<Dayoff>) request.getAttribute("allDayoff");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
%>

<canvas id="bar-chart" width="640" height="360"></canvas>

<script>
    // Bar chart

    new Chart(document.getElementById("bar-chart"), {
        type: 'bar',
        data: {
            labels: ["Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Setpembre", "Octobre", "Novembre", "Décembre"],
            datasets: [
                {
                    label: "% employés en congés",
                    backgroundColor: "#ff1821",
                    data: <%=Arrays.toString(dataMonth) %>
                }
            ]
        },
        options: {
            legend: { display: false },
            title: {
                display: true,
                text: 'Prise des congés par mois (en %).'
            }
        }
    });

</script>
