/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Restaurant Control class - provides functionality for adding, updating, and
 * removing restaurants from the database. The way this module works is by
 * storing a restaurant object, which is initialized, and set by the user. This
 * object can then be easily added to the database using add() [this will add a
 * new object, or update an existing on in the database], or deleted from the
 * database using delete() [only if the object exists in the database!].
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
*/

package networkInterface;

import java.sql.*;
import entities.Restaurant;

public class DBRestaurantControl {

    // restaurant is the restaurant object we will work with.
    Restaurant restaurant;

    /**
     * Constructor
     *
     * Stores the persistent connection in the DBRestaurantControl, and creates
     * a new restaurant for the restaurant.
     */
    public DBRestaurantControl(){
	this.restaurant = new Restaurant();
    }

    /**
     * Constructor
     *
     * Stores the persistent connection in the DBRestaurantControl, and stores
     * the passed restaurant for the restaurant.
     *
     * @param restaurant - the restaurant to store in the DBRestaurantControl
     */
    public DBRestaurantControl(Restaurant restaurant){
	this.restaurant = restaurant;
    }

    // Getters and Setters, automatically generated by NetBeans
    public Restaurant getRestaurant() {
	return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
	this.restaurant = restaurant;
    }

    /**
     * add()
     *
     * Commits the restaurant object stored in the DBRestaurantControl to the
     * database. This method is very dependent on the restaurantID stored in
     * the restaurant object. A restaurantID of -1 means "this is a new
     * restaurant," and the method will add the new record to the database. A
     * restaurantID not equal to -1 means "this is a restaurant already in the
     * database," and the add() will treat it as such. If the restaurantID does
     * exist in the database, that is fine, and the commit should go through
     * without a hitch. If the restaurantID does not exist in the database,
     * however, nothing will be changed in the database.
     */
    public void add(){

	// addRestaurant
	//	the SQL command that adds the current restaurant object into the
	//	database. Note, we will not allow code to edit the values of
	//	averagerating, and numberofratings. These should only be
	//	modified by inserting/modifying ratings.
	// modifyRestaurant
	//	the basic SQL command for updating an entry in the restaurant
	//	field. We need to perform this on every field, though.
	String addRestaurant = "insert into restaurants values(default, 0.0, " +
				"0, " +
				"'" + restaurant.getName() + "', " +
				restaurant.getStreetNumber() + ", " +
				"'" + restaurant.getStreetName() + "', " +
				"'" + restaurant.getPostalCode() + "', " +
				"'" + restaurant.getCity() + "', " +
				"'" + restaurant.getProvince() + "', " +
				"'" + restaurant.getCountry() + "', " +
				"'" + restaurant.getPhone() +"', " +
				"'" + restaurant.getCuisine() + "', " +
				"'" + restaurant.getWebaddress() + "', " +
				"'" + restaurant.getOpenHours() + "', " +
				"'" + restaurant.getCloseHours() + "'," +
				restaurant.getCost() + ", " +
				restaurant.getAlcohol() + ", " +
				"'" + restaurant.getPayment() + "', " +
				"'" + restaurant.getDescription() + "', " +
				restaurant.getReservationsAccepted() + ", " +
				restaurant.getReservationsRecommended() + ", " +
				"'" + restaurant.getImagePath() + "'" + ");";
	String verifyExistanceQuery = "select count(name) from restaurants " + 
		"where restaurantid =  " + restaurant.getRestaurantID() + ";";
	String modifyRestaurant = "update restaurants set ";
	String finishModifyRestaurant = " where restaurantid = "
		+ restaurant.getRestaurantID();
	Statement st;
	ResultSet rs;

	// Try to run the commands on the database, handling any exceptions
	try{
	    Connection conn = DBSettings.getConn();
	    st = conn.createStatement();

	    // If this is a new restaurant, then the restaurantid will be -1.
	    // This means we want to execute the add statement.
	    if (restaurant.getRestaurantID() == -1){
                System.out.println("tried to add");
		st.execute(addRestaurant);
	    }
	    // Otherwise, first check if this restaurantid exists in the
	    // database, if it does, we want to update all of the possible
	    // fields in the database.
	    else {
		rs = st.executeQuery(verifyExistanceQuery);
		rs.first();

		// no such restaurant
		if (rs.getInt("count(name)") == 0){
		    return;
		}

		// We found the restaurantID, so now we want to update it
		st.addBatch(modifyRestaurant + "name = " + "'" +
			restaurant.getName() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "streetnumber = " +
			restaurant.getStreetNumber() + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "streetname = " + "'" +
			restaurant.getStreetName() + "'" +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "postalcode = " + "'" +
			restaurant.getPostalCode() + "'" +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "city = " + "'" + 
			restaurant.getCity() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "province = " + "'" +
			restaurant.getProvince() + "'" +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "country = " + "'" + 
			restaurant.getCountry() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "phone = " + "'" +
			restaurant.getPhone() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "cuisine = " + "'" + 
			restaurant.getCuisine() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "webaddress = " + "'" + 
			restaurant.getWebaddress() + "'" +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "openhours = " + "'" +
			restaurant.getOpenHours() + "'" + 
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "closehours = " + "'" +
			restaurant.getCloseHours() + "'");

		st.addBatch(modifyRestaurant + "cost = " + 
			restaurant.getCost() + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "alcohol = " + 
			restaurant.getAlcohol() + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "payment = " + "'" +
			restaurant.getPayment() + "'" + finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "description = " + "'" +
			restaurant.getDescription() + "'" +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "reservationsaccepted = " +
			restaurant.getReservationsAccepted() +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "reservationsrecommended = " +
			restaurant.getReservationsRecommended() +
			finishModifyRestaurant);

		st.addBatch(modifyRestaurant + "imagepath = " + "'" +
			restaurant.getImagePath() + "'" +
			finishModifyRestaurant);
		
		st.executeBatch();
	    }
	} catch (SQLException ex) {
	    // If there was a problem with the connection, stop
            System.out.println("problem trying to add\n" + ex);
	    return;
	}
    }

    /**
     * delete()
     *
     * Deletes the restaurant from the system. Again, this is based on the
     * restaurantID, so this will need to be correct. The restaurantID for a
     * particular restaurant can be gotten through the DBSearchControl.
     */
    public void delete(){

	// existsQuery
	//	used to verify that the record exists in the database. This will
	//	help us to avoid unneccessary exceptions
	// deleteCommand 
	//	the SQL command used to delete the record from the database
	String existsQuery = "select name from restaurants where restaurantid" + 
		" = " + restaurant.getRestaurantID() + ";";
	String deleteCommand = "delete from restaurants where restaurantid = " +
		    restaurant.getRestaurantID() + ";";
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