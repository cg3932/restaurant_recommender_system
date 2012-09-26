/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBStatsControl test class - tests the operations implemented in the
 * DBStatsControl class.
 *==============================================================================
 * In this unit test, we verify that each DBStats operation returns the correct
 * result from the database. The contents of the database MUST be:
 *
 * USERS:   ID	    DISPLAYNAME
 *	    1	    "iain"
 *	    2	    "armen"
 *	    3	    "david"
 *	    4	    "christian"
 *
 * RETAURANTS:	ID	NAME		AVERAGERATING	NUMBEROFRATINGS
 *		1	"McDonalds"	1.5		2
 *		2	"Burger King"	3		1
 *		3	"Subway"	4		1
 *		4	"Baton Rouge"	4.5		2
 *		5	"Trottier"	1.5		2
 *
 * RATINGS:	ID  USERID  RESTAURANTID    RATING  RATINGDATE
 *		1   1	    3		    4	    2009-12-1
 *		2   1	    5		    1	    2009-12-1
 *		3   2	    4		    5	    2009-11-15
 *		4   3	    1		    2	    2009-10-10
 *		5   3	    5		    2	    2009-10-10
 *		6   4	    1		    1	    2009-11-20
 *		7   4	    2		    3	    2009-11-20
 *		8   4	    4		    4	    2009-11-20
 * 
 * ADS:		ID  HYPERLINK
 *		1   "http://www.mcdonalds.com/"
 *		2   "http://www.burgerking.com/"
 *		3   "http://www.batonrouge.com/"
 *		4   "http://www.mcgill.ca/cafeterias/"
 *
 * You can add these entries however you like, but if you use the CLI, you
 * can reset the auto_increment counter for the various id columns using:
 *	ALTER TABLE <name> AUTO_INCREMENT = 0;
 * Deleting the all contents of a table is done through (careful when using
 * this):
 *	DELETE FROM <name>;
 * And inserting is done using (auto_increment fields must have value DEAFULT):
 *	INSERT INTO <name>(fld1, fld2, ...) VALUES(val1, val2, ...);
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */

package networkInterfaceTest;

import entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import networkInterface.*;
import java.sql.*;

public class DBStatsControlTest {

    // The DBStatsControl we will work with
    DBStatsControl dbsc;

    // Instantiate the object, failing the tests if there was a connection
    // error
    @Before
    public void setUp() {
	try{
	    dbsc = new DBStatsControl();
	} catch (SQLException ex){
	    fail("could not connect to the database");
	}
    }

    // Test getNumberUsers
    @Test
    public void testGetNumberUsers() {
	System.out.println("get number users");
	int expected  = 4;
	assertEquals(expected, dbsc.getNumberUsers());
    }

    // Test getNumberRestaurants
    @Test
    public void testGetNumberRestaurants() {
	System.out.println("get number restaurants");
	int expected = 5;
	assertEquals(expected, dbsc.getNumberRestaurants());
    }

    // Test getNumberRatings
    @Test
    public void testGetNumberRatings() {
	System.out.println("get number ratings");
	int expected = 8;
	assertEquals(expected, dbsc.getNumberRatings());
    }

    // Test getTopRated
    @Test
    public void testGetTopRated() {
	System.out.println("get top rated");
	String expected1 = "Baton Rouge";
	String expected2 = "Subway";
	Restaurant[] restos;
	restos = dbsc.getTopRated(2);
	assertEquals(expected1, restos[0].getName());
	assertEquals(expected2, restos[1].getName());

	expected1 = "Baton Rouge";
	restos = dbsc.getTopRated(1, 4);
	assertEquals(expected1, restos[0].getName());
    }

    // Test getMostActiveUsers
    @Test
    public void testGetMostActiveUsers() {
	System.out.println("get most active users");
	String expected1 = "christian";
	String expected2 = "iain";
	User[] usrs;
	usrs = dbsc.getMostActiveUsers(2);
	assertEquals(expected1, usrs[0].getDisplayName());
	assertEquals(expected2, usrs[1].getDisplayName());
    }

    // Test getMostActiveRestaurants
    @Test
    public void testGetMostActiveRestaurants() {
	System.out.println("get most active restaurants");
	String expected1 = "Baton Rouge";
	String expected2 = "McDonalds";
	Restaurant[] restos;
	restos = dbsc.getMostActiveRestaurants(2);
	assertEquals(expected1, restos[0].getName());
	assertEquals(expected2, restos[1].getName());
    }

    // Test getRecentlyRated
    @Test
    public void testGetRecentlyRated() {
	System.out.println("get recently rated");
	String expected1 = "Subway";
	String expected2 = "Trottier";
	Restaurant[] restos;
	restos = dbsc.getRecentlyRated(2);
	assertEquals(expected1, restos[0].getName());
	assertEquals(expected2, restos[1].getName());

	expected1 = "Baton Rouge";
	restos = dbsc.getRecentlyRated(1, 2);
	assertEquals(expected1, restos[0].getName());
    }

}