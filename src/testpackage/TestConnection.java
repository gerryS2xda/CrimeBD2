package testpackage;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class TestConnection implements AutoCloseable {

    private final Driver driver;

    public TestConnection(String uri, String username, String password){

        driver = GraphDatabase.driver(uri, AuthTokens.basic(username,password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void insert(){
        try ( Session session = driver.session() ) {

            session.writeTransaction(tx -> tx.run("MERGE (c:crime { incident_number: $x })",parameters("x",1)));
        }
    }

    public static void main(String[] args){

        TestConnection tc= new TestConnection("bolt://localhost:7687","neo4j","root");
        tc.insert();
    }


}
