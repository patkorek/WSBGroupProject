<?php
function wycinek($text, $dlugosc) {
    if (strlen($text) >= $dlugosc) {
        $wynik = mb_substr($text, 0, $dlugosc, 'UTF-8') . "...";
        $div = "<div class='pop_show_wycinek'>$text</div>";
    } else {
        $wynik = $text;
        $div = '';
    }
    echo $wynik . "\n" . $div;
}

function zlicz($text) {
    $kodowanie = 'utf-8';
    $wynik = mb_strlen($text, $kodowanie);
    return $wynik;
}

?>