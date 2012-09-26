/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * Restaurant test class - tests the restaurant entity class in the entities
 * package.
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

public class RestaurantTest {

    // restaurant object we will operate on
    // I'm pretty tired of typing the work restaurant, so I'll abbreviate
    private static Restaurant resto;
    
    @Before
    public void setUp() {
	resto = new Restaurant();
    }

    // Test get and set alcohol
    @Test
    public void testGetNSetAlcohol() {
	System.out.println("get and set alcohol");
	boolean testAlcohol = false;
	resto.setAlcohol(testAlcohol);
	assertEquals(testAlcohol, resto.getAlcohol());
    }

    // Test get and set description
    @Test
    public void testGetNSetDescription() {
	System.out.println("get and set ambiance");
	String testDescription = "this is the ambiance";
	resto.setDescription(testDescription);
	assertEquals(testDescription, resto.getDescription());
    }

    // Test get and set average rating
    @Test
    public void testGetNSetAverageRating() {
	System.out.println("get and set average rating");
	float testAvgRating = (float) 4.3;
	resto.setAverageRating(testAvgRating);

	// We specify a tolerance, because of the slight error in storing
	// floating point numbers in a computer
	assertEquals(testAvgRating, resto.getAverageRating(), 0.01);
    }

    /// Test get and set city
    @Test
    public void testGeNSettCity() {
	System.out.println("get and set city");
	String testCity = "Chicago";
	resto.setCity(testCity);
	assertEquals(testCity, resto.getCity());
    }

    // Test get and set cost
    @Test
    public void testGetNSetCost() {
	System.out.println("get and set cost");
	float testCost = (float) 4.3;
	resto.setCost(testCost);
	assertEquals(testCost, resto.getCost(), 0.01);
    }

    // Test get and set country
    @Test
    public void testGetNSetCountry() {
	System.out.println("get and set country");
	String testCountry = "USA";
	resto.setCountry(testCountry);
	assertEquals(testCountry, resto.getCountry());
    }

    // Test get and set cuisine
    @Test
    public void testGetNSetCuisine() {
	System.out.println("get and set cuisine");
	String testCuisine = "Coffee";
	resto.setCuisine(testCuisine);
	assertEquals(testCuisine, resto.getCuisine());
    }

    // Test get and set image path
    // Note: in Java, "\\\\" means "\" in real life (it's a special character)
    @Test
    public void testGetNSetImagePath() {
	System.out.println("get and set image path");
	String testImgPath = "C:\\\\Users\\\\SomeUser\\\\Pictures\\\\Restaura" +
		"nticaPhotos\\\\CoffeeShop.jpg";
	resto.setImagePath(testImgPath);
	assertEquals(testImgPath, resto.getImagePath());
    }

    // Test get and set name
    @Test
    public void testGetNSetName() {
	System.out.println("get and set name");
	String testName = "Some Diner";
	resto.setName(testName);
	assertEquals(testName, resto.getName());
    }

    // Test get and set number of ratings
    @Test
    public void testGetNSetNumberOfRatings() {
	System.out.println("get and set number of ratings");
	int testNoRatings = 14;
	resto.setNumberOfRatings(testNoRatings);
	assertEquals(testNoRatings, resto.getNumberOfRatings());
    }

    // Test get and set open hours
    @Test
    public void testGetNSetOpenHours() {
	System.out.println("get and set open hours");
	String testOpenHours = "10 AM";
	resto.setOpenHours(testOpenHours);
	assertEquals(testOpenHours, resto.getOpenHours());
    }

    // Test get and set close hours
    @Test
    public void testGetNSetCloseHours(){
	System.out.println("get and set close hours");
	String testCloseHours = "9 PM";
	resto.setCloseHours(testCloseHours);
	assertEquals(testCloseHours, resto.getCloseHours());
    }

    // Test get and set payment
    @Test
    public void testGetNSetPayment() {
	System.out.println("get and set payment");
	String testPymnt = "cash only";
	resto.setPayment(testPymnt);
	assertEquals(testPymnt, resto.getPayment());
    }

    // Test get and set phone
    @Test
    public void testGetNSetPhone() {
	System.out.println("get and set phone");
	String testPhone = "1-800-234-5678";
	resto.setPhone(testPhone);
	assertEquals(testPhone, resto.getPhone());
    }

    // Test get and set postal code
    @Test
    public void testGetNSetPostalCode() {
	System.out.println("get and set postal code");
	String testPostal = "H1A 1A1";
	resto.setPostalCode(testPostal);
	assertEquals(testPostal, resto.getPostalCode());
    }

    // Test get and set province
    @Test
    public void testGetNSetProvince() {
	System.out.println("get and set province");
	String testProvince = "Illinois";
	resto.setProvince(testProvince);
	assertEquals(testProvince,resto.getProvince());
    }

    // Test get and set reservations accepted
    @Test
    public void testGetNSetReservationsAccepted() {
	System.out.println("get and set reservations accepted");
	boolean testRA = true;
	resto.setReservationsAccepted(testRA);
	assertEquals(testRA, resto.getReservationsAccepted());
    }

    // Test get and set reservations recommended
    @Test
    public void testGetNSetReservationsRecommended() {
	System.out.println("get and set reservations recommended");
	boolean testRR = false;
	resto.setReservationsRecommended(testRR);
	assertEquals(testRR, resto.getReservationsRecommended());
    }

    // Test get and set restaurant ID
    @Test
    public void testGetNSetRestaurantID() {
	System.out.println("get and set restaurant ID");
	int testRID = 100;
	resto.setRestaurantID(testRID);
	assertEquals(testRID, resto.getRestaurantID());
    }

    // Test get and set street name
    @Test
    public void testGetNSetStreetName() {
	System.out.println("get and set street name");
	String testStrtNm = "Elm st.";
	resto.setStreetName(testStrtNm);
	assertEquals(testStrtNm, resto.getStreetName());
    }

    // Test get and set street number
    @Test
    public void testGetNSetStreetNumber() {
	System.out.println("get and set street number");
	int testStrtNo = 1;
	resto.setStreetNumber(testStrtNo);
	assertEquals(testStrtNo, resto.getStreetNumber());
    }

    // Test get and set web address
    @Test
    public void testGetNSetWebaddress() {
	System.out.println("get and set web address");
	String testWWW = "http://www.restaurantica.com/";
	resto.setWebaddress(testWWW);
	assertEquals(testWWW, resto.getWebaddress());
    }

    // Test toString
    @Test
    public void testToString() {
	System.out.println("toString");
	resto = new Restaurant(1, (float) 2.0, 3, "name", 4, "name st.",
		"A1A 1A1", "somecity", "theprovince", "thiscountry",
		"1-800-234-5678", "Coffee", "http://www.myresto.com", "10 AM",
		"9 PM", (float) 2.0, false, "cash only", "nice ambiance", true,
		false, "C:\\\\Users\\\\someUser\\\\apicture.jpg");
	String expected = "1, 2.0, 3, name, 4, name st., A1A 1A1, somecity, " + 
		"theprovince, thiscountry, 1-800-234-5678, Coffee, " +
		"http://www.myresto.com, 10 AM, 9 PM, 2.0, false, cash only, " +
		"nice ambiance, true, false, " +
		"C:\\\\Users\\\\someUser\\\\apicture.jpg";
	assertEquals(expected, resto.toString());
    }

}