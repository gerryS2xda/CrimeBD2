//variabili globali
var optioncategory = ""; //conterra' il codice html per mostare tutte le category (scelta impiegata per richiedere una sola volta la category alla servlet)


//main function
$(document).ready(function(){
    $("#select_query").trigger("change");
    sendRequestForInitCategorySelect();

});

function sendRequestForInitCategorySelect(){

    $.post("crime-contr", {"action": "init_select_category_incident"}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var flag = o["category0"];
            if(flag !== "noresult"){
                var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
                for (var i = 0; i < size; i++) {
                    var k = o["category" + i];	//prendi l'oggetto JS associato alla proprieta' 'prod' dell'oggetto JS appena convertito
                    optioncategory += "<option value=\"" + k + "\">" + k + "</option>";

                }
            }else{
                optioncategory += "<option value=\"NoContent\"> Nessun contenuto da mostrare</option>";
            }

        }

    });
}

//Action button
$("#execute_query_btn").click(function(){

    //Prima di proseguire, nascondi tutti gli elementi relativi alle query già formulate
    $(".noresult_p").hide();
    $(".single_result_container").hide();
    $("#result_query1_container").hide();

    //Esegui codice dedicato sulla base del numero di query
    var querynum = $(".query_legend").text();

    if(!validationSingleQuery(querynum)){
        return;
    }

    //Mostra il caricamento
    $("#loader_load_query").show();
    
    if(querynum === "Query 1"){
        sendRequestAndResponseForQuery1();
    }else if(querynum == "Query 11"){
        sendRequestQuery11();
    } else if(querynum === "Query 12"){
        createAndSetQuerySelectString();
        sendRequestQuery12();
    }else if(querynum === "Query 13"){
        createAndSetQuerySelectString();
        sendRequestQuery13();
    }else if(querynum === "Query 14"){
        createAndSetQuerySelectString();
        sendRequestQuery14();
    }else{
        createAndSetQuerySelectString();
        sendRequestAndObtainResponseQuery();
    }

});

$("#reset_btn").click(function(){
    $("#select_query").val("Query 1");
    $("#select_query").trigger("change");
    $("#select_query_page").show();
    $("#result_content_page").hide();

    resetResultPage();
    $("#loader_load_query").hide();
});

function resetResultPage(){

    var str = "<div id=\"table_header\" class=\"row header noHover\">" +
        "<div class=\"cell\"> Incident number </div><div class=\"cell\"> Offense code </div>" +
        "<div class=\"cell\"> Offense code group</div><div class=\"cell\"> Offense description </div>" +
        "<div class=\"cell district_header\"> District </div><div class=\"cell\"> Reporting area </div>" +
        "<div class=\"cell\"> Shooting </div><div class=\"cell\"> Date and Time </div>\n" +
        "<div class=\"cell ucr_header\"> UCR part </div><div class=\"cell street_header\"> Street </div>\n" +
        "<div class=\"cell lat_header\"> Latitude </div><div class=\"cell long_header\"> Longitude </div></div>";
    $(".table").html(str);
    $(".noresult_p").hide();
    $(".single_result_container").hide();
}

//General function
$("#select_query").change(function(){
    var query = $(this).val(); //dammi il contenuto di value della select
    var selectedText = $("#select_query option:selected").html();
    selectedText = selectedText.substring(4, selectedText.length);

    $(".query_legend").text(query);
    $(".query_sel_text").text(selectedText);
    $(".content_fieldset").html(createContentForFieldSet(query, selectedText));
    $(".single_result_container").hide();
    $(".noresult_p").hide();
    $(".cust_sel").selectize({
        sortField: 'text'
    });

    otherSettingsForQuery(query);

    //reset code
    $("#result_query1_container").hide();
    $("#loader_load_query").hide();
});

function otherSettingsForQuery(querynum){
    if(querynum === "Query 9"){
        showInsertContentPopup();
        $("#execute_query_btn").hide();
        $("#insert_query9_btn").show();
    }else{
        $("#execute_query_btn").show();
        $("#insert_query9_btn").hide();
    }

}

