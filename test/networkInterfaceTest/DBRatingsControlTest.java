/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networkInterfaceTest;

import networkInterface.*;
import entities.Rating;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.sql.*;

/**
 *
 * @author Armen Kitbalian
 */
public class DBRatingsControlTest {
    
    Rating rating1;
    DBRatingsControl dbrc;

    // SetUp Class - instantiate and initialize all of our objects
    @Before
    public void SetUp(){

	java.util.Date date = new Date();
	java.sql.Date addDate = new java.sql.Date(date.getTime());
	rating1 = new Rating(0, 98, 99, 3, "I want a burger", addDate, false);
	
	try{
	    dbrc = new DBRatingsControl();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	}
    }
    
    // Test addRating
    @Test
    public void testAddRating() {
        System.out.println("addRating");
        Boolean expResult = true;

	// Debugging
//        System.out.println("ratingid: " + rating1.getRatingID()+ " resto Id: " +
//		rating1.getRestaurantID() + " userID: " + rating1.getUserID() +
//		" comment: " + rating1.getComment() + " flagged: " +
//		rating1.getFlagged() + " rating score: " + rating1.getRating() +
//		" date: " + rating1.getRatingDate());
        assertEquals(expResult, dbrc.addRating(rating1));
    }

    // Test change rating score
    @Test
    public void testChangeRatingScore() {
        System.out.println("changeRatingScore");
        boolean expResult = true;
        rating1 = dbrc.getRating(98, 99);
        rating1.setRating(1);
        boolean result = dbrc.changeRatingScore(rating1);

	// Verify that the change worked
        assertEquals(expResult, result);
        assertEquals(rating1.getRating(), dbrc.getRating(98, 99).getRating());
       
    }

    // Test changing the comment
    @Test
    public void testChangeRatingComment() {
        System.out.println("changeRatingComment");
        rating1 = dbrc.getRating(98, 99);
        boolean expResult = true;
        rating1.setComment("WHERE IS MY BURGER");
        boolean result = dbrc.changeRatingComment(rating1);

	// Verify that the change worked
        assertEquals(expResult, result);
        assertEquals(rating1.getComment(), dbrc.getRating(98, 99).getComment());
    }

    

    // Test get
    @Test
    public void testGetRating() {
        System.out.println("getRating");
        Rating result = dbrc.getRating(rating1.getUserID(),
		rating1.getRestaurantID());
        assertEquals(rating1.getUserID(), result.getUserID());
   }

    // Test change flagged
    @Test
    public void testChangeRatingFlagged() {
        System.out.println("changeRatingFlagged");
        rating1 = dbrc.getRating(98, 99);
        boolean expResult = true;
        rating1.setFlagged(true);

	// change the flag
        boolean result = dbrc.changeRatingFlagged(rating1);

	// Verify that the change worked
        assertEquals(expResult, result);
        assertEquals(rating1.getFlagged(), dbrc.getRating(98, 99).getFlagged());
    }

    // Test deletion
    // This should clean up after the test
    @Test
    public void testDeleteRating() {
        System.out.println("deleteRating");
        rating1 = dbrc.getRating(98, 99);
        Boolean expResult = true;
        Boolean result = dbrc.deleteRating(rating1);
        assertEquals(expResult, result);
    }
}