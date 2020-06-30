
/*
$(document).ready(function () {
    $('select').selectize({
        sortField: 'text'
    });
});*/

$(document).ready(function(){
    $("#select_query").trigger("change");
});

//Action button
$("#execute_query_btn").click(function(){
    showResultPage();
    /*
    if(isRequestSearch()){
        sendSearchRequest();
    }else{
        showResultPage();
    }
    */

});

function showResultPage(){
    $("#select_query_page").hide();
    $("#result_content_page").show();
    createAndSetQuerySelectString();
    sendRequestAndObtainResponseQuery();
}

$("#reset_btn").click(function(){
    $("#select_query_page").show();
    $("#result_content_page").hide();
    resetResultPage();
});

function resetResultPage(){

    var str = "<div id=\"table_header\" class=\"row header noHover\">" +
        "<div class=\"cell\"> Incident number </div><div class=\"cell\"> Offense code </div>" +
        "<div class=\"cell\"> Offense code group</div><div class=\"cell\"> Offense description </div>" +
        "<div class=\"cell\"> District </div><div class=\"cell\"> Reporting area </div>" +
        "<div class=\"cell\"> Shooting </div><div class=\"cell\"> Occured on date </div>\n" +
        "<div class=\"cell\"> UCR part </div><div class=\"cell\"> Street </div>\n" +
        "<div class=\"cell\"> Latitude </div><div class=\"cell\"> Longitude </div></div>";
    $(".table").html(str);
    $(".noresult_p").hide();
}

//General function
$("#select_query").change(function(){
    var query = $(this).val(); //dammi il contenuto di value della select
    var selectedText = $("#select_query option:selected").html();
    $(".query_legend").text(query);
    $(".query_sel_text").text(selectedText);
    $(".content_fieldset").html(createContentForFieldSet(query, selectedText));
});

function createContentForFieldSet(querynum, selectedText){
    var str = "";
    if(querynum === "Query 1"){
        str+= "<p class=\"label_p\"> Nessun input richiesto </p>";
        $(".query_text_for_result").html(selectedText);
    }
    if(querynum === "Query 2"){
        str+= "<label>Distretto</label><input type=\"text\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\"> <br>" +
            "<label>Fascia oraria</label><input type=\"number\" class=\"numberfield\" name=\"fascia_oraria_min\" min=\"1\" max=\"24\" value=\"1\" onblur=\"validateFasciaOraria($(this))\"> - " +
            "<input type=\"number\" class=\"numberfield\" name=\"fascia_oraria_max\" min=\"1\" max=\"24\" value=\"2\" onblur=\"validateFasciaOraria($(this))\">";
        $(".query_text_for_result").html("Numero reati con sparatoria nell'ultimo mese avvenuti nel distretto <span class=\"tf_span\"></span> e in una data fascia oraria <span class=\"fascia_or_nm_min\"> </span> - <span class=\"fascia_or_nm_max\"> </span>");
    }
    if(querynum === "Query 3"){
        str+="<label>Street</label><input type=\"text\" class=\"inputfield\" name=\"street\" placeholder=\"(es. GIBSON ST)\">"
        $(".query_text_for_result").html("Incidenti/reati avvenuti nella street <span class=\"tf_span\"></span>");
    }
    if(querynum === "Query 4"){
        str+= "<label>Distretto</label><input type=\"text\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\">";
        $(".query_text_for_result").html("Visualizza la categoria di incidenti/reati che avvengono maggiormente nel distretto <span class=\"tf_span\"></span>");
    }
    if(querynum === "Query 5"){
        str+= "<label>Distretto</label><input type=\"text\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\">";
        $(".query_text_for_result").html("Mostra in quale giorno della settimana avvengono più reati/incidenti di tipo <span class=\"select_span\"> </span> nel distretto <span class=\"tf_span\"></span>");
    }
    if(querynum === "Query 6"){
        str+= "<label>Distretto </label> <input type=\"text\" class=\"inputfield\" name=\"street\" placeholder=\"(es. GIBSON ST)\"> <br>" +
            "<label>Fascia oraria </label> <input type=\"number\" class=\"numberfield\" name=\"fascia_oraria_min\" min=\"1\" max=\"24\" value=\"1\" onblur=\"validateFasciaOraria($(this))\"> - " +
            "<input type=\"number\" class=\"numberfield\" name=\"fascia_oraria_max\" min=\"1\" max=\"24\" value=\"2\" onblur=\"validateFasciaOraria($(this))\">";
        $(".query_text_for_result").html("Incidenti/reati avvenuti nella street <span class=\"tf_span\"></span> e nella fascia oraria <span class=\"fascia_or_nm_min\"> </span> - <span class=\"fascia_or_nm_max\"");
    }
    if(querynum === "Query 7"){
        str+= "<label>Tipo di incidente/reato </label><div class=\"custom-select-w3c\">" +
            "<select placeholder=\"Pick a state...\"><option value=\"\">Select a state...</option>" +
            "<option value=\"1\">Value2</option></select></div>";
        $(".query_text_for_result").html("Visualizza l'ora in cui si verifica maggiormente un incidente/reato di tipo <span class=\"select_span\"> </span>");
    }
    //query 8 -> Insert
    if(querynum === "Query 9"){
        str+= "<label> UCR </label><div class=\"custom-select-w3c\">" +
            "<select placeholder=\"Pick a state...\"><option value=\"\">Select a state...</option>" +
            "<option value=\"Part One\">Part One</option><option value=\"Part Two\">Part Two</option>" +
            "<option value=\"Part Three\">Part Three</option></select></div>";
        $(".query_text_for_result").html("Incidenti/reati in base al valore di UCR <span class=\"select_span\"> </span>");
    }
    if(querynum === "Query 10"){
        str+= "<label>Incident number</label><input type=\"text\" class=\"inputfield\" name=\"incidentnumber\" placeholder=\"(es. I92097173)\">";
        $(".query_text_for_result").html("Cancellazione mediante inserimento dell'Incident number <span class=\"tf_span\"></span>");
    }
    return str;
}

