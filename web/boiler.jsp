<%@ page import="compile.CompileHelper" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Properties" %>
<%@ page import="helpers.PropertiesHelper" %>
<%@ page import="helpers.PropertyConsts" %>
<%@ include file="header.jsp" %>
<head>
    <title>Boiler</title>
</head>

<%
    String code = request.getParameter("code");
    String name = request.getParameter("name");
    String message = null;
    if (code != null && name != null)
    try {
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.COMPILE_PROPERTIES);
        CompileHelper.compileClass(name, code, pr.getProperty("class_path"));
    } catch (Exception e) {
        message = e.toString();
    } catch (Error e) {
        message = e.toString();
    }
%>
<form action="boiler.jsp" method="post">
    <input type="text" placeholder="Class name" name="name" value="<%=name != null ? name : ""%>"/><p/>
    <textarea name="code"
              id="code"
              autofocus
              style="width: 100%; height: 25em;"><%= request.getParameter("code") != null ? request.getParameter("code") : ""%></textarea>
    <input type="submit" value="compile"/>
    </form>
<h2><%=message != null ? message : ""%></h2>
<%@ include file="footer.jsp" %>