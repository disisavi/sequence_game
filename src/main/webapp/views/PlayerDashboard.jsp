<%@ page import="edu.isa681.web.server.Params" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Sequence</title>
    <meta name="description" content="Welcome to Sequence">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            type="javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            type="javascript"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            type="javascript"></script>
    <link href="../style/stylesheet.css" rel="stylesheet"/>
</head>
<body>
<!--[if IE]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade
    your browser</a> to improve your experience and security.</p>
<![endif]-->

<div class="container">
    <div class="jumbotron">
        <h1 class="display-4">Welcome <span id="namePlaceHolder"></span></h1>
        <p class="lead">We are ready to shuffle the cards.. </p>
        <hr class="my-4">
    </div>
    <div>
        <div>
            <div class="row row-containers">
                <div class="col-4">
                    <h3><span id="HeadingPlaceholder"></span></h3>
                    <form id="playerForm">
                    </form>
                </div>
            </div>
            <footer>
                <div class="row">
                    <%--                Boiler Plate to make invite button in the middle--%>
                    <div class="col-2"></div>
                    <div class="col-8">
                        <form id="submitPlayers">
                            <center>
                                <button type='button' style="visibility: hidden" class='btn btn-primary btn-lg' onclick=""
                                        id='You have been invited to the Game'>Invite
                                </button>
                            </center>
                        </form>
                    </div>
                    <div class="col-2"></div>
                </div>
            </footer>
        </div>
    </div>
</div>
<script>
    <% String param = Params.getDashboardParam();%>
    const param = "<%=param%>";
    const playerName = '<%=request.getParameter("playerName")%>';
    const playerSub = '<%=request.getParameter("playerSub")%>';
</script>
<script src="../script/messageClasses/PlayerInviteMessage.js" type="text/javascript"></script>
<script src="../script/messageClasses/PlayerInfoMessage.js" type="text/javascript"></script>
<script src="../script/dashboardScript.js" type="text/javascript"></script>
</body>
</html>