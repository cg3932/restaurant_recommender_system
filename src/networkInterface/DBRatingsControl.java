/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networkInterface;
import entities.*;
import java.util.Date;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Armen Kitbalian
 */
public class DBRatingsControl {
    Rating ratingEnt = new Rating();
    private Boolean success = false;
    private String ratingColumns = "ratingid, userid, restaurantid, rating, comment, ratingdate, flagged";
    private int userID = 0;
    private int restaurantID = 0;
    private int rating = 0;
    private String comment = "";
    private boolean flagged = false;
    private String fieldControl = "";
    private String newValue = "";
    Connection conn;
    Statement st;

    public DBRatingsControl() throws SQLException {
       try {
	    conn = DBSettings.getConn();
	}
	catch (SQLException ex) {
	    throw ex;
	}
    }
    
     public boolean changeRatingScore(Rating rating){
        int score = rating.getRating();
        if(score > 5 || score < 0){
            System.out.println("score must be between 0 and 5!");
            return success;
        }
        fieldControl = "rating";
        this.newValue = Integer.toString(score);
        changeRating(rating);
        return success;
    }

    public boolean changeRatingComment(Rating rating){
        String newComment = rating.getComment();
        fieldControl = "comment";
        this.newValue = newComment;
        changeRating(rating);
        return success;
    }

    public boolean changeRatingFlagged(Rating rating){
        boolean newFlagged = rating.getFlagged();
        fieldControl = "flagged";
        if(newFlagged == true){
        this.newValue = "1";
    }else{
        this.newValue = "0";
    }
        changeRating(rating);
        return success;
    }


    private boolean changeRating(Rating rating){
        try {
    // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       int ratingID = rating.getRatingID();
       java.util.Date blah = new Date();
       java.sql.Date changeDate = new java.sql.Date(blah.getTime());
       st = conn.createStatement();
       String upquery = "update ratings set " + fieldControl + "='" + newValue + "' where ratingID = " + ratingID;
       String upquery1 = "update ratings set " + "ratingdate" + "='" + changeDate.toString() + "' where ratingID = " + ratingID;
       st.executeUpdate(upquery);
       st.executeUpdate(upquery1);
       }catch (SQLException ex) {
            Logger.getLogger(DBRatingsControl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
            return false;
       }
     success = true;
     return success;
    }

    public Boolean deleteRating(Rating rating){

       try {
       st = conn.createStatement();
       // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       int ratingID = rating.getRatingID();
       String upquery = "DELETE FROM ratings WHERE ratingID = " + ratingID;
       st.executeUpdate(upquery);
       }catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
       }
        success = true;
        return success;
       }


    public Rating getRating(int userID1, int restoID1){
       try {
       // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       st = conn.createStatement();
       st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
       ResultSet.CONCUR_READ_ONLY);
       // Getting the string with all the information from the database
       ResultSet rs;
       String query = "select "+ ratingColumns + " from ratings where " +
                      "userid = '" + userID1 + "' and restaurantid = '" + restoID1 + "'";
       rs = st.executeQuery(query);

       // Inserting the respective values to the variables
       if (rs.next()) {
            rs = st.executeQuery(query);
            while (rs.next()) {
                int ratingid1 = rs.getInt("ratingid");
                int uid = rs.getInt("userid");
                int restoID = rs.getInt("restaurantid");
                int rating1 = rs.getInt("rating");
                String comment1 = rs.getString("comment");          
                String date2 = rs.getString("ratingdate");
                boolean flagged1 = rs.getBoolean("flagged");
                java.sql.Date jsqlD = java.sql.Date.valueOf(date2);                
                ratingEnt.setRatingID(ratingid1);
                ratingEnt.setUserID(uid);
                ratingEnt.setRestaurantID(restoID);
                ratingEnt.setRating(rating1);
                ratingEnt.setComment(comment1);
                ratingEnt.setRatingDate(jsqlD);
                ratingEnt.setFlagged(flagged1);
            }
       }
       else{
           System.out.println("Couldn't find: " + userID1 + " or " + restoID1);
            }
       }
       catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
            return null;
       }
       return ratingEnt;
    }

    public Boolean addRating(Rating rating){
        int flagBool = 0;
        this.userID = rating.getUserID();
        this.restaurantID = rating.getRestaurantID();
        this.rating = rating.getRating();
        this.comment = rating.getComment();
        java.util.Date blah = new Date();
        java.sql.Date addDate = new java.sql.Date(blah.getTime());
        this.flagged = rating.getFlagged();
        if (this.flagged == true){
            flagBool = 0;
        }
        // making sure we don't add twice!
        if(getRating(rating.getUserID(), rating.getRestaurantID()).getUserID() == userID && getRating(rating.getUserID(), rating.getRestaurantID()).getRestaurantID() == restaurantID){
            System.out.println("This restaurant has already been rated by this user");
            return success;
        }
        try {
       // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       st = conn.createStatement();
       String sqlInsrt = "insert into ratings (" + ratingColumns + ") values ";
       String insertion = " (DEFAULT, '" + userID + "', '" + restaurantID + "', '" + this.rating + "', '" + comment + "', '" + addDate + "', '" + flagBool + "')";
       st.executeUpdate(sqlInsrt + insertion);
       }catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
       }
    success = true;
    return success;
    }

    // Update the rating to recompute the number of ratings, and the
    // averagerating
    public void updateRating(int restaurantID){

	Connection conn;
	Statement st;
	ResultSet rs;
	String getNoRatings = "select rating from ratings where restaurantid " +
		" = " + restaurantID + ";";
	int curRecord = 0;
	int noRatings = 0;
	float avg = (float) 0.0;

	try {
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(getNoRatings);
	    rs.first();
	    while (!rs.isAfterLast()){
		noRatings++;
		avg += rs.getFloat("rating");
		rs.next();
	    }

	    if (noRatings == 0){
		avg = (float) 0.0;
	    }
	    else {
		avg /= noRatings;
	    }

	    String updateNoRatings = "update restaurants set numberofratings " +
		    "= " + noRatings + " where restaurantid = " + restaurantID +
		    ";";
	    String updateAvgRating = "update restaurants set averagerating " +
		    " = " + avg + " where restaurantid = " + restaurantID +
		    ";";

	    st.execute(updateNoRatings);
	    st.execute(updateAvgRating);
	} catch (SQLException ex) {
	    return;
	}

    }
}
