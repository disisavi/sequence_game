window.onload = function () {
    sendInvite()
};

function sendInvite() {
    const url = hb;
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {

    };
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(playerSub);
    setTimeout(sendInvite, 1500)
    return false;
}
