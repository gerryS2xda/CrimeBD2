package Model;

import crime.Crime;
import org.neo4j.driver.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.neo4j.driver.Values.parameters;

public class Model_Data {

    private final Driver driver;

    public Model_Data(){
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j","root"));
    }

    public ArrayList<Crime> query_1(){


        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
               Result result  = tx.run("match (c:crime)-[:type]->(o:offense),\n" +
                       "(c:crime)-[:occurred_district]->(d:district),\n" +
                       "(c:crime)-[:occurred_street]->(s:street),\n" +
                       "(c:crime)-[:UCR]->(u:UCR_part)\n" +
                       "where duration.between(date(c.occurred_on_date),date())<=duration(\"P1D\")\n" +
                       "return c,o,d,s,u");
               while(result.hasNext()){
                   Record r = result.next();
                   Crime crime = Model_Data.buildCrime(r);
                   crimini.add(crime);
               }
               return crimini;
            });
        }


    }

    public Crime query_2(){
        Crime crime= null;

        return crime;
    }

    public ArrayList<Crime> query_3(String street_name){


        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense),\n" +
                        "(c)-[:occurred_district]->(d:district),\n" +
                        "(c)-[:occurred_street]->(s:street),\n" +
                        "(c)-[:UCR]->(u:UCR_part)\n" +
                        "where s.street_name = $street_name" +
                        " return c,o,d,s,u", parameters("district_name",street_name));
                while(result.hasNext()){
                    Record r = result.next();
                    Crime crime = Model_Data.buildCrime(r);
                    System.out.println(crime);
                    crimini.add(crime);
                }
                return crimini;
            });
        }
    }

    public Crime query_4(){
        Crime crime= null;

        return crime;
    }

    public Crime query_5(){
        Crime crime= null;

        return crime;
    }

    public Crime query_6(){
        Crime crime= null;

        return crime;
    }

    public Crime query_7(){
        Crime crime= null;

        return crime;
    }

    public Crime query_8(){
        Crime crime= null;

        return crime;
    }

    public Crime query_9(){
        Crime crime= null;

        return crime;
    }

    public Crime query_10(){
        Crime crime= null;

        return crime;
    }


    public static Crime buildCrime(Record r){
        String[] attributi= new String[17];

        attributi[0] = r.get(0).get("incident_number").toString();
        attributi[1] = r.get(1).get("offense_code").toString();
        attributi[2] = r.get(1).get("offense_code_group").toString();
        attributi[3] = r.get(1).get("offense_description").toString();
        attributi[4] = r.get(2).get("district_name").toString();
        attributi[5] = r.get(0).get("reporting_area").toString();
        attributi[6] = r.get(0).get("shooting").toString();

        LocalDateTime occurred_on_date = LocalDateTime.parse(r.get(0).get("occurred_on_date").toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        attributi[7] = occurred_on_date.format(formatter);
        attributi[8] = r.get(0).get("year").toString();
        attributi[9] = r.get(0).get("month").toString();
        attributi[10] = r.get(0).get("day_of_week").toString();
        attributi[11] = r.get(0).get("hour").toString();
        attributi[12] = r.get(4).get("ucr_part").toString();
        attributi[13] = r.get(3).get("street_name").toString();
        attributi[14] = r.get(0).get("lat").toString();
        attributi[15] = r.get(0).get("long").toString();
        attributi[16] = r.get(0).get("location").toString();

        Crime crime = new Crime(attributi);
        return crime;
    }

    public static void main(String[] args){
        Model_Data md= new Model_Data();
        md.query_3("A1");
    }

}
