console.log('It works!');
boardContent = new Array(100);
var i=0;
imagesArray = new Array(48);
imagesArray[0] = "images/2C.jpg";
imagesArray[1] = "images/2D.jpg";
imagesArray[2] = "images/2H.jpg";
imagesArray[3] =  "images/2S.jpg";
imagesArray[4] =  "images/3C.jpg";
imagesArray[5] =  "images/3D.jpg";
imagesArray[6] =  "images/3S.jpg";
imagesArray[7] =  "images/3H.jpg";
imagesArray[8] =  "images/4C.jpg";
imagesArray[9] =  "images/4D.jpg";
imagesArray[10] =  "images/4H.jpg";
imagesArray[11] =  "images/4S.jpg";
imagesArray[12] =  "images/5C.jpg";
imagesArray[13] =  "images/5D.jpg";
imagesArray[14] =  "images/5H.jpg";
imagesArray[15] =  "images/5S.jpg";
imagesArray[16] =  "images/6C.jpg";
imagesArray[17] =  "images/6D.jpg";
imagesArray[18] =  "images/6H.jpg";
imagesArray[19] =  "images/6S.jpg";
imagesArray[20] =  "images/7C.jpg";
imagesArray[21] =  "images/7D.jpg";
imagesArray[22] =  "images/7H.jpg";
imagesArray[23] =  "images/7S.jpg";
imagesArray[24] =  "images/8C.jpg";
imagesArray[25] =  "images/8D.jpg";
imagesArray[26] =  "images/8H.jpg";
imagesArray[27] =  "images/8S.jpg";
imagesArray[28] =  "images/9C.jpg";
imagesArray[29] =  "images/9D.jpg";
imagesArray[30] =  "images/9H.jpg";
imagesArray[31] =  "images/9S.jpg";
imagesArray[32] =  "images/10C.jpg";
imagesArray[33] =  "images/10D.jpg";
imagesArray[34] =  "images/10H.jpg";
imagesArray[35] =  "images/10S.jpg";
imagesArray[36] =  "images/AC.jpg";
imagesArray[37] =  "images/AH.jpg";
imagesArray[38] =  "images/ADi.jpg";
imagesArray[39] =  "images/AS.jpg";
imagesArray[40] =  "images/KC.jpg";
imagesArray[41] =  "images/KD.jpg";
imagesArray[42] =  "images/KH.jpg";
imagesArray[43] =  "images/KS.jpg";
imagesArray[44] =  "images/QC.jpg";
imagesArray[45] =  "images/QD.jpg";
imagesArray[46] =  "images/QH.jpg";
imagesArray[47] =  "images/QS.jpg";



function loadImage()
{
    var num = Math.floor(Math.random() * imagesArray.length);
    document.write('<img class="seq-img" src="'+imagesArray[num]+'"/>')
    boardContent[i] = imagesArray[num];
    i++;
   
}
function loadPlayerCard()
{
    var player = Math.floor(Math.random() * imagesArray.length);
    for(i=0;i<100;i++)
    {
        if(boardContent[i]==imagesArray[player])
        {
        console.log('value matches');
        document.write('<img class = "seq-player-img" draggable="true" src="'+imagesArray[player]+'" />');
        return 0;
        } 
    else{
        console.log('loading new value');
    }
    }
}