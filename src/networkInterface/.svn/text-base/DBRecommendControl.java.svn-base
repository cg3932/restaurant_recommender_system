/*
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Recommend Control class - provides the data for the recommendation algorithm.
 * This should be server side, and should be accompanied by a DBSettings object
 * with localhost database information, and a DBStatsControl object.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
*/

package networkInterface;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBRecommendControl {

    // Constructor
    // Doesn't do anything...
    public DBRecommendControl() {
    }

    /**
     * getMatrix()
     *
     * Gets the matrix information for the recommender.
     *
     * @return - a two dimensional array of size 3xn. row 0 is the userids, row
     * 1 is the restaurantids, and row 2 is the rating for that pair.
     */
    public int[][] getMatrix(){

	Connection conn;
	DBStatsControl dbsc;
	try{
	    dbsc = new DBStatsControl();
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    return null;
	}

	// matrix
	//	The two dimensional array to return, containing all of the
	//	rating information
	// getUsersQuery
	//	The SQL query that retrieves the rating triplets
	int numberOfRatings = dbsc.getNumberRatings();
	int[][] matrix = new int[3][numberOfRatings];
	String getUsersQuery = "select userid, restaurantid, rating from " +
		"ratings order by userid;";

	Statement st;
	ResultSet rs;
	int i, j;

	// Query the datbase, handling any exceptions. Then translate the
	// results into the two-dimensional format outlined in the JavaDoc
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(getUsersQuery);
	    rs.first();

	    for (i = 0; i < numberOfRatings; i++){
		matrix[0][i] = rs.getInt("userid");
		matrix[1][i] = rs.getInt("restaurantid");
		matrix[2][i] = rs.getInt("rating");
		rs.next();
	    }
	}
	catch (SQLException ex){
	    return null;
	}
	return matrix;
    }
}
