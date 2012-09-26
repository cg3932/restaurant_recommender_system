/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Stats Control class - provides database statistics to the application
 *==============================================================================
 * This class is largely built upon the translation of java methods to SQL 
 * comamnds, and then the results back into java datatypes.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */
package networkInterface;

import java.sql.*;
import entities.Restaurant;
import entities.User;
import recommend.Recommender;

public class DBStatsControl {

    // The connection object used for communicating
    private Connection conn;

    /**
     * Constructor
     *
     * Sets the connection to that stored in DBSettings. If there is an
     * exception thrown during connection, it is through this module.
     */
    public DBStatsControl() throws SQLException {
        try {
            this.conn = DBSettings.getConn();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    /**
     * getNumberUsers()
     *
     * Gets the number of registered users in the database by using the
     * SQL query:
     *	    SELECT COUNT(USERID) FROM USERS;
     * Then converts this result into an int to return; this should correspond
     * to the number of unique users registered in the system. We must have an
     * open connection to the database, and the database must follow the format
     * outlined in the low-level design document.
     */
    public int getNumberUsers() {

        // numberUsers
        //	the number of users as returned by the SQL query result, -1
        //	denotes an error.
        // numberUsersQuery
        //	the SQL query to retrieve the number of users in the database.
        int numberUsers = -1;
        String numberUsersQuery = "select count(userid) from users;";
        Statement st;
        ResultSet rs;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            rs = st.executeQuery(numberUsersQuery);

            // translate the results into a java int
            rs.first();
            numberUsers = rs.getInt(1);
        } catch (SQLException ex) {
            // If there was a problem with the connection, we will return -1
            return -1;
        }
        return numberUsers;
    }

    /**
     * getNumberRestaurants()
     *
     * Gets the number of registered restaurants in the database by using the
     * SQL query:
     *	    SELECT COUNT(RESTAURANTID) FROM RESTAURANTS;
     * Then converts this result into an int to return; this should correspond
     * to the number of unique restaurants in the system. We must have an open
     * connection to the database, and the database must follow the format
     * outlined in the low-level design document.
     */
    public int getNumberRestaurants() {

        // numberRestaurants
        //	the number of restaurants, as returned by the query -1 denotes
        //	an error.
        // numberRestaurantsQuery
        //	the query that generates the number of restaurants
        int numberRestaurants = -1;
        String numberRestaurantsQuery = "select count(restaurantid) from " +
                "restaurants;";
        Statement st;
        ResultSet rs;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            rs = st.executeQuery(numberRestaurantsQuery);

            // translate the results into a java integer
            rs.first();
            numberRestaurants = rs.getInt(1);
        } catch (SQLException ex) {
            // If there was a problem with the connection, we will return -1
            return -1;
        }
        return numberRestaurants;
    }

