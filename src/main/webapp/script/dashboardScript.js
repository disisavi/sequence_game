let isFormNotChecked = true;

window.onload = function (ev) {
    var interval;
    if (playerName == null || playerSub == null) {
        displayError("Player could not be found. You are in wrong page. Will redirect you to login screen in 5 seconds")
    } else {
        document.getElementById("namePlaceHolder").innerHTML = playerName;
    }
    interval = setInterval(getPlayersOnline, 5000);
};


function getPlayersOnline() {
    if(isFormNotChecked){
    const url = param + 'getPlayers';
    let xhr = new XMLHttpRequest();

    let playerInviteMessage = new PlayerInviteMessage(playerSub);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            try {
                if (xhr.status == 200) {
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
var entry;
var name;
var value;
 entry = playerSateResponse;
 let index = 0;
 document.getElementById('myForm').innerHTML = "";
 for(name in entry){
     value = display(name,index);
     index++;
 }
     
 }
 function display(msg,index) {
    document.getElementById('myForm').innerHTML += "<div><input type='checkbox' id= '"+index+"' value = '"+msg+"' />"+msg+"</div>";  
  }

function displayError(error) {

}
