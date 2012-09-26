/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBRecommendControl test class - tests the operation implemented in the
 * DBRecommendControl class.
 *==============================================================================
 * In this unit test, we verify that the getMatrix method of the DBRecommend
 * class returns the correct result. The contents of the database MUST be:
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
 * You can add these entries however you like, but if you use the CLI, you
 * can reset the auto_increment counter for the various id columns using:
 *	ALTER TABLE <name> AUTO_INCREMENT = 0;
 * Deleting the all contents of a table is done through (careful when using
 * this):
 *	DELETE FROM <name>;
 * And inserting is done using (auto_increment fields must have value DEAFULT):
 *	INSERT INTO <name>(fld1, fld2, ...) VALUES(val1, val2, ...);
 * Note: for this particular test, it does not matter what is in users, and
 * restaurants.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */
package networkInterfaceTest;

import networkInterface.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
//import recommend.*;

public class DBRecommendControlTest {

    // The DBRecommendControl we are working with
    private DBRecommendControl dbrc;

    // Initialize DBRC
    @Before
    public void setUp() {
	dbrc = new DBRecommendControl();
    }

    // Test the getMatrix
    @Test
    public void testGetMatrix() {
	System.out.println("get matrix test");
	int[][] expected = {{1,1,2,3,3,4,4,4},
			    {3,5,4,1,5,1,2,4},
			    {4,1,5,2,2,1,3,4}};
	assertEquals(expected, dbrc.getMatrix());
    }

    // Test the actual recommender
    // We (Pierre & Iain) desk tested this earlier, and it was working, so now
    // it is commented out, to avoid any problems.
//    @Test
//    public void testTheRecommender(){
//	System.out.println("recommendation test");
//	String[] blah;
//	Recommender rec = new Recommender();
//	blah = rec.recommend(3, "User", "Restaurant", "Rating");
//	System.out.println(blah[0]);
//	System.out.println(blah[1]);
//    }
}