/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBSearchControl test class - tests the searching operations implemented in
 * the DBSearchControl class.
 *==============================================================================
 * This test class tests the three searching operations implemented, user,
 * restaurant, and rating. In order for it to function properly, the database
 * must contain the following:
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

public class DBSearchControlTest {

    private DBSearchControl src;
    private String[] srcFlds;
    private String[] srcComps;
    private String[] srcVals;
    private String[] srcLinks;
    private int noFlds;

    public DBSearchControlTest() {}

    @Before
    public void setUp() {
	try{
	    src = new DBSearchControl();
	} catch (SQLException ex) {
	    fail("There was a problem connecting to the database");
	}
	srcFlds = new String[2];
	srcComps = new String[2];
	srcVals = new String[2];
	srcLinks = new String[2];
    }

    // Test get and set number of fields in search
    @Test
    public void testGetNSetNoFieldsInSearch() {
	System.out.println("get and set number of fields in search");
	noFlds = 4;
	src.setNoFieldsInSearch(noFlds);
	assertEquals(noFlds, src.getNoFieldsInSearch());
    }

    // Test get and set search comparisons
    @Test
    public void testGetNSetSearchComparisons() {
	System.out.println("get and set search comparisons");
	srcComps[0] = "=";
	srcComps[1] = "=";
	src.setSearchComparisons(srcComps);
	assertEquals(srcComps, src.getSearchComparisons());
    }

    // Test get and set search fields
    @Test
    public void testGetNSetSearchFields() {
	System.out.println("get and set search fields");
	srcFlds[0] = "displayname";
	srcFlds[1] = "email";
	src.setSearchFields(srcFlds);
	assertEquals(srcFlds,src.getSearchFields());
    }

    // Test get and set search values
    @Test
    public void testGetNSetSearchValues() {
	System.out.println("get and set search values");
	srcVals[0] = "'iain'";
	srcVals[1] = "'iain.macdonald2@mail.mcgill.ca'";
	src.setSearchValues(srcVals);
	assertEquals(srcVals, src.getSearchValues());
    }

    // Test get and set search linkers
    @Test
    public void testGetNSetSearchLinkers() {
	System.out.println("get and set search linkers");
	srcLinks[0] = "AND";
	srcLinks[1] = ";";
	src.setSearchLinkers(srcLinks);
	assertEquals(srcLinks, src.getSearchLinkers());
    }

    // Test search user
    @Test
    public void testSearchUser() {
	System.out.println("search user");
	srcFlds[0] = "displayname";
	srcFlds[1] = "email";
	srcComps[0] = "=";
	srcComps[1] = "=";
	srcVals[0] = "'iain'";
	srcVals[1] = "'iain.macdonald2@mail.mcgill.ca'";
	srcLinks[0] = "and";
	srcLinks[1] = ";";
	noFlds = 2;
	src.setNoFieldsInSearch(noFlds);
	src.setSearchFields(srcFlds);
	src.setSearchComparisons(srcComps);
	src.setSearchValues(srcVals);
	src.setSearchLinkers(srcLinks);
	System.out.println("nothing");
	User[] result = src.searchUser();
	String expectedPW = "secret";
	assertEquals(expectedPW, result[0].getPassword());
    }

    // Test search restaurants
    @Test
    public void testSearchRestaurant() {
	System.out.println("search restaurant");
	srcFlds[0] = "name";
	srcComps[0] = "=";
	srcVals[0] = "'Trottier'";
	srcLinks[0] = ";";
	noFlds = 1;
	src.setNoFieldsInSearch(noFlds);
	src.setSearchFields(srcFlds);
	src.setSearchComparisons(srcComps);
	src.setSearchValues(srcVals);
	src.setSearchLinkers(srcLinks);
	Restaurant[] result = src.searchRestaurant();
	String expectedDescription = "Really expensive";
	assertEquals(expectedDescription, result[0].getDescription());
    }
    // Test search rating
    @Test
    public void testSearchRating() {
	System.out.println("search rating");
	srcFlds[0] = "userid";
	srcComps[0] = "=";
	srcVals[0] = "1";
	srcLinks[0] = "AND";
	srcFlds[1] = "restaurantid";
	srcComps[1] = "=";
	srcVals[1] = "5";
	srcLinks[1] = ";";
	noFlds = 2;
	src.setNoFieldsInSearch(noFlds);
	src.setSearchFields(srcFlds);
	src.setSearchComparisons(srcComps);
	src.setSearchValues(srcVals);
	src.setSearchLinkers(srcLinks);
	String expectedComment = "Iain-Trottier";
	Rating[] result = src.searchRating();
	assertEquals(expectedComment, result[0].getComment());
    }

        // Test search ads
    @Test
    public void testSearchAd(){
	System.out.println("search ads");
	srcFlds[0] = "hyperlink";
	srcComps[0] = "LIKE";
	srcVals[0] = "'%www%'";
	srcLinks[0] = ";";
	noFlds = 1;
	src.setNoFieldsInSearch(noFlds);
	src.setSearchFields(srcFlds);
	src.setSearchComparisons(srcComps);
	src.setSearchValues(srcVals);
	src.setSearchLinkers(srcLinks);
	ad[] result = src.searchAd();
	int[] expected = {1, 2, 3, 4};
	int i;
	for (i = 0; i < 4; i++){
	    assertEquals(expected[i], result[i].getAdID());
	}
    }

     // Test clear search
    @Test
    public void testClearSearch() {
	System.out.println("clear search");

	// set the search fields
	srcFlds[0] = "userid";
	srcComps[0] = "=";
	srcVals[0] = "'1'";
	srcLinks[0] = "AND";
	srcFlds[1] = "restaurantid";
	srcComps[1] = "=";
	srcVals[1] = "5";
	srcLinks[1] = ";";
	noFlds = 2;
	src.setNoFieldsInSearch(noFlds);
	src.setSearchFields(srcFlds);
	src.setSearchComparisons(srcComps);
	src.setSearchValues(srcVals);
	src.setSearchLinkers(srcLinks);

	// clear the search fields
	String[] expectedString = new String[0];
	int expectedNoFlds = 0;
	src.clearSearch();
	assertEquals(expectedString, src.getSearchFields());
	assertEquals(expectedString, src.getSearchComparisons());
	assertEquals(expectedString, src.getSearchValues());
	assertEquals(expectedString, src.getSearchLinkers());
	assertEquals(expectedNoFlds, src.getNoFieldsInSearch());
    }
}