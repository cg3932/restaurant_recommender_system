/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Rating test class - tests the rating entity class in the entities package.
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

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.GregorianCalendar;
import entities.*;

public class RatingTest {

    // The rating object we will perform our operations on
    private static Rating rating;

    @Before
    public void setUp() {
	rating = new Rating();
    }

    // Test getting and setting a comment
    @Test
    public void testGetNSetComment() {
	System.out.println("get and set comment");
	String testComment = "this is a comment";
	rating.setComment(testComment);
	assertEquals(testComment, rating.getComment());
    }

    // Test getting and setting the flagged status
    @Test
    public void testGetNSetFlagged() {
	System.out.println("get and set flagged");
	boolean testFlagged = true;
	rating.setFlagged(testFlagged);
	assertEquals(testFlagged, rating.getFlagged());
    }

    // Test getting and setting a rating
    @Test
    public void testGetNSetRating() {
	System.out.println("get and set rating");
	int testRating = 3;
	rating.setRating(testRating);
	assertEquals(testRating, rating.getRating());
    }

    // Test getting and setting a rating date
    @Test
    public void testGetNSetRatingDate() {
	System.out.println("get and set rating date");
	Date testDate = new Date(2009, 12, 01);
	rating.setRatingDate(testDate);
	assertEquals(testDate, rating.getRatingDate());
    }

    // Test getting and setting a ratingID
    @Test
    public void testGetNSetRatingID() {
	System.out.println("get and set rating ID");
	int testID = 42;
	rating.setRatingID(testID);
	assertEquals(testID, rating.getRatingID());
    }

    // Test getting and setting a restaurantID
    @Test
    public void testGetNSetRestaurantID() {
	System.out.println("get and set restaurant ID");
	int testID = 32;
	rating.setRestaurantID(testID);
	assertEquals(testID, rating.getRestaurantID());
    }

    // Test getting and setting a userID
    @Test
    public void testGetNSetUserID() {
	System.out.println("get and set userID");
	int testID = 22;
	rating.setUserID(testID);
	assertEquals(testID, rating.getUserID());
    }

    // Test toString
    // Note: Java's date.toString() method outputs the data as:
    //	    DOW MON DD HH:MM:SS TMZ YYYY
    // where
    //	    DOW = day of week
    //	    MON = month
    //	    DD = day
    //	    HH = hour
    //	    MM = minute
    //	    SS = second
    //	    TMZ = timezone
    //	    YYYY = year
    // Thus, for the date: 2009/11/01, we expect:
    //	    "Mon Dec 01 00:00:00 EST 2009"
    //		(month is zero-based, so Jan = 0, ..., Dec = 11)
    @Test
    public void testToString() {
	System.out.println("toString");

	// Create a date for the rating
	Date ratingDate = new Date();
	GregorianCalendar cal = new GregorianCalendar(2009, 11, 1);
	ratingDate.setTime(cal.getTimeInMillis());

	// Continue with the test.
	rating = new Rating(1, 2, 3, 4, "comment", ratingDate, false);
	String testToString = "1, 2, 3, 4, comment, Tue Dec 01 00:00:00 EST " +
		"2009, false";
	System.out.println(rating.toString());
	assertEquals(testToString, rating.toString());
    }

}