<?php
// session_start();

$active_login = '';
$active_register = '';
$active_forgot = '';

switch ($active) {
  case 'login':
    $active_login = 'active';
    break;
  case 'register':
    $active_register = 'active';
    break;
  case 'forgot':
    $active_forgot = 'active';
    break;
}

?>

<nav class="navbar navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">

        <a class="navbar-brand navbar-left" href="<?php echo PATH; ?>">
            <img alt="Strona główna" src="<?php echo PATH; ?>img/uslugi2.png">
        </a>

      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>

      

    </div>



    <div id="navbar" class="navbar-collapse collapse">

      <ul class="nav navbar-nav ">
        <li class=" <?php echo $active_login; ?> "><a href="login.php">Logowanie</a></li>
        <li class=" <?php echo $active_register; ?> "><a href="register.php">Rejestracja</a></li>
        <li class=" <?php echo $active_forgot; ?> "><a href="forgot.php">Przypomnienie</a></li>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Szukaj usługę</a></li>
        <li><a href="# "><span class="glyphicon glyphicon-share" aria-hidden="true"></span> Wystaw usługę</a></li>
        <li><a href="# "><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Moje konto</a></li>
      </ul>
        
    </div><!--/.nav-collapse -->





  </div>

</nav>