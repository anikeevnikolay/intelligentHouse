<%@ page import="databaseTools.JDBCUtil" %>
<head>
    <title>reload config</title>
</head>
<%
    JDBCUtil.reloadConfig();
%>
<h1>Reloaded</h1>