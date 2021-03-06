



//Validate function
function validateFasciaOraria(item){

    item.removeClass("numberfield_err");
    var x = parseInt(item.val()); //dammi il valore
    if(x == 0) x = 24; //il valore 0 indica le ore 24
    var y = item.attr("name"); //dammi il valore di attr. "name" per sapere che tipo di field
    var val = false;
    if(y === "fascia_oraria_min"){
        var z = parseInt(item.next().val());
        if(x > z){
            alert("Primo valore della fascia oraria deve essere minore del secondo!!");
            item.addClass("numberfield_err");
            $("#loader_load_query").hide();
        }else if(x == z){
            alert("I due valori devono essere diversi!!");
            item.addClass("numberfield_err");
            item.next().addClass("numberfield_err");
            $("#loader_load_query").hide();
        }else{
            item.removeClass("numberfield_err");
            item.next().removeClass("numberfield_err");
            val = true;
        }
    }else if(y === "fascia_oraria_max"){
        var z = parseInt(item.prev().val());
        if(x < z){
            alert("Primo valore della fascia oraria deve essere minore del secondo");
            item.addClass("numberfield_err");
            $("#loader_load_query").hide();
        }else{
            item.removeClass("numberfield_err");
            item.prev().removeClass("numberfield_err");
            val = true;
        }
    }
    return val;
}


function styleForErrorTextInput(item){
    item.css("border","1px solid red");
    $("#loader_load_query").hide();
}

//Validation Insert Form
function validateIncidentNumber(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[A-Za-z]{0,1}[0-9]{7,9}$/; //Ci deve essere al più una lettera e al massimo 9 interi
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. I192012345");
    }
    return val;
}

function validateOffenseCodeAndSetOffenseCodeGroup(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[0-9]{3,5}$/; //Ci deve essere almeno 3 numeri e al massimo 5
    var val = false;

    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
        setOffenseCodeGroup(x);
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. 123 o 1234");
    }

    return val;
}

function setOffenseCodeGroup(offcode){
    $("#off_code_group").prop('disabled', true);
    $.post("crime-contr", {"action": "getOffenseCategory", "input" : offcode}, function(resp, statTxt, xhr) {
        if (xhr.readyState == 4 && statTxt == "success") {
            var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
            var cat = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
            var cat_desc = o["crime1"];
            if(cat !== "noresult"){
                $("#off_code_group").val(cat);
                $("#off_code_desc").val(cat_desc);
            }else{
                $("#off_code_group").val("");
                $("#off_code_group").prop('disabled', false);
                $("#off_code_desc").val("");
                $("#off_code_desc").prop('disabled', false);
            }
            $('.crime_ins_txt_err').eq(2).html("");
            $('.crime_ins_txt_err').eq(3).html("");
            $("#off_code_group").css("border", "1px solid #ccc");
            $("#off_code_desc").css("border", "1px solid #ccc");
        }
    });
}

function validateDistrict(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[A-Za-z]{1}[0-9]{1,2}$/; //Ci deve essere al più una lettera e al massimo 2 interi
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. B2, C11");
    }
    return val;
}

function validateReportingArea(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[0-9]{2,3}$/; //Ci deve essere al più due interi su 3
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " interi)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. 123");
    }
    return val;
}

function validateOffenseCodeGroup(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else{
        err.empty();
        val = true;
    }
    return val;
}

function validateOffenseDescription(item, maxlenght, err) { //controlla solamente la lunghezza dei caratteri inseriti
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var val = false;
    var x = item.val();
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else {
        err.empty();
        val = true;
    }
    return val;
}

var flagdata = false; //variabile globale per memorizzare valore ottenuto da post
function validateOccurredDate(item, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else {
        $.post("crime-contr", {"action": "validateInputDate", "input" : x}, function(resp, statTxt, xhr){
            if(xhr.readyState == 4 && statTxt == "success") {
                var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet

                var flag = o["crime0"]; //prendi l'oggetto JS associato alla proprieta' 'crime' dell'oggetto JS appena convertito
                if(flag === "yes") {
                    err.empty();
                    flagdata = true;
                    return flagdata;
                }else{
                    styleForErrorTextInput(item);
                    err.html("Non inserire una data successiva a quella odierna!!");
                }
            }
        });
    }
    return flagdata;
}

function validateStreet(item, maxlenght, err) { //controlla solamente la lunghezza dei caratteri inseriti
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var val = false;
    var x = item.val();
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else {
        err.empty();
        val = true;
    }
    return val;
}