    /**
     * getNumberRatings()
     *
     * Gets the number or rating & comment pairs in the database by using the
     * SQL query:
     *	    SELECT COUNT(RATINGID) FROM RATINGS;
     * Then converts this result into an into to return; this should correspond
     * to the number of unique ratings in the system. We must have an open
     * connection to the database, and the database must follow the format
     * outlined in the low-level design documents.
     */
    public int getNumberRatings() {

        // numberRatings
        //	The number of ratings, as returned by the query, -1 denotes an
        //	error
        // numberRatingsQuery
        //	The SQL query string that generates the required result
        int numberRatings = -1;
        String numberRatingsQuery = "select count(ratingid) from ratings;";
        Statement st;
        ResultSet rs;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            rs = st.executeQuery(numberRatingsQuery);

            // translate the results into a java integer
            rs.first();
            numberRatings = rs.getInt(1);
        } catch (SQLException ex) {
            // If there was a problem with the connection, we will return -1
            return -1;
        }
        return numberRatings;
    }

    /**
     * getTopRated(int noToGet)
     *
     * Gets the top noToGet rated restaurants in the system, using the SQL
     * query:
     *	    SELECT * FROM RESTAURANTS ORDER BY AVERAGERATING DESC LIMIT
     *	    <noToGet>
     * Then converts the results into an array of restaurant objects to return;
     * this array should be the top <noToGet> rated restaurants in the system.
     * We must have a connection to the database opened, and the database must
     * follow the format outlined in the low-level design document.
     *
     * @param noToGet - the number of top rated restaurants to get. Should be
     * larger than zero, and less than the total number of restaurants in the
     * system.
     */
    public Restaurant[] getTopRated(int noToGet) {

        // Check the inputs
        //	If we are to retrieve more records than exist in the database,
        //	we only retrive as many records as there are in the database
        //	If we are to retrive 0, or a negative number of records, we
        //	abort the procedure
        int maxNoToGet = this.getNumberRestaurants();
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        } else if (noToGet <= 0) {
            return null;
        }

        // topRated
        //	the restaurant array to return, storing the objects
        //	corresponding to the top noToGet restaurants in the system.
        // topRatedQuery
        //	the SQL query that generates the required results.
        Restaurant[] topRated = new Restaurant[noToGet];
        String topRatedQuery = "select * from restaurants order by " +
                "averagerating desc limit " + noToGet + ";";
        Statement st;
        ResultSet rs;
        int curRecord;
        Restaurant curRestaurant;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            rs = st.executeQuery(topRatedQuery);
            rs.first();

            // Translate the SQL results into a string array
            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                // Construct a new restaurant with the query returned parameters
                curRestaurant = new Restaurant(rs.getInt("restaurantid"),
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
                topRated[curRecord] = curRestaurant;
                rs.next();
            }
        } catch (SQLException ex) {
            // If there was a problem with the connection, we will return null
            System.out.println("Problem in getTopRated:\n" + ex);
            return null;
        }
        return topRated;
    }

    /**
     * getTopRated(int noToGet, int userid)
     *
     * Gets the top noToGet rated restaurants in the system rated by a
     * particular user, using the SQL query:
     *	    SELECT * FROM RESTAURANTS WHERE USERID = <curuser> ORDER BY
     *	    AVERAGERATING DESC LIMIT <noToGet>
     * Then converts the results into an array of restaurant objects to return;
     * this array should be the top <noToGet> rated restaurants by the user.
     * We must have a connection to the database opened, and the database must
     * follow the format outlined in the low-level design document.
     *
     * @param noToGet - the number of top rated restaurants to get. Should be
     * larger than zero, and less than the total number of restaurants in the
     * system.
     * @param userid - the id of the user to search for.
     */
    public Restaurant[] getTopRated(int noToGet, int userid) {

        // Check the inputs
        //	If we are to retrieve more records than exist in the database,
        //	we only retrive as many records as there are in the database
        //	If we are to retrieve 0, or a negative number of records, we
        //	abort the procedure
        int maxNoToGet;
        String howManyRatings = "select count(ratingid) from ratings where " +
                "userid = " + userid + ";";
        Statement st;
        ResultSet rs;
        try {
            conn = DBSettings.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(howManyRatings);
            if (!rs.first()){
		return null;
	    }
	    //if (!rs.afterLast()){
	    if (rs.isAfterLast()){
		return null;
	    }
	    maxNoToGet = rs.getInt("count(ratingid)");
        } catch (SQLException ex) {
            System.out.println("I hate printing out exceptions" + ex);
            return null;
        }
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        } else if (noToGet <= 0) {
            return null;
        }

        // topRated
        //	the restaurant array to return, storing the objects
        //	corresponding to the top noToGet restaurants in the system.
        // restoInfoQuery
        //	the SQL query that retrieves all information for a particular
        //	restaurant
        // topRatedQuery
        //	the SQL query that gets the top rated restaurants by a certain
        //      user
        Restaurant[] topRated = new Restaurant[noToGet];
        String restoInfoQuery = "select * from restaurants where restaurantid " +
                "= ";
        String topRatedQuery = "select restaurantid from ratings where userid" +
                " = " + userid + " order by rating desc limit " + noToGet + ";";
        String curQuery;
        int curRecord;
        Restaurant curRestaurant;
        ResultSet rsRestoInfo;
        Statement st2;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            st2 = conn.createStatement();
            rs = st.executeQuery(topRatedQuery);
            rs.first();

            // Translate the SQL results into a string array
            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                curQuery = restoInfoQuery + rs.getInt("restaurantid") + ";";
                rsRestoInfo = st2.executeQuery(curQuery);
		System.out.println(curQuery);
		if (!rsRestoInfo.first()){
		    break;
		}

                // Construct a new restaurant with the query returned parameters
                curRestaurant = new Restaurant(
                        rsRestoInfo.getInt("restaurantid"),
                        rsRestoInfo.getFloat("averagerating"),
                        rsRestoInfo.getInt("numberofratings"),
                        rsRestoInfo.getString("name"),
                        rsRestoInfo.getInt("streetnumber"),
                        rsRestoInfo.getString("streetname"),
                        rsRestoInfo.getString("postalcode"),
                        rsRestoInfo.getString("city"),
                        rsRestoInfo.getString("province"),
                        rsRestoInfo.getString("country"),
                        rsRestoInfo.getString("phone"),
                        rsRestoInfo.getString("cuisine"),
                        rsRestoInfo.getString("webaddress"),
                        rsRestoInfo.getString("openhours"),
                        rsRestoInfo.getString("closehours"),
                        rsRestoInfo.getFloat("cost"),
                        rsRestoInfo.getBoolean("alcohol"),
                        rsRestoInfo.getString("payment"),
                        rsRestoInfo.getString("description"),
                        rsRestoInfo.getBoolean("reservationsaccepted"),
                        rsRestoInfo.getBoolean("reservationsrecommended"),
                        rsRestoInfo.getString("imagepath"));
                topRated[curRecord] = curRestaurant;
                // I was getting a strange error for the last result in the
                // resultset rs, caused by the resultset moving past the end
                // of the database; this seems to have fixed it...
                if (curRecord != noToGet - 1) {
                    System.out.println(rs.isLast());
                    rs.next();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Problem in getTopRated:\n" + ex);
            return null;
            // If there was a problem with the connection, we will return null
        }
        return topRated;
    }

    /**
     * getMostActiveUsers(int noToGet)
     *
     * Gets the top noToGet most active users in the system. Activity is defined
     * as the number of ratings in the past 30 days. We can get the number of
     * ratings in the last 30 days for user with userid = <current user> using:
     *	    SELECT COUNT(USERID) FROM RATINGS WHERE RATINGDATE >
     *	    SUBDATE(CURDATE(), INTERVAL 30 DAY) AND USERID = <current user>;
     * Then we will have to find the top noToGet of these object. We can do this
     * by loading the results of the previous query into an array, and sorting,
     * then parsing the array. Then we will return the top noToGet from the
     * sorted array. We require a connection to the database be open, and the
     * database follow the format outlined in the low-level design document.
     *
     * @param noToGet - The number of most active users to retrieve. Should be
     * greater than 0, and less than the total number of users in the system.
     */
    public User[] getMostActiveUsers(int noToGet) {

        // Check the inputs
        //	If we are to retrieve more records than exist in the database,
        //	we only retrive as many records as there are in the database.
        //	If we are to retrive 0, or a negative number of records, we
        //	abort the procedure.
        int maxNoToGet = this.getNumberUsers();
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        } else if (noToGet <= 0) {
            return null;
        }

        // mostActive
        //	the user array to return, storing the user objects for the most
        //	active noToGet users in order of activity
        // activityLevel
        //	stores the activity level (in ratings per last 30 days), of each
        //	user
        // activityUsers
        //	stores the user associated with each activity level from
        //	activityLevel
        // allUsers
        //	stores the list of all user ids, so we know which userids we may
        //	query, and which we may not
        // mostActiveQuery
        //	the SQL query that generates the required output
        // allUsersQuery
        //	the SQL query that generates all of the users
        // getUserInfoQuery
        //	the SQL query that gets all of the information about a
        //	particular user, for storage in the User object
        User[] mostActive = new User[noToGet];
        int[] activityLevel = new int[maxNoToGet];
        //User[] activityUsers = new User[maxNoToGet];
        int[] allUsers = new int[maxNoToGet];
        String mostActiveQuery = "select count(userid) from ratings where " +
                "ratingdate > subdate(curdate(), interval 30 day) and userid =";
        String allUsersQuery = "select userid from users;";
        String getUserInfoQuery = "select * from users where userid = ";
        Statement st;
        ResultSet rs, rsUserInfo;
        String curQuery;
        int curRecord;
        User curUser;

        // Send the queries to the database, handling any exceptions
        try {
            // get the array of user ids for the second query
            st = conn.createStatement();
            rs = st.executeQuery(allUsersQuery);
            rs.first();
            for (curRecord = 0; curRecord < maxNoToGet; curRecord++) {
                allUsers[curRecord] = rs.getInt("userid");
                rs.next();
            }

            // Now, allUsers stores all of the different user ids, we have to
            // query how active each of these users is, sort the users based on
            // the activity level, and then query to find out all of the
            // information about EACH particular user
            for (curRecord = 0; curRecord < maxNoToGet; curRecord++) {
                st = conn.createStatement();
                curQuery = mostActiveQuery + allUsers[curRecord] + ";";
                rs = st.executeQuery(curQuery);
                rs.first();
                activityLevel[curRecord] = rs.getInt(1);
            }

            bubbleSort(activityLevel, allUsers, maxNoToGet);

            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                // Retrieve all information for this partuclar user, and create a User object
                st = conn.createStatement();
                curQuery = getUserInfoQuery + allUsers[curRecord] + ";";
                rsUserInfo = st.executeQuery(curQuery);
                rsUserInfo.first();
                curUser = new User(rsUserInfo.getInt("userid"),
                        rsUserInfo.getString("displayname"),
                        rsUserInfo.getString("password"),
                        rsUserInfo.getString("email"),
                        rsUserInfo.getBoolean("blocked"),
                        rsUserInfo.getBoolean("admin"));
                mostActive[curRecord] = curUser;
            }

        } catch (SQLException ex) {
            System.out.println("Error in getMostActiveUsers: " + ex);
            // if there was a problem with the conneciton, return null
            return null;
        }
        return mostActive;
    }

    /**
     * getMostActiveRestaurants(int noToGet)
     *
     * Gets the top noToGet most active restaurants in the system. Activity is
     * defined as the number of ratings in the past 30 days. We can get the
     * number of ratings in the last 30 days for restaurant with restaurantid
     * <current restaurant> using:
     *	    SELECT COUNT(RESTAURANTID) FROM RATINGS WHERE RATINGDATE >
     *	    SUBDATE(CURDATE(), INTERVAL 30 DAY) AND RESTAURANTID =
     *	    <current restaurant>
     * Then we will have to find the top noToGet of these. We can do this by
     * loading the results of the previous query into an array and sorting, then
     * parsing the array. Then return the top noToGet from the sorted array. We
     * require a connection to the database be opened, and the database follow
     * the format outlined in the low-level design document.
     * 
     * @param noToGet - The number of most active restaurants to get. Should be
     * greater than 0, and less than the total number of restaurants in the
     * system.
     */
    public Restaurant[] getMostActiveRestaurants(int noToGet) {

        // Check the inputs
        //	If we are to retrieve more records than exist in the database,
        //	we only retrive as many records as there are in the database
        //	If we are to retrive 0, or a negative number of records, we
        //	abort the procedure
        int maxNoToGet = this.getNumberRestaurants();
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        } else if (noToGet <= 0) {
            return null;
        }

        // mostActive
        //	the Restaurants array to return, storing the names of the most
        //	active noToGet restaurants in order of activity
        // activityLevel
        //	stores the restrautantID of all restaurants, sorted based on
        //	activity
        // activityLevelRestaurants
        //	stores the restaurant objects corresponding to the activity
        //	level in activityLevel
        // allRestaurants
        //	stores all of the restaurantids, so we know which restaurantids
        //	we may query, and which we may not
        // mostActiveQuery
        //	the SQL query that generates the required output
        // allRestaurantsQuery
        //	the SQL query that generates all of the restaurantids
        // getRestaurantInfoQuery
        //	the SQL query that retrieves all information about a particular
        //	restaurant
        Restaurant[] mostActive = new Restaurant[noToGet];
        int[] activityLevel = new int[maxNoToGet];
        int[] allRestaurants = new int[maxNoToGet];
        String mostActiveQuery = "select count(restaurantid) from ratings " +
                "where ratingdate >  subdate(curdate(), interval 30 day) and" +
                " restaurantid = ";
        String allRestaurantsQuery = "select restaurantid from restaurants;";
        String getRestaurantInfoQuery = "select * from restaurants where " +
                "restaurantid = ";
        Statement st;
        ResultSet rs, rsRestaurantInfo;
        int curRecord;
        Restaurant curRestaurant;
        String curQuery;

        // Send the queries to the database, handling any exceptions
        try {
            // get the array of restaurant ids for the second query
            st = conn.createStatement();
            rs = st.executeQuery(allRestaurantsQuery);
            rs.first();
            for (curRecord = 0; curRecord < maxNoToGet; curRecord++) {
                allRestaurants[curRecord] = rs.getInt("restaurantid");
                rs.next();
            }

            // Now, allRestaurants stores all of the different restaurant ids,
            // we have to query how active each of these restaurants is, sort
            // the restaurants based on activity, and get the information about
            // that particular restaurant
            for (curRecord = 0; curRecord < maxNoToGet; curRecord++) {
                // Determine how active the restaurant is
                st = conn.createStatement();
                curQuery = mostActiveQuery + allRestaurants[curRecord];
                rs = st.executeQuery(curQuery);
                rs.first();
                activityLevel[curRecord] = rs.getInt(1);
            }

            bubbleSort(activityLevel, allRestaurants, maxNoToGet);

            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                st = conn.createStatement();
                curQuery = getRestaurantInfoQuery + allRestaurants[curRecord];
                rsRestaurantInfo = st.executeQuery(curQuery);
                rsRestaurantInfo.first();
                curRestaurant = new Restaurant(rsRestaurantInfo.getInt("restaurantid"),
                        rsRestaurantInfo.getFloat("averagerating"),
                        rsRestaurantInfo.getInt("numberofratings"),
                        rsRestaurantInfo.getString("name"),
                        rsRestaurantInfo.getInt("streetnumber"),
                        rsRestaurantInfo.getString("streetname"),
                        rsRestaurantInfo.getString("postalcode"),
                        rsRestaurantInfo.getString("city"),
                        rsRestaurantInfo.getString("province"),
                        rsRestaurantInfo.getString("country"),
                        rsRestaurantInfo.getString("phone"),
                        rsRestaurantInfo.getString("cuisine"),
                        rsRestaurantInfo.getString("webaddress"),
                        rsRestaurantInfo.getString("openhours"),
                        rsRestaurantInfo.getString("closehours"),
                        rsRestaurantInfo.getFloat("cost"),
                        rsRestaurantInfo.getBoolean("alcohol"),
                        rsRestaurantInfo.getString("payment"),
                        rsRestaurantInfo.getString("description"),
                        rsRestaurantInfo.getBoolean("reservationsaccepted"),
                        rsRestaurantInfo.getBoolean("reservationsrecommended"),
                        rsRestaurantInfo.getString("imagepath"));
                mostActive[curRecord] = curRestaurant;
            }

        } catch (SQLException ex) {
            // if there was a problem with the connection, return null
            return null;
        }
        return mostActive;
    }

    /**
     * getRecentlyRated(int noToGet)
     *
     * Gets the noToGet most recently rated restaurants in the system using:
     *	    SELECT DISTINCT RESTAURANTID FROM RATINGS ORDER BY RATINGDATE DESC
     *	    LIMIT <noToGet>;
     * Then we will have to go through and retrieve all of the restaurant information for each
     * restaurantID we get from the above query. This can be done using a simple
     *	    SELECT * FROM RESTAURANTS WHERE RESTAURANTID = <current restaurant id>
     * This will give us the required result. We require that a connection to
     * the database be open, and the database follow the format outlined in the
     * low-level design document.
     *
     * @param noToGet - the number of recentlyRated restaurants to get, should
     * be greater than 0, and less than the number of restaurants in the
     * system.
     */
    public Restaurant[] getRecentlyRated(int noToGet) {

        // Check the inputs
        //	If we are to retrieve more results than exist, only retrieve the
        //	maximum number of results.
        //	If we are to retrieve 0 or less results, abort the operation.
        int maxNoToGet;
        maxNoToGet = getNumberRatedRestaurants();
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        }
        if (noToGet <= 0) {
            return null;
        }

        // recentlyRated
        //	the array of restaurant objects corresponding to the most
        //	recently rated restaurants in the system
        //  recentlyRatedRestaurantIDs
        //	the array of restaurantIDs for the top noToGet most recently
        //	rated restaurants in the system
        //  recentlyRatedRestaurantsQuery
        //	the SQL query that retrieves the noToGet most recently rated
        //	restaurants in the system
        //  getRestaurantInfoQuery
        //	the SQL query that retrives all of the information about a
        //	particular restaurant
        Restaurant[] recentlyRated = new Restaurant[noToGet];
        int[] recentlyRatedRestaurantIDs = new int[noToGet];
        String recentlyRatedRestaurantsQuery = "select distinct restaurantid " +
                "from ratings order by  ratingdate desc limit " + noToGet;
        String getRestaurantInfoQuery = "select * from restaurants where " +
                "restaurantid = ";
        ResultSet rs, rsRestaurantInfo;
        Statement st;
        String curQuery;
        Restaurant curRestaurant;
        int curRecord;

        // Send the queries to the database, handling any exceptions
        try {
            // Get the restaurant indices
            st = conn.createStatement();
            rs = st.executeQuery(recentlyRatedRestaurantsQuery);
            rs.first();

            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                recentlyRatedRestaurantIDs[curRecord] = rs.getInt("restaurantid");
                rs.next();
            }

            // Now recentlyRatedRestaurantIDs stores the ID numbers for each
            // restaurant that has recently been rated, so we only need to
            // convert these IDs into restaurant objects
           for (curRecord = 0; curRecord < noToGet; curRecord++) {
                st = conn.createStatement();
                curQuery = getRestaurantInfoQuery +
                        recentlyRatedRestaurantIDs[curRecord] + ";";
                rsRestaurantInfo = st.executeQuery(curQuery);
                rsRestaurantInfo.first();
                curRestaurant = new Restaurant(
                        rsRestaurantInfo.getInt("restaurantid"),
                        rsRestaurantInfo.getFloat("averagerating"),
                        rsRestaurantInfo.getInt("numberofratings"),
                        rsRestaurantInfo.getString("name"),
                        rsRestaurantInfo.getInt("streetnumber"),
                        rsRestaurantInfo.getString("streetname"),
                        rsRestaurantInfo.getString("postalcode"),
                        rsRestaurantInfo.getString("city"),
                        rsRestaurantInfo.getString("province"),
                        rsRestaurantInfo.getString("country"),
                        rsRestaurantInfo.getString("phone"),
                        rsRestaurantInfo.getString("cuisine"),
                        rsRestaurantInfo.getString("webaddress"),
                        rsRestaurantInfo.getString("openhours"),
                        rsRestaurantInfo.getString("closehours"),
                        rsRestaurantInfo.getFloat("cost"),
                        rsRestaurantInfo.getBoolean("alcohol"),
                        rsRestaurantInfo.getString("payment"),
                        rsRestaurantInfo.getString("description"),
                        rsRestaurantInfo.getBoolean("reservationsaccepted"),
                        rsRestaurantInfo.getBoolean("reservationsrecommended"),
                        rsRestaurantInfo.getString("imagepath"));
                recentlyRated[curRecord] = curRestaurant;
            }
        } catch (SQLException ex) {
            // If there was a problem with the connection, return null
            System.out.println("Error in getRecentlyRated1\n" + ex);
            return null;
        }
        return recentlyRated;
    }

    /**
     * getRecentlyRated(int noToGet,int userid)
     *
     * Gets the noToGet most recently rated restaurants rated by a particular
     * restaurant in the system using:
     *	    SELECT DISTINCT RESTAURANTID FROM RATINGS WHERE USERID = <curuser>
     *	    ORDER BY RATINGDATE DESC LIMIT <noToGet>;
     * Then we will have to go through and retrieve all of the restaurant
     * information for each restaurantID we get from the above query. This can
     * be done using a simple
     *	    SELECT * FROM RESTAURANTS WHERE RESTAURANTID = <current restaurant id>
     * This will give us the required result. We require that a connection to
     * the database be open, and the database follow the format outlined in the
     * low-level design document.
     *
     * @param noToGet - the number of recentlyRated restaurants to get, should
     * be greater than 0, and less than the number of restaurants in the
     * system.
     * @param userid - the userid that we want to find recently rated
     * restaurants for.
     */
    public Restaurant[] getRecentlyRated(int noToGet, int userid) {

        // Check the inputs
        //	If we are to retrieve more results than exist, only retrieve the
        //	maximum number of results.
        //	If we are to retrieve 0 or less results, abort the operation.
        String howManyRatings = "Select count(ratingid) from ratings where " +
                "userid = " + userid + ";";
        int maxNoToGet;
        Statement st;
        ResultSet rs;
        try {
            conn = DBSettings.getConn();
            st = conn.createStatement();
            rs = st.executeQuery(howManyRatings);
            rs.first();
	    if (!rs.isAfterLast()) {
		maxNoToGet = rs.getInt("count(ratingid)");
	    }
	    else {
		return null;
	    }
        } catch (SQLException ex) {
            System.out.println("Error in getRecentlyRated:\n" + ex);
            return null;
        }
        if (noToGet > maxNoToGet) {
            noToGet = maxNoToGet;
        } else if (noToGet <= 0) {
            return null;
        }

        // recentlyRated
        //	the array of restaurant objects corresponding to the most
        //	recently rated restaurants in the system
        //  recentlyRatedRestaurantIDs
        //	the array of restaurantIDs for the top noToGet most recently
        //	rated restaurants in the system
        //  recentlyRatedRestaurantsQuery
        //	the SQL query that retrieves the noToGet most recently rated
        //	restaurants in the system
        //  getRestaurantInfoQuery
        //	the SQL query that retrives all of the information about a
        //	particular restaurant
        Restaurant[] recentlyRated = new Restaurant[noToGet];
        int[] recentlyRatedRestaurantIDs = new int[noToGet];
        String recentlyRatedRestaurantsQuery = "select distinct restaurantid " +
                "from ratings where userid = " + userid + " order by  " +
                "ratingdate desc limit " + noToGet;
        String getRestaurantInfoQuery = "select * from restaurants where " +
                "restaurantid = ";
        ResultSet rsRestaurantInfo;
        String curQuery;
        Restaurant curRestaurant;
        int curRecord;

        // Send the queries to the database, handling any exceptions
        try {
            // Get the restaurant indices
            st = conn.createStatement();
            rs = st.executeQuery(recentlyRatedRestaurantsQuery);
            rs.first();
            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                recentlyRatedRestaurantIDs[curRecord] = rs.getInt("restaurantid");
                rs.next();
            }

            // Now recentlyRatedRestaurantIDs stores the ID numbers for each
            // restaurant that has recently been rated, so we only need to
            // convert these IDs into restaurant objects
            for (curRecord = 0; curRecord < noToGet; curRecord++) {
                st = conn.createStatement();
                curQuery = getRestaurantInfoQuery + recentlyRatedRestaurantIDs[curRecord] + ";";
                rsRestaurantInfo = st.executeQuery(curQuery);
                rsRestaurantInfo.first();
                curRestaurant = new Restaurant(rsRestaurantInfo.getInt("restaurantid"),
                        rsRestaurantInfo.getFloat("averagerating"),
                        rsRestaurantInfo.getInt("numberofratings"),
                        rsRestaurantInfo.getString("name"),
                        rsRestaurantInfo.getInt("streetnumber"),
                        rsRestaurantInfo.getString("streetname"),
                        rsRestaurantInfo.getString("postalcode"),
                        rsRestaurantInfo.getString("city"),
                        rsRestaurantInfo.getString("province"),
                        rsRestaurantInfo.getString("country"),
                        rsRestaurantInfo.getString("phone"),
                        rsRestaurantInfo.getString("cuisine"),
                        rsRestaurantInfo.getString("webaddress"),
                        rsRestaurantInfo.getString("openhours"),
                        rsRestaurantInfo.getString("closehours"),
                        rsRestaurantInfo.getFloat("cost"),
                        rsRestaurantInfo.getBoolean("alcohol"),
                        rsRestaurantInfo.getString("payment"),
                        rsRestaurantInfo.getString("description"),
                        rsRestaurantInfo.getBoolean("reservationsaccepted"),
                        rsRestaurantInfo.getBoolean("reservationsrecommended"),
                        rsRestaurantInfo.getString("imagepath"));
                recentlyRated[curRecord] = curRestaurant;
            }
        } catch (SQLException ex) {
            // If there was a problem with the connection, return null
             System.out.println("Error in getRecentlyRated2:\n" + ex);
            return null;
        }
        return recentlyRated;
    }

    //We had some issue compiling the java environment on the server, so we call it here
    //In order to show off the functionality.
    public Restaurant[] getRecommended(User loggedUser) {
        Recommender recommend = new Recommender();
        int ID = loggedUser.getUserID();
        return recommend.recommend(ID, "Users", "Restaurants", "Ratings");
    }

    public int getNumberRatedRestaurants() {
        int numberRatings = -1;
        String ratedRestaurantsQuery = "select count(distinct restaurantid) " +
           "from ratings order by ratingdate desc ";
        Statement st;
        ResultSet rs;

        // Send the query to the database, handling any exceptions
        try {
            st = conn.createStatement();
            rs = st.executeQuery(ratedRestaurantsQuery);

            // translate the results into a java integer
            rs.first();
            numberRatings = rs.getInt(1);
        } catch (SQLException ex) {
            // If there was a problem with the connection, we will return -1
            return -1;
        }
        return numberRatings;
    }

    // Auxillary methods
    // Provide essential functionality for the above, public, methods
    /**
     * bubbleSort(int array1[], int array2[], int max)
     *
     * A simple bubble sort implementation to sort the integer array1, while
     * keeping the reference ordering consistent in array2. This means that
     * any swaps made in array1, are identically made in array2. Use this in the
     * event that quickSort is not functioning properly. For more information
     * visit: http://en.wikipedia.org/wiki/Bubble_sort
     * Note: Sorts the array in descending order.
     *
     * @param array1 - The array of integers that we want to perform our
     * sorting on.
     * @param array2 - The secondary array of integers. This array is swapped
     * identically to array1 during the sort. This is just a cheap way of
     * sorting pairs of values, without having to make a new pairs object.
     * @param max - The maximum index to sort up to in the array.
     */
    private void bubbleSort(int array1[], int array2[], int max) {
        int i, j;
        for (i = 1; i < max; i++) {
            for (j = 0; j < max - i; j++) {
                if (array1[j] < array1[j + 1]) {
                    swap(array1, array2, j, j + 1);
                }
            }
        }
    }

    /**
     * quickSort(int array1[], int array2[], int left, int right)
     *
     * An implementation of quicksort that sorts based on array1 between indices
     * left, and right. During the sorting of array 1, it maintains a constant
     * relative ordering of array2. This is a similar idea to the bubblesort
     * above, but faster. For more information visit:
     * http://en.wikipedia.org/wiki/Quicksort
     * Note: Sorts the array in descending order.
     *
     * @param array1 - The array of integers that we want to perform our
     * sorting on.
     * @param array2 - The secondary array of integers. This array is swapped
     * identically to array1 during the sort.
     * @param left - The left-bound for the sorting.
     * @param right - The right-bound for the sorting.
     */
    private void quickSort(int array1[], int array2[], int left, int right) {

        if (left >= right) {
            return;
        }

        int p = partition(array1, array2, left, right);

        quickSort(array1, array2, left, p);
        quickSort(array1, array2, p + 1, right);

    }

    /**
     * partition(int array1[], int array2[], int left, int right)
     *
     * Quicksort's partition routine. This method picks a pivot in the array
     * to sort on, and moves that pivot to the appropriate place in the array,
     * while reordering the elemnts around it. The index of the pivot is
     * returned.
     *
     * @param array1 - The array of integers we are sorting on.
     * @param array2 - The secondary array of integers to preserver ordering on.
     * @param left - The left-bound for the sorting.
     * @param right - The right-bound for the sorting.
     * @return  - The index of the partition element.
     */
    private int partition(int array1[], int array2[], int left, int right) {

        int i;
        int j = left;
        int pivot = array1[right];

        for (i = left; i < right; i++) {
            if (array1[i] > pivot) {
                swap(array1, array2, i, j);
                j++;
            }
        }

        swap(array1, array2, j, right);

        return j;
    }

    /**
     * Swap
     *
     * Swaps the elements in array1 located at i, and j, and the elements in
     * array2 located at i, and j.
     *
     * @param array1 - Integer array to swap elements in.
     * @param array2 - Integer array to swap elements in.
     * @param i - Index for first swapee element.
     * @param j - Index for second swapee element.
     */
    private void swap(int array1[], int array2[], int i, int j) {

        int tmpInt;
        int otherTmpInt;

        tmpInt = array1[i];
        otherTmpInt = array2[i];
        array1[i] = array1[j];
        array2[i] = array2[j];
        array1[j] = tmpInt;
        array2[j] = otherTmpInt;
    }
}