function createContentForFieldSet(querynum, selectedText){
    var str = "";
    if(querynum === "Query 1"){
        str+= "<label>Incident number</label><input type=\"text\" id=\"incident_q1\" class=\"inputfield\" name=\"incidentnumber\" placeholder=\"(es. I92097173)\" onblur=\"validateIncidentNumber($(this), 10, $('#inc_q1_err'))\"> <span id=\"inc_q1_err\" class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Mostra le informazioni relative ad un incidente/reato \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 2"){
        str+= "<p class=\"label_p\"> Nessun input richiesto </p>";
        $(".query_text_for_result").html(selectedText);
    }
    if(querynum === "Query 3"){
        str+= "<label>Distretto</label><input type=\"text\" id=\"district_q3\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q3_err'))\"> <span id=\"distr_q3_err\" class=\"crime_query_txt_err\"> </span><br>" +
            "<label>Fascia oraria</label><input type=\"number\" id=\"fascia_min_q3\" class=\"numberfield\" name=\"fascia_oraria_min\" min=\"0\" max=\"23\" value=\"13\" onblur='validateFasciaOraria($(this))'> - " +
            "<input type=\"number\" id=\"fascia_maxq3\" class=\"numberfield\" name=\"fascia_oraria_max\" min=\"0\" max=\"23\" value=\"15\" onblur='validateFasciaOraria($(this))'> <span class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Reati con sparatoria nell'ultimo mese avvenuti nel distretto \"<span class=\"tf_span\"></span>\" e in una data fascia oraria \"<span class=\"fascia_or_nm_min\"> </span> - <span class=\"fascia_or_nm_max\"> </span>\"");
    }
    if(querynum === "Query 4"){
        str+="<label>Street</label><input type=\"text\" id=\"street_q4\" class=\"inputfield\" name=\"street\" placeholder=\"(es. GIBSON ST)\" onblur=\"validateStreet($(this), 20, $('#street_q4_err'))\"> <span id=\"street_q4_err\" class=\"crime_query_txt_err\"></span>"
        $(".query_text_for_result").html("Incidenti/reati avvenuti nella street \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 5"){
        str+= "<label>Distretto</label><input type=\"text\" id=\"district_q5\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q5_err'))\"> <span id=\"distr_q5_err\" class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Visualizza la categoria di incidenti/reati che avvengono maggiormente nel distretto \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 6"){
        str+= "<label>Tipo di incidente/reato </label><div class=\"custom-select-w3c\">" +
            "<select id=\"cat_q6\" class=\"cust_sel\" placeholder=\"Pick a state...\" onblur=\"validateSelectCategory($(this), $('#cat_q6_err'))\"><option value=\"\">Select a state...</option>" + optioncategory + "</select></div> <span id=\"cat_q6_err\" class=\"crime_query_txt_err\"> </span> <br>" +
            "<label>Distretto</label><input type=\"text\" id=\"distr_q6\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\"> <span id=\"distr_q6_err\" class=\"crime_query_txt_err\"> </span>";
        $(".query_text_for_result").html("Mostra in quale giorno della settimana avvengono più reati/incidenti di tipo \"<span class=\"select_span\"> </span>\" nel distretto \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 7"){
        str+= "<label>Distretto </label> <input type=\"text\" id=\"district_q7\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q7_err'))\"> <span id=\"distr_q7_err\" class=\"crime_query_txt_err\"> </span> <br>" +
            "<label>Fascia oraria </label> <input type=\"number\" id=\"fascia_min_q7\" class=\"numberfield\" name=\"fascia_oraria_min\" min=\"0\" max=\"23\" value=\"13\" onblur='validateFasciaOraria($(this))'> - " +
            "<input type=\"number\" id=\"fascia_max_q7\" class=\"numberfield\" name=\"fascia_oraria_max\" min=\"0\" max=\"23\" value=\"15\" onblur='validateFasciaOraria($(this))'> <span  class=\"crime_query_txt_err\"> </span>";
        $(".query_text_for_result").html("Incidenti/reati avvenuti nel distretto \"<span class=\"tf_span\"></span>\" e nella fascia oraria \"<span class=\"fascia_or_nm_min\"> </span> - <span class=\"fascia_or_nm_max\"></span>\"");
    }
    if(querynum === "Query 8"){
        str+= "<label>Tipo di incidente/reato </label><div class=\"custom-select-w3c\">" +
            "<select id=\"cat_q8\" class=\"cust_sel\" placeholder=\"Pick a state...\" onblur=\"validateSelectCategory($(this), $('#cat_q8_err'))\"><option value=\"\">Select a state...</option>" + optioncategory +
            "</select></div> <span class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Visualizza l'ora in cui si verifica maggiormente un incidente/reato di tipo \"<span id=\"cat_q8_err\" class=\"select_span\"> </span>\"");
    }
    //query 9 -> Insert (non necessita di questo)
    if(querynum === "Query 10"){
        str+= "<label>Distretto </label> <input type=\"text\" id=\"district_q10\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q10_err'))\"> <span id=\"distr_q10_err\" class=\"crime_query_txt_err\"></span> <br>" +
            "<label> UCR </label><div class=\"custom-select-w3c\">" +
            "<select id=\"ucr_q10\" class=\"cust_sel\" placeholder=\"Pick a state...\"><option value=\"\">Select a state...</option>" +
            "<option value=\"Part One\">Part One</option><option value=\"Part Two\">Part Two</option>" +
            "<option value=\"Part Three\">Part Three</option><option value=\"Other\">Other</option></select></div> <span id=\"ucr_q10_err\" class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Incidenti/reati in base al valore di UCR \"<span class=\"select_span\"> </span>\" e al distretto \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 11"){
        str+= "<label>Incident number</label><input type=\"text\" id=\"incident_q11\" class=\"inputfield\" name=\"incidentnumber\" placeholder=\"(es. I92097173)\" onblur=\"validateIncidentNumber($(this), 10, $('#incnum_q11_err'))\"> <span id=\"incnum_q11_err\" class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Cancellazione mediante inserimento dell'Incident number \"<span class=\"tf_span\"></span>\"");
    }
    if(querynum === "Query 12"){
        str+= "<label>Distretto </label> <input type=\"text\" id=\"district_q12\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q12_err'))\"> <span id=\"distr_q12_err\" class=\"crime_query_txt_err\"></span> <br>";
        $(".query_text_for_result").html("Per ogni ora visualizza il crimine che viene eseguito maggiormente nel distretto \"<span class=\"tf_span\"> </span>\"");
    }
    if(querynum === "Query 13"){
        str+= "<label>Street</label><input type=\"text\" id=\"street_q13\" class=\"inputfield\" name=\"street\" placeholder=\"(es. GIBSON ST)\" onblur=\"validateStreet($(this), 50, $('#street_q13_err'))\"> <span id=\"street_q13_err\" class=\"crime_query_txt_err\"></span><br>";
        $(".query_text_for_result").html("Mostra la percentuale di reati avvenuti nella strada \"<span class=\"tf_span\"> </span>\"");
    }
    if(querynum === "Query 14"){
        str+= "<label>Distretto </label> <input type=\"text\" id=\"district_q14\" class=\"inputfield\" name=\"distretto\" placeholder=\"(es. E13)\" onblur=\"validateDistrict($(this), 3, $('#distr_q14_err'))\"> <span id=\"distr_q14_err\" class=\"crime_query_txt_err\"></span> <br>" +
        "<label> Ora </label> <input type=\"number\" class=\"numberfield\" name=\"fascia_oraria_min\" min=\"0\" max=\"23\" value=\"13\"> <span class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Mostra i crimini avvenuti nel distretto \"<span class=\"tf_span\"> </span>\" alle ore \"<span class=\"fascia_or_nm_min\"> </span>\"");
    }
    if(querynum === "Query 15"){
        str+= "<label>Street</label><input type=\"text\" id=\"street_q15\" class=\"inputfield\" name=\"street\" placeholder=\"(es. GIBSON ST)\" onblur=\"validateStreet($(this), 50, $('#street_q15_err'))\"> <span id=\"street_q15_err\" class=\"crime_query_txt_err\"></span> <br>" +
            "<label>Tipo di incidente/reato </label><div class=\"custom-select-w3c\">" +
            "<select id=\"cat_q15\" class=\"cust_sel\" placeholder=\"Pick a state...\"><option value=\"\">Select a state...</option>" + optioncategory +
            "</select></div> <span class=\"crime_query_txt_err\"></span>";
        $(".query_text_for_result").html("Mostra i crimini avvenuti nella strada \"<span class=\"tf_span\"> </span>\" alle ore \"<span id=\"cat_q15_err\" class=\"select_span\"> </span>\"");
    }
    return str;
}



