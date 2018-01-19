<%@ page import="databaseTools.StatisticTool" %>
<%@ include file="header.jsp" %>
<script src="js/jobs.js"></script>
<script src="js/job.js"></script>
<script src="js/statistics.js"></script>
<script src="js/statistic.js"></script>
<style><%@include file="css/job.css"%></style>
<style><%@include file="css/statistics.css"%></style>
<%
    String strId = request.getParameter("id");
    BigDecimal id = null;
    if (strId != null)
        id = new BigDecimal(strId);
    else {
        id = StatisticTool.createNewStatistic();
    }
    if (request.getParameter("update") != null) {
        StatisticTool.updateStatistic(id,
                request.getParameter("name"),
                request.getParameter("query"),
                request.getParameter("width"),
                request.getParameter("height"),
                new BigDecimal(request.getParameter("stat_type")));
        response.sendRedirect("statistic.jsp?id=" + id);
    }
    Object[] statistic = StatisticTool.getStatisticParams(id);
%>
<script>
    var data = '<%=StatisticTool.prepareValueForJavaScript((String) statistic[1])%>';
    var name = '<%=statistic[0]%>';
    var type = '<%=JDBCUtil.executeForObject(JDBCUtil.getSQLContainer().selectStatisticType(), id)%>';
</script>
<head>
    <title><%=statistic[0]%></title>
</head>
<a href="statistics.jsp">back</a><p/>
<a href="statistic.jsp?id=<%=id%>" class="name"><%=statistic[0]%></a><p/>
<canvas width="<%=statistic[2]%>" height="<%=statistic[3]%>" id="demo"></canvas>
<form action="statistic.jsp" method="get">
    <input type="hidden" name="id" value="<%=id%>"/>
    <table class="job-params" style="width: 100%;">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name" placeholder="name" value="<%=statistic[0]%>"/></td>
        </tr>
        <tr>
            <td>Body</td>
            <td><textarea name="query"
                          placeholder="query"><%=statistic[1]%></textarea></td>
        </tr>
        <tr>
            <td>Width</td>
            <td><input type="number" name="width" value="<%=statistic[2]%>" onkeyup="editWidth(this.value);"/></td>
        </tr>
        <tr>
            <td>Height</td>
            <td><input type="number" name="height" value="<%=statistic[3]%>" onkeyup="editHeight(this.value);"/></td>
        </tr>
        <tr>
            <td>Type</td>
            <td><select name="stat_type">
                <%
                    for(Object[] o : JDBCUtil.executeQuery("select id, name from statistic_types", null)) {
                        %>
                <option <%=o[0].equals(statistic[4]) ? "selected" : ""%> value="<%=o[0]%>"><%=o[1]%></option>
                <%
                    }
                %>
            </select></td>
        </tr>
    </table>
    <input type="submit" name="update" value="update"/>
</form>

<p/>

<%@ include file="footer.jsp" %>