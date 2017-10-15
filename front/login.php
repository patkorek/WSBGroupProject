<?php 
session_start();
include('inc/include.php');
$active = 'login';
?>
<!DOCTYPE html>
<html lang="pl">
    <?php include 'inc/header.php'; ?>
    <body>
    
    <?php include 'inc/navbar.php'; ?>
    <?php // include 'inc/menu.php'; ?>

  
<div class="container">
    
<div class="row" style="margin-top:60px">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
        <form role="form">
                <h2>Zaloguj się</h2>
                <hr class="colorgraph">
                <div class="form-group">
                    <input type="email" name="email" id="email" class="form-control input-lg" placeholder="Login">
                </div>
                <div class="form-group">
                    <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Hasło">
                </div>
                <span class="button-checkbox">
                    <button type="button" class="btn" data-color="info">Zapamiętaj mnie</button>
                    <input type="checkbox" name="remember_me" id="remember_me" class="hidden">
                    <a href="forgot.php" class="btn btn-link pull-right">Zapomniałeś hasła?</a>
                </span>
                <hr class="colorgraph">
                <div class="row">
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <input type="button" class="btn btn-lg btn-success btn-block" value="Zaloguj">
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-6">
                        <a href="register.php" class="btn btn-lg btn-primary btn-block">Rejestracja</a>
                    </div>
                </div>
        </form>
    </div>
</div>

</div>  




    <?php include 'inc/footer.php'; ?>
    <?php include 'inc/js.php'; ?>   
    </body>
</html>
