package controller;

import Model.Model_Data;
import Model.Tuple;
import Model.Tuple_Count;
import com.google.gson.Gson;
import crime.Crime;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/crime-contr")
public class CrimeServlet extends HttpServlet {

    //STATIC ISTANCE
    private static final int MAX_PIECE_PIE_CHART_Q13 = 10;
    private static final int MAX_COLUMNS_BAR_CHART_Q14 = 7;
    private static final int RESULT_LIMIT_DEFAULT = 500; //limita il numero di risultati (per risolvere problema di caricamento lento)
    private static final int RESULT_LIMIT_DEFAULT_PIE = 2000; //limita il numero di risultati (per risolvere problema di caricamento lento)

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson json = new Gson();
        Model_Data model_data = new Model_Data();
        String action = request.getParameter("action");


        if(action == null){
            response.setStatus(404);
            response.sendRedirect("./error.jsp"); //pagina errore 404
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        if(action.equalsIgnoreCase("init_select_category_incident")){
            ArrayList<String> categories = model_data.query_offense_code_group();
            if(categories.size() > 0){
                String str = "{";
                int i = 0;
                for(String c : categories) {
                    str += "\"category"+i +"\": \"" + categories.get(i) + "\",";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"category0\": \"noresult\"}"));
            }
        }

