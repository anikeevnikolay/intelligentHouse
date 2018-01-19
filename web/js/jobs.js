/**
 * Created by antiz_000 on 2/22/2016.
 */

window.onload = init;

function init() {
    minutesToString();
    selectHideDivs();
}

function minutesToString() {
    var arr = document.getElementsByClassName('minutes');
    for (var i = 0; i < arr.length; i++) {
        var time = +arr[i].firstChild.nodeValue;
        if (isNaN(time)) {
            arr[i].firstChild.nodeValue = 'never';
            continue;
        }
        var result = '';
        var minus = false;
        if (time < 0) {
            minus = true;
            time *= -1;
        }
        if (time > 7 * 24 * 60) {
            if (Math.floor(time / (7 * 24 * 60)) == 1)
                result +='1 week ';
            else
                result += Math.floor(time / (7 * 24 * 60)) + ' weeks ';
            time %= (7 * 24 * 60);
        }
        if (time > 24 * 60) {
            if (Math.floor(time / (24 * 60)) == 1)
                result += '1 day ';
            else
                result += Math.floor(time / (24 * 60)) + ' days ';
            time %= (24 * 60);
        }
        if (time > 60) {
            if (Math.floor(time / 60) == 1)
                result += '1 hour ';
            else
                result += Math.floor(time / 60) + ' hours ';
            time %= 60;
        }
        if (time == 1)
            result += '1 minute';
        else
            result += time + ' minutes';
        if (minus)
            result += ' ago';
        arr[i].firstChild.nodeValue = result;
    }
}

function selectHideDivs() {
    var arr = document.getElementsByClassName('hide-div');
    for (var i = 0; i < arr.length; i++) {
        var td = arr[i].parentNode;
        td.style.borderStyle = 'dotted';
        td.style.borderWidth = '1px';
        td.style.backgroundColor = 'white';
    }
}

function changeHideOptions(td) {
    var elem = td.firstChild;
    if (elem.classList.contains('hide-div')) {
        elem.classList.remove('hide-div');
        elem.classList.add('normal-div');
        td.style.borderWidth = '0px';
        td.style.padding = '1px';
    } else {
        elem.classList.add('hide-div');
        elem.classList.remove('normal-div');
        td.style.borderStyle = 'dotted';
        td.style.borderWidth = '1px';
    }
}