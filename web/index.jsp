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
      <div id="form_layout">
        <div id="radio_btn_container">
          <p class="suggest_txt">Seleziona la query da eseguire</p>
          <form action="" method="POST">

              <select id="select_query" name="select_query">
                <option value="Query 1"> Visualizza reati/incidenti del giorno precedente </option>
                <option value="Query 2"> Reati con sparatoria nell'ultimo mese avvenuti in un dato distretto e in una data fascia oraria </option>
                <option value="Query 3"> Incidenti/reati avvenuti in una determinata street </option>
                <option value="Query 4"> Visualizza la categoria di incidenti/reati che avvengono maggiormente in un determinato distretto </option>
                <option value="Query 5"> Mostra in quale giorno della settimana avvengono più reati/incidenti di un deteminato tipo in un dato distretto </option>
                <option value="Query 6"> Incidenti/reati avvenuti in una determinato distretto e in una data fascia oraria </option>
                <option value="Query 7"> Visualizza l'ora in cui si verifica maggiormente un determinato tipo di incidente/reato </option>
                <!--<option value="Query 8"> Inserimento di un incidente/reato </option> -->
                <option value="Query 9"> Incidenti/reati in base al valore di UCR e al distretto </option>
                <option value="Query 10"> Cancellazione mediante inserimento dell'Incident number </option>
                <option value="Query 11"> Per ogni ora visualizza il crimine che viene eseguito maggiormente </option>
                <option value="Query 12"> Mostra la percentuale di reati avvenuti in un distretto </option>
                <option value="Query 13"> Selezionato un punto sulla mappa, verificare gli incidenti che sono accaduti </option>
              </select>

            <fieldset id="fieldsetquery">
              <legend class="query_legend"> Query x</legend>
              <p class="query_sel_text"> Title </p>
              <div class="content_fieldset"></div>
              <div class="single_result_container"></div>
              <p class="noresult_p"> Nessun risultato da mostrare! </p>
              <span class="query_text_for_result"></span> <!-- hide content for utility -->
            </fieldset>
          </form>
        </div>
      </div>
      <div class="btn_container">
        <a href="#" id="execute_query_btn" class="myButton">Esegui query</a>
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
              <div class="cell"> District </div>
              <div class="cell"> Reporting area </div>
              <div class="cell"> Shooting </div>
              <div class="cell"> Date </div>
              <div class="cell"> UCR part </div>
              <div class="cell"> Street </div>
              <div class="cell"> Latitude </div>
              <div class="cell"> Longitude </div>
            </div>
          </div>

        </div>
      </div>
      <!-- grafico -->
      <div class="btn_container">
        <a href="#" id="reset_btn" class="myButton">Reset</a>
      </div>
    </div>
  </section>
  <section id="right_sidebar"> </section>
  <footer> </footer>
  <script type="text/javascript" src="script/jquery-3.5.1.js"></script>
  <script type="text/javascript" src="script/query_page_script.js"></script>
  <!--==========================================================================================-->
  <script src="script/table_bootstrap_js/popper.js"></script>
  <script src="script/table_bootstrap_js/bootstrap.min.js"></script>
  <script src="script/table_bootstrap_js/select2.min.js"></script>
  <script src="script/table_bootstrap_js/main.js"></script>
  <script src="script/custom_select_script.js"></script>
  <script src="script/selectize-min.js"></script>
  </body>
</html>
