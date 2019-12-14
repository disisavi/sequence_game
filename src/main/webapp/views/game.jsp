<%@ page import="edu.isa681.web.server.Params" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sequence</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            type="javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            type="javascript"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            type="javascript"></script>
    <!-- Custom styles for this template -->
    <link href="../style/gamestyle.css" rel="stylesheet">
</head>
<body>
<div class="cover-container">
    <div id="displayError"></div>
    <div class="seq-grid">
        <div class="seq-game" id="parentGrid">
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="player-container col-2">
                <div role="alert" id="turnPlaceHolder">
                </div>
            </div>
            <div class="player-container col-6">
                <div id="playerInfo"></div>
            </div>
            <div class="player-container col-4">
                <form id="playerInviteForm">
                    <div class="form-row align-items-center">
                        <div class="col-auto">
                            <label class="sr-only" for="IDform">X,Y as seen on form</label>
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">ID-</div>
                                </div>
                                <input type="text" class="form-control" id="IDform"
                                       placeholder="X,Y as seen on form">
                            </div>
                        </div>
                        <div class="col-auto">
                            <label class="sr-only" for="Cindexform">Index Card</label>
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">ID-</div>
                                </div>
                                <input type="text" class="form-control" id="Cindexform"
                                       placeholder="Index of card">
                            </div>
                        </div>
                        <div class="col-auto">
                            <button type="button" class="btn btn-primary mb-2" onClick="submitInviteMessage()">
                            </button>
                        </div>
                        <div id="error" class="error"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script language="javascript">
    <% String param = Params.getBoardParam();%>
    <% String hb = Params.getHeartBeat();%>
    const hb = "<%=hb%>";
    const param = "<%=param%>";
    const playerName = '<%=request.getParameter("playerName")%>';
    const playerSub = '<%=request.getParameter("playerSub")%>';
</script>
<script src="../script/gamescript.js"></script>
<script src="../script/messageClasses/PlayerMove.js"></script>
<script src="../script/messageClasses/PlayerInviteMessage.js"></script>
<script src="../script/clientHeartBeat.js" type="text/javascript"></script>
</body>
</html>
