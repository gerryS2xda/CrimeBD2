package controller;

import Model.Model_Data;
import com.google.gson.Gson;
import crime.Crime;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/crime-contr")
public class CrimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson json = new Gson();
        Model_Data model_data = new Model_Data();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Crime crime = new Crime("I92097173", "3115", "INVESTIGATE PERSON", "INVESTIGATE PERSON", "C11", "355", "0",
                LocalDateTime.parse("2019-10-23 00:00:00", formatter), 2019, 10, "Wednesday", 0, "", "GIBSON ST",
                42.297555,-71.059709, "(42.29755500, -71.05970900)");
        Crime crime2 = new Crime("I92097174", "3115", "INVESTIGATE PERSON", "INVESTIGATE PERSON", "C11", "355", "0",
                LocalDateTime.parse("2020-06-27 00:00:00", formatter), 2019, 10, "Wednesday", 0, "", "GIBSON ST",
                42.297555,-71.059709, "(42.29755500, -71.05970900)");
        String action = request.getParameter("action");


        if(action == null){
            response.setStatus(404);
            response.sendRedirect("./error.jsp"); //pagina errore 404
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        /*
        if(action.equals("search")){
            String key = request.getParameter("key");
            String value = request.getParameter("value");
            boolean flag = false;

            //codice per ricerca nel dataset

            ArrayList<Crime> crimes = new ArrayList<Crime>();
            crimes.add(crime);
            crimes.add(crime2);
            if(key!= null && value != null){
                if(key.equals("distretto")){
                    for(Crime c : crimes){
                        if(c.getDistrict().equals(value)){
                            flag = true;
                            break;
                        }
                    }
                }else if(key.equals("street")){
                    for(Crime c : crimes){
                        if(c.getStreet().equals(value)){
                            flag = true;
                            break;
                        }
                    }
                }
            }
            response.getWriter().write(json.toJson("{\"done\":" + flag + "}"));
        }
*/
        if(action.equals("Query 1")){
            //Visualizza reati/incidenti del giorno precedente
            //LocalDateTime yesterdayDate = LocalDateTime.now().minusDays(1);

            String str = "{";
            ArrayList<Crime> crimes = new ArrayList<Crime>();
            crimes.add(crime);
            crimes.add(crime2);

            int i = 0;
            for(Crime c : crimes) {
                str += "\"crime"+i +"\":" + c.toJSONString();
                str+= "},";
                i++;
            }
            str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
            response.getWriter().write(json.toJson(str));

        }else if(action.equals("Query 2")){
            //Numero reati con sparatoria nell'ultimo mese avvenuti nel distretto (input) nella fascia oraria (input min) - (input max)


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

        }else if(action.equals("Query 5")){

        }else if(action.equals("Query 6")){

        }else if(action.equals("Query 7")){

        }else if(action.equals("Query 8")){

        }else if(action.equals("Query 9")){

        }else if(action.equals("Query 10")){

        }

            /*
            response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			String str = "{";
			ArrayList<ProdottoBean> prodotti = pm.doRetrieveAll("DataScadenza");
			int i = 0;
			for(ProdottoBean p : prodotti) {
				str += "\"prod"+i +"\":" + p.toString();
				if(p.getSconto() != 0){
					str += ", \"prezzoScontato\":" + Utils.calculatePrezzoScontato(p.getPrezzo(), p.getSconto()) + "},";
				}else{str+= "},";}
			    	i++;
			}
			str = str.substring(0, str.length() - 1) + "}"; //rimuovi ultima ',' e poi aggiungi '}'
			response.getWriter().write(json.toJson(str));
			*/


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
