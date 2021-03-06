package util_CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;


public class Fix_NULL_CSV {

    static Logger logger=Logger.getLogger("global");

    public static void main(String[] args) {
        logger.info("INIZIO IL FIXING DEI DATI");
        String pathORIG = "dataset/dataset_completo.txt";
        String pathDEST="dataset/dataset_completo_no_NULL.txt";
        Fix_NULL_CSV.fixCSV(pathORIG, pathDEST);
        logger.info("FIXING DEI DATI COMPLETATO");
    }


    public static void fixCSV(String pathORIG, String pathDEST){

        File fileORIG = new File(pathORIG);
        File fileDEST = new File(pathDEST);
        PrintWriter pw = null;
        Scanner scanner= null;
        try {

            logger.info("CREO LO SCANNER PER LA LETTURA DEL FILE DI ORIGINE");
            scanner = new Scanner(fileORIG);

        } catch (FileNotFoundException e) {

            logger.info("ERRORE DURANTE L'APERTURA DEL FILE DI ORIGINE");
            logger.info("SI E' VERIFICATA LA SEGUENTE ECCEZIONE: " + e.getMessage());

            e.printStackTrace();

            return;
        }

        try {

            logger.info("CREO IL PRINTWRITER PER LA SCRITTURA DEL FILE DESTINAZIONE");
            pw = new PrintWriter(fileDEST);

        } catch (FileNotFoundException e) {

            logger.info("ERRORE DURANTE L'APERTURA/CREAZIONE DEL FILE DESTINAZIONE");
            logger.info("SI E' VERIFICATA LA SEGUENTE ECCEZIONE: " + e.getMessage());

            e.printStackTrace();

            logger.info("INIZIO LA CHIUSURA DELLO SCANNER");
            scanner.close();
            logger.info("SCANNER CHIUSO CON SUCCESSO");

            return;
        }

        //Leggo gli header
        logger.info("LEGGO GLI HEADER DEL FILE CSV");
        String line= scanner.nextLine();

        /*Viene costruita una tabella hash contenente le coppie offense_code e offense_code_group per mappare
        il codice con il valore corrispondente*/
        HashMap<String,String> offense = new HashMap<String,String>();

        while(scanner.hasNextLine() && line != "")  {

            line= scanner.nextLine();
            String[] attributi= line.split(";");

            //l'offense_code viene normalizzato togliendo tutti gli 0 in eccesso dinanzi al numero effettivo
            int offense_code = Integer.parseInt(attributi[1]);

            //se il valore di offense_code_group non è vuoto lo si va a salvare correlandolo a offense_code
            if(!attributi[2].equals("") && attributi[2] != null) {
                offense.put(Integer.toString(offense_code), attributi[2]);
            }
        }

        logger.info("INIZIO LA CHIUSURA DELLO SCANNER DOPO LA PRIMA LETTURA");
        scanner.close();
        logger.info("SCANNER CHIUSO CON SUCCESSO DOPO LA PRIMA LETTURA");

        try {

            logger.info("RIAPERTURA DELLO SCANNER PER LA LETTURA DEL FILE DI ORIGINE");
            scanner = new Scanner(fileORIG);

        } catch (FileNotFoundException e) {

            logger.info("ERRORE DURANTE L'APERTURA DEL FILE DI ORIGINE");
            logger.info("SI E' VERIFICATA LA SEGUENTE ECCEZIONE: " + e.getMessage());

            e.printStackTrace();

            return;
        }

        //Vado ora a ricostruire il dataset andando a sostituire i valori mancanti
        logger.info("LEGGO GLI HEADER DEL FILE CSV");
        line= scanner.nextLine();
        logger.info("SCRIVO GLI HEADER NEL NUOVO FILE DESTINAZIONE");
        pw.println(line);

        while(scanner.hasNextLine() && line != "")  {

            line= scanner.nextLine();
            String[] attributi= line.split(";");

            int offense_code = Integer.parseInt(attributi[1]);
            String offense_code_string = Integer.toString(offense_code);


            String good_line= attributi[0] +";" + offense_code_string +";" + offense.get(offense_code_string) +";" + attributi[3]
                    +";" + attributi[4] +";" + attributi[5] +";" + attributi[6] +";" + attributi[7] +";" + attributi[8]
                    +";" + attributi[9] +";" + attributi[10] +";" + attributi[11] +";" + attributi[12] +";" + attributi[13]
                    +";" + attributi[14] +";" + attributi[15] +";" + attributi[16];


            pw.println(good_line);


        }



        logger.info("FILE COMPLETATO");
        logger.info("INIZIO LA CHIUSURA DELLO SCANNER");
        scanner.close();
        logger.info("SCANNER CHIUSO CON SUCCESSO");
        logger.info("INIZIO LA CHIUSURA DEL PRINTWRITER");
        pw.close();
        logger.info("PRINTWRITER CHIUSO CON SUCCESSO");
    }



}

