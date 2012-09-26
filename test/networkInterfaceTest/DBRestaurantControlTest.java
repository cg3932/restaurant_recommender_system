/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBRestaurantControl test class - tests the operations implemented in the
 * DBRestaurantControl class.
 *==============================================================================
 * This test class tests all of the operations implemented in the
 * DBRestaurantControl class. It also tests the add() and delete() for new,
 * old (proper), and old (improper) restaurants. This class should automatically
 * clean up after itself (unless delete is not working!). However, it will cause
 * the auto_incremente in the restaurant table to go up for future restaurants.
 * If any of the tests fail, ensure that the important records in the database
 * have not been changed.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */


package networkInterfaceTest;

import networkInterface.*;
import entities.Restaurant;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

public class DBRestaurantControlTest {

    // The DBRestaurantControl object we will work with
    DBRestaurantControl dbrc;

    // Instantiate the DBRestaurantControl object
    @Before
    public void setUp() {
	dbrc = new DBRestaurantControl();
    }

    // Test get and set restaurant
    @Test
    public void testGetRestaurant() {
	System.out.println("get and set restaurant");
	Restaurant resto = new Restaurant();
	resto.setName("someResto");
	resto.setRestaurantID(4);
	resto.setStreetNumber(15);
	resto.setStreetName("Elm St.");
	resto.setPostalCode("A1A 1A1");
	dbrc.setRestaurant(resto);
	assertEquals(resto.toString(), dbrc.getRestaurant().toString());
    }

    // Test adding a new restaurant, properly (i.e. restaurntid = -1)
    @Test
    public void testAddNewProper() {
	System.out.println("add new restaurant proper");
	Restaurant resto = new Restaurant(-1, (float) 1.0,2,"thename",3,
		"Elm St.","A1A 1A1", "acity","thisprovince","LOOKFORME",
		"1-800-234-5678","Coffee", "http://www.restaurantica.com/",
		"9 AM", "10 PM", (float) 4.0,false,"cash only", "nice ambiance",
		true, false,"C:\\users\\mypictures\\restaurant.gif");
	dbrc.setRestaurant(resto);
	dbrc.add();

	// Check that the restaurant was added to the database by searching for
	// country = 'LOOKFORME' in the database
	Connection conn;
	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    fail("There was a problem connection to the database");
	    return;
	}
	ResultSet rs;
	Statement st;
	
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery("select description from restaurants where " +
		    "country = 'LOOKFORME'");
	    rs.first();

	    // Check that the description for the result is "nice ambiance" as
	    // required
	    assertEquals(rs.getString("description"), "nice ambiance");
	} catch (SQLException ex){
	    fail("could not query the database");
	    return;
	}
    }

    // Test adding a new restaurant with restaurantid != -1. As this is
    // improper, it should not do anything
    @Test
    public void testAddNewImproper(){
	System.out.println("add new restaurant improper");
	Restaurant resto = new Restaurant(9000, (float) 1.0,2,"thename",3,
		"Elm St.","A1A 1A1", "acity","LOOKFORME","somecountry",
		"1-800-234-5678","Coffee", "http://www.restaurantica.com/",
		"9 AM", "10 PM", (float) 4.0,false,"cash only", "nice ambiance",
		true, false,"C:\\users\\mypictures\\restaurant.gif");
	dbrc.setRestaurant(resto);
	dbrc.add();

	// Open a connection and check that we do not have a restaurant in the
	// database with
	//	country = 'LOOKFORME'
	Connection conn;
	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}
	ResultSet rs;
	Statement st;
	
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery("select count(restaurantid) from restaurants" +
		    " where province = 'LOOKFORME'");
	    rs.first();
	    // Check that there were no such restaurants
	    assertEquals(rs.getInt("count(restaurantid)"), 0);
	} catch (SQLException ex){
	    fail("could not query the database");
	    return;
	}
    }

    // Test modification of an existing restaurant
    @Test
    public void testAddExisting(){
	System.out.println("add new restaurant");

	// Let's get the restaurantID for the restaurant we just added...
	Connection conn;
	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}
	ResultSet rs;
	Statement st;
	int newRID = -1;
	
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery("select restaurantid from restaurants where" +
		    " country = 'LOOKFORME'");
	    rs.first();

	    // Get the restaurantID for this object
	    newRID = rs.getInt("restaurantid");
	} catch (SQLException ex){
	    fail("There was a problem querying the database");
	    return;
	}

	// Now we set the restaurantID in our object to the restaurantID we
	// want to modify, and update the information
	Restaurant resto = new Restaurant(newRID, (float) 1.0,2,"LOOKFORME",3,
		"Elm St.","A1A 1A1", "acity","thisprovince","LOOKFORME",
		"1-800-234-5678","Coffee", "http://www.restaurantica.com/",
		"9 AM", "10 PM", (float) 4.0,false,"cash only", "nice ambiance",
		true, false,"C:\\users\\mypictures\\restaurant.gif");
	dbrc.setRestaurant(resto);
	dbrc.add();

	// Now we want to check that we have a restaurant in the database with
	//	name = 'LOOKFORME'
	try {
	    rs = st.executeQuery("select count(restaurantid) from restaurants" + 
		    " where name =  'lookforme';");
	    rs.first();
	    assertEquals(1, rs.getInt("count(restaurantid)"));
	} catch(SQLException ex){
	    fail("there was a problem querying the database");
	    return;
	}	
    }

    // Test deletion of restaurants
    @Test
    public void testDelete() {
	System.out.println("delete");

	// First we will need to find the restaurant we have been modifying
	Connection conn;
	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}

	ResultSet rs;
	Statement st;
	int badRID = -1;
	
	try{
	    st = conn.createStatement();

	    // The restaurant we want to remove has name = 'LOOKFORME'
	    rs = st.executeQuery("select restaurantid from restaurants where " +
		    "name = 'LOOKFORME'");
	    rs.first();
	    badRID = rs.getInt("restaurantid");
	} catch (SQLException ex){
	    fail("could not query the database");
	    return;
	}

	// Create a new restaurant with the id-to-delete, and delete it from the
	// database
	Restaurant resto = new Restaurant();
	resto.setRestaurantID(badRID);
	dbrc.setRestaurant(resto);
	dbrc.delete();

	// Ensure that the restaurant is gone
	try{
	    rs = st.executeQuery("select count(restaurantid) from restaurants" +
		    " where name = 'LOOKFORME';");
	    rs.first();
	    assertEquals(0, rs.getInt("count(restaurantid)"));
	} catch(SQLException ex) {
	    fail("could not verify deletion");
	    return;
	}
    }
}