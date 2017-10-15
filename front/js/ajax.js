$(function () {

    
    

 });

/* -------- ładowanie danych do odpowiednich pól po wybraniu klienta z listy na modal invoice ----------- */
$(document).on('change', '#klient', function() {
    var select_id = $(this).val();
    if(select_id == 0) {
        $("#klienci_nazwa, #klienci_nip, #klienci_kod, #klienci_miasto, #klienci_ulica, #klienci_ulica_nr, #klienci_mail").val('');
    } else {
        $.ajax({
            url: 'ajax_order_client.php',
            type: 'post',
            data: {opc:'set_klient', select_id:select_id  },
            datatype: 'json',
                success: function(info) {
                    $('#klienci_nazwa').val(info.nazwa);
                    $('#klienci_nip').val(info.nip);
                    $('#klienci_kod').val(info.kod);
                    $('#klienci_miasto').val(info.miasto);
                    $('#klienci_ulica').val(info.ulica);
                    $('#klienci_ulica_nr').val(info.ulica_nr);
                    $('#klienci_mail').val(info.mail);
                }
        }); 	
    }
});	

/* --------------- wysłanie formularza: dodawania, edycja faktury ---------------- */
$(document).on("submit", '#form_invoice', function(e) {
    var $target = $("#"+$(this).attr('id')+" #submitForm"),
        $btn = $target.button('loading'),
        postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href,
        modal_invoice = ".modal_invoice",
        modal_client = ".modal_client";
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(modal_invoice + ', .modal-body').animate({ scrollTop: 0 }, "normal");
                $(modal_invoice + ' #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(modal_invoice + " #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $(modal_invoice + ' #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(modal_invoice + ' #fv_nr_1').val(info.fv_nr_1);
                $(modal_invoice + ' #fv_nr_2').val(info.fv_nr_2);
                $(modal_invoice + ' #klient').val(info.klient);
                $(modal_invoice + ' #klienci_nazwa').val(info.nazwa);
                $(modal_invoice + ' #klienci_nip').val(info.nip);
                $(modal_invoice + ' #klienci_kod').val(info.kod);
                $(modal_invoice + ' #klienci_miasto').val(info.miasto);
                $(modal_invoice + ' #klienci_ulica').val(info.ulica);
                $(modal_invoice + ' #klienci_ulica_nr').val(info.ulica_nr);
                $(modal_invoice + ' #klienci_mail').val(info.mail);
                $(modal_invoice + ' #fv_listy').val(info.fv_listy);
                $(modal_invoice + ' #fv_komentarz').val(info.fv_komentarz);
                $(modal_invoice + ' #netto').val(info.netto);
                $(modal_invoice + ' #vat').val(info.vat);
                $(modal_invoice + ' #brutto').val(info.brutto);
                $(modal_invoice + ' #data').val(info.data);

                if (info.action == 'add') {
                    $(modal_invoice + ' button[data-toggle="dropdown"]').addClass('bs-placeholder');
                    $(modal_invoice + ' span[class="filter-option pull-left"]').html('Wybierz klienta...');
                }
                /*
                $('.modal_invoice').on('hidden.bs.modal', function() {
                  location.reload();
                });
                */
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal, .modal-body").animate({ scrollTop: 0 }, "normal");
                });

            }
            $btn.button('reset');
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

