/*
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Ad Control class - provides functionality for adding, updating, and removing
 * advertisements from the database. The way this module works is by storing an
 * advertisement object, which is initialized, and set by the user. This object
 * can then be easily added to the database using add() [this will add a
 * new object, or update an existing on in the database], or deleted from the
 * database using delete() [only if the object exists in the database!].
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
*/

package networkInterface;

import java.sql.*;
import entities.ad;

public class DBAdControl {

    // myAd is the advertisement we will work with.
    ad myAd;

    /**
     * Parameterless constructor
     * 
     * Instantiates an AdControl object with a new ad set
     */
    public DBAdControl(){
	this.myAd = new ad();
    }

    /**
     * Parameterized constructor
     *
     * Instantiates an AdControl object with the ad passed
     */
    public DBAdControl(ad yourAd){
	this.myAd = yourAd;
    }

    // Getters and setters, automatically generated by NetBeans
    public ad getAd() {
	return myAd;
    }

    public void setAd(ad myAd) {
	this.myAd = myAd;
    }

    /**
     * add()
     *
     * Commits the advertisement object stored in the DBADControl to the 
     * database. This method is very dependent on the adID stored in the ad
     * object. A restaurantID of -1 means "this is a new ad," and the method
     * will add the new record to the database. An adID not equal to -1 means
     * "this is an ad already in the database," and the add() will treat it as
     * such. If the adID does exist in the database, that is fine, and the
     * commit should go through without a hitch. If the adID does not exist in
     * the database, however, nothing will be changed in the database.
     */
        public void add(){

	// addAd
	//	the SQL command that adds the current adv object into the
	//	database. Note, we will not allow code to edit the value of
	//	timesClicked
	// modifyAd
	//	the basic SQL command for updating an entry in the ad
	//	field. We need to perform this on every field, though.
	String addAd = "insert into ads values(default, " +
		"'" + myAd.getImagePath() + "', " +
		"'" + myAd.getHyperlink() + "', " +
		"0);";
	String verifyExistanceQuery = "select count(adid) from ads where " +
		"adid =  " + myAd.getAdID() + ";";
	String modifyAd = "update ads set ";
	String finishModifyAd = " where adid = " + myAd.getAdID();
	Statement st;
	ResultSet rs;

	// Try to run the commands on the database, handling any exceptions
	try{
	    Connection conn = DBSettings.getConn();
	    st = conn.createStatement();

	    // If this is a new ad, then the adid will be -1.
	    // This means we want to execute the add statement.
	    if (myAd.getAdID() == -1){
		st.execute(addAd);
	    }

	    // Otherwise, first check if this adid exists in the database, if it
	    // does, we want to update all of the possible fields in the
	    // database.
	    else {
		rs = st.executeQuery(verifyExistanceQuery);
		rs.first();

		// no such restaurant
		if (rs.getInt("count(adid)") == 0){
		    return;
		}

		// We found the restaurantID, so now we want to update it
		st.addBatch(modifyAd + "imagepath = '" + myAd.getImagePath() +
			"'" + finishModifyAd);

		st.addBatch(modifyAd + "hyperlink = '" + myAd.getHyperlink() +
			"'" + finishModifyAd);

		st.addBatch(modifyAd + "timesclicked = " +
			myAd.getTimesClicked() + finishModifyAd);
		
		st.executeBatch();
	    }
	} catch (SQLException ex) {
	    // If there was a problem with the connection, stop
	    return;
	}
    }

    /**
     * delete()
     *
     * Deletes the ad from the system. Again, this is based on the adID, so this
     * will need to be correct. The adID for a particular ad can be gotten
     * through the DBSearchControl.
     */
    public void delete(){

	// existsQuery
	//	used to verify that the record exists in the database. This will
	//	help us to avoid unneccessary exceptions
	// deleteCommand
	//	the SQL command used to delete the record from the database
	String existsQuery = "select * from ads where adid = " + myAd.getAdID();
	String deleteCommand = "delete from ads where adid = " + myAd.getAdID();
	Statement st;
	ResultSet rs;

	// Try to send the commands to the database,handling any exceptions
	try{
	    Connection conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(existsQuery);

	    // Does the restaurantID exist in the database?
	    if (rs.isLast()) {
		return;
	    }

	    // If the restaurantID is valid in the database, continue deleting
	    st.execute(deleteCommand);
	} catch (SQLException ex) {
	    // If there was a problem with the connection, stop
	    return;
	}
    }
}
