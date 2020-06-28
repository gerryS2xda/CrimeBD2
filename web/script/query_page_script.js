
//Action button
$("#execute_query_btn").click(function(){
    $("#select_query_page").hide();
    $("#result_content_page").show();
    createAndSetQuerySelectString();
    sendRequestQuery();
});

$("#reset_btn").click(function(){
    $("#select_query_page").show();
    $("#result_content_page").hide();
});

//General function

//function per costruire e settare la stringa relativa alla query selezionata
function createAndSetQuerySelectString(){
    var rbtn = $(":checked"); //prendi tutti gli element "checked" (es. un radio button)
    var idquery = rbtn.attr("id"); //dammi id della query selezionata
    var d = rbtn.parent(); //dammi il padre di <input> selezionato

    var s = "";
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("span_txt_radio")){  //se il figlio del <div> e' uno <span class"span_txt_radio">...
            s+= "" + e.text();
        }
        if(e.hasClass("custom-select-w3c")){ //se il figlio del <div> e' uno <div class"custom-select-w3c">...
            s+= "" + e.children().val(); //dammi il valore della <select>
        }
    }
    $(".result_page_name").text(s);
}

function sendRequestQuery(){
    var rbtn = $(":checked"); //prendi tutti gli element "checked" (es. un radio button)
    var idquery = rbtn.attr("id"); //dammi id della query selezionata
    var d = rbtn.parent(); //dammi il padre di <input> selezionato

    var s = ""; //stringa che contiene gli input dell'utente
    var j = 1; //count dei parametri da passare alla request
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("custom-select-w3c")){ //se il figlio del <div> e' uno <div class"custom-select-w3c">...
            s+= "param" + j  +":"  + e.children().val()+";"; //dammi il valore della <select>
            j++;
        }
    }

    $.post("crime-contr", {"action": idquery, "input" : s}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){


            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
            var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle

            for(var i = 0; i < size; i++) {
                var k = o["crime" + i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito
                str+= "<div class=\"row\"><div class=\"cell\">"+ k.incidentNumber + "</div><div class=\"cell\">"+ k.offenseCode + "</div>" +
                    "<div class=\"cell\">"+ k.offenseCodeGroup + "</div>" + "<div class=\"cell\">"+ k.offenseDescription + "</div>"
                    + "<div class=\"cell\">"+ k.district + "</div>" + "<div class=\"cell\">"+ k.reportingArea + "</div>"
                    + "<div class=\"cell\">"+ k.shooting + "</div>" + "<div class=\"cell\">"+ k.occurredOnDate + "</div>"
                    + "<div class=\"cell\">"+ k.UCR_Part + "</div>" + "<div class=\"cell\">"+ k.street + "</div>"
                    + "<div class=\"cell\">"+ k.lat + "</div>" + "<div class=\"cell\">"+ k.Long + "</div>" + "<div class=\"cell\">"+ k.location + "</div></div>";

            }
            $("#table_header").after(str);
            $(".container-table100").show();

            /*
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
			var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
			var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle
			for(var i = 0; i < size; i++){
				var k = o["prod"+i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito
				var str2 = k.tipo;
				if(k.numUovaConf > 0){ str2 += " da " + k.numUovaConf + " uova"; }
				str+= "<tr><td>" + k.codice + "</td><td>" + str2 + "</td>" +
					"<td><input type=\"text\" class=\"price_input\" value=\"" + k.prezzo+ "\" onblur=\"validatePrezzo($(this), $(this).next())\"> € <p class=\"prod_txt_err_table\"></p></td>" +
					"<td><input type=\"number\" min=\"0\" max=\"100\" value=\"" + k.sconto + "\">%</td> "+
					"<td><input type=\"number\" min=\"0\" max=\"100\" value=\"" + k.iva + "\">%</td> "+
					"<td><input type=\"text\" value=\"" + k.immagine + "\" onblur=\"validateLinkImmagine($(this), 100, $(this).next())\"> <p class=\"prod_txt_err_table\"></p></td>" +
					"<td><select name=\"prod_action\" onChange=\"manageProduct($(this));\">" +
					"<option value=\"\">-</option><option value=\"edit_save\">Salva</option><option = value=\"delete\">Cancella</option></select></td></tr>";

			}
			$("#tb_prod").show();
			$("#tb_prod tbody").html(str);
			$(".prod_txt_err_table").hide();
            */
        }

    });
}

//Other function
$(document).ready(function () {
    $('select').selectize({
        sortField: 'text'
    });
});

/* funzioni di utilita' */
/* calcola il numero di proprieta' presenti in un oggetto */
function sizeObject(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};