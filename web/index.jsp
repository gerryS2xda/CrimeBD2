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
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="" checked="checked">
              <span class="span_txt_radio"> Visualizza reati/incidenti del giorno precedente </span>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Numero reati con sparatoria nell'ultimo mese avvenuti in  </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
              <span class="span_txt_radio"> nell'intervallo orario </span>
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Incidenti/reati avvenuti nella città di </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Visualizza la categoria di incidenti/reati che avvengono maggiormente nella città di </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Mostra in quali giorni della settimana avvengono reati/incidenti di tipo </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
              <span class="span_txt_radio"> nella città di  </span>
                <div class="custom-select-w3c">
                  <select placeholder="Pick a state...">
                    <option value="">Select a state...</option>
                    <option value="1">Value2</option>
                  </select>
                </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Incidenti/reati avvenuti nella città di </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
              <span class="span_txt_radio"> nella fascia oraria </span>
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Visualizza l'ora in cui si verifica maggiormente un incidente/reato di tipo </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Incidenti/reati in base al valore di UCR </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Verifica se vi sono incidenti/reati a distanza 3 sulla base di longitudine </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
              <span class="span_txt_radio"> e latitudine </span>
                <div class="custom-select-w3c">
                  <select placeholder="Pick a state...">
                    <option value="">Select a state...</option>
                    <option value="1">Value2</option>
                  </select>
                </div>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Incidenti/reati in base al valore di UCR </span>
              <!-- trovare suggerimenti -->
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
              <span class="span_txt_radio"> e la città di </span>
              <div class="custom-select-w3c">
                <select placeholder="Pick a state...">
                  <option value="">Select a state...</option>
                  <option value="1">Value2</option>
                </select>
              </div>
            </div>
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

            <div class="row header noHover">
              <div class="cell">
                Full Name
              </div>
              <div class="cell">
                Age
              </div>
              <div class="cell">
                Job Title
              </div>
              <div class="cell">
                Location
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Vincent Williamson
              </div>
              <div class="cell" data-title="Age">
                31
              </div>
              <div class="cell" data-title="Job Title">
                iOS Developer
              </div>
              <div class="cell" data-title="Location">
                Washington
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Joseph Smith
              </div>
              <div class="cell" data-title="Age">
                27
              </div>
              <div class="cell" data-title="Job Title">
                Project Manager
              </div>
              <div class="cell" data-title="Location">
                Somerville, MA
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Justin Black
              </div>
              <div class="cell" data-title="Age">
                26
              </div>
              <div class="cell" data-title="Job Title">
                Front-End Developer
              </div>
              <div class="cell" data-title="Location">
                Los Angeles
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Sean Guzman
              </div>
              <div class="cell" data-title="Age">
                25
              </div>
              <div class="cell" data-title="Job Title">
                Web Designer
              </div>
              <div class="cell" data-title="Location">
                San Francisco
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Keith Carter
              </div>
              <div class="cell" data-title="Age">
                20
              </div>
              <div class="cell" data-title="Job Title">
                Graphic Designer
              </div>
              <div class="cell" data-title="Location">
                New York, NY
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Austin Medina
              </div>
              <div class="cell" data-title="Age">
                32
              </div>
              <div class="cell" data-title="Job Title">
                Photographer
              </div>
              <div class="cell" data-title="Location">
                New York
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Vincent Williamson
              </div>
              <div class="cell" data-title="Age">
                31
              </div>
              <div class="cell" data-title="Job Title">
                iOS Developer
              </div>
              <div class="cell" data-title="Location">
                Washington
              </div>
            </div>

            <div class="row">
              <div class="cell" data-title="Full Name">
                Joseph Smith
              </div>
              <div class="cell" data-title="Age">
                27
              </div>
              <div class="cell" data-title="Job Title">
                Project Manager
              </div>
              <div class="cell" data-title="Location">
                Somerville, MA
              </div>
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
 <!-- <script src="script/custom_select_script.js"></script> -->
  <script src="script/selectize-min.js"></script>
  </body>
</html>
