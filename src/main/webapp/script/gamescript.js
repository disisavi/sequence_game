let allPlayersJoined = false;

function loadData() {
    const url = param + 'getBoard';
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            printBoard(xhr.responseText);
        }
    };
    xhr.open('POST', url);
    xhr.send(playerSub);

    setTimeout(loadData, 1000);
}

function getPlayerData() {
    const url = param + 'getPlayerData';
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            printPlayerInfo(xhr.responseText);
        }
    };
    xhr.open('POST', url);
    xhr.send(playerSub);
}

function printBoard(outputJsonString) {
    document.getElementById("parentGrid").innerHTML = "";
    let cell = JSON.parse(outputJsonString);

    document.getElementById("parentGrid").innerHTML += "<div class = 'row seq-row' id = 'row" + 0 + "'>";
    printTurn(cell.currentPlayerSub, cell.currentPlayer);
    let id = "row" + 0;
    for (let i in cell.boardStateSnapShot) {
        let chip = "";
        if (cell.boardStateSnapShot[i].chip !== null) {
            chip = cell.boardStateSnapShot[i].chip;
        }
        document.getElementById(id).innerHTML += "<div class='seq-card col' style='color: " + chip + "' ><div>" + cell.boardStateSnapShot[i].cellType.cardType + "</div><div> " + cell.boardStateSnapShot[i].cellType.cardValue + "</div><div>ID - " + calculateCellNumber(i) + "</div></div>";
        if (i > 0 && ((parseInt(i) + 1) % 10) === 0) {
            let nextID = "row" + i;
            document.getElementById(id).innerHTML += "</div>";
            document.getElementById("parentGrid").innerHTML += "<div class = 'seq-row row' id = '" + nextID + "'>";
            id = nextID;
        }
    }
}

function printPlayerInfo(outputJsonString) {
    document.getElementById("playerInfo").className = "";
    document.getElementById("playerInfo").innerHTML = "";
    let cell = JSON.parse(outputJsonString);

    document.getElementById("playerInfo").innerHTML += "<div class = 'row seq-row' id = 'rowPlayer'>";
    let cardList = cell.cardsList;
    for (let i in cardList) {
        document.getElementById("rowPlayer").innerHTML += "<div class='seq-player-card col'  ><div>" + cardList[i].cardType + "</div><div> " + cardList[i].cardValue + "</div><div>ID - " + i + "</div></div>";
    }
    document.getElementById("playerInfo").innerHTML += "</div>";
    document.getElementById("playerInfo").innerHTML += "<div class = 'row seq-row' id = 'rowPlayer2'>";
    document.getElementById("rowPlayer2").innerHTML += "<div class='seq-player-card col'> Your chip color is " + cell.chip + "</div>";
    document.getElementById("rowPlayer2").innerHTML += "<div class='seq-player-card col'> Number of chips " + cell.numberChipsAvailable + "</div>";
}

function printTurn(remotePlayerSub, remotePlayerName) {
    let className = "";
    document.getElementById("turnPlaceHolder").className = "";
    document.getElementById("turnPlaceHolder").innerHTML = "";
    if (remotePlayerSub === playerSub) {
        document.getElementById("turnPlaceHolder").innerHTML = "<h4 class='alert-heading'>Your Turn!!</h4>";
        className = "alert-danger";
    } else {
        document.getElementById("turnPlaceHolder").innerHTML = "<h4 class='alert-heading'>" + remotePlayerName + "'s Turn</h4>";
        className = "alert-success";
    }
    document.getElementById("turnPlaceHolder").classList.add("alert");
    document.getElementById("turnPlaceHolder").classList.add(className);
}


function calculateCellNumber(cellNumber) {
    let cellString = cellNumber.toString();
    if (cellString.length === 1) {
        let tempString = cellString;
        cellString = "0";
        cellString = cellString + "," + tempString;
    } else {
        cellString = cellString.slice(0, 1) + "," + (cellString.slice(1, 2));
    }
    return cellString;
}

function submitInviteMessage() {
    if (allPlayersJoined) {
        let errorID = "error";
        let cellid = document.getElementById("IDform").value;
        let cardIndex = document.getElementById("Cindexform").value;
        let errorInForm = false;

        if (cellid == null || cellid.length === 0) {
            document.getElementById(errorID).innerHTML += "<div>Please enter cell number</div>";
            errorInForm = true;
        } else if (cellid.charAt(1) !== ',' || cellid.length !== 3) {
            document.getElementById(errorID).innerHTML += "<div>Please enter cellID in X,Y format as displayed in the board</div>";
            errorInForm = true
        }

        if (cardIndex == null || cardIndex.length === 0) {
            document.getElementById(errorID).innerHTML += "<div>Please enter Card Index</div>";
            errorInForm = true;
        } else if (cardIndex.length !== 1) {
            document.getElementById(errorID).innerHTML += "<div>Please enter card number as a single digit as displayed</div>";
            errorInForm = true
        }
        if (!errorInForm) {
            //Construction of message begins;
            let playerMove = new PlayerMove(playerSub);
            try {
                playerMove.x = parseInt(cellid.charAt(0));
                playerMove.y = parseInt(cellid.charAt(2));
                playerMove.cardIndex = parseInt(cardIndex);
                sendPlayerMove(playerMove);
                document.getElementById(errorID).innerHTML = "";
            } catch (ex) {
                console.log(ex);
                document.getElementById(errorID).innerHTML = "<div>Please use numbers only</div>";
            }
        }
    } else {
        displayError("All PLayers habvent joined yet. You can only start the game once everyone joins", true)
    }
    return false;
}

function sendPlayerMove(playerMove) {
    const url = param + 'move';
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            if (xhr.status === 500) {
                displayError(xhr.responseText, true);
            } else if (xhr.status === 200) {
                displayError("", true);
            }
            getPlayerData();
        }
    };
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(playerMove));
}

function allPlayersOnlineCheck() {
    const url = param + 'playersJoined';
    let playerInviteMessage = new PlayerInviteMessage(playerSub);
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            console.log(xhr.status);
            if (xhr.status === 500) {
                displayError(xhr.responseText, true);
            } else if (xhr.status === 200) {
                displayError("", true);
                allPlayersJoined = (xhr.responseText === "true");
                if (allPlayersJoined) {
                    clearTimeout(allPlayersOnlineCheck);
                }
            }
        }
    };
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(playerInviteMessage));
    setTimeout(allPlayersOnlineCheck, 1000);
}


function displayError(message, clearMessage = false) {
    let errorID = "displayError";
    if (clearMessage) {
        document.getElementById(errorID).innerHTML = "";
    }
    document.getElementById(errorID).innerHTML += "<div>" + message + "</div>";
}

window.onload = function () {
    loadData();
    getPlayerData();
    allPlayersOnlineCheck();
};
