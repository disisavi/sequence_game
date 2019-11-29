class PlayerInfoMessage {
    constructor(jsonObject) {
        let gotMessage = jsonObject.gotMessage;
        let errorMessage = jsonObject.errorMessage;
        let playerSateMap = jsonObject.playerSateMap;
    }

    gotMessage() {
        return this.gotMessage;
    }

    errorMessage() {
        return this.errorMessage;
    }

    playerSateMap(){
        return this.playerSateMap;
    }

}