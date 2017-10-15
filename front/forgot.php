<?php 
session_start();
include('inc/include.php');
$active = 'forgot';
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
                <h2>Zresetuj hasło</h2>
                <hr class="colorgraph">

                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
                        <div class="form-group">
                            <label for="email">Adres e-mail:</label>
                            <input type="text" name="email" id="email" class="form-control input-lg" placeholder="Wprowadź adres e-mail">
                        </div>
                    </div>
                </div>
                <hr class="colorgraph">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                        <input type="button" value="Wyślij nowe hasło" class="btn btn-success btn-block btn-lg">
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                        <a href="login.php" class="btn btn-primary btn-block btn-lg">Logowanie</a>
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
