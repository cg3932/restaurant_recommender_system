/*
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Search Control class - provides database searching operations to the
 * application
 *==============================================================================
 * This class requires a bit of knowledge on the user's part about about SQL
 * operations, and syntax. Let me outline the usage:
 *	searchFields - the field in the database that we want to search for.
 *	    Options: any field in the database.
 *	searchComparisons - the comparison between the field, and the value
 *		    we are searching for.
 *	    Options: =, >, >=, <, <=, <>, BETWEEN, LIKE, IN.
 *	searchValues - the value that we want to compare to field in our search.
 *	    Options: any valid data for the field specified.
 *	    Notes: string values must be enclosed in single quotes. i.e. if you
 *		    specify a string value "string", this will cause an error,
 *		    whereas a string " 'string' " is fine.
 *	searchLinks - for multi-faceted searches, we need a way to link the
 *		    parameters, this is done using searchLinks. The last
 *		    searchLink must always be a ";", as this tells SQL that the
 *		    search input is complete.
 *	    Options: AND, OR, ;, [advanced: ORDER BY <fld> [asc/desc],
 *			LIMIT <num>].
 *	noFieldsInSearch - the number of fields in the search
 * 
 * Joins not supported. Let's do a brief example now, suppose we want to find a
 * user who has "iain" in either their display name, or e-mail address, and we
 * want to order these users based on their registration date. We would store
 * the following in the arrays:
 *	searchFields - {"displayname", "email"}
 *	searchComparisons - {"like", "like"}
 *	searchValues - {"'iain'", "'iain'"}
 *	searchLinks - {"or", " order by userid asc;"}
 *	noFieldsInSearch - 2
 *  The generated SQL query for a user search will then be:
 *	SELECT * FROM USERS WHERE displayname like 'iain' or email like 'iain'
 *	order by usedid asc;
 * and this should give us the results we want.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
*/

package networkInterface;

import java.sql.*;
import entities.*;

public class DBSearchControl {

    // searchFields
    //	    stores the number of fields we would like to search, these must be
    //	    valid fields in the datbase we are concerned with.
    // searchComparisons 
    //	    tores the comparisons we want to make between each field and its
    //	    value.
    // searchValues
    //	    stores the value we are comparing the field too.
    // noFieldsInSearch
    //	    tells us how many fields we are going to search, or how far down the
    //	    array to parse.
    private String[] searchFields;
    private String[] searchComparisons;
    private String[] searchValues;
    private String[] searchLinkers;
    private int noFieldsInSearch;

    // Constructor
    public DBSearchControl() throws SQLException {}

    /**
     * Overloaded constructor
     *
     * Stores the specified inputs in the object.
     *
     * @param searchFields
     * @param searchComparisons
     * @param searchValues
     * @param searchLinkers
     * @param noFieldsInSearch
     * @throws SQLException
     */
    public DBSearchControl(String[] searchFields, String[] searchComparisons, String[] searchValues,
			   String[] searchLinkers, int noFieldsInSearch) throws SQLException {
	this.searchFields = searchFields;
	this.searchComparisons = searchComparisons;
	this.searchValues = searchValues;
	this.searchLinkers = searchLinkers;
	this.noFieldsInSearch = noFieldsInSearch;
    }

    // Getters and Setters, automatically generated by NetBeans
    public int getNoFieldsInSearch() {
	return noFieldsInSearch;
    }

    public void setNoFieldsInSearch(int noFieldsInSearch) {
	this.noFieldsInSearch = noFieldsInSearch;
    }

    public String[] getSearchComparisons() {
	return searchComparisons;
    }

    public void setSearchComparisons(String[] searchComparisons) {
	this.searchComparisons = searchComparisons;
    }

    public String[] getSearchFields() {
	return searchFields;
    }

    public void setSearchFields(String[] searchFields) {
	this.searchFields = searchFields;
    }

    public String[] getSearchValues() {
	return searchValues;
    }

    public void setSearchValues(String[] searchValues) {
	this.searchValues = searchValues;
    }

    public String[] getSearchLinkers() {
	return searchLinkers;
    }

    public void setSearchLinkers(String[] searchLinkers) {
	this.searchLinkers = searchLinkers;
    }

    /**
     * clearSearch
     *
     * Resets all of the search parameters.
     */
    public void clearSearch(){
	this.searchFields = new String[0];
	this.searchValues = new String[0];
	this.searchComparisons = new String[0];
	this.searchLinkers = new String[0];
	this.noFieldsInSearch = 0;
    }

