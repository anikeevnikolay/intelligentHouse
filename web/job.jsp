<%@ page import="databaseTools.ErrorTool" %>
<%@ page import="databaseTools.JobTool" %>
<%@ page import="helpers.JavaHelper" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>
<script src="js/jobs.js"></script>
<script src="js/job.js"></script>
<style><%@include file="css/job.css"%></style>
<%
    String strId = request.getParameter("id");
    BigDecimal id = null;
    if (strId != null)
        id = new BigDecimal(strId);
    else {
        id = JobTool.createNewJob();
    }
    if (request.getParameter("execute") != null) {
        JobTool.updateJob(id, request.getParameter("name"), request.getParameter("body"), request.getParameter("timeout"), request.getParameter("status"), request.getParameter("type"));
        JavaHelper.executeJob(id);
        response.sendRedirect("job.jsp?id=" + id);
    } else if (request.getParameter("save") != null) {
        JobTool.updateJob(id, request.getParameter("name"), request.getParameter("body"), request.getParameter("timeout"), request.getParameter("status"), request.getParameter("type"));
        response.sendRedirect("job.jsp?id=" + id);
    }
    Object[] job = JobTool.getJobInfo(id);
%>
<head>
    <title><%=job[0]%></title>
</head>
<a href="job.jsp?id=<%=id%>" class="name"><%=job[0]%></a><p/>
<form action="job.jsp" method="get">
    <input type="hidden" name="mode"/>
    <input type="hidden" name="id" value="<%=id%>"/>
    <table class="job-params" style="width: 100%;">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name" placeholder="name" value="<%=job[0]%>"/></td>
        </tr>
        <tr>
            <td>Body</td>
            <td><textarea name="body"
              placeholder="body"><%=job[1]%></textarea></td>
        </tr>
        <tr>
            <td>Schedule</td>
            <td><input type="number" name="timeout" placeholder="interval(min)" value="<%=job[2]%>"/></td>
        </tr>
        <tr>
            <td>Active</td>
            <td><input type="checkbox" name="status" <%=BigDecimal.ONE.equals(job[4]) ? "checked" : "unchecked"%>/></td>
        </tr>
        <tr>
            <td>Job type</td>
            <td>
                <select name="type">
                    <option <%="SQL".equals(job[5]) ? "selected" : ""%>>SQL</option>
                    <option <%="Java".equals(job[5]) ? "selected" : ""%>>Java</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Last execution</td>
            <td><%=job[3]%></td>
        </tr>
        <tr>
            <td>Time left:</td>
            <td><span class="minutes"><%=job[6]%></span></td>
        </tr>
    </table>
    <input type="submit" name="save" value="save"/>
    <input type="submit" name="execute" value="execute now"/>
</form>

<p/>
<%
    List<Object[]> list = ErrorTool.getJobsErrors(id);
    if (list != null)
    for (Object[] error : list) {
        %>
<a href="errorRecord.jsp?id=<%=error[0]%>"><%=error[2]%></a><p/>
<%
    }
%>

<%@ include file="footer.jsp" %>