console.log('It works!');
var i = 0;
var  str = {"boardVersionNumber":0,"isSequenceDone":false,"boardStateSnapShot":[{"chip":null,"cellType":{"cardType":"Generic","cardValue":"CornerPocket"}},
{"chip":null,"cellType":{"cardType":"Spades","cardValue":"Nine"}},
{"chip":null,"cellType":{"cardType":"Clubs","cardValue":"Five"}},
{"chip":null,"cellType":{"cardType":"Clubs","cardValue":"Ace"}},
{"chip":null,"cellType":{"cardType":"Spades","cardValue":"Nine"}},
{"chip":null,"cellType":{"cardType":"Generic","cardValue":"CornerPocket"}}],"playerWon":null};

for (snapshot in str.boardStateSnapShot)
{
    document.getElementById("demo").innerHTML += "<div class=col >" +str.boardStateSnapShot[snapshot].cellType.cardType+ " " +str.boardStateSnapShot[snapshot].cellType.cardValue+ "</div><br><br>";
    
}

