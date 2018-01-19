/**
 * Created by antiz_000 on 2/24/2016.
 */
window.onload = function() {
    minutesToString();
    var arr = document.getElementsByClassName('job-params');
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].tagName == 'TABLE') {
            arr[i].classList.add('j-table');
            var trs = arr[i].rows;
            for (var j = 0; j < trs.length; j++) {
                var tds = trs[j].cells;
                for (var k = 0; k < tds.length; k++) {
                    tds[k].classList.add('bordered');
                }
            }
        }
    }
}