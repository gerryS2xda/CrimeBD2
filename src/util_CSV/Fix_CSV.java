package util_CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Logger;


public class Fix_CSV {

    static Logger logger=Logger.getLogger("global");

    public static void main(String[] args) {

        logger.info("INIZIO IL FIXING DEI DATI");
        String pathORIG = "C:\\Users\\Gennaro Teodoro\\Desktop\\dataset_TXT.txt";
        String pathDEST="C:\\Users\\Gennaro Teodoro\\Desktop\\dataset_completo.txt";
        Fix_CSV.fixCSV(pathORIG, pathDEST);
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

        //Leggo gli headers
        logger.info("LEGGO GLI HEADER DEL FILE CSV");
        String line= scanner.nextLine();
        logger.info("SCRIVO GLI HEADER NEL NUOVO FILE DESTINAZIONE");
        pw.println(line);


        /*
         * Il problema è con gli attributi che sono stringhe che contengono il carattere ,
         * andrò quindi a prendere questi attributi e sostituire le , con i !
         * successivamente ricompongo la stringa e sostituisco tutte le , separatrici di attributi con ;
         * vado ad aggiustare poi gli attributi trasformando i ! in , nuovamente.
         *
         */

        while(scanner.hasNextLine() && line != "")  {

            line= scanner.nextLine();
            String[] attributi= line.split("\"");
            String lineTemp;

            if(attributi.length == 2) {
                attributi[1] = attributi[1].replace(",", "!");
                lineTemp = attributi[0] + "\"" + attributi[1]+"\"";
                lineTemp = lineTemp.replace(",", ";");
                lineTemp = lineTemp.replace("!", ",");
                pw.println(lineTemp);
            }
            if(attributi.length == 4) {

                attributi[1] = attributi[1].replace(",", "!");
                attributi[3] = attributi[3].replace(",", "!");
                lineTemp= attributi[0] + "\"" + attributi[1]+"\""+attributi[2] + "\"" + attributi[3]+"\"";
                lineTemp = lineTemp.replace(",", ";");
                lineTemp = lineTemp.replace("!", ",");
                pw.println(lineTemp);
            }


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

