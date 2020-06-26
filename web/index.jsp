<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <title>BPD Report</title>
 <!--=================================================================================================-->
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/animate.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/select2.min.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/perfect-scrollbar.css">
  <link rel="stylesheet" type="text/css" href="./css/table_bootstrap/util.css">
  <link rel="stylesheet" type="text/css" href="css/table_bootstrap/main.css">
  <link type="text/css" rel="stylesheet" href="./css/indexStyle.css" />
  <!--================================================================================================-->
  <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
  <header>
    <!-- barra di navigazione -->
  </header>
  <section id="left_sidebar">

  </section>
  <section id="main_content">
    <p id="main_text"> Boston Police Department Report </p>
    <div id="select_query_page">
      <div id="form_layout">
        <div id="radio_btn_container">
          <p class="suggest_txt">Seleziona la query da eseguire</p>
          <form action="" method="POST">
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Query 1 </span>
              <!-- trovare suggerimenti -->
              <select name="attr1" class="combo_box_radio">
                <option value="val1">Value1</option>
                <option value="val2">Value2</option>
              </select>
            </div>
            <div class="radiobtn_container">
              <input class="radio_btn_style" type="radio" name="query_sel" value="">
              <span class="span_txt_radio"> Query 2 </span>
              <!-- trovare suggerimenti -->
              <select name="attr1" class="combo_box_radio">
                <option value="val1">Value1</option>
                <option value="val2">Value2</option>
              </select>
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
  </body>
</html>
