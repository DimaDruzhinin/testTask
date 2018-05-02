function doFilter() {
    var minId = document.getElementById("minId").value;
    var maxId = document.getElementById("maxId").value;
    if (minId > maxId && maxId !== "") {
        alert("TO field must be more than FROM field");
        return;
    }
    //отрицательные значения характеризуют отсутствие ограничения.
    if (minId === "") {
        minId = -1;
    }
    if (maxId === "") {
        maxId = -1;
    }
    var userFilter = {};
    userFilter.minId = minId;
    userFilter.maxId = maxId;
    var xhr = new XMLHttpRequest();
    xhr.open("POST", location.origin + "/task/user/get", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log("Before sending")
    xhr.send(JSON.stringify(userFilter));
    xhr.onload = function () {
        var response = JSON.parse(xhr.responseText);
        console.dir(response);
        if (response.status == "SUCCESS") {
            buildUserTable(response.users)
        } else {
            alert("Try later");
        }
    }

}

function buildUserTable(users) {
    var rows = "";
    for (var i = 0; i < users.length; i++) {
        var user = users[i];
        rows += "<tr><td>" + user.id + "</td><td>" + user.name + "</td></tr>";
    }
    document.getElementById("users").innerHTML = rows;
}