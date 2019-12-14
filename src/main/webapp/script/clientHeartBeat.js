function sendHB() {
    const url = hb;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {

    };
    xhr.open('POST', url);
    xhr.send(playerSub);
    setTimeout(sendHB, 1500)
    return false;
}
