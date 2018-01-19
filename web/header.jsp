<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="databaseTools.JDBCUtil" %>
<%@ page import="databaseTools.SessionTool" %>
<%@ page import="java.math.BigDecimal" %>
<!DOCTYPE html>
<html>

<script src="js/Detect.js-master/detect.js"></script>
<script src="js/authentification.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<input type="hidden" name="info" id="info"/>

    <%
    BigDecimal userSession = (BigDecimal) session.getAttribute("session");
    if (userSession == null || !SessionTool.checkSession(userSession)) {
        SessionTool.initSession(JDBCUtil.getNewId(), request.getRemoteAddr(), request.getParameter("info") + " " +  request.getRequestURL(), 4);
        response.sendRedirect("authorization.jsp");
    } else {
        SessionTool.updateSession(userSession);
%>
    <style>
        <%@include file="css/all_styles.css"%>
    </style>
        <div class="header-class">
            <table>
                <tr>
                    <td><a href="index.jsp">Index</a></td>
                    <td><a href="db150.jsp">db150</a></td>
                    <td><a href="reloadConfig.jsp">reload DB config</a></td>
                    <td><a href="canvas.jsp">Schema</a></td>
                    <td><a href="sessions.jsp">Audit</a></td>
                    <td><a href="jobs.jsp">Jobs</a></td>
                    <td><a href="boiler.jsp">boiler</a></td>
                    <td><a href="statistics.jsp">Statistics</a></td>
                    <td><a href="settings.jsp">Settings</a></td>
                    <td><a href="tempStat.jsp">Temperature stat</a></td>
                </tr>
            </table>
        </div>
    <div class="basic-class">
        <div id="alarmDiv" class="alarm-div"></div>
        <script>
            function checkAlarm() {
                $.get("checkAlarm.jsp", alarm, "html");
                setInterval(function() {
                    $.get("checkAlarm.jsp", alarm, "html");
                }, 2000);
            }

            function alarm(forecastData) {
                if (forecastData == '')
                    $('#alarmDiv').css('border', '0px red solid');
                else
                    $('#alarmDiv').css('border', '1px red solid');
                $('#alarmDiv').html(forecastData);
            }

            checkAlarm();
        </script>