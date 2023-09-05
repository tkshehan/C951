package Database;

import java.sql.ResultSet;
import java.sql.Statement;

/** This method executes and returns queries to the database.
 */
public class Query {
    /** The result of a query. */
    private static ResultSet result;

    /** This method parses and executes queries to the database.
     * @param q The query to execute.
     */
    public static void makeQuery(String q){
        try{
            Statement stmt = DBConnection.connection.createStatement();
            // determine query execution
            if(q.toLowerCase().startsWith("select"))
                result= stmt.executeQuery(q);
            if(q.toLowerCase().startsWith("delete")|| q.toLowerCase().startsWith("insert")|| q.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /** This method returns the results of a query.
     * @return The result of a query.
     */
    public static ResultSet getResult(){
        return result;
    }
}