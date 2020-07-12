



//Validate function
function validateFasciaOraria(item){

    var x = item.val(); //dammi il valore
    if(x == 0) x = 24; //il valore 0 indica le ore 24
    var y = item.attr("name"); //dammi il valore di attr. "name" per sapere che tipo di field
    var val = false;
    if(y === "fascia_oraria_min"){
        var z = item.next();
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
        var z = item.prev();
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


function styleForErrorTextInput(item){
    item.css("border","1px solid red");
}

//Validation Insert Form
function validateIncidentNumber(item, maxlenght, err) {
    var x = item.val();
    var re = /^[A-Za-z]{1}[0-9]{9}$/; //Ci deve essere al più una lettera e al massimo 9 interi
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
            if(cat !== "noresult"){
                $("#off_code_group").val(cat);
            }else{
                $("#off_code_group").val("");
                $("#off_code_group").prop('disabled', false);
            }
        }
    });
}

function validateDistrict(item, maxlenght, err) {
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
    var x = item.val();
    var val = false;
    if(x === "") { //errore campo vuoto
        styleForErrorTextInput(item);
        err.html("Campo obbligatorio");
    }else if(x.length > maxlenght){ //codice errore per stringa troppo lunga
        styleForErrorTextInput(item);
        err.html("Valore troppo lungo!! (max " + maxlenght + " caratteri)");
    }else{
        styleForErrorTextInput(item);
        err.html("Valore inserito non valido!! Es. B2, C11");err.empty();
        val = true;
    }
    return val;
}

function validateOffenseDescription(item, maxlenght, err) { //controlla solamente la lunghezza dei caratteri inseriti
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

function validateOccurredDate(item, err) {
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

function validateStreet(item, maxlenght, err) { //controlla solamente la lunghezza dei caratteri inseriti
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
    var x = item.val();
    var re = /^[\-]{0,1}[0-9]{2,2}[\.]{1}[0-9]{8,8}$/; //Ci deve essere al più '-', 2 interi, un ".", 8 interi
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
        err.html("Valore inserito non valido!! Es. 123");
    }
    return val;
}

function validateLatitude(item, maxlenght, err) {
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
        err.html("Valore inserito non valido!! Es. (-)12.34567890 (range -180, 180");
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
                        if(validateOccurredDate($("#datetime_ins"), $('.crime_ins_txt_err').eq(6))){	//verifica se occorre inserire il numero di uova
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
        alert("Ci stanno uno o piu' campi che presentano degli errori!! Non si puo' proseguire");
        return false;
    }
}

function validateSelectCategory(item, err) { //controlla solamente la lunghezza dei caratteri inseriti
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
        case "Query 13": if(validateDistrict($("#district_q13"), 3, $("#distr_q13_err"))) val=true; break;
        case "Query 14": if(validateDistrict($("#district_q14"), 3, $("#distr_q14_err"))) val=true; break;
        case "Query 15": if((validateDistrict($("#district_q15"), 3, $("#distr_q15_err"))) && (validateSelectCategory($("#cat_q15"),  $("#cat_q15_err"))) ) val=true; break;
    }
    return val;
}