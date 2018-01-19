<%@ page import="databaseTools.StatisticTool" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>
<head>
    <title>Statistics</title>
</head>
<style><%@include file="css/statistics.css"%></style>
<script src="js/statistics.js"></script>
<a href="statistic.jsp">create new</a><p/>
<%
    List<Object[]> statistics = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectAllStatistics(), null);
    int i = 0;
    for (Object[] statistic : statistics) {
        String statStr = StatisticTool.prepareValueForJavaScript(statistic[2].toString());
%>
<script>
    rawResult[<%=i%>] = '<%=statStr%>';
    names[<%=i%>] = '<%=statistic[1]%>';
    types[<%=i%>] = '<%=JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().selectStatisticName(), new Object[]{statistic[5]})%>';
</script>

<a class="no-border" href="statistic.jsp?id=<%=statistic[0]%>">
    <canvas width="<%=statistic[3]%>" height="<%=statistic[4]%>" id="<%=i%>"></canvas>
</a>
<%
        i++;
    }
%>
<%@ include file="footer.jsp" %>