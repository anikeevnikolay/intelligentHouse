var user = detect.parse(navigator.userAgent);
window.onload = function() {
    var text = user.browser.family + ' v.' + user.browser.version + ' in ' + user.os.name + '; ' + user.device.name +  ' device (' + user.device.type + ') ';
    document.getElementById('info').value = text;
}