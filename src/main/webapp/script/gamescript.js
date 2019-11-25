function loadData() {
    var url = param + 'getBoard';
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            printBoard(xhr.responseText);
        }
    };
    xhr.open('GET', url);
    xhr.send();
}

function printBoard(outputJsonString) {
    var cell = JSON.parse(outputJsonString);

    document.getElementById("parentGrid").innerHTML += "<div class = 'row seq-row' id = 'row" + 0 + "'>";
    var i;
    var id = "row" + 0;
    for (i in cell.boardStateSnapShot) {
        document.getElementById(id).innerHTML += "<div class='seq-card col'  ><div>" + cell.boardStateSnapShot[i].cellType.cardType + "</div><div> " + cell.boardStateSnapShot[i].cellType.cardValue + "</div></div>";
        if (i > 0 && ((parseInt(i) + 1) % 10) === 0) {
            console.log(i);
            var nextID = "row" + i;
            document.getElementById(id).innerHTML += "</div>";
            document.getElementById("parentGrid").innerHTML += "<div class = 'seq-row row' id = '" + nextID + "'>";
            id = nextID;
        }
    }
}

window.onload = function () {
    loadData();
};
