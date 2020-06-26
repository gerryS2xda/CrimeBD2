<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
  <title>BPD Report</title>
  <link type="text/css" rel="stylesheet" href="./css/indexStyle.css" />
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
        <button type="button" id="execute_query_btn" class="btn_style" >Esegui query</button>
      </div>
    </div>
    <div id="result_content_page">
      <p class="result_page_name"> Query selezionata </p>
      <!-- table -->
      <!-- grafico -->
      <div class="btn_container">
        <button type="button" id="reset_btn" class="btn_style">Reset</button>
      </div>
    </div>
  </section>
  <section id="right_sidebar"> </section>
  <footer> </footer>
  <script type="text/javascript" src="script/jquery-3.5.1.js"></script>
  <script type="text/javascript" src="script/query_page_script.js"></script>
  </body>
</html>
