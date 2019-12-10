let isFormNotChecked = true;

window.onload = function (ev) {

    if (playerName == null || playerSub == null) {
        displayError("Player could not be found. You are in wrong page. Will redirect you to login screen in 5 seconds")
    } else {
        document.getElementById("namePlaceHolder").innerHTML = playerName;
    }

    getPlayersOnline();
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
        setTimeout(getPlayersOnline, 5000);
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
    let entry;

    console.log(playerSateResponse);
    entry = playerSateResponse;

    document.getElementById('playerForm').innerHTML = "";
    for (let index in entry) {
        document.getElementById('playerForm').innerHTML += "<div><input type='checkbox' name='players' id= 'c" + index + "' value = '" + entry[index] + "' onchange='toggleCheckbox()' />" + entry[index] + "</div>";
        console.log("c" + index + "");
    }
}

function display(msg, index) {
    document.getElementById('myForm').innerHTML += "<div id='checked'><input type='checkbox' id= '" + index + "' value = '" + msg + "' />" + msg + "</div>";
    if (document.getElementById("checked").checked === true) {
        console.log('its checked');
    }
}

function toggleCheckbox() {
    isFormNotChecked = false;
    console.log("its checked");
    checkboxlimit(document.forms.playerForm.players, 2);
    //element.checked = !element.checked;
}

function checkboxlimit(checkgroup, limit) {
    for (let i = 0; i < checkgroup.length; i++) {
        checkgroup[i].onclick = function () {
            let checkedcount = 0;
            for (var i = 0; i < checkgroup.length; i++)
                checkedcount += (checkgroup[i].checked) ? 1 : 0;
            if (checkedcount > limit) {
                alert("You can only select a maximum of " + limit + " players");
                this.checked = false
            }
        }
    }
}

/*function sendInvite() {

  const url = param + 'invite';
  let xhr = new XMLHttpRequest();
  let playerInviteMessage = new PlayerInviteMessage(playerSub);
  console.log(playerInviteMessage);
  xhr.onreadystatechange = function () {
      if (xhr.readyState === 4) {
          try {
              if (xhr.status == 200) {
                  console.log(playerInviteMessage);
              } else {
                  throw "something went wrong";
              }
          } catch (err) {
              console.log(err.message + " in " + xhr.responseText);
              displayError("Something went wrong. Contact the developers please ");
          }
  };
  xhr.open('POST', url);
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.send(JSON.stringify(playerInviteMessage));
}
}*/

function displayError(error) {

}
