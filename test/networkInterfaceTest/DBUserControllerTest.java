/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBUserController test class - tests the operations implemented in the
 * DBUserController class.
 *==============================================================================
 * This test class tests all of the operations implemented in the
 * DBUserContoller class. This class should automatically clean up after itself
 * (unless delete is not working!). However, it will cause the auto_increment in
 * the restaurant table to go up for future restaurants. If any of the tests
 * fail, ensure that the important records in the database have not been
 * changed.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */

package networkInterfaceTest;

import entities.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import networkInterface.*;
import java.sql.*;

public class DBUserControllerTest {

    Connection conn;
    DBUserController dbuc;

    // Set up for a test - instantiate a new DBUC, and connection
    @Before
    public void setUp() {
	try {
	    conn = DBSettings.getConn();
            dbuc = new DBUserController();
	} catch (SQLException ex) {
	    fail("could not connect to the database");
	}
    }

    // Test getting a user by name
    @Test
    public void testGetUserByName() {
	System.out.println("get user by name");
	int expected = 1;
	User result = dbuc.getUserByName("iain");
	assertEquals(expected, result.getUserID());
    }

    // Test getting a user by email address
    @Test
    public void testGetUserByEmail() {
	System.out.println("get user by email");
	int expected = 1;
	User result = dbuc.getUserByEmail("iain.macdonald2@mail.mcgill.ca");
	assertEquals(expected, result.getUserID());
    }

    // Test getting and setting a user
    @Test
    public void testSetUser() {
	System.out.println("set user");
	User user = new User();
	user.setDisplayName("Bob");
	user.setPassword("mypassword");
	String expected = "mypassword";
	String result = dbuc.setUser(user);
	User qryUsr = dbuc.getUserByName("Bob");
	assertEquals(expected, qryUsr.getPassword());
    }

    // Test changing the username
    @Test
    public void testChangeUserName() {
	System.out.println("change user name");
	User user = dbuc.getUserByName("Bob");
	String expected = "Blah";
	dbuc.changeUserName(user, expected);
	user = dbuc.getUserByName("blah");
	assertEquals(expected, user.getDisplayName());
    }

    // Test changint the user email
    @Test
    public void testChangeUserEmail() {
	System.out.println("change user email");
        User user = new User();      
        user = dbuc.getUserByName("Blah");
	String expected = "abc@def.com";
        dbuc.changeUserEmail(user, "abc@def.com");
        user = dbuc.getUserByName("Blah");
	assertEquals(expected, user.getEmail());
    }

    // Test change the user password
    @Test
    public void testChangeUserPassword() {
	System.out.println("change user password");
	User user = dbuc.getUserByName("Blah");
	String expected = "secret";
	String result = dbuc.changeUserPassword(user, expected);
	user = dbuc.getUserByName("Blah");
	assertEquals(expected, user.getPassword());
    }

    // Test changing the blocked status
    @Test
    public void testChangeBlocked() {
	System.out.println("change blocked");
	User user = dbuc.getUserByName("Blah");
        String expected = "all is well";
	user = dbuc.getUserByName("Blah");
	String result = dbuc.changeBlocked(user, false);
	assertEquals(expected, result);
    }

    // Test changing the admin status
    @Test
    public void testChangeAdmin() {
	System.out.println("change admin");
	User user = dbuc.getUserByName("Blah");
	Boolean expected = true;
	dbuc.changeAdmin(user, expected);
	user = dbuc.getUserByName("Blah");
	assertEquals(expected, user.getAdmin());
    }

    // Test deletion
    @Test
    public void testDeleteUser() {
	System.out.println("delete user");
	User user = dbuc.getUserByName("Blah");
	dbuc.deleteUser(user);

	Statement st;
	ResultSet rs;
	String dltVerify = "select count(userid) from users where displayname" +
		" = 'Blah';";

	try{
	    conn = DBSettings.getConn();
	    st = conn.createStatement();
	    rs = st.executeQuery(dltVerify);
	    rs.first();
	    assertEquals(0,rs.getInt("count(userid)"));
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	}
    }
}