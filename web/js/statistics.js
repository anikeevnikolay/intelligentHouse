window.onload = function() {
    for (var i = 0; i < rawResult.length; i++) {
        var cx = document.getElementById(i).getContext("2d");
        printDiagram(cx, parsStr(rawResult[i]), names[i], types[i]);
    }
}

var rawResult = [];
var names = [];
var types = [];

function parsStr(str) {
    var arr = str.split(';');
    var records = [];
    for (var i = 0; i < arr.length; i++) {
        var record = arr[i].split(',');
        var rec = {
            name: record[0],
            count: +record[1],
            color: record[2]
        }
        records.push(rec);
    }
    return records;
}

function printDiagram(cx, results, name, type) {
    switch (type) {
        case 'radial': {
            printRadialDiagram(cx, results, name);
            break;
        }
        case 'bar': {
            printBarDiagram(cx, results, name)
            break;
        }
    }
}

function printBarDiagram(cx, results, name) {
    var max = results[0].count, min = results[0].count;
    results.forEach(function(result) {
        if (min > result.count)
            min = result.count;
        if (max < result.count)
            max = result.count;
    });
    max += 0.1 * (max-min);
    min -= 0.1 * (max-min);
    var w = cx.canvas.width;
    var h = cx.canvas.height;
    var tab = 40, width = (w - 2 * tab) / results.length / 2;

    var headFontSize = Math.floor(h / 10);
    var hShift = headFontSize + 10;
    h -= hShift;
    cx.font = headFontSize + "px Arial";
    cx.fillText(name, 10, hShift - 15);

    cx.beginPath();
    cx.moveTo(tab, 10 + hShift);
    cx.lineTo(tab, h - tab + hShift);
    cx.lineTo(w - tab, h - tab + hShift);
    cx.stroke();
    cx.font = "14px Arial";
    for (var j = results.length; j > 0; j--) {
        var i = j - 1;
        cx.beginPath();
        var heightAbs = results[i].count - min;
        var heightRel = (h - tab - 10) * (heightAbs / (max - min));
        cx.rect(tab + i * 2 * width + width / 2, h - tab - heightRel + hShift, width, heightRel - 2);
        cx.stroke();
        cx.fillStyle = results[i].color != undefined ? results[i].color : 'gray';
        cx.fill();
        cx.fillStyle = 'black';
        cx.fillText(results[i].name, tab + i * 2 * width + width / 2, h - tab / 2 + hShift);
        cx.fillText(results[i].count, 5, h - tab - heightRel + hShift);
        var dashList = [12, 12];
        cx.setLineDash(dashList);
        cx.beginPath();
        cx.moveTo(tab, h - tab - heightRel + hShift);
        cx.lineTo(tab + i * 2 * width + width / 2, h - tab - heightRel + hShift);
        cx.stroke();
        cx.setLineDash([]);
    }
}

function printRadialDiagram(cx, results, name) {
    var w = cx.canvas.width;
    var h = cx.canvas.height;
    var minSide = w > h ? h : w;
    var fontSize = Math.floor(minSide / 4 / results.length);
    var headFontSize = fontSize * 2 > 60 ? 60 : fontSize * 2;
    var hShift = headFontSize + 10;
    h -= hShift;
    cx.font = headFontSize + "px Arial";
    cx.fillText(name, 10, hShift-15);
    //cx.fillText(name, 10, 20);
    minSide = w > h ? h : w;
    var radius = Math.floor(minSide / 2);
    var total = results.reduce(function(sum, choice) {
        return sum + choice.count;
    }, 0);
    var currentAngle = -0.5 * Math.PI;
    results.forEach(function(result) {
        var sliceAngle = (result.count / total) * 2 * Math.PI;
        cx.beginPath();
        cx.arc(radius, radius + hShift, radius, currentAngle, currentAngle + sliceAngle);
        currentAngle += sliceAngle;
        cx.lineTo(radius, radius + hShift);
        cx.fillStyle = result.color;
        cx.fill();
    });
    var rectH = minSide / results.length;
    var x = minSide + rectH / 2, y = rectH / 4 + hShift;
    if (minSide == w) {
        y = minSide + 20 + hShift;
        x = 20;
    }

    cx.font = fontSize + "px Arial";

    results.forEach(function(result) {
        cx.fillStyle = result.color;
        cx.fillRect(x, y, rectH, rectH / 2.5);
        cx.fillStyle = "black";
        cx.fillText(result.name + ' (' + result.count + ')', x + rectH * 1.1, y + rectH / 4);
        y += rectH;
    });
}