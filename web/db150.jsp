<%@page import="java.util.List" %>
<%@ include file="header.jsp" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>db150</title>
    <style><%@include file="css/db150.css"%></style>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script src="js/db150.js"></script>
</head>
<%@ page language="java"
         pageEncoding="UTF-8"
         isELIgnored="false"
%>
    <form action="db150.jsp" method="post" id="form" onsubmit="saveStartPoint();">
        <input type="hidden" id="start-point" name="start-point" value="<%=request.getParameter("start-point")%>"/>
        <input type="button" onclick="convertJavaToText();" value="Java -> Text"/>
        <input type="button" onclick="idsBacklight();" value="IDs backlight"/><p/>
        <textarea name="query"
                  id="query"
                  onkeydown="filterInput(this,event);"
                  onkeyup="doubleFilterInput(this,event);"
                  onkeypress="doubleFilterInput(this,event);"
                  autofocus
                  style="width: 100%; height: 25em;"><%= request.getParameter("query") != null ? request.getParameter("query") : ""%></textarea>
        <input type="submit" value="exequte"/><p/>
    </form>
<div class="tables">
<%
    String queriesStr = request.getParameter("query");
    if (queriesStr != null) {
        String[] queries = queriesStr.split(";");
        for (String query : queries)
            if (query != null) {
                try {
                    List<String[]> resultList = JDBCUtil.executeQueryDB150(query);
                    if (resultList == null || resultList.size() == 0) {
                        out.println("no one row found");
                    } else {%>
    <table class="table-background">
            <%
            for (String[] s : resultList) {
                    out.println("<tr class=\"tr-db150\">");
                    for (String str : s) {
                        out.println("<td class=\"td-db150\" style=\"white-space: pre-wrap;\">" + str + "</td>");
                    }
                    out.println("</tr>");
                }%>
        <table/>
<p/>
<% }
} catch (Exception e) {
    out.println("<h2>" + e.getClass().getSimpleName() + "</h2>");
    out.println("<h3>" + e.getMessage() + "</h3>");
}
}
}
%>
</div>
<%@ include file="footer.jsp" %>