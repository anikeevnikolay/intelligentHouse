var abbreviation = [];

window.onload = init;

function init() {
    abbreviation.push({key: 'sf', value: 'select * from '});
    abbreviation.push({key: 'sp', value: 'select * from points where id = '});
    abbreviation.push({key: 'sl', value: 'select * from lines where id = '});
    abbreviation.push({key: 'sse', value: 'select * from sessions where id = '});
    abbreviation.push({key: 'sst', value: 'select * from statuses where id = '});
    abbreviation.push({key: 'sd', value: 'select * from devices where id = '});
    abbreviation.push({key: 'sip', value: 'select * from ip_black_list where ip = '});
    abbreviation.push({key: 'p', value: 'points '});
    abbreviation.push({key: 'd', value: 'delete '});
    abbreviation.push({key: 'de', value: 'devices '});
    abbreviation.push({key: 'l', value: 'lines '});
    abbreviation.push({key: 'w', value: 'where '});
    abbreviation.push({key: 'f', value: 'from '});
    abbreviation.push({key: 's', value: 'select '});
    abbreviation.push({key: 'ob', value: 'order by '});
    abbreviation.push({key: 'gb', value: 'group by '});
    abbreviation.push({key: 'lj', value: 'left join '});
    abbreviation.push({key: 'j', value: 'join '});
    abbreviation.push({key: 'se', value: 'sessions '});
    abbreviation.push({key: 'st', value: 'statuses '});

    var hints = "Type SQL query\nAvailable abbreviations:\n";
    for (var i = 0; i < abbreviation.length; i++) {
        var a = abbreviation[i];
        hints += a.key + ' : ' + a.value + '\n';
    }
    var textArea = document.getElementById("query");
    textArea.placeholder = hints;
    restoreStartPoint();
}

function restoreStartPoint() {
    var textArea = document.getElementById("query");
    textArea.selectionStart = textArea.selectionEnd = +document.getElementById("start-point").value;
}

function saveStartPoint() {
    document.getElementById('start-point').value = document.getElementById('query').selectionStart;
}

function filterInput(textArea, event) {
    /**
     * "f8" override
     */
    if (event.keyCode == 119) {
        saveStartPoint();
        document.forms['form'].submit();
    }
    /**
     * "enter" override
     */
    if (event.keyCode == 13) {
        var v = textArea.value, start = textArea.selectionStart, end = textArea.selectionEnd;
        var newStr = '\n';
        var tmpStr = v.substring(0, start);
        var currStr = v.substring(tmpStr.lastIndexOf('\n') + 1, start);
        var i = 0;
        while (i < currStr.length) {
            if (currStr[i] == ' ')
                newStr += ' ';
            else if (currStr[i] == '\t')
                newStr += '\t';
            else
                break;
            i++;
        }
        textArea.value = v.substring(0, start) + newStr + v.substring(end);
        textArea.selectionStart = textArea.selectionEnd = start + 1 + i;
        event.preventDefault();
    }
    /**
     * "tab" override
     */
    if (event.keyCode == 9) {
        var v = textArea.value, start = textArea.selectionStart, end = textArea.selectionEnd;
        var isFound = false;
        for (var i = 0; i < abbreviation.length; i++) {
            var a = abbreviation[i];
            if (v.substring(start - a.key.length, start) == a.key) {
                textArea.value = v.substring(0, start - a.key.length) + a.value + v.substring(end);
                textArea.selectionStart = textArea.selectionEnd = start + a.value.length - a.key.length;
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            textArea.value = v.substring(0, start) + '\t' + v.substring(end);
            textArea.selectionStart = textArea.selectionEnd = start + 1;
        }
        event.preventDefault();
    }
}
/**
 * defence from system reaction on "enter"
 */
function doubleFilterInput(textArea, event) {
    if (event.keyCode == 13) {
        event.preventDefault();
    }
}
/**
 * return text from java's ""
 */
function convertJavaToText() {
    var elem = document.getElementById('query');
    var arr = elem.value.split('\n');
    var result = '';
    for (var i = 0; i < arr.length; i++) {
        var startPattern = /([\s\t])*"(.*)/g;
        var endPattern = /(.*)"([\s\t])*[\+;]/g;
        var uselessNPattern = /(.*)\\n/g;
        //alert('"' + arr[i].split(pattern)[2] + '"');
        if (startPattern.test(arr[i])) {
            arr[i] = arr[i].split(startPattern)[2];
        }
        if (endPattern.test(arr[i])) {
            arr[i] = arr[i].split(endPattern)[1];
        }
        if (uselessNPattern.test(arr[i])) {
            arr[i] = arr[i].split(uselessNPattern)[1];
        }
        result += arr[i] + '\n';
    }
    elem.value = result;
    var textArea = document.getElementById("query");
    textArea.focus();
}
function idsBacklight() {
    $.get("getNameByIdBackend.jsp",{text:  $('#query').val()}, setBacklightedIds, "html");
}

function setBacklightedIds(forecastData) {
    $('#query').val(forecastData);
}