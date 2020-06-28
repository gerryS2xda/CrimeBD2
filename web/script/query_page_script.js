
//Action button
$("#execute_query_btn").click(function(){
    $("#select_query_page").hide();
    $("#result_content_page").show();
    //Add function to set text of query select
    createAndSetQuerySelectString();
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

//Other function
$(document).ready(function () {
    $('select').selectize({
        sortField: 'text'
    });
});
