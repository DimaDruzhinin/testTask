function create() {
    console.log("create()");
    var name = document.getElementById("createInput").value;
    if (name === "") {
        alert("The length of the name must be at least one character");
        return;
    }
    var xhr = new XMLHttpRequest();
    xhr.open("POST", location.origin + "/task/user/create", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    console.log("Before sending");
    var user = {};
    user.name = name;
    xhr.send(JSON.stringify(user));
    xhr.onload = function () {
        var response = JSON.parse(xhr.responseText);
        if (response.status == "INTERNAL_ERROR") {
            alert("Try later");
        } else if (response.status == "USER_ALREADY_EXISTS") {
            alert("Perhaps the user with the same name already exists. Try again");
            location.reload(true);
        } else if (response.status == "SUCCESS") {
            alert("User created");
            location.reload(true);
        }
    }

    xhr.onerror = function () {
        alert("Try later");
    }
}

function transitionToList() {
    document.location.href = document.location.origin + "/task/list"
}