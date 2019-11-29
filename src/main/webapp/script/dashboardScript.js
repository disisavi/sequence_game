window.onload = function (ev) {
    if (playerName == null) {
        displayError("Player could not be found. You are in wrong page. Will redirect you to login screen in 5 seconds")
    } else {
        document.getElementById("namePLaceHolder").innerHTML = playerName;
    }
getPlayersOnline();
};


function getPlayersOnline() {
    const url = param + 'getPlayers';
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            let playerMap = generatePlayerMap(xhr.responseText);
            // drawForm(playerMap)
        }
    };
    xhr.open('GET', url);
    xhr.send();
}

function generatePlayerMap(response) {
    let responseJson = JSON.parse(response)
    let playerInfo = new PlayerInfoMessage(responseJson);

    return playerInfo.playerSateMap();
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
