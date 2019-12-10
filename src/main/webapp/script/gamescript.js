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
        document.getElementById(id).innerHTML += "<div class='seq-card col'  ><div>" + cell.boardStateSnapShot[i].cellType.cardType + "</div><div> " + cell.boardStateSnapShot[i].cellType.cardValue + "</div><div>ID - " + calculateCellNumber(i) + "</div></div>";
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

window.onload = function () {
    loadData();
    getPlayerData();
};
