package controller;

import Model.Model_Data;
import Model.Tuple;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
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

        if(action.equals("init_select_category_incident")){
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

        if(action.equals("Query 1")){
            //Mostra le informazioni relative ad un determinato incidente/reato
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni Incident number
            if(!params.getTextfield().equals("")){
                String incidentNumber = params.getTextfield();

                Crime c = model_data.query_1(incidentNumber);
                if(!c.getIncidentNumber().equalsIgnoreCase("")){
                    response.getWriter().write(json.toJson("{\"crime0\":" + c.toJSONString() + "}}"));
                }else{
                    response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
                }
            }
        }else if(action.equals("Query 2")){
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
        }else if(action.equals("Query 3")){
            //Reati con sparatoria nell'ultimo mese avvenuti nel distretto (input) nella fascia oraria (input min) - (input max)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
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

        }else if(action.equals("Query 4")){
            //Incidenti/reati avvenuti nella street
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_4(params.getTextfield());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
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

        }else if(action.equals("Query 5")){
            //Visualizza la categoria di incidenti/reati che avvengono maggiormente in un determinato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String category = model_data.query_5(params.getTextfield());
            if(category.equals("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + category + "\"}"));
            }

        }else if(action.equals("Query 6")){
            //Mostra in quale giorno della settimana avvengono più reati/incidenti di un deteminato tipo in un dato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String day_of_week = model_data.query_6(params.getTextfield(), params.getSelect());
            if(day_of_week.equals("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + day_of_week + "\"}"));
            }

        }else if(action.equals("Query 7")){
            //Incidenti/reati avvenuti in un determinato distretto e in una data fascia oraria
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_7(params.getTextfield(), params.getNumfieldmin(), params.getNumfieldmax());
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

        }else if(action.equals("Query 8")){
            //Visualizza l'ora in cui si verifica maggiormente un determinato tipo di incidente/reato
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            int hour = model_data.query_8(params.getSelect());
            if(hour >= 0){
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": " + hour + "}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equals("Query 9")){
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
        }else if(action.equals("Query 10")){
            //Incidenti/reati in base al valore di UCR e al distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_10(params.getTextfield(), params.getSelect());
            }
            if(crimes.size() > 0){
                String str = "{";
                int i = 0;
                for(Crime c : crimes) {
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


        }else if(action.equals("Query 11")){
            //Cancellazione mediante inserimento dell'Incident number
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni Incident number
            boolean flag = false; //usa per testare se cancellazione ha avuto successo oppure no
            if(!params.getTextfield().equals("")){
                String incidentNumber = params.getTextfield();
                System.out.println(incidentNumber); //testa se incident number e' stato ricevuto

                model_data.query_11(incidentNumber);
                if(flag){
                    response.getWriter().write(json.toJson("{\"crime0\": \"done\"}"));
                }else{
                    response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
                }
            }

        }else if(action.equals("Query 12")){
            //Per ogni ora visualizza il crimine che viene eseguito maggiormente in un dato distretto (usare istogramma)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equals("")) {
                String distretto = params.getTextfield();

                //Eseguo la query per ottenere una lista di oggetti "Tuple "categoria di incidente/reato" in base all'ora
                //e converti in stringa JSON
                ArrayList<Tuple> tuples = model_data.query_12(distretto); //crea una lista di tuple in base all'ora

                String jsonResult = "[";
                for(Tuple t : tuples){
                    jsonResult += "{\"offense_code_group\": \"" + t.getOffense_code_group() +"\", " +
                            "\"hour\": " + t.getHour() +"},";
                }

                jsonResult = jsonResult.substring(0, jsonResult.length() - 1) + "]"; //rimuovi ultima ',' e poi aggiungi ']'
                System.out.println(jsonResult);
                response.getWriter().write(json.toJson(jsonResult));
            }

        }else if(action.equals("Query 13")){
            //Mostra la percentuale di reati avvenuti in un distretto (usare grafico a torta)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equals("")) {
                String distretto = params.getTextfield();

                ArrayList<Crime> crimini = model_data.query_13(distretto);

                String jsonRes = buildJSONResultForQuery13(crimini);
                System.out.println("JSON: "+ jsonRes);
                response.getWriter().write(json.toJson(jsonRes));
            }
        }else if(action.equals("Query 14")){
            //Selezionato un punto sulla mappa, verificare gli incidenti che sono accaduti
            String location = request.getParameter("location");
            if(location != null || !(location.equals(""))){
                System.out.println(location);  //si ottiene questo risultato selezionando un punto sulla mappa: (42.069841971408174, -87.4601584792328)
                //query14 (location in input) - rest. una lista di crimini
                response.getWriter().write(json.toJson("{\"flag\": \"ok\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"flag\": \"exception\"}"));
            }
        }
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

    private String buildJSONResultForQuery13(ArrayList<Crime> crimini){
        //Conta quanti incidenti/reati vengono eseguiti in quel distretto
        Map<String, Integer> map = new HashMap<>();  //(categoriaCrimine, frequenza)

        for(Crime c : crimini){
            if(!map.keySet().contains(c.getOffenseCodeGroup())){ //se hashtable non contiene offensecodegroup, allora aggiungi
                map.put(c.getOffenseCodeGroup(), 1);
            }else{  //se gia' presente, incrementa il conteggio
                int count = map.get(c.getOffenseCodeGroup());
                count++;
                map.replace(c.getOffenseCodeGroup(), count);
            }
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
}
