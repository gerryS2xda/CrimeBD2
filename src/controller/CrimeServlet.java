package controller;

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

        if(action.equals("search")){

        }

        if(action.equals("query1")){
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

        }else if(action.equals("query2")){

        }else if(action.equals("query3")){

        }else if(action.equals("query4")){

        }else if(action.equals("query5")){

        }else if(action.equals("query6")){

        }else if(action.equals("query7")){

        }else if(action.equals("query8")){

        }else if(action.equals("query9")){

        }else if(action.equals("query10")){

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
