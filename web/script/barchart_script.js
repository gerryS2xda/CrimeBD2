
function loadbarchart(respdata) { //respdata (dati provenienti da servlet)

    var o = JSON.parse(respdata); //conversione in oggetto JS da strina JSON ricevuta da servlet
    var size = sizeObject(o);

    if(size > 7) size = 7; //limitazione del risultato

    //Crea array per datapoint
    var vect = new Array();
    for(var j = 0; j < size; j++){
        vect[j] = { y: o["crime"+j].frequency, label: o["crime"+j].category };
    }

    var chart = new CanvasJS.Chart("barchartContainer", {
        animationEnabled: true,
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        width: 900,
        height: 400,
        title:{
            text: " "
        },
        axisY: {
            title: "Numero di crimini"
        },
        axisX: {
            labelAngle: -20,
            labelMaxWidth: 100,
            labelWrap: true
        },
        data: [{
            type: "column",
            showInLegend: false,
            legendMarkerColor: "grey",
            legendText: "MMbbl = one million barrels",
            dataPoints: vect
        }]
    });
    chart.render();
}