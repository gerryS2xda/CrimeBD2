package controller;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/crime-contr")
public class CrimeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        Gson json = new Gson();
        String action = request.getParameter("");

        if(action == null){
            response.setStatus(404);
            response.sendRedirect("./error.jsp"); //pagina errore 404
        }
        if(action.equals("act1"){
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
        }else{
			response.setStatus(404);
			response.sendRedirect("./error.jsp"); //pagina errore 404
		}
*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
