<%@ page import="databaseTools.DB150Tool" %><%
    String result = request.getParameter("text");
    result = DB150Tool.idBacklight(result);
    out.print(result);
%>