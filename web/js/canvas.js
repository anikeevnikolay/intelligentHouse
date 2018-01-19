var k = 0, i = 0;
var xShift = 0, yShift = 0, shift = 10;
var fontColor = 'green';
window.onload = function(){
    /**
     * fill lines by points
     */
    for (i = 0; i < lines.length; i++) {
        var startP = points[lines[i].start - 1];
        var endP = points[lines[i].end - 1];
        lines[i] = {
            start: startP,
            end: endP
        }
    }
    draw();
}
window.addEventListener("resize", function(){draw(img)});

function draw() {

    var canvas = document.getElementById("c");
    canvas.width = window.innerWidth - 200;
    canvas.height = window.innerHeight - 200;

    fontColor = document.getElementById('selectColor').value;

    /**
     * find optimal start size and position
     */
    if (k == 0) {
        var maxH = -10000, maxW = -10000;
        var minH = 10000, minW = 10000;
        for (i = 0; i < points.length; i++) {
            if (points[i].y < minH) {
                minH = points[i].y;
            }
            if (points[i].y > maxH) {
                maxH = points[i].y;
            }
            if (points[i].x > maxW) {
                maxW = points[i].x;
            }
            if (points[i].x < minW) {
                minW = points[i].x;
            }
        }
        k = canvas.height / (maxH - minH);
        xShift = (canvas.width - (maxW - minW) * k) / 2;
        yShift = 0;
        if (k > canvas.width / (maxW - minW)) {
            xShift = 0;
            k = canvas.width / maxW;
            yShift = (canvas.height - (maxH - minH) * k) / 2;
        }
    }
    /**
     *work with canvas tag
     * @type {CanvasRenderingContext2D}
     */
    ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "rgb(0,0,0)";
    ctx.globalAlpha = 2;
    i = 0;
    var j = 0;

    /**
     * print lines
     */
    for (i = 0; i < lines.length; i++) {
        ctx.beginPath();
        ctx.moveTo(lines[i].start.x * k + xShift, lines[i].start.y * k + yShift);
        ctx.lineTo(lines[i].end.x * k + xShift, lines[i].end.y * k + yShift);
        ctx.closePath();
        ctx.stroke();
    }
    /**
     * print devices
     */
    for (i = 0; i < devices.length; i++) {
        drawCircle(ctx, devices[i].x * k + xShift, devices[i].y * k + yShift, 1);
        drawStroked(ctx, devices[i]);
    }
}
/**
 * draw stroked text;
 * font is const
 * @param ctx - 2d context
 * @param device - structure with: name, (x,y), data - params from device
 */
function drawStroked(ctx, device) {
    var saveLineWidth = ctx.lineWidth;
    var saveFont = ctx.font;
    var saveStokeStyle = ctx.strokeStyle;
    var saveFillStile = ctx.fillStyle;

    ctx.font = "16px Arial";
    ctx.strokeStyle = fontColor;
    ctx.lineWidth = 4;
    var text = device.name;
    var x = device.x * k + xShift;
    var y = device.y * k + 20 + yShift;
    var data = device.data;
    ctx.strokeText(text, x, y);
    ctx.fillStyle = 'white';
    ctx.fillText(text, x, y);
    ctx.font = "12px Arial";
    ctx.fillStyle = fontColor;
    ctx.fillText(data, x, y + 25);

    ctx.lineWidth = saveLineWidth;
    ctx.font = saveFont;
    ctx.strokeStyle = saveStokeStyle;
    ctx.fillStyle = saveFillStile;
}

function drawCircle(ctx, x, y, r) {
    var saveFillStile = ctx.fillStyle;
    var saveStokeStyle = ctx.strokeStyle;

    ctx.fillStyle = fontColor;
    ctx.beginPath();
    ctx.arc(x, y, 5, 0, 2 * Math.PI);
    ctx.fill();

    ctx.fillStyle = 'white';
    ctx.beginPath();
    ctx.arc(x, y, 2, 0, 2 * Math.PI);
    ctx.fill();

    ctx.strokeStyle = saveStokeStyle;
    ctx.fillStyle = saveFillStile;
}
function plusScale() {
    k *= 1.1;
    draw();
}
function minusScale() {
    k *= 0.9;
    draw();
}

var KEY_CODE = {
    LEFT: 37,
    UP: 38,
    RIGHT: 39,
    DOWN: 40,
    PLUS: 107,
    MINUS: 109,
    ZERO: 96
};
function processKey(e) {
    switch (e.keyCode) {
        case KEY_CODE.LEFT:
        {
            xShift += shift;
            draw();
            break;
        }
        case KEY_CODE.UP:
        {
            yShift += shift;
            draw();
            break;
        }
        case KEY_CODE.RIGHT:
        {
            xShift -= shift;
            draw();
            break;
        }
        case KEY_CODE.DOWN:
        {
            yShift -= shift;
            draw();
            break;
        }
        case KEY_CODE.PLUS:
        {
            plusScale();
            break;
        }
        case KEY_CODE.MINUS:
        {
            minusScale();
            break;
        }
        case KEY_CODE.ZERO:
        {
            k = 0;
            draw();
            break;
        }
    }
}
window.addEventListener('keydown', processKey, false);