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
    <!-- Custom styles for this template -->
    <link href="style/stylesheet.css" rel="stylesheet"/>
    <script language="javascript">
        <% String str=Params.getParam();%>
    </script>
</head>
<body>
<!--[if IE]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade
    your browser</a> to improve your experience and security.</p>
<![endif]-->
<div class="container">
</div>
<div class="container">
    <div class="jumbotron">
        <h1 class="display-4">Sequence is finally online!</h1>
        <p class="lead">Welcome to the fun family, abstract strategy board-and-card game. So what are you waiting for,
            sign in and start playing with your friends online </p>
        <hr class="my-4">
        <p>For the puprpus of keeping user information and credentials as safe as posible, we use google provided single
            sign on service. Please use your google account to sign on.</p>
    </div>
    <form id='loginform' name="loginform" method="get"
          action='<%=str%>'>
        <button type="submit" class="btn btn-outline-primary">Primary</button>
    </form>
</div>
<div class="container">
</div>
<script src="./script/script.js" onclick="" type="text/javascript"></script>
</body>
</html>