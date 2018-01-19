<%@ page import="databaseTools.JobTool" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: antiz_000
  Date: 2/22/2016
  Time: 5:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="header.jsp" %>
<script src="js/sessions.js"></script>
<script src="js/jobs.js"></script>
<style><%@include file="css/sessions.css"%></style>
<style><%@include file="css/jobs.css"%></style>
<head>
    <title>Jobs</title>
</head>
<%
    Enumeration str = request.getParameterNames();
    boolean redirect = false;
    while (str.hasMoreElements()) {
        redirect = true;
        try {
            BigDecimal s = new BigDecimal((String) str.nextElement());
            if ("on".equals(request.getParameter(s.toString()))) {
                if ("set enable".equals(request.getParameter("operation"))) {
                    JobTool.setJobEnable(s);
                }
                if ("set disable".equals(request.getParameter("operation"))) {
                    JobTool.setJobDisable(s);
                }
                if ("delete".equals(request.getParameter("operation"))) {
                    JobTool.deleteJob(s);
                }
            }
        } catch (NumberFormatException e) {}
    }
    if (redirect)
        response.sendRedirect("jobs.jsp");
%>
<%!
    private String getStatusColor(Object[] s){
        if (BigDecimal.valueOf(2).equals(s[5]))
            return "gray";
        if (BigDecimal.ZERO.equals(s[7]))
            return "lime";
        return "red";
    }

    private String checkHideOrNot(String text) {
        if (text == null)
            return "";
        String[] arr = text.split("\n");
        if (arr.length <= 2) {
            if (arr.length == 0)
                return "";
            if (arr.length == 1 && arr[0].length() < 60)
                return "";
            if (arr.length == 2 && arr[0].length() < 60 && arr[1].length() < 60)
                return "";
        }
        return " class=\"hide-div\"";

    }
%>
<a href="job.jsp">Create new job</a><p/>
<form action="jobs.jsp" method="get">
    <input type="submit" name="operation" value="set enable"/>
    <input type="submit" name="operation" value="set disable"/>
    <input type="submit" name="operation" value="delete"/>
    <input type="hidden" name="action"/>
    <table class="s-table">
        <tr>
            <td><input type="checkbox" onchange="setAllCheckboxes(this);" id="main-checkbox"/></td>
            <td>Status</td>
            <td>Name</td>
            <td>Body</td>
            <td>Schedule</td>
            <td>Estimated time</td>
            <td>Last execute when</td>
            <td>Errors in executions</td>
            <td>Type</td>
        </tr>
        <%
            List<Object[]> list = JDBCUtil.executeQuery(JDBCUtil.getSQLContainer().selectAllJobsFullInfo(), null);
            if (list != null)
                for (Object[] s : list) {
        %>
        <tr class="s-tr">
            <td class="s-td"><input type="checkbox" class="session-checkbox" name="<%=s[0]%>" onchange="selectString(this); checkAllCheckboxes();"/></td>
            <td class="s-td"><div class="status-div" style="background-color: <%=getStatusColor(s)%>;"/></td>
            <td class="s-td" style="white-space: nowrap; text-align: center"><a href="job.jsp?id=<%=s[0]%>"><%=s[1]%></a></td>
            <td class="s-td" onclick="changeHideOptions(this);"><div<%=checkHideOrNot((String) s[2])%>><%=s[2]%></div></td>
            <td class="s-td minutes"><%=s[3]%></td>
            <td class="s-td minutes"><%=s[4]%></td>
            <td class="s-td"><%=s[6]%></td>
            <td class="s-td"><%=s[7]%></td>
            <td class="s-td"><%=s[8]%></td>
        </tr>
        <%
                }
        %>
    </table>
</form>
<%@ include file="footer.jsp" %>