//function per costruire e settare la stringa relativa alla query selezionata
function createAndSetQuerySelectString(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato
    var qr = $(".query_text_for_result").children(); //dammi tutti i figli di tale <span> per creare la stringa

    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <fieldset> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        alert("E: " + e.attr("class"));
        if(e.hasClass("custom-select-w3c")){ //se il figlio del <div> e' uno <div class"custom-select-w3c">...
            for(var j=0; j< qr.length; j++){
                var f = qr.eq(j); //cerca <span class="tf_span">...
                if(f.hasClass("select_span")){
                    f.text(e.children().val()); //dammi il valore della <select>
                }
            }
        }
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            for(var j=0; j< qr.length; j++){
                var f = qr.eq(j); //cerca <span class="tf_span">...
                if(f.hasClass("tf_span")){
                    f.text(e.val()); //dammi il valore della <input>
                }
            }
        }
        if(e.hasClass("numberfield")){ //se il figlio del <div> e' uno <input class"numberfield">...
            if (e.attr("fascia_oraria_min")) {
                for (var j = 0; j < qr.length; j++) {
                    var f = qr.eq(j); //cerca <span class="district_tf">...
                    if (f.hasClass("fascia_or_nm_min")) {
                        f.text(e.val()); //dammi il valore della <input>
                    }
                }
            }else if (e.attr("fascia_oraria_max")){
                for (var j = 0; j < qr.length; j++) {
                    var f = qr.eq(j); //cerca <span class="district_tf">...
                    if (f.hasClass("fascia_or_nm_max")) {
                        f.text(e.val()); //dammi il valore della <input>
                    }
                }
            }
        }

    }
    $(".result_page_name").text($(".query_text_for_result").text());
}

