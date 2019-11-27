<%@ page import="edu.isa681.web.server.Params" %>
<!doctype html>
<html>
<head>
<style>

.bg-image {
  /* The image used */
  background-image: url("Sequence.jpg");

  /* Add the blur effect */
  filter: blur(8px);
  -webkit-filter: blur(8px);

  /* Full height */
  height: 100%;

  /* Center and scale the image nicely */
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}


/* Position text in the middle of the page/image */
.bg-text {
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0, 0.4); /* Black w/opacity/see-through */
  color: white;
  font-weight: bold;
  border: 3px solid #f1f1f1;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  width: 80%;
  padding: 20px;
  text-align: center;
}


.button {background-color: #008CBA;}
</style>
    <meta charset="utf-8">
    <title>Sequence</title>
    <meta name="description" content="Welcome to Sequence">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            type="javascript"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            type="javascript"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            type="javascript"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <!-- Custom styles for this template -->
    <link href="style/stylesheet.css" rel="stylesheet"/>
    <script language="javascript">
        <% String param=Params.getLoginParam();%>
    </script>
</head>
<body>
<!--[if IE]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade
    your browser</a> to improve your experience and security.</p>
<![endif]-->
<div class="bg-image"></div>
<div class="bg-text">
        <center><h1 class="display-4">Hey!!! </h1></center>
		<center><h1 class="display-4"> Sequence is finally online!</h1></center>
        <center> <p class="lead">Welcome to the fun family, abstract strategy board-and-card game. So what are you waiting for,
            sign in and start playing with your friends online </p> </center>
        <hr class="my-4">
        <center> <p>For the purpose of keeping user information and credentials as safe as posible, we use google provided single
            sign on service. Please use your google account to sign on.</p> </center>
    
    <form id='loginform' name="loginform" method="get"
          action='<%=param%>'>
        <center> <button type="submit" class="btn btn-primary btn-lg">Sign In!</button> </center>
    </form>
	<p>Click on Instructions to understand how to play!</p>
  <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#demo">Instructions</button>
  <div id="demo" class="collapse">
    <br/> Set-up: <br/>
	Each player selects a card of their choice from their hand and places it face up on a discard pile (players should start their own discard pile in front of them visible to all other players) and then places one of their marker chips on the matching card on the game board. Each card is pictured twice on the game board. Jacks do not appear on the game board. A player can play on either one of the card spaces as long as it is not already covered by another marker chip. Once a marker chip has been played, it cannot be removed by an opponent except when using a one-eyed Jack as explained below.
	<br/> The Jacks: <br/>
	There are 8 Jacks in the card deck. The 4 Jacks with TWO EYES are wild. To play a two-eyed Jack, place it on your discard pile and place one of your marker chips on any open space on the game board. The 4 jacks with ONE EYE are anti-wild. To play a one-eyed Jack, place it on your discard pile and remove one marker chip from the game board belonging to your opponent. That completes your turn. You cannot place one of your marker chips on that same space during this turn. You cannot remove a marker chip that is already part of a completed SEQUENCE. Once a SEQUENCE is achieved by a player or a team, it cannot be broken. You may play either one of the Jacks whenever they work best for your strategy, during your turn.
  </div>
</div>
<div class="container">
</div>
<script src="./script/script.js" onclick="" type="text/javascript"></script>
</body>
</html>