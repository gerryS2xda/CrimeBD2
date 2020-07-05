package controller;

import Model.Model_Data;
import Model.Tuple;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import crime.Crime;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/crime-contr")
public class CrimeServlet extends HttpServlet {
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
            //Visualizza reati/incidenti del giorno precedente
            //LocalDateTime yesterdayDate = LocalDateTime.now().minusDays(1);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            crimes = model_data.query_1();
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
        }else if(action.equals("Query 2")){
            //Reati con sparatoria nell'ultimo mese avvenuti nel distretto (input) nella fascia oraria (input min) - (input max)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_2(params.getTextfield(), params.getNumfieldmin(), params.getNumfieldmax());
            }
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
            //Incidenti/reati avvenuti nella street
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_3(params.getTextfield());
            }
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

        }else if(action.equals("Query 4")){
            //Visualizza la categoria di incidenti/reati che avvengono maggiormente in un determinato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String category = model_data.query_4(params.getTextfield());
            if(category.equals("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + category + "\"}"));
            }

        }else if(action.equals("Query 5")){
            //Mostra in quale giorno della settimana avvengono più reati/incidenti di un deteminato tipo in un dato distretto
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            String day_of_week = model_data.query_5(params.getTextfield(), params.getSelect());
            if(day_of_week.equals("")){
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": \"" + day_of_week + "\"}"));
            }

        }else if(action.equals("Query 6")){
            //Incidenti/reati avvenuti in una determinata street e in una data fascia oraria
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_6(params.getTextfield(), params.getNumfieldmin(), params.getNumfieldmax());
            }
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

        }else if(action.equals("Query 7")){
            //Visualizza l'ora in cui si verifica maggiormente un determinato tipo di incidente/reato
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            int hour = model_data.query_7(params.getSelect());
            if(hour >= 0){
                response.getWriter().write(json.toJson("{\"crime0\": \"oneresult\", \"crime1\": " + hour + "}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }

        }else if(action.equals("Query 8")){
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
            System.out.println(c.toString() + "\nOccuredDate: " + occuredDate);

            //aggiungere location
            //model_data.query_8(c);
            if(done){
                response.getWriter().write(json.toJson("{\"crime0\": \"done\"}"));
            }else{
                response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
            }
        }else if(action.equals("Query 9")){
            //Incidenti/reati in base al valore di UCR e alla street
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class);
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            if(!params.getTextfield().equals("")) {
                crimes = model_data.query_9(params.getTextfield(), params.getSelect());
            }
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


        }else if(action.equals("Query 10")){
            //Cancellazione mediante inserimento dell'Incident number
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni Incident number
            boolean flag = false; //usa per testare se cancellazione ha avuto successo oppure no
            if(!params.getTextfield().equals("")){
                String incidentNumber = params.getTextfield();
                System.out.println(incidentNumber); //testa se incident number e' stato ricevuto
                //manca codice della query 10 (modelData)
                if(flag){
                    response.getWriter().write(json.toJson("{\"crime0\": \"done\"}"));
                }else{
                    response.getWriter().write(json.toJson("{\"crime0\": \"noresult\"}"));
                }
            }

        }else if(action.equals("Query 11")){
            //Per ogni ora visualizza il crimine che viene eseguito maggiormente in un dato distretto (usare istogramma)
            InputParameter params = json.fromJson(request.getParameter("input"), InputParameter.class); //ottieni distretto
            if(!params.getTextfield().equals("")) {
                String distretto = params.getTextfield();

                //Eseguo la query per ottenere una lista di oggetti "Tuple "categoria di incidente/reato" in base all'ora
                //e converti in stringa JSON
                ArrayList<Tuple> tuples = new ArrayList<Tuple>(); //crea una lista di tuple in base all'ora
                String jsonResult = "[";
                for(int i = 0; i <24; i++){
                    Tuple t = model_data.subQuery_11(distretto, i);
                    tuples.add(t);
                    jsonResult += "{\"offense_code_group\": \"" + t.getOffense_code_group() +"\", " +
                            "\"hour\": " + t.getHour() +"},";
                }

                //Conta quanti incidenti/reati vengono eseguiti in quel distretto
                Map<String, Integer> map = new HashMap<>();
                for(Tuple t : tuples){ //per ogni tupla
                    if(!map.keySet().contains(t.getOffense_code_group())){ //se hashtable non contiene offensecodegroup, allora aggiungi
                        map.put(t.getOffense_code_group(), 1);
                    }else{  //se gia' presente, incrementa il conteggio
                        int count = map.get(t.getOffense_code_group());
                        count++;
                        map.replace(t.getOffense_code_group(), count);
                    }
                }


                jsonResult = jsonResult.substring(0, jsonResult.length() - 1) + "]"; //rimuovi ultima ',' e poi aggiungi ']'
                System.out.println(jsonResult);
                response.getWriter().write(json.toJson(jsonResult));
            }

        }else if(action.equals("Query 12")){
            //Mostra la percentuale di reati avvenuti in un distretto (usare grafico a torta)


        }else if(action.equals("Query 13")){
            //Selezionato un punto sulla mappa, verificare gli incidenti che sono accaduti
            String location = request.getParameter("location");
            if(location != null || !(location.equals(""))){
                System.out.println(location);  //si ottiene questo risultato selezionando un punto sulla mappa: (42.069841971408174, -87.4601584792328)
                //query13 (location in input) - rest. una lista di crimini
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
    public void writeTupleJsonObject(JsonWriter writer, Tuple t) throws IOException {
        writer.beginObject();
        writer.name("offense_code_group").value(t.getOffense_code_group());
        writer.name("hour").value(t.getHour());
        writer.endObject();
    }
}
