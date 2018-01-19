function setAllCheckboxes(checkbox) {
    var arr = document.getElementsByClassName("session-checkbox");
    for (var i = 0; i < arr.length; i++) {
        arr[i].checked = checkbox.checked;
        selectString(arr[i]);
    }
}

function checkAllCheckboxes() {
    var arr = document.getElementsByClassName("session-checkbox");
    var mainCheckbox = document.getElementById('main-checkbox');
    var status = arr[0].checked;
    for (var i = 1; i < arr.length; i++) {
        if (arr[i].checked != status) {
            mainCheckbox.checked = false;
            return;
        }
    }
    mainCheckbox.checked = status;
}

function selectString(chkbx) {
    var arr = chkbx.parentElement.parentElement.children;
    for (var i = 0; i < arr.length; i++) {
        if (chkbx.checked) {
            if (arr[i].parentElement.id == 'my_session') {
                arr[i].style.color = 'red';
                arr[i].style.backgroundColor = 'black';
            } else {
                arr[i].style.color = 'white';
                arr[i].style.backgroundColor = 'black';
            }

        } else {
            arr[i].style.color = 'black';
            if (arr[i].parentElement.id == 'my_session') {
                arr[i].style.backgroundColor = 'red';
            } else {
                arr[i].style.backgroundColor = 'white';
            }
        }
    }
}

window.onload = function () {
    var arr = document.getElementsByClassName("session-checkbox");
    for (var i = 0; i < arr.length; i++) {
        selectString(arr[i]);
    }
}