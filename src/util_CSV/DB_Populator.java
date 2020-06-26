package util_CSV;

import crime.Crime;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.util.ArrayList;
import java.util.HashMap;

import static org.neo4j.driver.Values.parameters;

public class DB_Populator implements AutoCloseable {

    private final Driver driver;

    public DB_Populator(String uri, String username, String password){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(username,password));
    }

    public void insertCrime(Crime crime){
        try ( Session session = driver.session() ) {

            HashMap<String,Object> valuesCrime= new HashMap<String,Object>();
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

            HashMap<String,Object> valuesDistrict= new HashMap<String,Object>();
            valuesDistrict.put("district_name", crime.getDistrict());

            HashMap<String,Object> valuesOffense= new HashMap<String,Object>();
            valuesOffense.put("offense_code", crime.getOffenseCode());
            valuesOffense.put("offense_code_group", crime.getOffenseCodeGroup());
            valuesOffense.put("offense_description", crime.getOffenseDescription());
            valuesOffense.put("shooting", crime.getShooting());

            HashMap<String,Object> valuesStreet= new HashMap<String,Object>();
            valuesStreet.put("street_name", crime.getStreet());

            HashMap<String,Object> valuesUCR= new HashMap<String,Object>();
            valuesUCR.put("ucr_part", crime.getUCR_Part());

            HashMap<String,Object> valuesOccured_district= new HashMap<String,Object>();
            valuesOccured_district.put("incident_number", crime.getIncidentNumber());
            valuesOccured_district.put("district_name", crime.getDistrict());

            HashMap<String,Object> valuesType= new HashMap<String,Object>();
            valuesType.put("incident_number", crime.getIncidentNumber());
            valuesType.put("offense_code", crime.getOffenseCode());

            HashMap<String,Object> valuesOccured_street= new HashMap<String,Object>();
            valuesOccured_street.put("incident_number", crime.getIncidentNumber());
            valuesOccured_street.put("street_name", crime.getStreet());

            HashMap<String,Object> valuesUCR_P= new HashMap<String,Object>();
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
                    "location: $location });",valuesCrime));

            //inserisco il nodo district
            session.writeTransaction(tx -> tx.run("MERGE (d:district {district_name: $district_name});", valuesDistrict));

            //inserisco il nodo offense
            session.writeTransaction(tx -> tx.run("MERGE (o:offense {offense_code: $offense_code," +
                    "offense_code_group: $offense_code_group," +
                    "offense_description: $offense_description," +
                    "shooting: $shooting});", valuesOffense));

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
                    "MERGE (c)-[:occurred_district]->(s);", valuesOccured_street));

            //inserisco arco crime ucr_part
            session.writeTransaction(tx -> tx.run("MATCH (c:crime {incident_number: $incident_number})," +
                    "(u:UCR_part {ucr_part: $ucr_part})" +
                    "MERGE (c)-[:UCR]->(u);", valuesUCR_P));
        }
    }




    public static void main(String[] args){

        DB_Populator db_populator = new DB_Populator("bolt://localhost:7687","neo4j","root");
        ArrayList<Crime> crimes = Reader_CSV.readCSV("dataset/dataset_completo.txt");
        int count = 0;
        for(Crime temp : crimes){
            db_populator.insertCrime(temp);
            count++;
        }

        System.out.println(count);
    }


    @Override
    public void close() throws Exception {
        driver.close();
    }
}
