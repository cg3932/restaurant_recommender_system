/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ratings;
import entities.*;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import networkInterface.*;
import java.sql.*;
import login.Encrypt;

/**
 *
 * @author David
 *
 *  new Rating --> Comment reset
 *
 */
public class RatingControl {

    DBSearchControl dbsc;

    // creates a new Rating object (ratingID = 0) and returns it, or it returns an existing rating
    public static Rating newRating(int uid, int rid)
    {
        Rating existingRating = getRating(uid, rid);
        if(existingRating == null)
        {
            Date currentDate = new Date();
            Rating newRating = new Rating(-1, uid, rid, 0, "", null, false);
            return newRating;
        }
        else
        {
            return existingRating;
        }
    }

    // adds or modifies a rating and returns the result
    public static String addRating(Rating rating)
    {
        int id = rating.getRatingID();
        DBRatingsControl ratingControl;

        try{
            ratingControl = new DBRatingsControl();
        } catch (SQLException ex) {
            return "The rating could not be added. There was a problem connecting to the database.";
        }

        if(id == -1)
        {
            boolean result = ratingControl.addRating(rating);
            ratingControl.updateRating(rating.getRestaurantID());
           
            if(result == true)
            {
                return "The rating was successfully added. Thank you for your help!";
            }
            return "The rating could not be added. Try again or contact the administrator.";
        }
        else
        {       
            // Change restaurant's average rating
            ratingControl.updateRating(rating.getRestaurantID());

            // Change rating
            boolean result1 = ratingControl.changeRatingScore(rating);
            boolean result2 = ratingControl.changeRatingComment(rating);

            if(result1 == true && result2 == true)
            {
                return "The rating was successfully updated. Thank you for your help!";
            }
            return "The rating could not be updated. Try again or contact the administrator.";
        }
    }

    // returns a rating object specified by userID and restaurantID or "null" if it doesn't exist
    private static Rating getRating(int uid, int rid)
    {
        DBRatingsControl ratingControl;
	try {
	    ratingControl = new DBRatingsControl();
	} catch (SQLException ex) {
            System.out.println("Error in getRating:" + ex);
	    return null;
	}
        Rating existingRating = ratingControl.getRating(uid, rid);
        System.out.println(existingRating.getRatingID());
        if (existingRating.getRatingID() == 0) return null;
        else return existingRating;
    }

    // deletes a Rating passed during the call and returns a boolean that indicates if the change was performed properly
    public static String deleteRating(Rating rating)
    {
        DBRatingsControl ratingControl;
	try
        {
	    ratingControl = new DBRatingsControl();
	} 
        catch (SQLException ex)
        {
	    return "The rating could not be deleted. There was a problem connecting to the database.";
	}

        boolean result = ratingControl.deleteRating(rating);
        if(result == true)
        {
            return "The rating was successfully deleted.";
        }
        else
        {
            return "The rating could not be deleted.";
        }
    }

    public static String deleteAllUserRatings(User user)
    {
        DBRatingsControl ratingControl;
	try
        {
	    ratingControl = new DBRatingsControl();
	}
        catch (SQLException ex)
        {
	    return "The rating could not be deleted. There was a problem connecting to the database.";
	}
        Rating[] userRatings = getUserRatings(user.getUserID());
        if(userRatings != null) {
            String deleteResult = "All Ratings were deleted.";
            for(int i = 0; i < userRatings.length; i++) {
                String result = deleteRating(userRatings[0]);
                if(result.equals("The rating could not be deleted.")) {
                    deleteResult = "Not every rating could be deleted.";
                }
                return deleteResult;
            }
        }
        return "No ratings found for this user.";
    }

    // returns all ratings of a user that is specified by the restaurantID (uid)
    public static Rating[] getUserRatings(int uid)
    {
        DBSearchControl dbsc;

        try
        {
           dbsc = new DBSearchControl();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
            return null;
        }


        String[] srcFields = new String[1];
        srcFields[0] = "userid";
        String[] srcVals = new String[1];
        srcVals[0] = "'" + uid + "'";
        String[] srcComps = new String[1];
        srcComps[0] = "=";
        String[] srcLinks = new String[1];
        srcLinks[0] = "ORDER BY ratingdate asc;";

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(1);

        Rating[] resultset;
        resultset = dbsc.searchRating();
        return resultset;
    }


    // returns all ratings of a restaurant that is specified by the restaurantID (rid)
    public static Rating[] getRestaurantRatings(int rid)
    {
        DBSearchControl dbsc;

        try
        {
           dbsc = new DBSearchControl();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
            return null;
        }

        String[] srcFields = new String[1];
        srcFields[0] = "restaurantid";
        String[] srcVals = new String[1];
        srcVals[0] = "'" + rid + "'";
        String[] srcComps = new String[1];
        srcComps[0] = "=";
        String[] srcLinks = new String[1];
        srcLinks[0] = " order by ratingid desc;";

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(1);

        Rating[] resultset;
        resultset = dbsc.searchRating();
        return resultset;
    }

    public static String getUsername(int uid)
    {
        DBSearchControl dbsc;

        try
        {
           dbsc = new DBSearchControl();
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
            return null;
        }
        String[] srcFields = new String[1];
        srcFields[0] = "userid";
        String[] srcVals = new String[1];
        srcVals[0] = "'" + uid + "'";
        String[] srcComps = new String[1];
        srcComps[0] = "=";
        String[] srcLinks = new String[1];
        srcLinks[0] = ";";

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(1);

        User[] resultset;
        resultset = dbsc.searchUser();
        if(resultset != null) {
            String encryptedUsername = resultset[0].getDisplayName();
            Encrypt encrypter = new Encrypt();
            return encrypter.decrypt(encryptedUsername);
             
        }
        return "deleted user";
    }

    public static String unflagRating(Rating rating)
    {
        DBRatingsControl ratingControl;
	try
        {
	    ratingControl = new DBRatingsControl();
	}
        catch (SQLException ex)
        {
	    return "The rating could not be unflagged. There was a problem connecting to the database.";
	}
        boolean success = ratingControl.changeRatingFlagged(rating);
        if(success) {
            return "The rating was successfully unflagged.";
        }
        return "The rating could not be unflagged.";
    }

//    public static Boolean hasRated(int uid, int rid)
//    {
//        Rating rated = getRating( uid, rid);
//        if(rated == null)
//        {
//            return false;
//        }
//        return true;
//    }
}
