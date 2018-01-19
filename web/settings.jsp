<%@ page import="helpers.SettingsHelper" %>
<%@ page import="java.util.Map" %>
<%@ page import="helpers.PropertyConsts" %>
<%@ include file="header.jsp" %>
<head>
    <title>Settings</title>
</head>

<%
    Map<String, String> map = SettingsHelper.getAvailableSettings(PropertyConsts.ARDUINO_PROPERTIES);
    boolean needSave = false;
    for (String s : map.keySet()) {
        String newValue = request.getParameter(s);
        if (newValue != null) {
            map.put(s, newValue);
            needSave = true;
        }
    }
    if (needSave)
        SettingsHelper.saveParams(map, PropertyConsts.ARDUINO_PROPERTIES);
%>
<h1>Settings</h1>
<form action="settings.jsp" method="get">
    <h3>Arduino settings</h3>
    <table>
        <%
            for (Object o : map.keySet()) {
        %>
        <tr>
            <td><%=o%>
            </td>
            <td><input type="text" value="<%=map.get(o)%>" name="<%=o%>"/></td>
        </tr>
        <%
            }
        %>
    </table>

<input type="submit" value="save"/>
</form>
<%@ include file="footer.jsp" %>