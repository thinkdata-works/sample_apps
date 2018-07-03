import namara.client.Client;
import namara.client.Record;
import namara.client.Value;
import namara.client.exception.AuthorizationException;
import namara.client.exception.ConnectionException;
import namara.client.exception.NamaraException;
import namara.client.exception.QueryException;
import namara.query.Identifier;
import namara.query.QueryBuilder;
import namara.client.ResultSet;

import java.util.Iterator;

/*
 *
 * This is a small application to show the basic usage of this library.
 * It demonstrates how to make a connection to namara, initialize a query
 * to a data set, and then traverse the results of that query.
 *
 * Additionally, it shows how to look for any exceptions that could have occured
 * when examining your query results.
 *
 * For full client capabilities, please see the offical javadocs
 * https://thinkdata-works.github.io/namara-java-sdk/
 *
 */
public class Application {
    // TODO Enter your Namara API Key
    static String API_KEY = "";
    // TODO Select your Namara host
    static String NAMARA_HOST = "https://api.namara.io/";


    static String DATA_SET_ID = "733934b4-5434-43a6-a487-cdf8091b3a49";
    static String VERSION = "en-0";

    public static void main(String[] args) throws AuthorizationException, ConnectionException, QueryException, NamaraException {
        /*
         * Initialize the Namara client to make requests to
         */
        Client client = new Client(NAMARA_HOST, API_KEY);

        /*
         * Test your Namara connection. This will throw an error if
         */
        client.testConnection();

        /*
         * Construct a new identifier, which points to a data set for querying
         */
        Identifier identifier = new Identifier(DATA_SET_ID, VERSION);

        /*
         * ================= QUERY OPTION 1 ===========================
         *
         * Create a new query builder for that data set with some view parameters.
         * The query builder will be passed into the result set in order to
         * iterate through all of the query results
         */
        QueryBuilder queryBuilder1 = new QueryBuilder(10, 3)
                .select().column("id AS ID").column("ST_AsGeoJson(ST_GeomFromText(geometry)) AS geojson")
                .from().dataSet(identifier).getBuilder();

        ResultSet resultSet = new ResultSet(queryBuilder1, client);

        /*
         * Walk through the results and the query and print the rows
         */
        while(resultSet.hasNext()) {
            Record record = resultSet.next();
            System.out.println("Examing record: " + record);

            /*
             * For each row, gets the values for that row
             */
            Iterator<Value> valueIterator = record.iterator();

            while(valueIterator.hasNext()) {
                /*
                 * Pulls the value in the row. Each value is a key/pair of the column name + column value
                 * in the data set. Please consult the javadoc for full list of value transformations
                 */
                Value value = valueIterator.next();
                System.out.println("Got value " + value.getKey() + ": " + value.asString());
            }
        }

        /*
         * If ResultSet#hasNext has returned false, it may be because there are no more values to iterate over,
         * OR because an exception was encountered in retrieving more values
         *
         * Check if an exception has been raised, and throw if it has been.
         */
        if(resultSet.hasException()) {
            resultSet.throwException();
        }

        /*
         * =================== QUERY OPTION 2 ===========================
         *
         * You can also construct queries from a raw query string, and interact with the builder in the same way.
         * Please see the full NiQL documentation for details
         *
         * https://thinkdataworks.gitbook.io/namara/query-api
         *
         */
        QueryBuilder queryBuilder2 = new QueryBuilder("SELECT id AS ID, ST_AsGeoJson(ST_GeomFromText(geometry)) AS geojson" +
                " FROM " + DATA_SET_ID + "/" + VERSION, 10, 3);

        ResultSet resultSet2 = new ResultSet(queryBuilder2, client);

        // ... process result set
    }
}