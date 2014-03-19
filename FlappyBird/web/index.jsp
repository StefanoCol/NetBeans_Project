<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
<head>
  <meta charset="utf-8" />
  <title>Flappy Bird Clone</title>
  <link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
  <style>
    #game_div, p {
      width: 400px;
      margin: auto;
      margin-top: 20px;
    }
  </style>
  
  
</head>

<body>
    
    <div class="container">
        <div class="row">
            <p>Press the spacebar to jump</p>
            <div style="text-align: center;">
                username: <input type="text" value="anonymus" id="username" />
            </div>
        </div>
        <div class="row">
            <div id="game_div"> </div>
        </div>
        <div class="row">
            <div class="col-xs-4"></div>
            <div class="col-xs-4">
                <div id="topFive">
                    <table id='tableTopFive' class="table">
                      <thead>
                          <tr>
                              <th>Username</th>
                              <th>Score</th>
                              <th>Timestamp</th>
                          </tr>
                      </thead>
                      <tbody>
                      </tbody>
                      </table>
                </div>
            </div>
        </div>

        <p>Learn how to make this game with a tutorial <a href="http://blog.lessmilk.com/how-to-make-flappy-bird-in-html5-1/">here</a></p>
  </div>
  <script type="text/javascript" src="jquery-1.11.0.js"></script>
  <script type="text/javascript" src="phaser.min.js"></script>
  <script type="text/javascript" src="main.js"></script>
  <script src='topFive.js'></script>
</body>

</html>