//function per costruire e settare la stringa relativa alla query selezionata
function createAndSetQuerySelectString(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato
    var qr = $(".query_text_for_result").children(); //dammi tutti i figli di tale <span> per creare la stringa

    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <fieldset> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
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
            if (e.attr("name") === "fascia_oraria_min") {
                for (var j = 0; j < qr.length; j++) {
                    var f = qr.eq(j); //cerca <span class="district_tf">...
                    if (f.hasClass("fascia_or_nm_min")) {
                        f.text(e.val()); //dammi il valore della <input>
                    }
                }
            }else if (e.attr("name") === "fascia_oraria_max"){
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
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
        }
        if(e.hasClass("numberfield")) { //se il figlio del <div> e' uno <input class"numberfield">...
            if (e.attr("name") === "fascia_oraria_min"){
                a.numfieldmin = e.val(); //dammi il valore della <select>
            }
            if (e.attr("name") === "fascia_oraria_max"){
                a.numfieldmax = e.val(); //dammi il valore della <select>
            }
        }

    }

    $.post("crime-contr", {"action": idquery, "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            //Crea tabella risultato, ma prima verifica se vi sono risultati
            var flag = o["crime0"];
            if(flag === "oneresult"){
                createSingleResultContent(o["crime1"]);
            }else if(flag !== "noresult"){
                var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
                var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle

                for (var i = 0; i < size; i++) {
                    var k = o["crime" + i];	//prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
                    str += "<div class=\"row\"><div class=\"cell\">" + k.incidentNumber + "</div><div class=\"cell\">" + k.offenseCode + "</div>" +
                        "<div class=\"cell\">" + k.offenseCodeGroup + "</div>" + "<div class=\"cell\">" + k.offenseDescription + "</div>"
                        + "<div class=\"cell district_row\">" + k.district + "</div>" + "<div class=\"cell\">" + k.reportingArea + "</div>"
                        + "<div class=\"cell\">" + k.shooting + "</div>" + "<div class=\"cell\">" + k.occurredOnDate + "</div>"
                        + "<div class=\"cell ucr_row\">" + k.UCR_Part + "</div>" + "<div class=\"cell street_row\">" + k.street + "</div>"
                        + "<div class=\"cell lat_row\">" + k.lat + "</div>" + "<div class=\"cell long_row\">" + k.Long + "</div></div>";

                }
                $("#table_header").after(str);

                //Prima di mostrara la table, verifica se ci sono colonne da rimuovere
                hideColumnOfTable(flag);

                $(".container-table100").show();

                //mostra la result page solo per la table (per ora)
                $("#select_query_page").hide();
                $("#result_content_page").show();
            }else{
                $(".container-table100").hide();
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }

    });
}

