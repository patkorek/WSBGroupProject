 $(function () {

    /* ----------------- funkcja ustawiająca datepickery na inputach ----------------------- */
    bindDateFields = function() {
        $(".date").datetimepicker({
        format:'YYYY-MM-DD',
            icons: {
                time: "fa fa-clock-o",
                date: "fa fa-calendar",
                up: "fa fa-arrow-up",
                down: "fa fa-arrow-down"
            },
            locale: 'pl',
            
        }).find('input:first').on("blur",function () {
            // check if the date is correct. We can accept dd-mm-yyyy and yyyy-mm-dd.
            // update the format if it's yyyy-mm-dd
            var date = parseDate($(this).val());

            if (! isValidDate(date)) {
                //create date based on momentjs (we have that)
                date = moment().format('YYYY-MM-DD');
            }

            $(this).val(date);
        });
    }
   
    var isValidDate = function(value, format) {
        format = format || false;
        // lets parse the date to the best of our knowledge
        if (format) {
            value = parseDate(value);
        }

        var timestamp = Date.parse(value);

        return isNaN(timestamp) == false;
   }
   
    var parseDate = function(value) {
        var m = value.match(/^(\d{1,2})(\/|-)?(\d{1,2})(\/|-)?(\d{4})$/);
        if (m)
            value = m[5] + '-' + ("00" + m[3]).slice(-2) + '-' + ("00" + m[1]).slice(-2);

        return value;
   }

   /* ----------------- funkcja wykorzystywana w przeliczaniu kwot netto, vat, brutto ----------------------- */
   Round = function(n, k) {
        var factor = Math.pow(10, k);
        return Math.round(n*factor)/factor;
    }

    /* ----------------- wstawianie tooltip do za długich nazw------------------------ */
    tooltip = function() {
        $('[data-toggle="tooltip"]').tooltip({
            'delay': { "show": 0, "hide": 50 }
           // 'template': '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
        });
    }
    
    /* -------- ładowanie niezbędnych funkcji po otwarciu modal: dodawanie klienta, edycja klienta ----------- */
    modalClient = function() {
        $('.modal_client #modal_status').html('');

        /* automatyczne uzupełnianie pola nazwa podczas pisania skrótu klienta */
        $('.modal_client #klienci_skrot').keyup(function() {
            var klient_skrot = $('.modal_client #klienci_skrot').val();
            $('.modal_client #klienci_nazwa').val(klient_skrot);
        });

        /* ustawienie maski na pola kodu pocztowego i nip */
        $('.modal_client #klienci_kod').mask('99-999', {
            placeholder:" ", 
            autoclear: false
        });

        $('.modal_client #klienci_nip').mask('999-999-99-99', {
            placeholder:" ", 
            autoclear: false
        });

        if ( $("#form_client").attr('name') == 'add' ) {
            $(".modal_client #reset").removeClass('hide');
        }

        $(".modal_client #reset").click(function() {
            $('.modal_client #klienci_skrot, #klienci_nazwa, #klienci_nip, #klienci_kod, #klienci_miasto, #klienci_ulica, #klienci_ulica_nr, #klienci_mail, #klienci_komentarz, #krs').val('');
            $(".modal_client #modal_status").html('');
        });

        
    }

    modalAction = function() {
        $("#send_mail").change(function() {
            if ( $("#send_mail").is(':checked') ) {
                $("#gen_mail").removeClass('hide');
                $(".modal_action #submitForm").html('Wygeneruj i wyślij');
                $(".modal_action #send_text").attr('required', 'true');
            }
            else {
                $("#gen_mail").addClass('hide');
                $(".modal_action #submitForm").html('Wygeneruj');
                $(".modal_action #send_text").removeAttr('required');
            }
        });

        $("#del_invoice").change(function() {
            if ( $("#del_invoice").is(':checked') ) {
                $(".modal_action #submitForm").removeClass('hide');
                $(".modal_action #display_body").removeClass('hide');
            }
            else {
                $(".modal_action #submitForm").addClass('hide');
                $(".modal_action #display_body").addClass('hide');
            }
        });
    }

    elektronicznaClick = function() {
        $('.modal_invoice #modal_status').html('');
        $('#reczna').removeAttr('checked');
        $('#elektroniczna').attr('checked', 'checked');

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
                        $(".modal_invoice").animate({ scrollTop: 0 }, "normal");
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
    }

    recznaClick = function() {
        $('.modal_invoice #modal_status').html('');
        $('#elektroniczna').removeAttr('checked');
        $('#reczna').attr('checked', 'checked');

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
                        $(".modal_invoice").animate({ scrollTop: 0 }, "slow");
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
    }

    setModalMaxHeight = function(element) {
      this.$element     = $(element);
      this.$content     = this.$element.find('.modal-content');
      var borderWidth   = this.$content.outerHeight() - this.$content.innerHeight();
      var dialogMargin  = $(window).width() > 767 ? 60 : 20;
      var contentHeight = $(window).height() - (dialogMargin + borderWidth);
      var headerHeight  = this.$element.find('.modal-header').outerHeight() || 0;
      var footerHeight  = this.$element.find('.modal-footer').outerHeight() || 0;
      var maxHeight     = contentHeight - (headerHeight + footerHeight);

      this.$content.css({
          'overflow': 'hidden'
      });

      this.$element
        .find('.modal-body').css({
          'max-height': maxHeight,
          'overflow-y': 'auto'
      });
    }

    searchInTable = function() {
        $(".search").keyup(function () {
            var searchTerm = $(this).val();
            var listItem = $('.results tbody').children('tr');
            var searchSplit = searchTerm.replace(/ /g, "'):containsi('")
            
            $.extend($.expr[':'], {'containsi': function(elem, i, match, array){
                return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
            });
                
            $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function(e){
                $(this).attr('visible','false');
            });

            $(".results tbody tr:containsi('" + searchSplit + "')").each(function(e){
                $(this).attr('visible','true');
            });

            var jobCount = $('.results tbody tr[visible="true"]').length;
                $('.counter').text(jobCount + ' ');

            if(jobCount == '0') {$('.no-result').show();}
            else {$('.no-result').hide();}
        });
    }





 });




