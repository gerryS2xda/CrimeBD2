
function loadpiechart(respdata) { //respdata (dati provenienti da servlet)

    var o = JSON.parse(respdata); //conversione in oggetto JS da strina JSON ricevuta da servlet
    var size = sizeObject(o);

    if(size > 10) size = 10; //limitazione del risultato

    //Compute "Other"
    var z = 0;
    for(var i = 0; i<size; i++){
        z+= o["crime"+i].percentage;
    }
    var otherperc = 100 - z;

    //Crea array per datapoint
    var vect = new Array();
    for(var j = 0; j < size; j++){
        vect[j] = { y: o["crime"+j].percentage, name: o["crime"+j].category };
    }
    vect[size] = { y: otherperc, name: "Other" };

    var chart = new CanvasJS.Chart("chartContainer", {
        exportEnabled: true,
        animationEnabled: true,
        width: 700,
        height: 600,
        title: {
            text: " "
        },
        legend: {
            cursor: "pointer",
            itemclick: explodePie,
            fontSize: 12
        },
        data: [{
            type: "pie",
            indexLabelFontSize: 11,
            radius: 140,
            showInLegend: true,
            toolTipContent: "{name}: <strong>{y}%</strong>",
            indexLabel: "{name} - {y}%",
            dataPoints: vect
        }]
    });

    chart.render();
}

function explodePie (e) {
    if(typeof (e.dataSeries.dataPoints[e.dataPointIndex].exploded) === "undefined" || !e.dataSeries.dataPoints[e.dataPointIndex].exploded) {
        e.dataSeries.dataPoints[e.dataPointIndex].exploded = true;
    } else {
        e.dataSeries.dataPoints[e.dataPointIndex].exploded = false;
    }
    e.chart.render();

}