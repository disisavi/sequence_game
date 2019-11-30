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
    <link href="style/gamestyle.css" rel="stylesheet">
</head>
<body>
<div class="cover-container">
    <div class="seq-game container " id="parentGrid">
    </div>
</div>
</div>
<div class="player-container">
    <div class="seq-player" id="playerGrid">
        <p>Your turn</p>
    </div>
</div>
<script language="javascript">
    <% String param=Params.getBoardParam();%>
    var param = "<%=param%>";
</script>
<script src="script/gamescript.js"></script>
</body>
</html>
