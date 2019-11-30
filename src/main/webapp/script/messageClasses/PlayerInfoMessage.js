class PlayerInfoMessage {
    constructor(jsonObject) {
        this.gotMessage = jsonObject.gotMessage;
        this.errorMessage = jsonObject.errorMessage;
        this.playerSateMap = jsonObject.playerSateMap;
    }
}