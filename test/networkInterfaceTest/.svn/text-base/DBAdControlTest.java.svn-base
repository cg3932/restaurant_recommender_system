/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBAdControl test class - tests the operations implemented in the DBAd class.
 *==============================================================================
 * This test class tests all of the operations implemented in the DBAdControl
 * class. It also tests the add() and delete() for new, old (proper), and old
 * (improper) restaurants. This class should automatically clean up after itself
 * (unless delete is not working!). However, it will cause the auto_incremente
 * in the ads table to go up for future restaurants. If any of the tests fail,
 * ensure that the important records in the database have not been changed.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */

package networkInterfaceTest;

import entities.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import networkInterface.*;
import java.sql.*;

public class DBAdControlTest {

    // The DBAdControl object we will work with
    DBAdControl dbac;

    // Instantiate the DBAdControl object,
    @Before
    public void setUp() {
	dbac = new DBAdControl();
    }

    // Test get and set Ad
    @Test
    public void testGetAd() {
	System.out.println("get ad");
	String expectedHLink = "http://www.blah.com";
	ad myad = new ad(42, "C:\\\\pictures\\\\somepicture.jpg",
		"http://www.blah.com", 5);
	dbac.setAd(myad);
	assertEquals(expectedHLink, dbac.getAd().getHyperlink());
    }

    // Test adding a new ad properly (i.e. adid = -1)
    @Test
    public void testAddNewProper(){
	System.out.println("add new ad proper");
	ad myad = new ad(-1, "C:\\\\pictures\\\\somepicture.jpg",
		"http://www.blah.com", 5);
	dbac.setAd(myad);
	dbac.add();

	// Now we need to check that the restaurant was added to the database by
	// searching for hyperlink = "http://www.blah.com" in the database.
	Connection conn;
	try{
	    conn = DBSettings.getConn();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}
	ResultSet rs;
	Statement st;
	String testResults = "select hyperlink from ads where imagepath = " +
		"'C:\\\\pictures\\\\somepicture.jpg';";
	String expectedHLink = "http://www.blah.com";
	
	try{
	    st = conn.createStatement();
	    rs = st.executeQuery(testResults);
	    rs.first();
	    assertEquals(expectedHLink, rs.getString("hyperlink"));
	} catch (SQLException ex) {
	    fail ("There was a problem connecting to the database");
	    return;
	}
    }

    // Test adding a new restaurant improperly
    @Test
    public void testAdd() {
	System.out.println("add new ad improper");
	ad myad = new ad(9000, "C:\\\\Pics\\\\mypicture.jpg",
		"http://www.mypicture.com", 0);
	dbac.setAd(myad);
	dbac.add();

	// Now we need to verify that the ad was not added to the database
	Connection conn;
	Statement st;
	ResultSet rs;
	String testResults = "select count(adid) from ads where imagepath = " +
		"'C:\\\\Pics\\\\mypicture.jpg';";
	
	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(testResults);
	    rs.first();
	    assertEquals(0, rs.getInt("count(adid)"));
	} catch (SQLException ex){
	    fail("There was a problem connecting to the database");
	    return;
	}
    }

    // Test modify ad
    @Test
    public void testModify(){
	System.out.println("modify advertisement");

	// First we need to find out what the adid we just added is
	Connection conn;
	Statement st;
	ResultSet rs;
	String getAdID = "select adid from ads where imagepath = " +
		"'C:\\\\pictures\\\\somepicture.jpg';";
	int adid;

	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(getAdID);
	    rs.first();
	    adid = rs.getInt("adID");
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}

	// modify the advertisement record
	ad myad = new ad(adid, "C:\\\\pictures\\\\somepicture.jpg",
		"http://www.iain.com", 0);
	dbac.setAd(myad);
	dbac.add();

	// check that the modify worked properly
	String expectedHLink = "http://www.iain.com";
	String verifyChange = "select hyperlink from ads where adid = " +
		adid + ";";
	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(verifyChange);
	    rs.first();
	    assertEquals(expectedHLink, rs.getString("hyperlink"));
	} catch (SQLException ex) {
	    fail("there was a problem connecting to the database");
	    return;
	}
    }

    // Test deletion
    // Here we will clean up after ourselves
    @Test
    public void testDelete() {
	System.out.println("delete");

	// Get the adid that we have been playing with
	String dltIDQry = "select adid from ads where hyperlink = " +
		"'http://www.iain.com';";
	String verifyDlt = "select count(adid) from ads where hyperlink = " +
		"'http://www.iain.com';";
	Connection conn;
	ResultSet rs;
	Statement st;
	int adid;

	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(dltIDQry);
	    rs.first();
	    adid = rs.getInt("adid");
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}

	ad myad = new ad(adid, "C:\\\\pictures\\\\somepicture.jpg",
		"http://www.blah.com", 5);
	dbac.setAd(myad);
	dbac.delete();

	// verify deletion
	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(verifyDlt);
	    rs.first();
	    assertEquals(0, rs.getInt("count(adid)"));
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	    return;
	}
    }
}