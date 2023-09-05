package Database;

import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


/** This class accesses the database for the Country Class.
 */
public class CountryDao {
    /**
     * A persistent list of Countries, these are not expected to change often.
     */
    private static ArrayList<Country> countries = null;

    /** This method retrieves all countries from the database.
     * @return The list of all available countries.
     * @throws SQLException
     */
    public static ArrayList<Country> getAllCountries() throws SQLException {
        if (countries != null) {
            return countries;
        } else {
            countries = new ArrayList<Country>();
        }

        DBConnection.openConnection();
        String sqlStatement = "SELECT first_level_divisions.division, countries.country " +
                "FROM first_level_divisions " +
                "INNER JOIN countries ON first_level_divisions.Country_ID=countries.Country_ID " +
                "ORDER BY country";
        Query.makeQuery(sqlStatement);
        ResultSet result = Query.getResult();

        Country newCountry = null;
        while(result.next()){
            String division = result.getString("Division");
            String countryName = result.getString("Country");

            if(newCountry == null) {
                newCountry = new Country(countryName);
            }
            if(Objects.equals(newCountry.getName(), countryName)) {
                newCountry.addDivision(division);
            } else {
                countries.add(newCountry);
                newCountry = new Country((countryName));
                newCountry.addDivision(division);
            }
        }
        countries.add(newCountry);

        DBConnection.closeConnection();
        return countries;
    }

}
