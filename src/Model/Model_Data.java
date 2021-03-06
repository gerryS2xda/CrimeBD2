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

    public Crime query_1(String incident_number){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                Crime crimine = new Crime();
                Result result = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where c.incident_number=$incident_number " +
                        "return c,o,d,s,u", parameters("incident_number", incident_number));
                while (result.hasNext()) {
                    Record r = result.next();
                    crimine = Model_Data.buildCrime(r);
                }
                return crimine;
            });
        }
    }

    public ArrayList<Crime> query_2(){


        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
               Result result  = tx.run("match (c:crime)-[:type]->(o:offense),\n" +
                       "(c)-[:occurred_district]->(d:district),\n" +
                       "(c)-[:occurred_street]->(s:street),\n" +
                       "(c)-[:UCR]->(u:UCR_part)\n" +
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

    public ArrayList<Crime> query_3(String district_name, int oraInizio, int oraFine){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where c.shooting='1' AND " +
                        "duration.inMonths(date(c.occurred_on_date),date())<=duration(\"P0M\") AND " +
                        "d.district_name= $district_name AND " +
                        "c.hour>=$oraInizio AND c.hour<=$oraFine " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"oraInizio",oraInizio
                        ,"oraFine",oraFine));
                while(result.hasNext()){
                    Record r = result.next();
                    Crime crime = Model_Data.buildCrime(r);
                    crimini.add(crime);
                }
                return crimini;
            });
        }
    }

    public ArrayList<Crime> query_4(String street_name){
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
                    crimini.add(crime);
                }
                return crimini;
            });
        }
    }

    public String query_5(String district_name){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String offese_description= "";
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense),\n" +
                        "(c:crime)-[:occurred_district]->(d:district)\n" +
                        "where d.district_name = $district_name " +
                        "return o.offense_code_group as offense_code_group , count(*) as times\n" +
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

    public String query_6(String district_name, String offense_code_group){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String day_of_week= "";
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)" +
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

    public ArrayList<Crime> query_7(String district_name, int oraInizio, int oraFine){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where d.district_name= $district_name AND c.hour>=$oraInizio AND c.hour<=$oraFine " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"oraInizio",oraInizio
                        ,"oraFine",oraFine));
                while(result.hasNext()){
                    Record r = result.next();
                    Crime crime = Model_Data.buildCrime(r);
                    crimini.add(crime);
                }
                return crimini;
            });
        }
    }

    public int query_8(String offense_code_group){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                int hour = -1; //se resta -1 significa che non c'e la strada
                Result result = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)" +
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

    public void query_9(Crime crime){
        Model_Data md= new Model_Data();
        md.insertCrime(crime);
    }


    public ArrayList<Crime> query_10(String district_name, String ucr_part){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Crime> crimini = new ArrayList<Crime>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where d.district_name=$district_name  AND u.ucr_part=$ucr_part " +
                        "return c,o,d,s,u", parameters("district_name",district_name,"ucr_part",ucr_part));
                while(result.hasNext()){
                    Record r = result.next();
                    Crime crime = Model_Data.buildCrime(r);
                    crimini.add(crime);
                }
                return crimini;
            });
        }
    }


    public void query_11(String incident_number){
        try ( Session session = driver.session() ) {
            session.writeTransaction(tx -> tx.run("MATCH (c:crime)-[t:type]->(:offense)," +
                    "(c)-[od:occurred_district]->(:district)," +
                    "(c)-[os:occurred_street]->(:street)," +
                    "(c)-[u:UCR]->(:UCR_part) " +
                    "where c.incident_number = $incident_number " +
                    "delete t,od,os,u,c", parameters("incident_number", incident_number)));
        }
    }

    public ArrayList<Tuple> query_12(String district_name){
        ArrayList<Tuple> tuples = new ArrayList<Tuple>();
        Model_Data md= new Model_Data();
        int i;
        for(i=0;i<24;i++){
            tuples.add(md.subQuery_12(district_name,i));
        }
        return tuples;
    }

    public Tuple subQuery_12(String district_name, int hour){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                Tuple tuple= null;
                Result result = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district) " +
                        "where d.district_name =$district_name AND c.hour =$hour " +
                        "return o.offense_code_group, count(*) as times " +
                        "order by times DESC " +
                        "Limit 1", parameters("district_name", district_name,"hour", hour));
                while (result.hasNext()) {
                    Record r = result.next();
                    tuple= new Tuple(r.get(0).asString(),hour);
                }
                return tuple;
            });
        }
    }

    public HashMap<String,Double> query_13(String street_name){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                HashMap<String,Double> percentuali=  new HashMap<String,Double>();
                Result result  = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_district]->(d:district)," +
                        "(c)-[:occurred_street]->(s:street)," +
                        "(c)-[:UCR]->(u:UCR_part) " +
                        "where s.street_name=$street_name " +
                        "return o.offense_code_group", parameters("street_name",street_name));
                while(result.hasNext()){
                    Record r = result.next();
                    String offense_code_group = r.get(0).asString();
                   percentuali.put(offense_code_group, Query_15(street_name,offense_code_group));
                }
                return percentuali;
            });
        }
    }

    public ArrayList<Tuple_Count> Query_14(String district_name, int hour){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                ArrayList<Tuple_Count> tuple_counts = new ArrayList<Tuple_Count>();
                Result result = tx.run("match (c:crime)-[:type]->(o:offense)," +
                        "(c:crime)-[:occurred_district]->(d:district) " +
                        "where d.district_name =$district_name AND c.hour =$hour " +
                        "return o.offense_code_group, count(*) as times", parameters("district_name", district_name,"hour", hour));
                while (result.hasNext()) {
                    Record r = result.next();
                    Tuple_Count tuple= new Tuple_Count(r.get(0).asString(),r.get(1).asInt());
                    tuple_counts.add(tuple);
                }
                return tuple_counts;
            });
        }
    }

    public double Query_15(String street_name, String offense_code_group){
            try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                double percentuale = -1; // se resta -1 singifica che non sono presenti distretto e/o offense_code_group

                Result result = tx.run("match (c)-[:occurred_street]->(s:street) " +
                        "where  s.street_name= $street_name " +
                        "with count(c) as total " +
                        "match (c:crime)-[:type]->(o:offense)," +
                        "(c)-[:occurred_street]->(s:street) " +
                        "where s.street_name=$street_name and o.offense_code_group=$offense_code_group " +
                        "return (count(c)*1.0)/total", parameters("street_name", street_name,"offense_code_group", offense_code_group));

                while (result.hasNext()) {
                    Record r = result.next();
                    percentuale = r.get(0).asDouble();
                }
                return percentuale;
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
                    offense_code_group.add(r.get(0).asString());
                }
                return offense_code_group;
            });
        }
    }

    public String get_offense_code_group(int offense_code){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String offense_code_group = ""; // vuota nel caso in cui non ci sia un offesne code corrispondente
                Result result = tx.run("match (o:offense) " +
                        "where o.offense_code = $offense_code " +
                        "return o.offense_code_group", parameters("offense_code", offense_code));
                while (result.hasNext()) {
                    Record r = result.next();
                    offense_code_group = r.get(0).asString();
                }
                return offense_code_group;
            });
        }
    }

    public String get_offense_description(int offense_code){
        try ( Session session = driver.session() ) {
            return session.readTransaction(tx -> {
                String offense_description = ""; // vuota nel caso in cui non ci sia un offesne code corrispondente
                Result result = tx.run("match (o:offense) " +
                        "where o.offense_code = $offense_code " +
                        "return o.offense_description", parameters("offense_code", offense_code));
                while (result.hasNext()) {
                    Record r = result.next();
                    offense_description = r.get(0).asString();
                }
                return offense_description;
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
        md.close();
    }

    public void close(){
        driver.close();
    }
}