function hideColumnOfTable(obj){

    if(obj.district === "distretto"){
        $(".district_header").addClass("hidecolumn");
        $(".district_row").addClass("hidecolumn");
    }
    if(obj.street  === "street"){
        $(".street_header").addClass("hidecolumn");
        $(".street_row").addClass("hidecolumn");
    }
    if(obj.UCR_Part  === "ucr"){
        $(".ucr_header").addClass("hidecolumn");
        $(".ucr_row").addClass("hidecolumn");
    }
}

function createSingleResultContent(item){
    $(".single_result_container").show();
    var str = "<label>Risultato </label> <input type=\"text\" class=\"resultinputfield\" name=\"result\" disabled value=\"" + item +"\">";
    $(".single_result_container").html(str);
}

//FUNCTIONS DEDICATE PER QUERY "PARTICOLARI"

//functions for query 1
function sendRequestAndResponseForQuery1(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i);
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
        }
    }

    $.post("crime-contr", {"action": "Query 1", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet

            //Crea una tabella risultato con una sola riga (risultato query 1)
            var flag = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
            if(flag !== "noresult"){
                $(".noresult_p").hide(); //nascondi se presente
                //crea html per la table
                var tableheader = "<div id=\"table_header2\" class=\"row header noHover\">" +
                    "<div class=\"cell\"> Offense code </div>" +
                    "<div class=\"cell\"> Offense code group</div><div class=\"cell\"> Offense description </div>" +
                    "<div class=\"cell district_header\"> District </div><div class=\"cell\"> Reporting area </div>" +
                    "<div class=\"cell\"> Shooting </div><div class=\"cell\"> Date and Time </div>\n" +
                    "<div class=\"cell ucr_header\"> UCR part </div><div class=\"cell street_header\"> Street </div>\n" +
                    "<div class=\"cell lat_header\"> Latitude </div><div class=\"cell long_header\"> Longitude </div></div>";

                var tablebody = "<div class=\"row\"><div class=\"cell\">" + flag.offenseCode + "</div>" +
                        "<div class=\"cell\">" + flag.offenseCodeGroup + "</div>" + "<div class=\"cell\">" + flag.offenseDescription + "</div>"
                        + "<div class=\"cell district_row\">" + flag.district + "</div>" + "<div class=\"cell\">" + flag.reportingArea + "</div>"
                        + "<div class=\"cell\">" + flag.shooting + "</div>" + "<div class=\"cell\">" + flag.occurredOnDate + "</div>"
                        + "<div class=\"cell ucr_row\">" + flag.UCR_Part + "</div>" + "<div class=\"cell street_row\">" + flag.street + "</div>"
                        + "<div class=\"cell lat_row\">" + flag.lat + "</div>" + "<div class=\"cell long_row\">" + flag.Long + "</div></div>";
                $(".table_q1").html(tableheader);
                $("#table_header2").after(tablebody);

                $("#result_query1_container").show();

            }else{
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }

    });
}


