package Model;

import crime.Crime;
import org.neo4j.driver.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Crime> query_2(String district_name, int oraInizio, int oraFine){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district)," +
                        "(c:crime)-[:occurred_street]->(s:street)," +
                        "(c:crime)-[:UCR]->(u:UCR_part) " +
                        "where c.shooting='1' AND " +
                        "duration.between(date(c.occurred_on_date),date())<=duration(\"P1M\") AND " +
                        "d.district_name= $district_name AND " +
                        "c.hour>=$oraInizio AND c.hour<=$oraFine " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"oraInizio",oraInizio
                        ,"oraFine",oraFine));
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

    public ArrayList<Crime> query_3(String street_name){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where s.street_name = $street_name" +
                        " return c,o,d,s,u", parameters("street_name",street_name));
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

    public String query_4(String district_name){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String offese_description= "";
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense),\n" +
                        "(c:crime)-[:occurred_district]->(d:district)\n" +
                        "where d.district_name = $district_name " +
                        "return o.offense_description as offense_description , count(*) as times\n" +
                        "order by times DESC\n" +
                        "LIMIT 1", parameters("district_name",district_name));
                while(result.hasNext()){
                    Record r = result.next();
                    offese_description = r.get(0).asString();

                }
                return offese_description;
            });
        }
    }

    public String query_5(String district_name, String offense_code_group){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String day_of_week= "";
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district)" +
                        "where d.district_name = $district_name  AND o.offense_code_group = $offense_code_group " +
                        "return c.day_of_week as day_of_week , count(*) as times " +
                        "order by times DESC " +
                        "LIMIT 1", parameters("district_name",district_name,"offense_code_group",offense_code_group));
                while(result.hasNext()){
                    Record r = result.next();
                    day_of_week = r.get(0).asString();

                }
                return day_of_week;
            });
        }
    }

    public ArrayList<Crime> query_6(String district_name, int oraInizio, int oraFine){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district)," +
                        "(c:crime)-[:occurred_street]->(s:street)," +
                        "(c:crime)-[:UCR]->(u:UCR_part) " +
                        "where d.district_name= $district_name AND c.hour>=$oraInizio AND c.hour<=$oraFine " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"oraInizio",oraInizio
                        ,"oraFine",oraFine));
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

    public int query_7(String offense_code_group){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                int hour = -1; //se resta -1 significa che non c'e la strada
                Result result = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district)" +
                        "where o.offense_code_group= $offense_code_group " +
                        "return c.hour as hour, count(*) as times " +
                        "order by times DESC " +
                        "LIMIT 1", parameters("offense_code_group", offense_code_group));
                while (result.hasNext()) {
                    Record r = result.next();
                    hour = r.get(0).asInt();
                }
                return hour;
            });
        }
    }

    public void query_8(Crime crime){
        Model_Data md= new Model_Data();
        md.insertCrime(crime);
    }


    public ArrayList<Crime> query_9(String district_name, String ucr_part){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district)," +
                        "(c:crime)-[:occurred_street]->(s:street)," +
                        "(c:crime)-[:UCR]->(u:UCR_part) " +
                        "where d.district_name=$district_name  AND u.ucr_part=$ucr_part " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"ucr_part",ucr_part));
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


    public ArrayList<String> query_offense_code_group(){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<String> offense_code_group = new ArrayList<String>();
                Result result  = tx.run("match (o:offense) return o.offense_code_group");
                while(result.hasNext()){
                    Record r = result.next();
                    System.out.println(r.get(0).asString());
                    offense_code_group.add(r.get(0).asString());
                }
                return offense_code_group;
            });
        }
    }



    public  void insertCrime(Crime crime) {
        try (Session session = driver.session()) {

            HashMap<String, Object> valuesCrime = new HashMap<String, Object>();
            valuesCrime.put("incident_number", crime.getIncidentNumber());
            valuesCrime.put("occurred_on_date", crime.getOccurredOnDate());
            valuesCrime.put("day_of_week", crime.getDayOfWeek());
            valuesCrime.put("hour", crime.getHour());
            valuesCrime.put("month", crime.getMonth());
            valuesCrime.put("year", crime.getYear());
            valuesCrime.put("reporting_area", crime.getReportingArea());
            valuesCrime.put("lat", crime.getLat());
            valuesCrime.put("long", crime.getLong());
            valuesCrime.put("location", crime.getLocation());
            valuesCrime.put("shooting", crime.getShooting());

            HashMap<String, Object> valuesDistrict = new HashMap<String, Object>();
            valuesDistrict.put("district_name", crime.getDistrict());

            HashMap<String, Object> valuesOffense = new HashMap<String, Object>();
            valuesOffense.put("offense_code", crime.getOffenseCode());
            valuesOffense.put("offense_code_group", crime.getOffenseCodeGroup());
            valuesOffense.put("offense_description", crime.getOffenseDescription());


            HashMap<String, Object> valuesStreet = new HashMap<String, Object>();
            valuesStreet.put("street_name", crime.getStreet());

            HashMap<String, Object> valuesUCR = new HashMap<String, Object>();
            valuesUCR.put("ucr_part", crime.getUCR_Part());

            HashMap<String, Object> valuesOccured_district = new HashMap<String, Object>();
            valuesOccured_district.put("incident_number", crime.getIncidentNumber());
            valuesOccured_district.put("district_name", crime.getDistrict());

            HashMap<String, Object> valuesType = new HashMap<String, Object>();
            valuesType.put("incident_number", crime.getIncidentNumber());
            valuesType.put("offense_code", crime.getOffenseCode());

            HashMap<String, Object> valuesOccured_street = new HashMap<String, Object>();
            valuesOccured_street.put("incident_number", crime.getIncidentNumber());
            valuesOccured_street.put("street_name", crime.getStreet());

            HashMap<String, Object> valuesUCR_P = new HashMap<String, Object>();
            valuesUCR_P.put("incident_number", crime.getIncidentNumber());
            valuesUCR_P.put("ucr_part", crime.getUCR_Part());

            //inserisco il nodo crime
            session.writeTransaction(tx -> tx.run("MERGE (c:crime { incident_number: $incident_number," +
                    "occurred_on_date: $occurred_on_date," +
                    "day_of_week: $day_of_week," +
                    "hour: $hour," +
                    "month: $month," +
                    "year: $year," +
                    "reporting_area: $reporting_area," +
                    "lat: $lat," +
                    "long: $long," +
                    "location: $location," +
                    "shooting: $shooting});", valuesCrime));

            //inserisco il nodo district
            session.writeTransaction(tx -> tx.run("MERGE (d:district {district_name: $district_name});", valuesDistrict));

            //inserisco il nodo offense
            session.writeTransaction(tx -> tx.run("MERGE (o:offense {offense_code: $offense_code," +
                    "offense_code_group: $offense_code_group," +
                    "offense_description: $offense_description});", valuesOffense));

            //inserisco il nodo street
            session.writeTransaction(tx -> tx.run("MERGE (s:street {street_name: $street_name});", valuesStreet));

            //inserisca il nodo UCR_part
            session.writeTransaction(tx -> tx.run("MERGE (u:UCR_part {ucr_part: $ucr_part});", valuesUCR));

            //inserisco arco crime district
            session.writeTransaction(tx -> tx.run("MATCH (c:crime {incident_number: $incident_number})," +
                    "(d:district {district_name: $district_name})" +
                    "MERGE (c)-[:occurred_district]->(d);", valuesOccured_district));

            //inserisco arco crime offense
            session.writeTransaction(tx -> tx.run("MATCH (c:crime {incident_number: $incident_number})," +
                    "(o:offense {offense_code: $offense_code})" +
                    "MERGE (c)-[:type]->(o);", valuesType));

            //inserisco arco crime street
            session.writeTransaction(tx -> tx.run("MATCH (c:crime {incident_number: $incident_number})," +
                    "(s:street {street_name: $street_name})" +
                    "MERGE (c)-[:occurred_street]->(s);", valuesOccured_street));

            //inserisco arco crime ucr_part
            session.writeTransaction(tx -> tx.run("MATCH (c:crime {incident_number: $incident_number})," +
                    "(u:UCR_part {ucr_part: $ucr_part})" +
                    "MERGE (c)-[:UCR]->(u);", valuesUCR_P));
        }
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
        md.query_offense_code_group();
    }

}
