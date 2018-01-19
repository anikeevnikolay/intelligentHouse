<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List" %>
<%@ include file="header.jsp" %>
<style><%@include file="css/sessions.css"%></style>
<script src="js/sessions.js"></script>
<%
    Enumeration str = request.getParameterNames();
    if (userSession != null && SessionTool.checkSession(userSession))
    while (str.hasMoreElements()) {
        String tmpStr = (String) str.nextElement();
        if ("on".equals(request.getParameter(tmpStr.toString())))
            try {
                BigDecimal s = new BigDecimal(tmpStr);
                SessionTool.dropSession(s);
            } catch (NumberFormatException e) {}
    }
%>
<%!
    private String checkMySession(BigDecimal s, BigDecimal userSession) {
        if (s.equals(userSession)) {
            return " id=\"my_session\" ";
        }
        return " ";
    }
%>
<%
    String searchStatus = request.getParameter("search_status");
    if (searchStatus == null || "".equals(searchStatus))
        searchStatus = "active";
%>
<head>
    <title>Sessions control</title>
</head>
<form action="sessions.jsp" method="post">
    <%
        if (searchStatus.equals("active")) {
    %>
    <input type="submit" value="drop sessions"/>
    <a href="sessions.jsp?search_status=disable">Go to error sessions</a>
    <% }
    if (searchStatus.equals("disable")) {
        %>
    <input type="hidden" value="disable" name="search_status"/>
    <input type="submit" value="back to normal"/>
    <a href="sessions.jsp?search_status=active">Go to normal sessions</a>
    <% } %>
    <table class="s-table">
        <tr>
            <td><input type="checkbox" onchange="setAllCheckboxes(this);" id="main-checkbox"/></td>
            <td>IP address</td>
            <td>Date</td>
            <td>Info</td>
            <%

                if ("active".equals(searchStatus)) {
            %>
            <td>Last activity</td>
            <%
                } else if ("disable".equals(searchStatus)) {
            %>
            <td>Error attempt</td>
            <% } %>
        </tr>
        <%
            List<Object[]> list = null;
            if ("active".equals(searchStatus)) {
                list = SessionTool.getActiveSessions();
            }
            if ("disable".equals(searchStatus)) {
                list = SessionTool.getDisabledSessions();
            }
            if (list != null)
            for (Object[] s : list) {
                %>
        <tr class="s-tr" <%=checkMySession((BigDecimal)s[0], userSession)%>>
            <td class="s-td"><input type="checkbox" class="session-checkbox" name="<%=s[0]%>" onchange="selectString(this); checkAllCheckboxes();"/></td>
            <td class="s-td"><%=s[1]%></td>
            <td class="s-td"><%=s[2]%></td>
            <td class="s-td"><%=s[3]%></td>
            <td class="s-td"><%=s[4]%></td>
        </tr>
        <%
            }
        %>
    </table>
</form>
<%@ include file="footer.jsp" %>