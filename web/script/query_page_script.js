
//Action button
$("#execute_query_btn").click(function(){
    $("#select_query_page").hide();
    $("#result_content_page").show();
});

$("#reset_btn").click(function(){
    $("#select_query_page").show();
    $("#result_content_page").hide();
});

//Other function
$(document).ready(function () {
    $('select').selectize({
        sortField: 'text'
    });
});
