
function loadpiechart(respdata) { //respdata (dati provenienti da servlet)

    var o = JSON.parse(respdata); //conversione in oggetto JS da strina JSON ricevuta da servlet

    //Compute "Other"
    var x = 0;
    for(var i = 0; i<10; i++){
        x+= o["crime"+i].percentage;
    }
    var otherperc = 100 - x;

    var chart = new CanvasJS.Chart("chartContainer", {
        exportEnabled: true,
        animationEnabled: true,
        width: 600,
        height: 500,
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
            indexLabelFontSize: 12,
            radius: 160,
            showInLegend: true,
            toolTipContent: "{name}: <strong>{y}%</strong>",
            indexLabel: "{name} - {y}%",
            dataPoints: [
                {y: o["crime0"].percentage, name: o["crime0"].category, exploded: true},
                {y: o["crime1"].percentage, name: o["crime1"].category},
                {y: o["crime2"].percentage, name: o["crime2"].category},
                {y: o["crime3"].percentage, name: o["crime3"].category},
                {y: o["crime4"].percentage, name: o["crime4"].category},
                {y: o["crime5"].percentage, name: o["crime5"].category},
                {y: otherperc, name: "Other"},
                {y: o["crime6"].percentage, name: o["crime6"].category},
                {y: o["crime7"].percentage, name: o["crime7"].category},
                {y: o["crime8"].percentage, name: o["crime8"].category},
                {y: o["crime9"].percentage, name: o["crime9"].category}

            ]
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