    /**
     * searchUser()
     *
     * Runs the search with the given parameters on the user table in the
     * database. This method will return an array of user objects corresponding
     * to the query entered, or null if there was a problem.
     */
    public User[] searchUser(){

	// curQuery
	//	The query we build from the inputs
	// returnUser
	//	The user array to return
	String curQuery = "select * from users where ";
	User[] returnUser = new User[0];
	User curUser;
	int i, numberOfResults = 0;
	Connection conn;
	ResultSet rs;
	Statement st;

	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex){
            System.out.println("Error establishing connection:\n: " + ex);
	    return null;
	}

	// Construct the query from the input fields. This WILL cause an error
	// if the last searchLinker field is not a semicolon!
	for (i = 0; i < noFieldsInSearch; i++){
	    curQuery += searchFields[i] + " ";
	    curQuery += searchComparisons[i] + " ";
	    curQuery += searchValues[i] + " ";
	    curQuery += searchLinkers[i] + " ";
	}

	// for debugging
	 System.out.println(curQuery);

	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(curQuery);

	    // Check if the query generated any results
	    if (!rs.isBeforeFirst()){
		return null;
	    }

	    // If there were results, we need to convert them into User objects
	    // But first, we must find out how many results there were
	    rs.first();
	    do {
		numberOfResults++;              //Infinite loop here? (if no results found)
		rs.next();
	    } while (!rs.isAfterLast());

