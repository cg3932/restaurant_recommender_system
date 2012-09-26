/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * User test class - tests the user entity class in the entities package.
 *==============================================================================
 * This unit test class is quite simple. We just check that all of the storing
 * and retrieval operations work. It was written largely to learn JUnit, and
 * for unit testing documentation.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */

package entitiesTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import entities.*;

public class UserTest {

    // The user object to perform operations on
    private static User user;

    @Before
    public void setUp() {
	user = new User();
    }

    // Test get and set admin
    @Test
    public void testGetNSetAdmin() {
	System.out.println("get and set admin");
	boolean testAdmin = false;
	user.setAdmin(testAdmin);
	assertEquals(testAdmin, user.getAdmin());
    }

    // Test get and set blocked
    @Test
    public void testGetNSetBlocked() {
	System.out.println("get and set blocked");
	boolean testBlocked = false;
	user.setBlocked(testBlocked);
	assertEquals(testBlocked, user.getBlocked());
    }

    // Test get and set display name
    @Test
    public void testGetNSetDisplayName() {
	System.out.println("get and set display name");
	String testName = "Iain";
	user.setDisplayName(testName);
	assertEquals(testName, user.getDisplayName());
    }

    // Test get and set email
    @Test
    public void testGetNSetEmail() {
	System.out.println("get and set email");
	String testEmail = "user@restaurantica.com";
	user.setEmail(testEmail);
	assertEquals(testEmail, user.getEmail());
    }

    // Test get and set password
    @Test
    public void testGetNSetPassword() {
	System.out.println("get and set password");
	String testPW = "topsecret";
	user.setPassword(testPW);
	assertEquals(testPW, user.getPassword());
    }

    // Test get and set user ID
    @Test
    public void testGetNSetUserID() {
	System.out.println("get and set user ID");
	int testID = 42;
	user.setUserID(testID);
	assertEquals(testID, user.getUserID());
    }

    // Test toString
    @Test
    public void testToString() {
	System.out.println("to string");
	user = new User(1, "name", "pw", "someone@something.com", false, true);
	String expected = "1, name, pw, someone@something.com, false, true";
	assertEquals(expected, user.toString());
    }

}