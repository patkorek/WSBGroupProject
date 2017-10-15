<?php 
session_start();
include('inc/include.php');
$active = 'register';
?>
<!DOCTYPE html>
<html lang="en">
    <?php include 'inc/header.php'; ?>
    <body>
    
    <?php include 'inc/navbar.php'; ?>
    <?php // include 'inc/menu.php'; ?>

<div class="container">
    <div class="row" style="margin-top:60px" >
        <div class="col-xs-12 col-sm-12 col-md-8 col-lg-8 col-sm-offset-0 col-md-offset-2 col-md-offset-2" >
            <form role="form" class="">
                <h2>Zarejestruj się <small>Nic Cię to nie kosztuje.</small></h2>
                <hr class="colorgraph">

                <div class="row" >
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group" >
                            <label for="name">Imię:</label>
                            <input type="text" name="name" id="name" class="form-control input-lg" placeholder="Podaj imię">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group">
                            <label for="surname">Nazwisko:</label>
                            <input type="text" name="surname" id="surname" class="form-control input-lg" placeholder="Podaj nazwisko">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group">
                            <label for="street">Ulica:</label>
                            <input type="text" name="street" id="street" class="form-control input-lg" placeholder="Podaj ulicę">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 ">
                        <div class="form-group">
                            <label for="streetNumber">Nr ulicy:</label>
                            <input type="text" name="streetNumber" id="streetNumber" class="form-control input-lg" placeholder="Nr ulicy">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3 ">
                        <div class="form-group">
                            <label for="zipCode">Kod pocztowy:</label>
                            <input type="text" name="zipCode" id="zipCode" class="form-control input-lg" placeholder="Kod pocztowy">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group">
                            <label for="city">Miasto:</label>
                            <input type="text" name="city" id="city" class="form-control input-lg" placeholder="Podaj miasto">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6" >
                        <div class="form-group date">
                            <label for="data">Data urodzenia:</label>
                            <div class="input-group date" style="width: 100%;">
                                <input placeholder="Wybierz datę" id="data" name="data" maxlength="10" type='text' class="form-control input-lg">
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="row" >
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group" >
                            <label for="email">E-mail:</label>
                            <input type="text" name="email" id="email" class="form-control input-lg" placeholder="Podaj e-mail">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group">
                            <label for="phone">Nr telefonu:</label>
                            <input type="text" name="phone" id="phone" class="form-control input-lg" placeholder="Podaj nr telefonu">
                        </div>
                    </div>
                </div>

                <div class="row" >
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group" >
                            <label for="password">Hasło:</label>
                            <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Hasło">
                        </div>
                    </div>

                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6 ">
                        <div class="form-group">
                            <label for="password2">Powtórz hasło:</label>
                            <input type="password" name="password2" id="password2" class="form-control input-lg" placeholder="Powtórz hasło">
                        </div>
                    </div>
                </div>

                
                <div class="row">
                    <div class="col-xs-4 col-sm-3 col-md-3">
                        <span class="button-checkbox">
                            <button type="button" class="btn" data-color="info" tabindex="7">Akceptuję</button>
                            <input type="checkbox" name="t_and_c" id="t_and_c" class="hidden" value="1">
                        </span>
                    </div>
                    <div class="col-xs-8 col-sm-9 col-md-9">
                         Klikając <strong class="label label-primary">Zarejestru</strong>, akceptujesz <a href="#" data-toggle="modal" data-target="#regulamin">Regulamin serwisu</a> oraz używanie plików cookie.
                    </div>
                </div>

                <hr class="colorgraph">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                        <input type="button" value="Zarejestruj" class="btn btn-success btn-block btn-lg" tabindex="7">
                    </div>
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                        <a href="login.php" class="btn btn-primary btn-block btn-lg">Logowanie</a>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <div class="modal fade" id="regulamin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">Regulamin</h4>
                </div>
                <div class="modal-body">
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Zamknij</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</div>  




<?php include 'inc/footer.php'; ?>
<?php include 'inc/js.php'; ?>   
</body>
</html>