        if(action.equalsIgnoreCase("Query 1")){
            //Mostra le informazioni relative ad un determinato incidente/reato
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni Incident number
            if(!params.getTextfield().equalsIgnoreCase("")){
                String incidentNumber = params.getTextfield();

                Crime c = model_data.query_1(incidentNumber);
                if(!c.getIncidentNumber().equalsIgnoreCase("\"null\"")){
                    response.getWriter().write(json.toJson("{\"crime0\":" + c.toJSONString() + "}}"));
                }else{
                    response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
                }
            }
        }else if(action.equalsIgnoreCase("Query 2")){
            //Visualizza reati/incidenti del giorno precedente

            ArrayList<Crime> crimes = new ArrayList<Crime>();
            crimes = model_data.query_2();
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
                    str += "\"crime"+i +"\":" + c.toJSONString();
                    str+= "},";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }
        }else if(action.equalsIgnoreCase("Query 3")){
            //Reati con sparatoria nell'ultimo mese avvenuti nel distretto (input) nella fascia oraria (input min) - (input max)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equalsIgnoreCase("")) {
                crimes = model_data.query_3(params.getTextfield(), params.getNumfieldmin(), params.getNumfieldmax());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
                    c.setDistrict("\"distretto\""); //per indicare la colonna da rimuovere
                    str += "\"crime"+i +"\":" + c.toJSONString();
                    str+= "},";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 4")){
            //Incidenti/reati avvenuti nella street
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equalsIgnoreCase("")) {
                crimes = model_data.query_4(params.getTextfield());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
                    if(i > RESULT_LIMIT_DEFAULT) break;
                    c.setStreet("\"street\""); //per indicare la colonna da rimuovere
                    str += "\"crime"+i +"\":" + c.toJSONString();
                    str+= "},";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 5")){
            //Visualizza la categoria di incidenti/reati che avvengono maggiormente in un determinato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String category = model_data.query_5(params.getTextfield());
            if(category.equalsIgnoreCase("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + category + "\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 6")){
            //Mostra in quale giorno della settimana avvengono più reati/incidenti di un deteminato tipo in un dato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String day_of_week = model_data.query_6(params.getTextfield(), params.getSelect());
            if(day_of_week.equalsIgnoreCase("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + day_of_week + "\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 7")){
            //Incidenti/reati avvenuti in un determinato distretto e in una data fascia oraria
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equalsIgnoreCase("")) {
                crimes = model_data.query_7(params.getTextfield(), params.getNumfieldmin(), params.getNumfieldmax());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
                    if(i > RESULT_LIMIT_DEFAULT) break;
                    c.setDistrict("\"distretto\""); //per indicare la colonna da rimuovere
                    str += "\"crime"+i +"\":" + c.toJSONString();
                    str+= "},";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 8")){
            //Visualizza l'ora in cui si verifica maggiormente un determinato tipo di incidente/reato
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            int hour = model_data.query_8(params.getSelect());
            if(hour >= 0){
                String hourres = "";
                if(hour < 10) hourres += "0" + hour + ":00";
                else hourres+= "" + hour + ":00";
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + hourres + "\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equalsIgnoreCase("Query 9")){
            //Inserimento di un incidente/reato
            boolean done = true; //nessun controllo di inserimento, quindi true
            Crime c = json.fromJson(request.getParameter("input"), Crime.class);
            String occuredDate = request.getParameter("occuredOnDate");
            LocalDateTime lc = LocalDateTime.parse(occuredDate);
            c.setOccurredOnDate(lc);
            c.setHour(lc.getHour());
            c.setDayOfWeek(lc.getDayOfWeek().toString());
            c.setMonth(lc.getMonthValue());
            c.setYear(lc.getYear());
            c.setLocation("\"\"\"("+ c.getLat() + ", "+ c.getLong() +")\"\"\"");
            System.out.println(c.toString() + "\nOccuredDate: " + occuredDate);

            //aggiungere location
            model_data.query_9(c);
            if(done){
                response.getWriter().write(json.toJson("{\"crime0\": \"done\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }
        }else if(action.equalsIgnoreCase("Query 10")){
            //Incidenti/reati in base al valore di UCR e al distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equalsIgnoreCase("")) {
                crimes = model_data.query_10(params.getTextfield(), params.getSelect());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
                    if(i > RESULT_LIMIT_DEFAULT) break;
                    c.setDistrict("\"distretto\""); //per indicare la colonna da rimuovere
                    c.setUCR_Part("\"ucr\""); //per indicare la colonna da rimuovere
                    str += "\"crime"+i +"\":" + c.toJSONString();
                    str+= "},";
                    i++;
                }
                str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
                response.getWriter().write(json.toJson(str));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }


        }else if(action.equalsIgnoreCase("Query 11")){
            //Cancellazione mediante inserimento dell'Incident number
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni Incident number
            boolean flag = true; //usa per testare se cancellazione ha avuto successo oppure no (true per default)
            if(!params.getTextfield().equalsIgnoreCase("")){
                String incidentNumber = params.getTextfield();
                System.out.println(incidentNumber); //testa se incident number e' stato ricevuto

                model_data.query_11(incidentNumber);
                if(flag){
                    response.getWriter().write(json.toJson("{\"crime0\": \"done\"}"));
                }else{
                    response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
                }
            }

        }else if(action.equalsIgnoreCase("Query 12")){
            //Per ogni ora visualizza il crimine che viene eseguito maggiormente in un dato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equalsIgnoreCase("")) {
                String distretto = params.getTextfield();

                //Eseguo la query per ottenere una lista di oggetti "Tuple" che contiene il reato che si verifica maggiormente ad ogni ora
                //e converti in stringa JSON
                ArrayList<Tuple> tuples = model_data.query_12(distretto);

                String result = createJSONResultForQuery12(tuples);

                response.getWriter().write(json.toJson(result));
            }

        }else if(action.equalsIgnoreCase("Query 13")){
            //Mostra la percentuale di reati avvenuti in un distretto (usare grafico a torta)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equalsIgnoreCase("")) {
                String distretto = params.getTextfield();

                ArrayList<Crime> crimini = model_data.query_13(distretto);

                String jsonRes = buildJSONResultForQuery13(crimini);

                response.getWriter().write(json.toJson(jsonRes));
            }
        }else if(action.equalsIgnoreCase("Query 14")){
            //Mostra i crimini avvenuti in un dato distretto in una data ora
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equalsIgnoreCase("") && params.getNumfieldmin() != -1) {
                String distretto = params.getTextfield();
                int hour = params.getNumfieldmin();

                ArrayList<Tuple_Count> tuple_counts = model_data.Query_14(distretto, hour);

                /*
                String jsonResult = "{";
                int i = 0;
                for(Tuple_Count t : tuple_counts){
                    jsonResult += "\"crime" + i + "\": {\"offense_code_group\": \"" + t.getOffense_code_group() +"\", " +
                            "\"count\": " + t.getCount() + "},";
                    i++;
                }
                jsonResult = jsonResult.substring(0, jsonResult.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi ']'
*/
                String jsonResult = buildJSONResultForQuery14(tuple_counts);
                response.getWriter().write(json.toJson(jsonResult));
            }
        }else if(action.equalsIgnoreCase("getOffenseCategory")){
            int offcode = Integer.parseInt(request.getParameter("input"));
            String category = model_data.get_offense_code_group(offcode);

            if(!category.equalsIgnoreCase("")){
                response.getWriter().write(json.toJson("{\"crime0\": \""+ category +"\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }
        /* RIMOZIONE QUERY 14 - MAPPA
        if(action.equalsIgnoreCase("Query 14")){
            //Selezionato un punto sulla mappa, verificare gli incidenti che sono accaduti
            String location = request.getParameter("location");
            if(location != null || !(location.equalsIgnoreCase(""))){
                System.out.println(location);  //si ottiene questo risultato selezionando un punto sulla mappa: (42.069841971408174, -87.4601584792328)
                //query14 (location in input) - rest. una lista di crimini
                response.getWriter().write(json.toJson("{\"flag\": \"ok\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"flag\": \"exception\"}"));
            }
        }

         */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    //Other methods
    private int computePercentage(double value, double total){
        double quoziente = value / total;
        BigDecimal bd = new BigDecimal(Double.toString(quoziente));
        BigDecimal bd2 = bd.setScale(2, RoundingMode.HALF_UP);
        double percentage = bd2.doubleValue() * 100;
        return (int) percentage;
    }

    private String createJSONResultForQuery12(ArrayList<Tuple> tuples){

        //Costruzione hashMap al fine di "accorpare" le ore con i risultati
        Map<String, String> map = new HashMap<>();
        for(Tuple t: tuples){
            if(!map.keySet().contains(t.getOffense_code_group())){ //se hashtable non contiene offensecodegroup, allora aggiungi
                map.put(t.getOffense_code_group(), t.getHour() + ":00");
            }else{ //aggiungi le ore
                String s = map.get(t.getOffense_code_group()) + " - " + t.getHour() + ":00";
                map.replace(t.getOffense_code_group(), s);
            }
        }

        String jsonResult = "{";
        int i = 0;
        for(String cat : map.keySet()){
            jsonResult += "\"crime" + i + "\": {\"offense_code_group\": \"" + cat +"\", " +
                    "\"hour\": \"" + map.get(cat) + "\"},";
            i++;
        }
        jsonResult = jsonResult.substring(0, jsonResult.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi ']'

        return jsonResult;
    }

    private String buildJSONResultForQuery13(ArrayList<Crime> crimini){
        //Conta quanti incidenti/reati vengono eseguiti in quel distretto
        Map<String, Integer> map = new HashMap<>();  //(categoriaCrimine, frequenza)

        int k = 0;
        for(Crime c : crimini){
            if(k > RESULT_LIMIT_DEFAULT_PIE) break;
            if(!map.keySet().contains(c.getOffenseCodeGroup())){ //se hashtable non contiene offensecodegroup, allora aggiungi
                map.put(c.getOffenseCodeGroup(), 1);
            }else{  //se gia' presente, incrementa il conteggio
                int count = map.get(c.getOffenseCodeGroup());
                count++;
                map.replace(c.getOffenseCodeGroup(), count);
            }
            k++;
        }

        //Ordina le categorie in modo descrescente (per avere le categorie piu' frequenti tra i primi risultati)
        Map<String, Integer> sorted = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        //Calcola le frequenze totali
        int tot = 0;
        for(String cat : sorted.keySet()) {
            System.out.println("Query 13: [OffenseCodeGroup: " + cat + "; count: " + sorted.get(cat));
            tot += sorted.get(cat);
        }
        System.out.println("Totale: " + tot);

        //Calcolo percentuali frequenza per pie chart (quindi il risultato)
        Map<String, Integer> results = new HashMap<>();
        int i = 0;
        for(String cat: sorted.keySet()){
            if(cat.equalsIgnoreCase("\"Other\"")) continue;
            if(i > MAX_PIECE_PIE_CHART_Q13-1){
                break;
            }
            results.put(cat, sorted.get(cat));
            i++;
        }

        //Setta le percentuali e costruisci il risultato in stringa JSON
        String str = "{";
        int j = 0;
        for(String cat : results.keySet()) {
            results.replace(cat, computePercentage((double) results.get(cat), (double) tot));
            System.out.println("Query 13: [OffenseCodeGroup: " + cat + "; percetage: " + results.get(cat));
            str+="\"crime"+ j +"\": {\"category\": " + cat + ", \"percentage\": " + results.get(cat) + "},";
            j++;
        }
        str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
        return str;
    }

    private String buildJSONResultForQuery14(ArrayList<Tuple_Count> tuples){
        //Conta quanti incidenti/reati vengono eseguiti in quel distretto
        Map<String, Integer> map = new HashMap<>();  //(categoriaCrimine, frequenza)

        for(Tuple_Count t : tuples){
            map.put(t.getOffense_code_group(), t.getCount());
        }

        //Ordina le categorie in modo descrescente (per avere le categorie piu' frequenti tra i primi risultati)
        Map<String, Integer> sorted = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        //Limita contenuto della hashmap
        Map<String, Integer> results = new HashMap<>();
        int i = 0;
        for(String cat: sorted.keySet()){
            if(cat.equalsIgnoreCase("\"Other\"")) continue;
            if(i > MAX_COLUMNS_BAR_CHART_Q14-1){
                break;
            }
            results.put(cat, sorted.get(cat));
            i++;
        }

        //Setta le percentuali e costruisci il risultato in stringa JSON
        String str = "{";
        int j = 0;
        for(String cat : results.keySet()) {
            System.out.println("Query 13: [OffenseCodeGroup: " + cat + "; frequency: " + results.get(cat));
            str+="\"crime"+ j +"\": {\"category\": \"" + cat + "\", \"frequency\": " + results.get(cat) + "},";
            j++;
        }
        str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
        return str;
    }
}
