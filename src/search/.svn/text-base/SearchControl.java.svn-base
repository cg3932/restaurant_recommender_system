/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package search;
import entities.Restaurant;
import entities.User;
import networkInterface.DBSearchControl;
import java.sql.*;
import java.util.Hashtable;
import javax.swing.JOptionPane;
import login.Encrypt;

/**
 *
 * @author David
 */
public class SearchControl {

    // called by the search function on the home tab
    // searches for restaurants that contain the serchStringn it their names
    public static Restaurant[] quickSearchRestaurant(String searchString)
    { 
        DBSearchControl dbsc;
	try {
	    dbsc = new DBSearchControl();
	}
	catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
	    return null;
	}
        String[] srcFields = new String[2];
        srcFields[0] = "name";
        srcFields[1] = "description";
        String[] srcVals = new String[2];
        srcVals[0] = "'%" + searchString + "%'";
        srcVals[1] = "'%" + searchString + "%'";
        String[] srcComps = new String[2];
        srcComps[0] = "LIKE";
        srcComps[1] = "LIKE";
        String[] srcLinks = new String[2];
        srcLinks[0] = "OR";
        srcLinks[1] = ";";

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(2);

        Restaurant[] resultset;
        resultset = dbsc.searchRestaurant();

        return resultset;
    }

    // called by the SearchTab
    public static Restaurant[] searchRestaurant(Hashtable newSearch)
    {
        DBSearchControl dbsc;
	try {
	    dbsc = new DBSearchControl();
	}
	catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
	    return null;
	}

        int size = newSearch.size();
//        System.out.println(newSearch.toString());
//        System.out.println("Size: "+size);
        String[] srcFields = new String[size];
        String[] srcVals = new String[size];
        String[] srcComps = new String[size];
        String[] srcLinks = new String[size];

        for( int i = 0; i < size-1; i++ )
        {
            srcLinks[i] = "AND";
        }
        if(size == 0){
            return null;
        }
        srcLinks[size-1] = ";";


        int i = 0;
        if(newSearch.containsKey("name"))
        {
            srcFields[i] = "name";
            srcVals[i] = "'%" + newSearch.get("name") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("phone"))
        {
            srcFields[i] = "phone";
            srcVals[i] = "'%" + newSearch.get("phone") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("cuisine"))
        {
            srcFields[i] = "cuisine";
            srcVals[i] = "'" + newSearch.get("cuisine") + "'";
            srcComps[i++] = "=";
        }
        if(newSearch.containsKey("averagerating"))
        {
            srcFields[i] = "averagerating";
            srcVals[i] = "'" + newSearch.get("averagerating") + "'";
            srcComps[i++] = ">";
        }
        if(newSearch.containsKey("openhours"))
        {
            srcFields[i] = "openhours";
            srcVals[i] = "'" + newSearch.get("openhours") + "'";
            srcComps[i++] = ">";
        }
        if(newSearch.containsKey("closehours"))
        {
            srcFields[i] = "closehours";
            srcVals[i] = "'" + newSearch.get("closehours") + "'";
            srcComps[i++] = "<";
        }
        if(newSearch.containsKey("cost"))
        {
            srcFields[i] = "cost";
            srcVals[i] = "'" + newSearch.get("cost") + "'";
            srcComps[i++] = "<";
        }
        if(newSearch.containsKey("streetnumber"))
        {
            srcFields[i] = "streetnumber";
            srcVals[i] = "'%" + newSearch.get("streetnumber") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("streetname"))
        {
            srcFields[i] = "streetname";
            srcVals[i] = "'%" + newSearch.get("streetname") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("province"))
        {
            srcFields[i] = "province";
            srcVals[i] = "'%" + newSearch.get("province") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("country"))
        {
            srcFields[i] = "country";
            srcVals[i] = "'%" + newSearch.get("country") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("postalcode"))
        {
            srcFields[i] = "postalcode";
            srcVals[i] = "'%" + newSearch.get("postalcode") + "%'";
            srcComps[i++] = "LIKE";
        }
        if(newSearch.containsKey("payment"))
        {
            srcFields[i] = "payment";
            srcVals[i] = "'" + newSearch.get("payment") + "'";
            srcComps[i++] = "=";
        }

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(size);

        Restaurant[] resultset;
        resultset = dbsc.searchRestaurant();
        return resultset;
    }

    // called by user search on the AdminTab
    // searches for users that contain the searchString either in the displayname or in the email address
    public static User[] searchUser(String searchString)
    {
        // To find one specific user!
        DBSearchControl dbsc;
	try {
	    dbsc = new DBSearchControl();
	}
	catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
	    return null;
	}
        Encrypt encrypter = new Encrypt();
        String Esearch = encrypter.encrypt(searchString);

        String[] srcFields = new String[1];
        srcFields[0] = "email";
        String[] srcVals = new String[1];
        srcVals[0] = "'" + Esearch + "'";
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
        return resultset;

//        DBSearchControl dbsc;
//	try {
//	    dbsc = new DBSearchControl();
//	}
//	catch (SQLException ex) {
//	    JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
//	    return null;
//	}
//        String[] srcFields = new String[2];
//        srcFields[0] = "displayname";
//        srcFields[1] = "email";
//        String[] srcVals = new String[2];
//        srcVals[0] = "'%" + searchString + "%'";
//        srcVals[1] = "'%" + searchString + "%'";
//        String[] srcComps = new String[2];
//        srcComps[0] = "LIKE";
//        srcComps[1] = "LIKE";
//        String[] srcLinks = new String[2];
//        srcLinks[0] = "OR";
//        srcLinks[1] = ";";
//
//        dbsc.setSearchFields(srcFields);
//        dbsc.setSearchValues(srcVals);
//        dbsc.setSearchComparisons(srcComps);
//        dbsc.setSearchLinkers(srcLinks);
//        dbsc.setNoFieldsInSearch(2);
//
//        User[] resultset;
//        resultset = dbsc.searchUser();
//        return resultset;
    }
    public static Restaurant getRestaurant(int rid)
    {
        DBSearchControl dbsc;
	try {
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
        srcLinks[0] = ";";

        dbsc.setSearchFields(srcFields);
        dbsc.setSearchValues(srcVals);
        dbsc.setSearchComparisons(srcComps);
        dbsc.setSearchLinkers(srcLinks);
        dbsc.setNoFieldsInSearch(1);

        Restaurant[] resultset;
        resultset = dbsc.searchRestaurant();

        return resultset[0];
    }

}