/* --------------- wysłanie formularza: dodawania, edycja klienta ---------------- */
$(document).on("submit", '#form_client', function(e) {
    var $target = $("#"+$(this).attr('id')+" #submitForm"),
        $btn = $target.button('loading'),
        postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href,
        modal_invoice = ".modal_invoice",
        modal_client = ".modal_client";
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(modal_client + ', .modal-body').animate({ scrollTop: 0 }, "normal");
                $(modal_client + ' #modal_status').html('');

                var error = info.session;
                error.forEach(function(el, i) {
                    $(modal_client + " #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $(modal_client + ' #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                if (info.action == 'add') {
                    $(modal_invoice + ' #klienci_nazwa').val(info.klienci_nazwa);
                    $(modal_invoice + ' #klienci_nip').val(info.klienci_nip);
                    $(modal_invoice + ' #klienci_kod').val(info.klienci_kod);
                    $(modal_invoice + ' #klienci_miasto').val(info.klienci_miasto);
                    $(modal_invoice + ' #klienci_ulica').val(info.klienci_ulica);
                    $(modal_invoice + ' #klienci_ulica_nr').val(info.klienci_ulica_nr);
                    $(modal_invoice + ' #klienci_mail').val(info.klienci_mail);
                    /*
                    $('.modal_client #krs').val('');
                    $('.modal_client #klienci_skrot').val('');
                    $('.modal_client #klienci_nazwa').val('');
                    $('.modal_client #klienci_nip').val('');
                    $('.modal_client #klienci_kod').val('');
                    $('.modal_client #klienci_miasto').val('');
                    $('.modal_client #klienci_ulica').val('');
                    $('.modal_client #klienci_ulica_nr').val('');
                    $('.modal_client #klienci_mail').val('');
                    $('.modal_client #klienci_komentarz').val('');
                    */
                    $(modal_invoice + " #klient")
                        .append('<option style="max-width:500px;" selected="selected" value="'+info.klienci_last_id+'">'+info.klienci_skrot+'</option>')
                        .selectpicker('refresh');
                    
                    if ($(modal_invoice).hasClass('in')) {
                        $(modal_client).modal('hide');
                    }
                    else {
                        /*
                        $(modal_client).on('hidden.bs.modal', function() {
                          location.reload();
                        });
                        */
                    }
                }
                else if (info.action == 'edit') {
                    $(modal_client + ' #klienci_skrot').val(info.klienci_skrot);
                    $(modal_client + ' #klienci_nazwa').val(info.klienci_nazwa);
                    $(modal_client + ' #klienci_nip').val(info.klienci_nip);
                    $(modal_client + ' #klienci_kod').val(info.klienci_kod);
                    $(modal_client + ' #klienci_miasto').val(info.klienci_miasto);
                    $(modal_client + ' #klienci_ulica').val(info.klienci_ulica);
                    $(modal_client + ' #klienci_ulica_nr').val(info.klienci_ulica_nr);
                    $(modal_client + ' #klienci_mail').val(info.klienci_mail);
                    $(modal_client + ' #klienci_komentarz').val(info.klienci_komentarz);
                }
                else {
                    alert('coś poszło nie tak');
                }
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal, .modal-body").animate({ scrollTop: 0 }, "normal");
                });
            }
            $btn.button('reset');
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

$(document).on("click", '#krs_search', function() {
    var $btn = $("#krs_search").button('loading'),
        nip = $("#krs").val(),
        modal_client = ".modal_client";
    $.ajax({
        url: 'ajax_order_client_krs.php',
        type: "POST",
        data: {opc:'set_nip', nip:nip},
        datatype: 'json',
        success: function(info) {
            if (info.status == 'ok') {
                $(modal_client + ' #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały pobrane z systemu KRS</div>');

                $(modal_client + ' #klienci_skrot').val(info.klienci_nazwa);
                $(modal_client + ' #klienci_nazwa').val(info.klienci_nazwa);
                $(modal_client + ' #klienci_nip').val(info.klienci_nip);
                $(modal_client + ' #klienci_kod').val(info.klienci_kod);
                $(modal_client + ' #klienci_miasto').val(info.klienci_miasto);
                $(modal_client + ' #klienci_ulica').val(info.klienci_ulica);
                $(modal_client + ' #klienci_ulica_nr').val(info.klienci_ulica_nr);
            }
            else if (info.status == 'nipFalse') {
                $(modal_client + ', .modal-body').animate({ scrollTop: 0 }, "normal");
                $(modal_client + ' #modal_status')
                    .html('')
                    .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Proszę podać poprawny NIP</div>');
            }
            else {
                $(modal_client + ', .modal-body').animate({ scrollTop: 0 }, "normal");
                $(modal_client + ' #modal_status')
                    .html('')
                    .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane nie zostały znalezione w systemie KRS</div>');
            }
            $btn.button('reset');
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
});


$(document).on("click", 'label.elektroniczna', function() {
    if ( $("#form_invoice").attr('name') == 'add' ) {
        $("#form_invoice").attr('action', 'invoice_action.php?action=add&sys=1');
        $.ajax({
            url: 'ajax_invoice_number.php',
            type: "POST",
            data: {opc:'number_elektroniczna'},
            datatype: 'json',
            success: function(info) {
                if (info.status == 'ok') {
                    $('#modal_status').html('');
                    // $('#pop #status').append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Numer faktury został pobrany z systemu</div>');

                    $('#fv_nr_1')
                        .val(info.fv_nr_1)
                        .removeAttr('disabled')
                        .removeAttr('placeholder');
                    $('#fv_nr_2')
                        .val(info.fv_nr_2)
                        .removeAttr('disabled')
                        .removeAttr('placeholder');
                }
                else {
                    $(".modal_invoice, .modal-body").animate({ scrollTop: 0 }, "normal");
                    $('#modal_status')
                        .html('')
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane nie zostały znalezione w systemie</div>');
                }
            },
            error: function(jqXHR, status, error) {
                console.log(status + ": " + error);
            }
        });
    }
});

$(document).on("click", 'label.reczna', function() {
    $("#fv_nr_1").focus();
    if ( $("#form_invoice").attr('name') == 'add' ) {
        $("#form_invoice").attr('action', 'invoice_action.php?action=add&sys=0');
        $.ajax({
            url: 'ajax_invoice_number.php',
            type: "POST",
            data: {opc:'number_reczna'},
            datatype: 'json',
            success: function(info) {
                if (info.status == 'ok') {
                    $('#modal_status').html('');
                    // $('#pop #status').append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Numer faktury został pobrany z systemu</div>');

                    $('#fv_nr_1')
                        .val(info.fv_nr_1)
                        .removeAttr('disabled')
                        .removeAttr('placeholder');
                    $('#fv_nr_2')
                        .val(info.fv_nr_2)
                        .removeAttr('disabled')
                        .removeAttr('placeholder');
                }
                else {
                    $(".modal_invoice, .modal-body").animate({ scrollTop: 0 }, "slow");
                    $('#modal_status')
                        .html('')
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane nie zostały znalezione w systemie</div>');
                }
            },
            error: function(jqXHR, status, error) {
                console.log(status + ": " + error);
            }
        });
    }
});



$(document).on("submit", '#form_invoice_del', function(e) {
    var postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href;
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(".modal_action").animate({ scrollTop: 0 }, "normal");
                $('.modal_action #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(".modal_action #modal_status").css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $('.modal_action #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(".modal_action").modal('hide');
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal").animate({ scrollTop: 0 }, "normal");
                });
            }
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

$(document).on("submit", '#form_invoice_back', function(e) {
    var postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href;
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(".modal_action").animate({ scrollTop: 0 }, "normal");
                $('.modal_action #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(".modal_action #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $('.modal_action #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(".modal_action").modal('hide');
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal").animate({ scrollTop: 0 }, "normal");
                });
            }
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

$(document).on("submit", '#form_invoice_send', function(e) {
    var $target = $("#"+$(this).attr('id')+" #submitForm"),
        $btn = $target.button('loading'),
        postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href;
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(".modal_action").animate({ scrollTop: 0 }, "normal");
                $('.modal_action #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(".modal_action #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $('.modal_action #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(".modal_action").modal('hide');
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal").animate({ scrollTop: 0 }, "normal");
                });
            }
            $btn.button('reset');
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

$(document).on("submit", '#form_invoice_gen', function(e) {
    var $target = $("#"+$(this).attr('id')+" #submitForm"),
        $btn = $target.button('loading'),
        postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href;
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(".modal_action").animate({ scrollTop: 0 }, "normal");
                $('.modal_action #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(".modal_action #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $('.modal_action #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(".modal_action").modal('hide');
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal").animate({ scrollTop: 0 }, "normal");
                });
            }
            $btn.button('reset');
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});

$(document).on("submit", '#form_client_del', function(e) {
    var postData = $(this).serializeArray(),
        formURL = $(this).attr("action"),
        href = location.href;
    $.ajax({
        url: formURL,
        type: "POST",
        data: postData,
        datatype: 'json',
        success: function(info) {
            if (info.status == 'error') {
                $(".modal_action").animate({ scrollTop: 0 }, "slow");
                $('.modal_action #modal_status').html('');
                var error = info.session;

                error.forEach(function(el, i) {
                    $(".modal_action #modal_status")
                        .css("margin-bottom", "20px")
                        .append('<div class="alert alert-danger alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>'+el+'</div>')[i];
                });
            }
            else if (info.status == 'ok') {
                $('.modal_action #modal_status')
                    .html('')
                    .append('<div class="alert alert-success alert-dismissable"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>Dane zostały zapisane poprawnie</div>');

                $(".modal_action").modal('hide');
                $("#load").load( href + " #load_target", function() {
                    $("html, body, .modal").animate({ scrollTop: 0 }, "normal");
                });
            }
        },
        error: function(jqXHR, status, error) {
            console.log(status + ": " + error);
        }
    });
    e.preventDefault();
});