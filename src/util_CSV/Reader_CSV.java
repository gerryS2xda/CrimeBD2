package util_CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import crime.Crime;

public class Reader_CSV {

    static Logger logger=Logger.getLogger("global");

    public static void main(String[] args) {

        logger.info("INIZIO LETTURA DATI");
        ArrayList<Crime> crimini = Reader_CSV.readCSV("C:\\Users\\Gennaro Teodoro\\Desktop\\dataset_completo.txt");

        for(Crime temp : crimini) {
            System.out.println(temp);
        }
        System.out.println(crimini.size());
    }


    public static ArrayList<Crime> readCSV(String path){

        ArrayList<Crime> crimini = new ArrayList<Crime>();
        File file = new File(path);
        Scanner scanner= null;
        try {

            logger.info("CREO LO SCANNER PER LA LETTURA");
            scanner = new Scanner(file);

        } catch (FileNotFoundException e) {

            logger.info("ERRORE DURANTE LA CREAZIONE DEL FILE");
            logger.info("SI E' VERIFICATA LA SEGUENTE ECCEZIONE: " + e.getMessage());

            e.printStackTrace();

            return null;
        }

        //Leggo gli headers
        logger.info("LEGGO GLI HEADER DEL FILE CSV");
        String line= scanner.nextLine();
        System.out.println(line);



        while(scanner.hasNextLine() && line != "") {

            line = scanner.nextLine();
            String[] attributi= line.split(";");
            Crime crimine = new Crime(attributi);
            crimini.add(crimine);

        }

        logger.info("INIZIO LA CHIUSURA DELLO SCANNER");
        scanner.close();
        logger.info("SCANNER CHIUSO CON SUCCESSO");
        return crimini;
    }

}

