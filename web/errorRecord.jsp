<%@ page import="databaseTools.ErrorTool" %>
<%@ include file="header.jsp" %>

<%
    String strId = request.getParameter("id");
    BigDecimal id = new BigDecimal(strId);
    Object[] error = ErrorTool.getErrorRecord(id);
%>

<head>
    <title><%=error[2]%></title>
</head>

<a href="job.jsp?id=<%=error[1]%>">Job: <%=error[5]%></a><p/>
<%=error[2]%><p/>
<%=error[4]%><p/>
<div style="white-space: pre-wrap"><%=error[3]%></div>

<%@ include file="footer.jsp" %>