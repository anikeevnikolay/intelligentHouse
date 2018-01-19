<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="databaseTools.DBConsts" %>
<%@ page import="java.util.List" %>
<%@ page import="databaseTools.StatisticTool" %>
<%@ include file="header.jsp" %>
<script src="js/jobs.js"></script>
<script src="js/job.js"></script>
<script src="js/statistics.js"></script>
<script src="js/statistic.js"></script>
<style><%@include file="css/job.css"%></style>
<style><%@include file="css/statistics.css"%></style>
<head>
    <title>Temperature statistic</title>
</head>
<%
    String startDate = request.getParameter("start_date");
    String endDate = request.getParameter("end_date");
%>
<a href="statistic.jsp?id=16041512135628688">statistic page</a>
<form action="tempStat.jsp">
    <input type="date" name="start_date" value="<%=startDate != null ? startDate : ""%>"/>
    <input type="date" name="end_date" value="<%=endDate != null ? endDate : ""%>"/>
    <input type="submit" value="plot"/>
</form>

<%
    try {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilStartDate = format.parse(startDate);
        java.util.Date utilEndDate = format.parse(endDate);

        java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());

        Object[] statistic = StatisticTool.getStatisticParams(DBConsts.TEMPERATURE_STATISTIC_ID);
%>
<script>
    var data = '<%=StatisticTool.prepareValueForJavaScript((String) statistic[1], sqlStartDate, sqlEndDate)%>';
    var name = '<%=statistic[0]%>';
    var type = '<%=JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().selectStatisticType(), DBConsts.TEMPERATURE_STATISTIC_ID)%>';
</script>
<canvas width="<%=statistic[2]%>" height="<%=statistic[3]%>" id="demo"></canvas>
<%

    } catch (NullPointerException e) {
        out.println("Date values specified incorrect");
    } catch (java.text.ParseException e) {
        out.println("Date values specified incorrect");
    }

%>

<%@ include file="footer.jsp" %>