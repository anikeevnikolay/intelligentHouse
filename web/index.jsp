<%@ page import="arduino.ArduinoAlarmController" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="helpers.PropertiesHelper" %>
<%@ page import="helpers.PropertyConsts" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ include file="header.jsp" %>
<head>
    <title>Main page</title>
</head>

<form action="index.jsp" method="post" enctype="multipart/form-data">
    <input type="text" name="description" />
    <input type="file" name="file" />
    <input type="submit" />
</form>

<%
    /*try {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        ArrayList<Integer> list = new ArrayList<>();
        while (fileContent.available() > 0)
            list.add(fileContent.read());
        FileOutputStream outputStream = new FileOutputStream(PropertiesHelper.getProperty(PropertyConsts.COMPILE_PROPERTIES).getProperty("class_path") + File.separator + fileName);
        outputStream.write();
    } catch (Exception e) {}*/
%>

<%@ include file="footer.jsp" %>