$(document).ready(function(){
    
    /* ---------------- uruchamia datepickery na odpowiednich inputach ----------------------- */
    bindDateFields();
    /* ---------------- uruchamia tooltip na za długich nazwach w fakturach i klientach ------ */
    tooltip();

    /* ---------------- wysyła formularz po zmianie wartości w select w fakturach ------------ */
    $(document).on('dp.change', e => $(e.target).change());
    $("#faktury").change(function() {
        $("#faktury").submit();
    });
    
    /* kwota netto, vat brutto w formularzu dodaj fakture, edycja faktury */
    $(document).on("blur", "#netto", function() {
        if ($("#netto").val()!='') {
            val_netto = Round(parseFloat($("#netto").val()),2);
            $("#netto").val(val_netto);

            val_vat = Round(parseFloat((val_netto*0.23).toFixed(2)),2);

            $("#vat").val(val_vat);

            val_brutto = Round(val_netto+val_vat,2);

            $("#brutto").val(val_brutto);
        }
    });

    $(document).on("blur", "#vat", function() {
        if ($("#vat").val()!='') {
            val_netto = parseFloat($("#netto").val());
            val_vat = Round(parseFloat($("#vat").val()),2);
            $("#vat").val(val_vat);

            val_brutto = Round(val_netto+val_vat,2);

            $("#brutto").val(val_brutto);
        }
    });

    $(document).on("keypress", "#netto, #vat, #brutto", function(e) {
        if(e.which!=8 && e.which!=0 && e.which!=44 && e.which!=46 && (e.which<48 || e.which>57)){
            return false;
        }
        else {
            val = this.value;
            this.value = val.replace(",", ".");

            if (this.value.indexOf('.') === 0) {
                this.value = val.substring(0, val.length - 1);
            }
            else {
                this.value = this.value;
            }
        }
    });

    /* -------------------- ładowanie funkcji po otwarciu modal: dodawanie faktury, edycja faktury ---------- */
    $('.modal_invoice').on('shown.bs.modal', function() {
        setModalMaxHeight(this);
        $('.modal_invoice #modal_status').html('');
        $('.selectpicker').selectpicker();
        bindDateFields();
        
        $(".modal_invoice #reset").click(function() {
            $('.modal_invoice #klienci_nazwa, #klienci_nip, #klienci_kod, #klienci_miasto, #klienci_ulica, #klienci_ulica_nr, #klienci_mail, #fv_listy, #fv_komentarz, #netto, #vat, #brutto, #data').val('');
            $(".modal_invoice #modal_status").html('');
            $(".modal_invoice #klient")
                .val('default')
                .selectpicker('refresh');
        });
        
        $('label.reczna').click(function() {
            
            $('.modal_invoice #modal_status').html('');
            $('#elektroniczna').removeAttr('checked');
            $('#reczna').attr('checked', 'checked');
            
           /* recznaClick(); */
        });
        
        $('label.elektroniczna').click(function() {
            
            $('.modal_invoice #modal_status').html('');
            $('#reczna').removeAttr('checked');
            $('#elektroniczna').attr('checked', 'checked');
            

           /* elektronicznaClick(); */
        });

         if ( $("#elektroniczna").is(':checked') && ( $("#fv_nr_1").is(':disabled') || $("#fv_nr_2").is(':disabled') ) ) {
             $("label.elektroniczna").click(); 
            /* elektronicznaClick(); */
        }
        
        if ( $("#form_invoice").attr('name') == 'add' ) {
            $(".modal_invoice #reset").removeClass('hide');
        }
        else if ( $("#form_invoice").attr('name') == 'edit' ) {
            $('.rodzaj_faktury')
                .click(function(){
                    return false;
                })
                .css('pointer-events', 'none');
        }

        $('#klient').on('shown.bs.select', function (e) {
            ("#klient").css('width', '100% !important');
        });
    });

    /* -------- ładowanie funkcji po otwarciu modal: dodawanie klienta, edycja klienta ----------- */
    $('.modal_client').on('shown.bs.modal', function() {
        setModalMaxHeight(this);
        modalClient();
    });

    /* -------- ładowanie funkcji po otwarciu modal: opcje w danej fakturze, w danym kliencie ------- */
    $('.modal_action').on('shown.bs.modal', function() {
        setModalMaxHeight(this);
        searchInTable();
        modalAction();
    });

    /* -------- ładowanie funkcji po otwarciu modal: opcje w danej fakturze, w danym kliencie ------- */
    $('.modal_client_invoice').on('shown.bs.modal', function() {
        setModalMaxHeight(this);
        tooltip();

        $('#modal')
            .append('<div id="second_invoice" class="modal fade modal_invoice" role="dialog"><div class="modal-dialog modal-md"><div class="modal-content"></div></div></div>')
            .append('<div id="second_client" class="modal fade modal_client" role="dialog"><div class="modal-dialog modal-md"><div class="modal-content"></div></div></div>')
            .append('<div id="second_action" class="modal fade modal_action" role="dialog"><div class="modal-dialog modal-lg"><div class="modal-content"></div></div></div>');


        $('.modal_invoice').on('shown.bs.modal', function() {
            $("#second_invoice #dodaj_klienta").attr('data-target', '#second_client');

            $('.modal_invoice #modal_status').html('');
            $('.selectpicker').selectpicker();
            bindDateFields();
            
            $('.rodzaj_faktury')
                .click(function(){
                    return false;
                })
                .css('pointer-events', 'none');
        });

        $('.modal_client').on('shown.bs.modal', function() {
            modalClient();
        });

        $('.modal_action').on('shown.bs.modal', function() {
            modalAction();
        });
        /*
        $('#second_action').on('hidden.bs.modal', function() {
            $("#second_invoice, #second_client, #second_action").removeData('bs.modal');
        });
        */

        /* ------------ funkcja szukająca wśród tabeli danych, zostawia na widoku tylko to co znajdzie --------- */
        searchInTable();
    });

    /* ---------------- usuwa dane ze wszystkich otwartych modali po zamknięciu jakiegokolwiek -------------- */
    $(document).on('hidden.bs.modal', function (e) {
        $(e.target).removeData('bs.modal');
    });

    /*
    $('.modal_action').on('hidden.bs.modal', function() {
        $(".modal_invoice").removeData('bs.modal');
        $(".modal_client").removeData('bs.modal');
        $(".modal_action").removeData('bs.modal');
        $(".modal_client_invoice").removeData('bs.modal');
        $("#second_invoice").remove();
        $("#second_client").remove();
        $("#second_action").remove();
    });

    $('.modal_client').on('hidden.bs.modal', function() {
        $(".modal_client").removeData('bs.modal');
    });

    $('#second_action').on('hidden.bs.modal', function() {
        $("#second_action").removeData('bs.modal');
    });
    */

    /* ----------------- po zamknięciu faktur klienta usuwa 3 dodatkowe bloki o dublującej się klasie 
    aby nie otwierały się podwójne modale w fakturach i klientach---------- */
    $('.modal_client_invoice').on('hidden.bs.modal', function() {
        $("#second_invoice, #second_client, #second_action").remove();
    });

    /* ------------------ powoduje brak reakcji po kliknięciu na element disabled link ------------------ */
    $(".action .disabled a,.dropdown-header").click(function(event) {
        event.preventDefault();
        return false;
    });

    /* ----------------- czyści pola w search i wysyła formularz na stronie klient */
    $("#reset_search").click(function() {
        $('#search_nazwa').val('');
        $('#search_nip').val('');
        $('#search_komentarz').val('');
        $("#search").submit();
    });

    /* ------------------ wysuwa, chowa blok search i ustawia focus na nazwe */
    $(document).on('click', '#invoice_advanced', function() {
        if ( $( "#advanced_div" ).is( ":hidden" ) ) {
            $( "#advanced_div" ).show( "fast" );
        } else {
            $( "#advanced_div" ).slideUp(250);
        }

    });

    /* ------------------ wysuwa, chowa blok advanced w fakturach */
    $(document).on('click', '#client_search', function() {
        if ( $( "#search_div" ).is( ":hidden" ) ) {
            $( "#search_div" ).show( "fast" );
            $( "#search_nazwa").focus();
        } else {
            $( "#search_div" ).slideUp(250);
        }

    });

    /* ------------------- sprawdzanie czy przynajmniej w jednym polu wyszukiwania są wpisane min 2 znaki ------- */
    if (!$("#div_search").hasClass('hide')) {
        $('#search_nazwa,#search_nip,#search_komentarz').keyup(function() {
            console.log( $("#search_nazwa").val().length );
            if ( $("#search_nazwa").val().length >= 2 || $("#search_nip").val().length >= 2 || $("#search_komentarz").val().length >= 2 ) {
                $("#submit_search").removeAttr('disabled');
               // $("#reset_search").removeAttr('disabled');
            }
            else {
                $("#submit_search").attr('disabled', 'true');
               // $("#reset_search").attr('disabled', 'true');
            }
        });
    }

    /* --------- przechodzenie pomiędzy stronami przez ajax -------------- */
    $(document).on('click', '.load_page a', function() {
        event.preventDefault();
        var href = $(this).attr('href');
        $body = $("#load_target");
        $body.append("<div id='loader'></div>");

        $(document).on({
            ajaxStart: function() { 
                if ( href.search('view_client_invoices.php') ) {
                    history.replaceState("", "", href);
                }
                
                $body.addClass("loading");
            },
            ajaxStop: function() { 
                // var stateObj = { foo: "bar" };
                // history.pushState("", "", href); // przechodzenie wstecz, w przod nie zmienia zawartosci wynikow
                $body.removeClass("loading");
                $body.removeData("#loader");
            }    
        });
        //console.log(href);

        $("#load").load( href + " #load_target", function() {
            $("html, body, .modal, .modal-body").animate({ scrollTop: 0 }, "normal");
            tooltip();
        });
    });












/* podświetla na zielono po kliknięciu dany wiersz */
/*
$('.click').click(function() {
    $('.container-fluid tr').each(function(index) {     
        $(this).removeClass("success");
    });
    var form = $(this).closest('tr').addClass("success");
});
*/



function fix_h(id) {
    $(id).css({'height':''});
    var highestBox = 25;
        $(id).each(function(){  
            if($(this).height() > highestBox){  
            highestBox = $(this).height();  
        }
    });    
    
    $(id).height(highestBox);

};




fix_h('.fixh1'); 



//fix menu overflow under the responsive table 
// hide menu on click... (This is a must because when we open a menu )
$(document).click(function (event) {
    //hide all our dropdowns
    $('.dropdown-menu[data-parent]').hide();

});

$(document).on('click', '.table-responsive [data-toggle="dropdown"]', function () {
    $('.dropdown-menu[data-parent]').hide();
});

$(document).on('click', '.table-responsive [data-toggle="dropdown"]', function () {
    // if the button is inside a modal
    if ($('body').hasClass('modal-open')) {
        throw new Error("This solution is not working inside a responsive table inside a modal, you need to find out a way to calculate the modal Z-index and add it to the element")
        return true;
    }

    $buttonGroup = $(this).parent();
    if (!$buttonGroup.attr('data-attachedUl')) {
        var ts = +new Date;
        $ul = $(this).siblings('ul');
        $ul.attr('data-parent', ts);
        $buttonGroup.attr('data-attachedUl', ts);
        $(window).resize(function () {
            $ul.css('display', 'none').data('top');
        });
    } else {
        $ul = $('[data-parent=' + $buttonGroup.attr('data-attachedUl') + ']');
    }
    if (!$buttonGroup.hasClass('open')) {
        $ul.css('display', 'none');
        return;
    }
    dropDownFixPosition($(this).parent(), $ul);
    function dropDownFixPosition(button, dropdown) {
        var dropDownTop = button.offset().top + button.outerHeight();
        dropdown.css('top', dropDownTop + "px");
        dropdown.css('left', button.offset().left + "px");
        dropdown.css('position', "absolute");

        dropdown.css('width', dropdown.width());
        dropdown.css('heigt', dropdown.height());
        dropdown.css('display', 'block');
        dropdown.css('z-index', '999');
        dropdown.appendTo('body');
    }
});





$('.modal').on('show.bs.modal', function() {
  $(this).show();
  setModalMaxHeight(this);
});

$(window).resize(function() {
  if ($('.modal.in').length != 0) {
    setModalMaxHeight($('.modal.in'));
  }
});


/*
$(window).on("popstate", function(e) {
    if (e.originalEvent.state !== null) {
    location.reload()
    }
});
*/





}); // ready


 /* ------------------------------- MARKIET --------------------- */

 $(function(){
    $('.button-checkbox').each(function(){
        var $widget = $(this),
            $button = $widget.find('button'),
            $checkbox = $widget.find('input:checkbox'),
            color = $button.data('color'),
            settings = {
                    on: {
                        icon: 'glyphicon glyphicon-check'
                    },
                    off: {
                        icon: 'glyphicon glyphicon-unchecked'
                    }
            };

        $button.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });

        $checkbox.on('change', function () {
            updateDisplay();
        });

        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');
            // Set the button's state
            $button.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $button.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$button.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $button
                    .removeClass('btn-default')
                    .addClass('btn-' + color + ' active');
            } 
            else 
            { 
                $button
                    .removeClass('btn-' + color + ' active')
                    .addClass('btn-default');
            }
        }
        function init() {
            updateDisplay();
            // Inject the icon if applicable
            if ($button.find('.state-icon').length == 0) {
                $button.prepend('<i class="state-icon ' + settings[$button.data('state')].icon + '"></i> ');
            }
        }
        init();
    });
});