function validateLatitude(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[\-]{0,1}[0-9]{1,3}[\.]{1}[0-9]{2,8}$/; //Ci deve essere al più due interi su 3
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. (-)12.34567890 (range -90, +90)");
    }
    return val;
}

function validateLongitude(item, maxlenght, err) {
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var x = item.val();
    var re = /^[\-]{0,1}[0-9]{1,3}[\.]{1}[0-9]{2,8}$/; //Ci deve essere al più due interi su 3
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else if(x.match(re)){ //la stringa è conforme all'espressione regolare
        err.empty();
        item.css("border","1px solid green");
        val = true;
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! (-)12.34567890 (range -180, 180");
    }
    return val;
}

//Added formvalidation function
function formInserimentoValidation(){
    var flag = false;
    if(validateIncidentNumber($("#inc_number"), 10, $('.crime_ins_txt_err').eq(0))){
        if(validateOffenseCodeGroup($("#off_code_group"), 50, $('.crime_ins_txt_err').eq(2))){
            if(validateOffenseDescription($("#off_code_desc"), 50, $('.crime_ins_txt_err').eq(3))){
                if(validateDistrict($("#district_ins"), 3, $('.crime_ins_txt_err').eq(4))){
                    if(validateReportingArea($("#report_area_ins"), 3, $('.crime_ins_txt_err').eq(5))){
                        if(validateOccurredDate($("#datetime_ins"), $('.crime_ins_txt_err').eq(6))){
                            if(validateStreet($("#street_ins"), 50, $('.crime_ins_txt_err').eq(7))){
                                if(validateLatitude($("#latitude_ins"), 13, $('.crime_ins_txt_err').eq(8))){
                                    if(validateLongitude($("#longitude_ins"), 14, $('.crime_ins_txt_err').eq(9))){
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if(flag){
        return true;
    }else{
        alert("Ci sono uno o piu' campi che presentano degli errori!! Non si puo' proseguire");
        return false;
    }
}

function validateSelectCategory(item, err) { //controlla solamente la lunghezza dei caratteri inseriti
    item.css("border", "1px solid #ccc"); //reset in caso di errore
    var val = false;
    var x = item.val();
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else {
        err.empty();
        val = true;
    }
    return val;
}

function validationSingleQuery(querynum){
    var val = false;
    switch (querynum) {
        case "Query 1": if(validateIncidentNumber($("#incident_q1"), 10, $("#inc_q1_err"))) val=true; break;
        case "Query 2": val=true; break; //NO input
        case "Query 3": if((validateDistrict($("#district_q3"), 3, $("#distr_q3_err"))) &&
            (!$("#fascia_min_q3").hasClass("numberfield_err") || !$("#fascia_max_q3").hasClass("numberfield_err"))) val=true; break; //sistemare fascia oraria
        case "Query 4": if(validateStreet($("#street_q4"), 50, $("#street_q4_err"))) val=true; break;
        case "Query 5": if(validateDistrict($("#district_q5"), 3, $("#distr_q5_err"))) val=true; break;
        case "Query 6": if((validateSelectCategory($("#cat_q6"),  $("#cat_q6_err")) && validateDistrict($("#distr_q6"), 3, $("#distr_q6_err")))) val=true; break;
        case "Query 7": if(validateDistrict($("#district_q7"), 3, $("#distr_q7_err")) &&
            (!$("#fascia_min_q7").hasClass("numberfield_err") || !$("#fascia_max_q7").hasClass("numberfield_err"))) val=true; break;  //sistemare fascia oraria
        case "Query 8": if(validateSelectCategory($("#cat_q8"), $("#cat_q8_err"))) val=true; break;  //sistemare fascia oraria
        case "Query 10": if((validateDistrict($("#district_q10"), 3, $("#distr_q10_err")))) val=true; break;
        case "Query 11": if(validateIncidentNumber($("#incident_q11"), 10, $("#inc_q11_err"))) val=true; break;
        case "Query 12": if(validateDistrict($("#district_q12"), 3, $("#distr_q12_err"))) val=true; break;
        case "Query 13": if(validateStreet($("#street_q13"), 50, $("#street_q13_err"))) val=true; break;
        case "Query 14": if(validateDistrict($("#district_q14"), 3, $("#distr_q14_err"))) val=true; break;
        case "Query 15": if((validateStreet($("#street_q15"), 50, $("#street_q15_err"))) && (validateSelectCategory($("#cat_q15"),  $("#cat_q15_err"))) ) val=true; break;
    }
    return val;
}