
//Main code of hist
// set the dimensions and margins of the graph
var margin = {top: 10, right: 30, bottom: 30, left: 40},
    width = 460 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

// append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
    .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform",
        "translate(" + margin.left + "," + margin.top + ")");

//Query11 with histogram script
var myJSONContent = ""; //usata per rimpiazzare contenuto del file "result/query11res.json"


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
            a.textfield = e.val(); //dammi il valore della <select>
        }
    }

    $.post("crime-contr", {"action": "Query 11", "input" : JSON.stringify(a)}, function(resp, statTxt, xhr){
        if(xhr.readyState == 4 && statTxt == "success"){
            myJSONContent = resp; //otteni array JSON come risposta
            $("#select_query_page").hide();
            $("#histogram_content_page").show();

            // get the data
            d3.json("query11res.json", function(data) {
                data = myJSONContent;

                // X axis: scale and draw:
                //ottiene valore max per le ascisse (quante categorie)

                var x = d3.scaleLinear()
                    .domain([0, 10])     // can use this instead of 1000 to have the max of data: d3.max(data, function(d) { return +d.price })
                    .range([0, width]);
                svg.append("g")
                    .attr("transform", "translate(0," + height + ")")
                    .call(d3.axisBottom(x));

                // set the parameters for the histogram
                var histogram = d3.histogram()
                    .value(function(d) { return d.offense_code_group; })   // I need to give the vector of value
                    .domain(x.domain())  // then the domain of the graphic
                    .thresholds(x.ticks(70)); // then the numbers of bins

                // And apply this function to data to get the bins
                var bins = histogram(data);

                // Y axis: scale and draw:
                var y = d3.scaleLinear()
                    .range([height, 0]);
                y.domain([0, d3.max(bins, function(d) { return d.hour; })]);   // d3.hist has to be called before the Y axis obviously
                svg.append("g")
                    .call(d3.axisLeft(y));

                // append the bar rectangles to the svg element
                svg.selectAll("rect")
                    .data(bins)
                    .enter()
                    .append("rect")
                    .attr("x", 1)
                    .attr("transform", function(d) { return "translate(" + x(d.x0) + "," + y(d.length) + ")"; })
                    .attr("width", function(d) { return x(d.x1) - x(d.x0) -1 ; })
                    .attr("height", function(d) { return height - y(d.length); })
                    .style("fill", "#69b3a2")

            });
        }

    });
}
