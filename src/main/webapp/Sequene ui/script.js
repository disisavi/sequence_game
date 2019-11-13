console.log('It works!');
imagesArray = new Array(24);
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



function loadImage()
{
    var num = Math.floor(Math.random() * imagesArray.length);
    document.write('<img class="seq-img" src="'+imagesArray[num]+'"/>')
  
}