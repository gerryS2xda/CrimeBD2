package util_CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;


public class Fix_Missing_District_CSV {

    static Logger logger=Logger.getLogger("global");

    public static void main(String[] args) {
        logger.info("INIZIO IL FIXING DEI DATI");
        String pathORIG = "dataset/dataset_no_wrong_description.txt";
        String pathDEST="dataset/dataset_completo_no_missing_district.txt";
        Fix_Missing_District_CSV.fixCSV(pathORIG, pathDEST);
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



      /*
      Andiamo a costrurire una tabella hash contenente le coppie street, district
       */
        HashMap<String,String> street = new HashMap<String,String>();

        while(scanner.hasNextLine() && line != "")  {

            line= scanner.nextLine();
            String[] attributi= line.split(";");

            //se il valore di district non è vuoto lo vado a salvare correlandolo a street
            if(!attributi[4].equals("") && attributi[4] != null) {
                street.put(attributi[13], attributi[4]);
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

        //Vado ora a ricostruire il dataset andando ad unificare i valori di street e district
        logger.info("LEGGO GLI HEADER DEL FILE CSV");
        line= scanner.nextLine();
        logger.info("SCRIVO GLI HEADER NEL NUOVO FILE DESTINAZIONE");
        pw.println(line);

        int count=1;
        while(scanner.hasNextLine() && line != "")  {

            line= scanner.nextLine();
            String[] attributi= line.split(";");


            if(street.get(attributi[13]) == null){
             System.out.println(count);
             count++;
             continue;
            }
            else {
                String good_line = attributi[0] + ";" + attributi[1] + ";" + attributi[2] + ";" + attributi[3]
                        + ";" + street.get(attributi[13]) + ";" + attributi[5] + ";" + attributi[6] + ";" + attributi[7] + ";" + attributi[8]
                        + ";" + attributi[9] + ";" + attributi[10] + ";" + attributi[11] + ";" + attributi[12] + ";" + attributi[13]
                        + ";" + attributi[14] + ";" + attributi[15] + ";" + attributi[16];
                pw.println(good_line);
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

