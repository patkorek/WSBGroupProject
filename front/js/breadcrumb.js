

var path = "";
var href = document.location.href;
var s = href.split("/");

path += '<ol class="breadcrumb"><code>Aktualnie jesteś tutaj: </code>';
for (var i = 4; i < (s.length-1); i++) {
	path += "<li><a class='text-capitalize' href=\""+href.substring(0,href.indexOf("/"+s[i])+s[i].length+1)+"/\">"+s[i]+"</a></li>";
}


i = s.length-1;

var last_link_0 = s[i].split(".");

if (last_link_0[0] == '' || last_link_0[0] == 'index') {
	if (s[5] == '') {
		var last_link = 'start';
	}
	else {
		var last_link = 'Ewidencja';
	}
}
else {
	var last_link = last_link_0[0];
	if (last_link == 'clients') {
		var last_link = 'klienci';
	}
	else if (last_link == 'reports') {
		var last_link = 'raporty';
	}
	else if (last_link == 'logs') {
		var last_link = 'logi';
	}
}

path += "<li class='active text-capitalize'>"+last_link+"</li>";
path += '</ol>';

var url = path;
document.writeln(url);