function sendRequestAndObtainResponseQuery(){

    var idquery = $(".query_legend").text(); //dammi id della query selezionata
    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("custom-select-w3c")){ //se il figlio del <div> e' uno <div class"custom-select-w3c">...
            a.select =  e.children().val(); //dammi il valore della <select>
        }
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            a.textfield = e.val(); //dammi il valore della <select>
        }
        if(e.hasClass("numberfield")) { //se il figlio del <div> e' uno <input class"numberfield">...
            if (e.attr("fascia_oraria_min")){
                a.numfieldmin = e.val(); //dammi il valore della <select>
            }
            if (e.attr("fascia_oraria_max")){
                a.numfieldmax = e.val(); //dammi il valore della <select>
            }
        }

    }

    $.post("crime-contr", {"action": idquery, "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var flag = o["crime0"];
            if(flag !== "noresult"){
                var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
                var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle

                for (var i = 0; i < size; i++) {
                    var k = o["crime" + i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito
                    str += "<div class=\"row\"><div class=\"cell\">" + k.incidentNumber + "</div><div class=\"cell\">" + k.offenseCode + "</div>" +
                        "<div class=\"cell\">" + k.offenseCodeGroup + "</div>" + "<div class=\"cell\">" + k.offenseDescription + "</div>"
                        + "<div class=\"cell\">" + k.district + "</div>" + "<div class=\"cell\">" + k.reportingArea + "</div>"
                        + "<div class=\"cell\">" + k.shooting + "</div>" + "<div class=\"cell\">" + k.occurredOnDate + "</div>"
                        + "<div class=\"cell\">" + k.UCR_Part + "</div>" + "<div class=\"cell\">" + k.street + "</div>"
                        + "<div class=\"cell\">" + k.lat + "</div>" + "<div class=\"cell\">" + k.Long + "</div></div>";

                }
                $("#table_header").after(str);
                $(".container-table100").show();
            }else{
                $(".container-table100").hide();
                $(".noresult_p").show();
            }

        }

    });
}

function isRequestSearch(){
    var rbtn = $(":checked"); //prendi tutti gli element "checked" (es. un radio button)
    var d = rbtn.parent(); //dammi il padre di <input> selezionato
    var flag = false;

    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...

            flag = true;
        }
    }
    return flag;
}

function sendSearchRequest(){
    var rbtn = $(":checked"); //prendi tutti gli element "checked" (es. un radio button)
    var d = rbtn.parent(); //dammi il padre di <input> selezionato
    var s = ""; //stringa che contiene gli input dell'utente
    var t = ""; //campo da cercare
    var j = 1; //count dei parametri da passare alla request
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            s+= "" + e.val()+"";
            t+= "" + e.attr("name");
        }
    }

    $.post("crime-contr", {"action": "search", "key" : t, "value" : s}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp);
            if(!o.done) {
                alert("Valore non trovato! Si prega di inserire un altro valore di ricerca");
            }else{
                showResultPage();
            }
        }
    });
}

//Validate function
function validateFasciaOraria(item){
    var x = item.val(); //dammi il valore
    var y = item.attr("name"); //dammi il valore di attr. "name" per sapere che tipo di field
    var val = false;
    if(y === "fascia_oraria_min"){
        var z = item.next().next();
        if(x > z.val()){
            alert("Primo valore della fascia oraria deve essere minore del secondo!!");
            item.addClass("numberfield_err");
        }else if(x == z.val()){
            alert("I due valori devono essere diversi!!");
            item.addClass("numberfield_err");
            z.addClass("numberfield_err");
        }else{
            item.removeClass("numberfield_err");
            z.removeClass("numberfield_err");
            val = true;
        }
    }else if(y === "fascia_oraria_max"){
        var z = item.prev().prev();
        if(x < z.val()){
            alert("Primo valore della fascia oraria deve essere minore del secondo");
            item.addClass("numberfield_err");
        }else{
            item.removeClass("numberfield_err");
            z.removeClass("numberfield_err");
            val = true;
        }
    }
    return val;
}

/* funzioni di utilita' */
/* calcola il numero di proprieta' presenti in un oggetto */
function sizeObject(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};