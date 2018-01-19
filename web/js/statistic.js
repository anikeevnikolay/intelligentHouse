/**
 * Created by antiz_000 on 3/5/2016.
 */
window.onload = function() {
    var cx = document.getElementById('demo').getContext("2d");
    printDiagram(cx, parsStr(data), name, type);
}

function editWidth(newW) {
    var cx = document.getElementById('demo').getContext("2d");
    cx.canvas.width = newW;
    printDiagram(cx, parsStr(data), name, type);
}

function editHeight(newH) {
    var cx = document.getElementById('demo').getContext("2d");
    cx.canvas.height = newH;
    printDiagram(cx, parsStr(data), name, type);
}