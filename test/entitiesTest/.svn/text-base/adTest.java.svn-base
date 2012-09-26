/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Ad test class - tests the ad entity class in the entities package.
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

public class adTest {

    private ad myad;

    @Before
    public void setUp() {
	myad = new ad();
    }

    // Test get and set ID
    @Test
    public void testGetNSetAdID() {
	System.out.println("get and set ad id");
	int id = 42;
	myad.setAdID(id);
	assertEquals(id, myad.getAdID());
    }

    // Test get and set hyperlink
    @Test
    public void testGetNSetHyperlink() {
	System.out.println("get and set hyperlink");
	String hlink = "http://www.website.com/";
	myad.setHyperlink(hlink);
	assertEquals(hlink, myad.getHyperlink());
    }

     // Test get and set image path
    @Test
    public void testGetNSetImagePath() {
	System.out.println("get and set image path");
	String imgpath = "C:\\\\Users\\\\restaurantica\\\\img.jpeg";
	myad.setImagePath(imgpath);
	assertEquals(imgpath, myad.getImagePath());
    }

    // Test get and set times clicked
    @Test
    public void testGetNSetTimesClicked() {
	System.out.println("get and set times clicked");
	int clicks = 128;
	myad.setTimesClicked(clicks);
	assertEquals(clicks, myad.getTimesClicked());
    }

    // Test toString
    @Test
    public void testToString() {
	System.out.println("to string");
	myad = new ad(42, "C:\\\\Users\\\\restaurantica\\\\img.jpeg",
		"http://www.website.com/", 128);
	String expected = "42, C:\\\\Users\\\\restaurantica\\\\img.jpeg, " +
		"http://www.website.com/, 128";
	assertEquals(expected, myad.toString());
    }
}