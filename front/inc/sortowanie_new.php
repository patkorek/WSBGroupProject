<?php
$strona=$_GET["strona"]; // potrzebne przy nawigacji stron
$na_stronie='20';


////////////////////funkcja pokazujaca pasek stron
function pasek($l_odp,$l_odp_nastronie,$l_odp_napasku,$skrypt,$strona) {
    
  $l_odp_podz = intval($l_odp / $l_odp_nastronie);
  $l_odp_podz_mod = $l_odp % $l_odp_nastronie;
  
  if ($l_odp_podz_mod>0) {
      $l_odp_podz++;
  }
  
  if ($strona<1) {
      $strona=1; 
  }
  
  if ($strona>=$l_odp_podz) {
      $strona=$l_odp_podz;
  }
  
  $start = $strona-1;
  
  if ($strona>1) {
      // $pop="<a href=\"".$skrypt."strona=$start\">&lt;&lt;</a>"; // pokazuje << na początku stron
      $pop='<li><a href="'.$skrypt.'strona='.$start.'" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>'; // pokazuje << na początku stron

 }

if ($strona<$l_odp_napasku) {
    $koniec = $l_odp_napasku*2+1;
}
else {
    $koniec = $strona+$l_odp_napasku+1;
}

if ($strona<=$koniec-$l_odp_napasku) {
    $start=$strona-$l_odp_napasku;
}

if ($strona>=$l_odp_podz-$l_odp_napasku) {
    $start=$l_odp_podz-$l_odp_napasku*2;
}
  
if ($koniec>$l_odp_podz) {
    $koniec = $l_odp_podz;
}

if ($star<1) {
    $star=1;
}

for ($i=$star; $i<$koniec+1; $i++) {
    
    
    if ($i <> $strona) {
        // $pasek .= "<a href=\"".$skrypt."strona=$i\">"; // pokazuje wszystkie odnosniki stron
        $pasek .= '<li><a href="'.$skrypt.'strona='.$i.'">'.$i.'</a></li>'; // pokazuje wszystkie odnosniki stron
    }
    else {
        //$pasek .= "<a href= ><font color=red><b>$i</b></a>"; // zaznacza na czerwono biezaca strone
        $pasek .= '<li class="active"><span>'.$i.'<span class="sr-only">(current)</span></span></li>'; // pokazuje wszystkie odnosniki stron
    }
    
    
    
    if ($l_odp_podz<>1) {
        $pomocniczy = $i;
    }
    
    if ($i<>$strona) {
      //  $pasek .= "$pomocniczy</a>"; // pokazuje srodek odnosnikow stron
    }
}

$dalej = $strona+1;

if ($strona<$l_odp_podz) {
    // $nas="<a href=\"".$skrypt."strona=$dalej\">&gt;&gt;</a>"; // pokazuje >> na końcu stron
    $nas='<li><a href="'.$skrypt.'strona='.$dalej.'" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>'; // pokazuje >> na końcu stron

}

if ($pomocniczy>0) {
    $br= "$pop $pasek $nas";
}

  echo "$br";
}


$l_odp_nastronie=$na_stronie;
$l_odp_napasku=49.5;


$start=($strona-1)*$l_odp_nastronie;

if ($start=='' || $start<0) {
    $start = 0;
}



////////////////////////////////////// koniec funkcji pokazujacej pasek stron
?>