//funzioni per la query 9 (inserimento)
$("#insert_query9_btn").click(function(){
    showInsertContentPopup();
});

function showInsertContentPopup(){
    $("#content_popup").show();
    $("#content_popup").addClass("popup_body");
    $(".single_result_container").hide(); //ogni volta che viene aperto il popup, nascondi risultato precedente
}

function resetContentPopup(){
    $("#inc_number").val(""); //string
    $("#off_code").val(""); //int
    $("#off_code_group").val(""); //string
    $("#off_code_desc").val(""); //string
    $("#district_ins").val(""); //string
    $("#report_area_ins").val(""); //string
    $("#shooting_ins").val("1"); //string  (1 = sì, 0 = no, "None" = non si sa)
    $("#ucr_path_in").val("Part One"); //string
    $("#street_ins").val(""); //string
    $("#latitude_ins").val(""); //double
    $("#longitude_ins").val(""); //longitude
}

$("#insert_crime_btn").click(function(){

    if(formInserimentoValidation()){
        removeContentPopup();
        sendRequestForInsert();
        resetFormInsert();
    }
});

$("#annulla_crime_btn").click(function(){
    removeContentPopup();
    resetFormInsert();
});

function resetFormInsert(){
    document.insert_form.reset();
    $("#inc_number").css("border", "1px solid #ccc");
    $("#off_code").css("border", "1px solid #ccc");
    $("#off_code_group").css("border", "1px solid #ccc");
    $("#off_code_desc").css("border", "1px solid #ccc");
    $("#district_ins").css("border", "1px solid #ccc");
    $("#report_area_ins").css("border", "1px solid #ccc");
    $("#datetime_ins").css("border", "1px solid #ccc");
    $("#street_ins").css("border", "1px solid #ccc");
    $("#latitude_ins").css("border", "1px solid #ccc");
    $("#longitude_ins").css("border", "1px solid #ccc");
    $('.crime_ins_txt_err').html("");
}

function removeContentPopup(){
    $("#content_popup").removeClass("popup_body");
    $("#content_popup").hide();
}

function sendRequestForInsert(){  //Query 9

    var occuredOnDate =  $("#datetime_ins").val();

    var a = new Object(); //construct JSON object Crime (convert it in Java Object in Servlet
    a.incidentNumber = $("#inc_number").val(); //string
    a.offenseCode = $("#off_code").val(); //int
    a.offenseCodeGroup = $("#off_code_group").val(); //string
    a.offenseDescription = $("#off_code_desc").val(); //string
    a.district = $("#district_ins").val().toUpperCase(); //string
    a.reportingArea = $("#report_area_ins").val(); //string
    a.shooting = $("#shooting_ins").val(); //string  (1 = sì, 0 = no, "None" = non si sa)
    //occuredOnDate e' di tipo LocaltDateTime  (passo come input)
    //a.year = 0;     a.month = 0 a.dayOfWeek = ""; //string
    a.UCR_Part = $("#ucr_path_in").val(); //string
    a.street = $("#street_ins").val(); //string
    a.lat = $("#latitude_ins").val(); //double
    a.Long = $("#longitude_ins").val(); //longitude

    $.post("crime-contr", {"action": "Query 9", "input" : JSON.stringify(a), "occuredOnDate": occuredOnDate}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var flag = o["crime0"];
            if(flag === "done"){
                $(".single_result_container").show();
                var str = "<p class=\"insert_suc_p\"> Inserimento effettuato con successo!</p>";
                $(".single_result_container").html(str);
            }else{
                $(".noresult_p").show();
            }
            resetContentPopup(); //reset popup quando riceve risposta
        }

    });
}

