let isFormNotChecked = true;
let selectedPlayer = [];
let isInvited = false;

window.onload = function () {
    if (playerName == null || playerSub == null) {
        displayError("Player could not be found. You are in wrong page. Will redirect you to login screen in 5 seconds")
    } else {
        document.getElementById("namePlaceHolder").innerHTML = playerName;
    }
    getPlayersOnline();
    isPlayerInvited()
};


function getPlayersOnline() {
    if (isFormNotChecked) {
        const url = param + 'getPlayers';
        let xhr = new XMLHttpRequest();

        let playerInviteMessage = new PlayerInviteMessage(playerSub);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                try {
                    if (xhr.status === 200) {
                        let playerMap = generatePlayerMap(xhr.responseText);
                        drawForm(playerMap);
                    } else {
                        throw "something went wrong";
                    }
                } catch (err) {
                    console.log(err.message + " in " + xhr.responseText);
                    displayError("Something went wrong. Contact the developers please ");
                }
            }
        };
        xhr.open('POST', url);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(playerInviteMessage));
    }
    setTimeout(getPlayersOnline, 1000);
}

function isPlayerInvited() {

    const url = param + 'isInvited';
    let xhr = new XMLHttpRequest();

    let playerInviteMessage = new PlayerInviteMessage(playerSub);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            try {
                if (xhr.status === 200) {
                    isInvited = (xhr.responseText === "true");
                    if (isInvited) {
                        document.getElementById("invite").style.visibility = "visible";
                    }
                } else {
                    throw "something went wrong";
                }
            } catch (err) {
                console.log(err.message + " in " + xhr.responseText);
                displayError("Something went wrong. Contact the developers please ");
            }
        }
    };
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(playerInviteMessage));
    setTimeout(isPlayerInvited, 1000);
}


function generatePlayerMap(response) {
    let responseJson = JSON.parse(response)
    let playerInfo = new PlayerInfoMessage(responseJson);
    if (playerInfo.gotMessage) {
        return playerInfo.playerSateMap;
    } else {
        displayError(playerInfo.errorMessage)
    }
    throw playerInfo.errorMessage;
}


function drawForm(playerSateResponse) {
    let entry = playerSateResponse;
    let iterable = 0;
    document.getElementById('playerForm').innerHTML = "";
    for (let index in entry) {
        document.getElementById('playerForm').innerHTML += "<div><input type='checkbox' name='players' id= 'c" + index + "' value = '" + index + "' onchange='toggleCheckbox(this)'/>" + entry[index] + "</div>";
        iterable++;
    }
    if (iterable > 1) {
        document.getElementById("playerForm").innerHTML += "<button type = 'button' class = 'btn btn-primary btn-lg' id='submit' onclick = 'sendInvite()'>Submit</button>";
        document.getElementById("HeadingPlaceholder").innerHTML = " Please select two players below to invite for the next game"
    } else {
        document.getElementById("HeadingPlaceholder").innerHTML = " There are not enough players for us to play right now. Lets wait for more players to join"
    }
}


function toggleCheckbox(cb) {
    isFormNotChecked = false;
    if (cb.checked === true) {
        selectedPlayer.push(cb.value);
    } else {
        let index = selectedPlayer.indexOf(cb.id);
        selectedPlayer.splice(index, 1);
    }

    if (selectedPlayer.length > 2) {
        alert("You can only select a maximum of " + limit + " players");
    }
    if (selectedPlayer.length === 0) {
        isFormNotChecked = true;
    }
}


function sendInvite() {
    if (selectedPlayer.length !== 2) {
        alert("Please select 2 people to play with");
    }
    const url = param + 'invite';
    let xhr = new XMLHttpRequest();
    let playerInviteMessage = new PlayerInviteMessage(playerSub);
    selectedPlayer.forEach(elt => playerInviteMessage.playerStubsInvited.push(elt));
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            try {
                if (xhr.status === 200) {
                    if (xhr.responseURL) {
                        window.location.replace(xhr.responseURL);
                    } else {
                        throw "something went wrong";
                    }
                }
            } catch (err) {
                console.log(err.message + " in " + xhr.responseText);
                displayError("Something went wrong. Contact the developers please ");
            }
        }
    };

    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(playerInviteMessage));
    return false;
}

function displayError(error) {

}


//TODO -- On invite