<%@ page import="databaseTools.CanvasTool" %>
<%@ include file="header.jsp" %>
<style><%@include file="css/canvas.css"%></style>
<script>
    <%=CanvasTool.getSchema()%>
</script>
<script src="js/canvas.js"></script>
<head>
    <title>Home schema</title>
</head>
        <div style="text-align: center;">
            <canvas id="c">
                <p>Браузер не поддерживает функцию Canvas</p>
            </canvas>
        </div>
        <div style="text-align: center;">
            <input type="button" onclick="minusScale()" value="-"/>
            <input type="button" onclick="plusScale()" value="+"/>
            <input type="button" onclick="k = 0; draw()" value="reset"/>
            <select onchange="fontColor = this.value; draw();" id="selectColor">
                <option>red</option>
                <option>green</option>
                <option>blue</option>
                <option>black</option>
                <option>orange</option>
            </select>
        </div>
        <div class="keys-container">
            Use keys
            <div class="key">&#8592</div>
            <div class="key">&#8593</div>
            <div class="key">&#8594</div>
            <div class="key">&#8595</div>
            to move schema,
            <div class="key">&#43</div>
            <div class="key">&#8722</div>
            for scaling and
            <div class="key">0</div>
            for reset to default size
        </div>
<%@ include file="footer.jsp" %>