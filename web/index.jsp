<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <title>BPD Report</title>
 <!--=================================================================================================-->
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/animate.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/select2.min.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/perfect-scrollbar.css">
  <link rel="stylesheet" type="text/css" href="./css/table_bootstrap/util.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/main.css">
  <link type="text/css" rel="stylesheet" href="css/select-bootstrap3-min.css" />
  <link type="text/css" rel="stylesheet" href="./css/indexStyle.css" />
  <!--================================================================================================-->
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
  <header>
    <!-- barra di navigazione -->
  </header>
  <section id="left_sidebar"> </section>
  <section id="main_content">
    <img id="police_img" src="images/police_distintivo.png" alt="Boston Police Department" />
    <p id="main_text"> Boston Police Department Report </p>
    <div id="select_query_page">
      <div id="content_popup">
        <div id="content_popup_area">
          <h1>Inserimento incidente/reato</h1>
          <label class="label_popup">Incident number </label><input type="text" class="inputfield_pop" id="inc_number" placeholder="(es. I192012345)"><br>
          <label class="label_popup">Offense code </label><input type="number" class="numberfield_pop" id="off_code" placeholder="(es. 1234)"> <br>
          <label class="label_popup">Offense code group </label><input type="text" class="inputfield_pop" id="off_code_group" placeholder="(es. Auto Theft)"><br>
          <label class="label_popup">Offense description </label><input type="text" class="inputfield_pop" id="off_code_desc" placeholder="(es. Auto Theft - Scooter)"><br>
          <label class="label_popup">District </label><input type="text" class="inputfield_pop" id="district_ins" placeholder="(es. A1)"><br>
          <label class="label_popup">Reporting area </label><input type="text" class="inputfield_pop" id="report_area_ins" placeholder="(es. 123)"><br>
          <label class="label_popup"> Shooting </label><select class="select_normal_style" id="shooting_ins"><option value="1">Sì</option><option value="0">No</option><option value="None">Non si sa</option></select>
          <label class="label_popup"> UCR part </label><select class="select_normal_style" id="ucr_path_in"><option value="Part One">Part One</option>
          <option value="Part Two">Part Two</option><option value="Part Three">Part Three</option>
          <option value="Other">Part Three</option></select><br>
          <label class="label_popup"> Date and time </label> <input type="datetime-local" class="datetime_input" id="datetime_ins"><br>
          <label class="label_popup"> Street </label><input type="text" class="inputfield_pop" id="street_ins" placeholder="(es. BOWDOIN ST)"><br>
          <label class="label_popup"> Latitude </label><input type="text" class="inputfield_pop" id="latitude_ins" placeholder="(es. 12.34567890)"><br>
          <label class="label_popup"> Longitude </label><input type="text" class="inputfield_pop" id="longitude_ins" placeholder="(es. 12.34567890)"><br>
          <div class="content_pop_button_area">
            <button id="insert_crime_btn" type="button" class="myButton">Inserisci</button>
            <button id="annulla_crime_btn" type="button" class="myButton">Annulla</button>
          </div>
        </div>
      </div>
      <div id="form_layout">
        <div id="radio_btn_container">
          <p class="suggest_txt">Seleziona la query da eseguire</p>
          <form action="" method="POST">

              <select id="select_query" name="select_query">
                <option value="Query 1"> Mostra le informazioni relative ad un determinato incidente/reato </option>
                <option value="Query 2"> Visualizza reati/incidenti del giorno precedente </option>
                <option value="Query 3"> Reati con sparatoria nell'ultimo mese avvenuti in un dato distretto e in una data fascia oraria </option>
                <option value="Query 4"> Incidenti/reati avvenuti in una determinata street </option>
                <option value="Query 5"> Visualizza la categoria di incidenti/reati che avvengono maggiormente in un determinato distretto </option>
                <option value="Query 6"> Mostra in quale giorno della settimana avvengono più reati/incidenti di un deteminato tipo in un dato distretto </option>
                <option value="Query 7"> Incidenti/reati avvenuti in un determinato distretto e in una data fascia oraria </option>
                <option value="Query 8"> Visualizza l'ora in cui si verifica maggiormente un determinato tipo di incidente/reato </option>
                <option value="Query 9"> Inserimento di un incidente/reato </option>
                <option value="Query 10"> Incidenti/reati in base al valore di UCR e al distretto </option>
                <option value="Query 11"> Cancellazione mediante inserimento dell'Incident number </option>
                <option value="Query 12"> Per ogni ora visualizza il crimine che viene eseguito maggiormente in quel distretto </option>
                <option value="Query 13"> Mostra la percentuale di reati avvenuti in un dato distretto </option>
                <option value="Query 14"> Selezionato un punto sulla mappa, verificare gli incidenti che sono accaduti </option>
              </select>

            <fieldset id="fieldsetquery">
              <legend class="query_legend"> Query x</legend>
              <p class="query_sel_text"> Title </p>
              <div class="content_fieldset"></div>
              <div class="single_result_container"></div>
              <p class="noresult_p"> Nessun risultato da mostrare! </p>
              <div id="result_query1_container">
                <div class="container-table100_q1">
                  <div class="wrap-table100_q1">
                    <div class="table_q1"></div>
                  </div>
                </div>
              </div>
              <span class="query_text_for_result"></span> <!-- hide content for utility -->
            </fieldset>
          </form>
        </div>
      </div>
      <div class="btn_container">
        <a href="#" id="execute_query_btn" class="myButton">Esegui query</a>
        <a href="#" id="insert_query9_btn" class="myButton">Inserisci</a>
      </div>
    </div>
    <div id="result_content_page">
      <p class="result_page_name"> Query selezionata </p>
      <!-- table -->
      <div class="container-table100">
        <div class="wrap-table100">
          <div class="table">
            <div id="table_header" class="row header noHover">
              <div class="cell"> Incident number </div>
              <div class="cell"> Offense code </div>
              <div class="cell"> Offense code group</div>
              <div class="cell"> Offense description </div>
              <div class="cell district_header"> District </div>
              <div class="cell"> Reporting area </div>
              <div class="cell"> Shooting </div>
              <div class="cell"> Date and Time </div>
              <div class="cell ucr_header"> UCR part </div>
              <div class="cell street_header"> Street </div>
              <div class="cell lat_header"> Latitude </div>
              <div class="cell long_header"> Longitude </div>
            </div>
          </div>

        </div>
      </div>
      <!-- grafico -->
      <div class="btn_container">
        <a href="#" id="reset_btn" class="myButton">Reset</a>
      </div>
    </div>
    <div id="map_content_page">
      <p class="map_page_title"> Seleziona un punto sulla mappa, per verificare gli incidenti che sono accaduti</p>
      <!-- map content -->
      <!--The div element for the map -->
      <div id="map"></div>
      <!--<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d47196.6336442026!2d-71.06267002774499!3d42.325686117559286!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89e3652d0d3d311b%3A0x787cbf240162e8a0!2sBoston%2C%20Massachusetts%2C%20Stati%20Uniti!5e0!3m2!1sit!2sit!4v1594021951326!5m2!1sit!2sit" class="mapstyle_iframe" frameborder="0" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe> -->
      <div class="btn_container">
        <a href="#" id="back_query14_btn" class="myButton">Torna indietro</a>
      </div>
    </div>
    <div id="histogram_content_page">
      <p class="histogram_page_title"> Per ogni ora visualizza il crimine che viene eseguito maggiormente</p>
      <!-- histogram content -->
      <div id="my_dataviz"></div>
      <div class="btn_container">
        <a href="#" id="back_query12_btn" class="myButton">Torna indietro</a>
      </div>
    </div>
    <div id="piechart_content_page">
      <p class="pie_page_title"> Mostra la percentuale di reati avvenuti in un dato distretto </p>
      <!-- piechart content -->
      <div id="chartContainer"></div>
      <div class="btn_container">
        <a href="#" id="back_query13_btn" class="myButton">Torna indietro</a>
      </div>
    </div>
  </section>
  <section id="right_sidebar"> </section>
  <footer> </footer>
  <script type="text/javascript" src="script/jquery-3.5.1.js"></script>
  <script type="text/javascript" src="script/query_page_script.js"></script>
  <script type="text/javascript" src="script/validation_field_script.js"></script>
  <!--==========================================================================================-->
  <script src="script/table_bootstrap_js/popper.js"></script>
  <script src="script/table_bootstrap_js/bootstrap.min.js"></script>
  <script src="script/table_bootstrap_js/select2.min.js"></script>
  <script src="script/table_bootstrap_js/main.js"></script>
  <script src="script/custom_select_script.js"></script>
  <script src="script/selectize-min.js"></script>
  <script src="script/map_script.js"></script>
  <!--Load the API from the specified URL
      * The async attribute allows the browser to render the page while the API loads
      * The key parameter will contain your own API key (which is not needed for this tutorial)
      * The callback parameter executes the initMap() function
      -->
  <script async defer
          src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAqHYFWIhxhaA6Fd_HvnwqfCunXjrul8_k&callback=initMap">
  </script>
  <!-- histogram script
  <script src="script/d3histlib.js"></script>
  <script src="script/histogram_script.js"></script>
  -->
  <!-- PIE CHART script -->
  <script src="script/canvasjsmin.js"></script>
  <script src="script/piechart.js"></script>
  </body>
</html>
