// Initialize and add the map
function initMap() {
    // The location of Boston
    var boston = {lat: 42.35843, lng: -71.05977};
    // The map, centered at Boston
    var map = new google.maps.Map(
        document.getElementById('map'), {zoom: 4, center: boston});

    // The marker, positioned at Boston
    //var marker = new google.maps.Marker({position: uluru, map: map});

    // Create the initial InfoWindow.
    var infoWindow = new google.maps.InfoWindow(
        {content: 'Click the map to get Lat/Lng!', position: boston});
    infoWindow.open(map);

    // Configure the click listener.
    map.addListener('click', function(mapsMouseEvent) {
        // Close the current InfoWindow.
        infoWindow.close();

        var location = mapsMouseEvent.latLng.toString(); //dammi latitudine e longitudine selezionata da utente

        // Create a new InfoWindow.
        infoWindow = new google.maps.InfoWindow({position: location});
        infoWindow.setContent(location);
        infoWindow.open(map);

        //Invia request alla servlet della latitudine e longitudine selezionata
        $.post("crime-contr", {"action": "Query 13", "location" : location}, function(resp, statTxt, xhr){
            if(xhr.readyState == 4 && statTxt == "success"){
                var o = JSON.parse(resp); //conversione in oggetto JS da strina JSON ricevuta da servlet
                var flag = o["flag"];
                if(flag === "ok"){
                    //give me result a list of crime (mostra il risultato nella table)
                    alert("RISPOSTA OK");
                }else{
                    //gestisci errore

                }

            }

        });
    });
}