// script for query 11 (cancellazione)
function sendRequestQuery11(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
        }
    }

    $.post("crime-contr", {"action": "Query 11", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var flag = o["crime0"];
            if(flag === "done"){
                $(".single_result_container").show();
                var str = "<p class=\"insert_suc_p\"> Cancellazione eseguita con successo!</p>";
                $(".single_result_container").html(str);
            }else{
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }

    });
}



//script for hist query 12
function sendRequestQuery12(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
            $("#district_input_q12").text(e.val()); //salva il distretto inserito da utente, serve per la query 14
        }
    }

    $.post("crime-contr", {"action": "Query 12", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success") {
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet

            //Crea una tabella risultato con una sola riga (risultato query 12)
            var flag = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
            if(flag !== "noresult"){
                var size = sizeObject(o); //calcolo del numero di proprieta' presenti nell'oggetto
                var str = ""; //stringa che contiene codice HTML per la costruzione del contenuto delle tabelle

                for(var i = 0; i < size; i++){
                    var k = o["crime" + i];	//prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
                    str+= "<div class=\"row\"><div class=\"cell cell_new_hour_tbody\">" + k.hour + "</div>" +
                        "<div class=\"cell cell_new_offense_tbody\">" + k.offense_code_group +
                        "</div></div>";

                }
                $("#table_header3").after(str);
                $(".query12_page_title").html($(".query_text_for_result").text());
                $("#select_query_page").hide();
                $("#query12_content_page").show();

            }else{
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }
    });
}

$("#back_query12_btn").click(function(){
    $("#select_query").val("Query 1");
    $("#query12_content_page").hide();
    $("#select_query_page").show();
    $("#select_query").trigger("change");
});

//script for query 13
function sendRequestQuery13(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
        }
    }

    $.post("crime-contr", {"action": "Query 13", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success") {
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet

            //Crea una tabella risultato con una sola riga (risultato query 12)
            var flag = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
            if(flag !== "noresult") {
                loadpiechart(resp); //carica o costruisci il pie chart
                $("#select_query_page").hide();
                $(".pie_page_title").html($(".query_text_for_result").text());
                $("#piechart_content_page").show();
            }else{
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }
    });
}

$("#back_query13_btn").click(function(){
    $("#select_query").val("Query 1");
    $("#piechart_content_page").hide();
    $("#select_query_page").show();
    $("#select_query").trigger("change");
});

/* QUERY 14 */
function sendRequestQuery14(){

    var d = $(".content_fieldset"); //dammi il padre di <fieldset> selezionato

    var a = new Object();
    a.select = "";
    a.textfield = "";
    a.numfieldmin = 0;
    a.numfieldmax = 0;

    //var s = "{"; //stringa che contiene gli input dell'utente
    for(var i=0; i < d.children().length; i++){  //per tutti i figli del <div> relativo alla query selezionata
        var e = d.children().eq(i); //elemento html che si sta esaminando
        if(e.hasClass("inputfield")){ //se il figlio del <div> e' uno <input class"inputfield">...
            if(e.attr("name") === "distretto") {
                a.textfield = e.val().toUpperCase(); //dammi il valore della <select>
            }else{
                a.textfield = e.val();
            }
        }
        if(e.hasClass("numberfield")) { //se il figlio del <div> e' uno <input class"numberfield">...
            if (e.attr("name") === "fascia_oraria_min"){
                a.numfieldmin = e.val(); //dammi il valore della <select>
            }
        }
    }

    $.post("crime-contr", {"action": "Query 14", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success") {
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet

            //Crea una tabella risultato con una sola riga (risultato query 12)
            var flag = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
            if(flag !== "noresult") {
                loadbarchart(resp); //carica o costruisci il pie chart
                $("#select_query_page").hide();
                $(".query14_page_title").html($(".query_text_for_result").text());
                $("#query14_content_page").show();
            }else{
                $(".noresult_p").show();
            }
            $("#loader_load_query").hide();
        }
    });
}

$("#back_query14_btn").click(function(){
    $("#select_query").val("Query 1");
    $("#query14_content_page").hide();
    $("#select_query_page").show();
    $("#select_query").trigger("change");
});

/* funzioni di utilita' */
/* calcola il numero di proprieta' presenti in un oggetto */
function sizeObject(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
}