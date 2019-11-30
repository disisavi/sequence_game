window.onload = function (ev) {
    if (playerName == null || playerSub == null) {
        displayError("Player could not be found. You are in wrong page. Will redirect you to login screen in 5 seconds")
    } else {
        document.getElementById("namePLaceHolder").innerHTML = playerName;
    }
    getPlayersOnline();
};


function getPlayersOnline() {
    const url = param + 'getPlayers';
    let xhr = new XMLHttpRequest();

    let playerInviteMessage = new PlayerInviteMessage(playerSub);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            try {
                if (ajaxRequest.status == 200) {
                    let playerMap = generatePlayerMap(xhr.responseText);
                    // drawForm(playerMap)
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

//
// function drawForm(playerMap) {
//
//
// <div class="form-check">
//         <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1"
//     checked>
//     <label class="form-check-label" for="exampleRadios1">
//         Default radio
//     </label>
//     </div>
// }

function displayError(error) {

}
