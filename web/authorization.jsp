<%@ page import="databaseTools.SessionTool" %>
<%@ page import="java.math.BigDecimal" %><%--
  Created by IntelliJ IDEA.
  User: antiz_000
  Date: 2/20/2016
  Time: 6:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BigDecimal userSession = (BigDecimal) session.getAttribute("session");
    if (userSession != null && SessionTool.checkSession(userSession))
        response.sendRedirect("index.jsp");
    String pass = request.getParameter("password");
    String login = request.getParameter("login");
    if (pass != null && login != null) {
        session.setAttribute("session", SessionTool.checkUser(login, pass, request.getRemoteAddr(), request.getParameter("info")));
        response.sendRedirect("index.jsp");
    }
%>
<html>
<script src="js/Detect.js-master/detect.js"></script>
<script src="js/authentification.js"></script>
<style>
    .main-div {
        border-radius: 10px;
        text-align: center;
        background-color: #ffffed;
        padding: 10px;
        border: 2px black solid;
        width: 250px;
        height: 120px;
        align: center;
    }
    input {
        width: 200px;
        border: 1px solid black;
        background-color: white;
        border-radius: 5px;
        padding: 5px;
    }
    input[type=submit]:hover {
        color: white;
        background-color: red;
    }
    body {
        align: center;
    }
</style>
<head>
    <title>Log in</title>
</head>
<body>
    <div class="main-div">
        <form method="post" action="authorization.jsp">
            <input type="hidden" name="info" id="info"/>
            <input type="text" required name="login" placeholder="login" autofocus/><p/>
            <input type="password" required name="password" placeholder="password"/><p/>
            <input type="submit"/>
        </form>
    </div>
</body>
</html>