	    // Now that we know how many results we found, let's initialize our
	    // array, and then create the correct number of user objects from
	    // the resultset for storage in the return array.
	    returnUser = new User[numberOfResults];
	    rs.first();
	    for (i = 0; i < numberOfResults; i++){
		curUser = new User(
			rs.getInt("userid"),
			rs.getString("displayname"),
			rs.getString("password"),
			rs.getString("email"),
			rs.getBoolean("blocked"),
			rs.getBoolean("admin"));
		returnUser[i] = curUser;
		rs.next();
	    }

	} catch (SQLException ex) {
	    // If there was a problem with the connection, return a null
            System.out.println("Error in searchUser" + ex);
	    return null;
	}
	return returnUser;
    }

    /**
     * searchRestaurant()
     * Runs the search with the given parameters on the restaurant object in
     * the database. Returns an array of restaurants corresponding to the result
     * of this query, or null if there was a problem.
     */
    public Restaurant[] searchRestaurant(){

	// curQuery
	//	The SQL query we build from the input
	// returnRestaurant
	//	The array of restaurants to return
	String curQuery = "select * from restaurants where ";
	Restaurant[] returnRestaurant = new Restaurant[0];
	Restaurant curRestaurant;
	int i, numberOfResults = 0;
	ResultSet rs;
	Statement st;
	Connection conn;

	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    return null;
	}

	// Construct the query from the input fields. This will cause an error
	// if the last searchLinker field is not a semicolon!
	for (i = 0; i < noFieldsInSearch; i++){
	    curQuery += searchFields[i] + " ";
	    curQuery += searchComparisons[i] + " ";
	    curQuery += searchValues[i] + " ";
	    curQuery += searchLinkers[i] + " ";
	}

	// for debugging
	 System.out.println(curQuery);

	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(curQuery);

	    // Check if the query generated any results
	    if (!rs.isBeforeFirst()){
		return null;
	    }

	    // If there were results, we need to convert them into Restaurant
	    // objects. But first, we must find out how many results there were
	    rs.first();
	    do {
		numberOfResults++;
		rs.next();
	    } while (!rs.isAfterLast());

	    // Now that we know how many results we found, let's initialize our
	    // array, and then create the correct number of Restaurant object
	    // from the resultset for storage in the return array.
	    returnRestaurant = new Restaurant[numberOfResults];
	    rs.first();
	    for (i = 0; i < numberOfResults; i++){
		curRestaurant = new Restaurant(
			rs.getInt("restaurantid"),
			rs.getFloat("averagerating"),
			rs.getInt("numberofratings"),
			rs.getString("name"),
			rs.getInt("streetnumber"),
			rs.getString("streetname"),
			rs.getString("postalcode"),
			rs.getString("city"),
			rs.getString("province"),
			rs.getString("country"),
			rs.getString("phone"),
			rs.getString("cuisine"),
			rs.getString("webaddress"),
			rs.getString("openhours"),
			rs.getString("closehours"),
			rs.getFloat("cost"),
			rs.getBoolean("alcohol"),
			rs.getString("payment"),
			rs.getString("description"),
			rs.getBoolean("reservationsaccepted"),
			rs.getBoolean("reservationsrecommended"),
			rs.getString("imagepath"));
		returnRestaurant[i] = curRestaurant;
		rs.next();
	    }
	} catch (SQLException ex) {
	    // If there was a problem with the connection,return null
	    return null;
	}
	return returnRestaurant;
    }

    /**
     * searchRating()
     *
     * Search the rating table in the database for the input parameters. Returns
     * an array of rating objects corresponding to those found by the database.
     * If there was a problem during the query, null is returned.
     */
    public Rating[] searchRating(){

	// curQuery
	//	The generated SQL query
	// returnRating
	//	The array of ratings to return
	String curQuery = "select * from ratings where ";
	Rating[] returnRating = new Rating[0];
	Rating curRating;
	int i, numberOfResults = 0;
	ResultSet rs;
	Statement st;
	Connection conn;

	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    return null;
	}
	// Construct the query from the input fields. This WILL cause an error
	// if the last searchLinker field is not a semicolon!
	for (i = 0; i < noFieldsInSearch; i++){
	    curQuery += searchFields[i] + " ";
	    curQuery += searchComparisons[i] + " ";
	    curQuery += searchValues[i] + " ";
	    curQuery += searchLinkers[i] + " ";
	}

	// for debugging
	// System.out.println(curQuery);
	
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(curQuery);

	    // Check if the query generated any results
	    if (!rs.isBeforeFirst()){
		return null;
	    }

	    // If there were results, we need to convert them into Rating
	    // objects. But first, we must find out how many results there were.
	    rs.first();
	    do {
		numberOfResults++;              
		rs.next();
	    } while (!rs.isAfterLast());

            
	    // Now that we know how many results we found, let's initialize our
	    // array, and then create the correct number of Rating objects from
	    // the resultset for storage in the return array.
	    returnRating = new Rating[numberOfResults];
	    rs.first();

	    // Date conversion, from SQL format to Java format
	    java.sql.Date ratingDateSQL;
	    for (i = 0; i < numberOfResults; i++){
		ratingDateSQL = java.sql.Date.valueOf(rs.getString("ratingdate"));
		curRating = new Rating(
			rs.getInt("ratingid"),
			rs.getInt("userid"),
			rs.getInt("restaurantid"),
			rs.getInt("rating"),
			rs.getString("comment"),
			ratingDateSQL,
			rs.getBoolean("flagged"));
		returnRating[i] = curRating;
		rs.next();
	    }
	} catch (SQLException ex) {
	    // If there was a problem with the connection, return null
            System.out.println("Error in searchRating\n" + ex);
	    return null;
	}
	return returnRating;
    }

    /**
     * searchAd()
     *
     * Search the ad table in the database for the input parameters. Returns
     * an array of ad objects corresponding to those found by the database.
     * If there was a problem during the query, null is returned.
     */
    public ad[] searchAd(){

	// curQuery
	//	The generated SQL query
	// returnRating
	//	The array of ratings to return
	String curQuery = "select * from ads where ";
	ad[] returnAd = new ad[0];
	ad curAd;
	int i, numberOfResults = 0;
	ResultSet rs;
	Statement st;
	Connection conn;

	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    return null;
	}
	
	// Construct the query from the input fields. This WILL cause an error
	// if the last searchLinker field is not a semicolon!
	for (i = 0; i < noFieldsInSearch; i++){
	    curQuery += searchFields[i] + " ";
	    curQuery += searchComparisons[i] + " ";
	    curQuery += searchValues[i] + " ";
	    curQuery += searchLinkers[i] + " ";
	}

	// for debugging
	// System.out.println(curQuery);

	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(curQuery);

	    // Check if the query generated any results
	    if (!rs.next()){
		return null;
	    }

	    // If there were results, we need to convert them into ad objects.
	    // But first, we must find out how many results there were.
	    rs.first();
	    do {
		numberOfResults++;
		rs.next();
	    } while (!rs.isAfterLast());

	    // Now that we know how many results we found, let's initialize our
	    // array, and then create the correct number of ad objects from
	    // the resultset for storage in the return array.
	    returnAd = new ad[numberOfResults];
	    rs.first();

	    for (i = 0; i < numberOfResults; i++){
		curAd = new ad(
			rs.getInt("adid"),
			rs.getString("imagepath"),
			rs.getString("hyperlink"),
			rs.getInt("timesclicked"));
		returnAd[i] = curAd;
		rs.next();
	    }
	} catch (SQLException ex) {
	    // If there was a problem with the connection, return null
	    return null;
	}
	return returnAd;
    }
}