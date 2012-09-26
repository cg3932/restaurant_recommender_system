/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package networkInterface;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import entities.*;
/**
 *
 * @author Armen Kitbalian
 */
public class DBUserController {
    String userColumns = "userId, email, displayname, password, Admin, Blocked";
    String input = "";
    String control1 = "";
    String control2 = "";
    User user = new User();
    Connection conn;
    Statement st;
    String username = "";
    String email = "";
    String password = "";
    String error = "all is well";
    int isAdmin = 0;
    int isBlocked = 0;
    DBSettings dbs;



public DBUserController() throws SQLException{
    try {
	    conn = DBSettings.getConn();
            st = conn.createStatement();
	}
	catch (SQLException ex) {
	    throw ex;
	}
}
public String deleteUser(User user){
    error = "all is well";
    try {
       st = conn.createStatement();
//     Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       int idOfUser = user.getUserID();
       //Running the sql command to delete the user using the ID
       String upquery = "DELETE FROM users WHERE userId = " + idOfUser;
       st.executeUpdate(upquery);
       }catch (SQLException ex) {
               Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
       }
       return error;
}

//control1 is the variable that sets the field to be changed in the databse
//control 2 is the variable that is the new field value

public User getUserByName(String input){
    this.input = input;
    this.control1 = "displayName='";
    return getUser();
}

public User getUserByEmail(String input){
    this.input = input;
    this.control1 = "email='";
    return getUser();
}

public String changeUserName(User user, String username){
    //checking for input errors
    if(user == null || username.equals("")){
        error = "Either the user or the displayName is null";
        return error;
    }
    control1 = "displayName";
    control2 = username;
    return changeUserInfo(user);
}

public String changeUserEmail(User user, String email){
     //checking for input errors
    if(user == null || email.equals("")){
        error = "Either the user or the email is null";
        return error;
    }
    control1 = "email";
    control2 = email;
    return changeUserInfo(user);
}

public String changeUserPassword(User user, String password){
     //checking for input errors
    if(user == null || password.equals("")){
        error = "Either the user or the password is null";
        return error;
    }
    control1 = "password";
    control2 = password;
    return changeUserInfo(user);
}

public String changeBlocked(User user, boolean blocked){
     //checking for input errors
    if(user == null){
        error = "The user is null";
        return error;
    }
    control1 = "Blocked";
    if(blocked == true){
        control2 = "1";
    } else {
        control2 = "0";
    }
    return changeUserInfo(user);
}

public String changeAdmin(User user, boolean admin){
    //checking for input errors
    if(user == null){
        error = "The user is null";
        return error;
    }
    control1 = "Admin";
    if(admin == true){
        control2 = "1";
    } else {
        control2 = "0";
    }
    return changeUserInfo(user);
}

private String changeUserInfo(User user){
    error = "all is well";
try {
    st = conn.createStatement();
    // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       int idOfUser = user.getUserID();
       //The query buildup for changing the user info
       String upquery = "update users set " + control1 + "='" + control2 + "' where userID = " + idOfUser;            
       st.executeUpdate(upquery);
    }catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
            error = ex.toString();
    }
    return error;
}

public String setUser(User user){
   if(user == null) {
       System.out.println("user seems to be null... (Error message 2)");
   }
try{
    // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       if (getUserByName(user.getDisplayName()).getUserID() != 0){
           error ="User Already exists";
           return error;
       }
       //The insertion query is built here
       String sqlInsrt = "insert into users (" + userColumns + ") values ";
       st = conn.createStatement();
       username = user.getDisplayName();
       password = user.getPassword();
       email = user.getEmail();
       if(user.getAdmin() == true){
            isAdmin = 1;
       }
       if(user.getBlocked() == true){
            isBlocked = 1;
       }
       //Add some entries to the database.
       String insertion = " (DEFAULT, '" + email + "', '" + username + "', '" + password + "', '" + isAdmin + "', '" + isBlocked + "')";
       //Use executeUpdate when our statement will change the database.
       st.executeUpdate(sqlInsrt + insertion);

       }catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);

       }
    return error;
}

private User getUser(){
    if (input == null){
        System.out.println("(input is null!!!  (Error message 1)");
        return null;
    }
    try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Database error");
        }
    try {
       // Getting a connection going here
       Properties props = new Properties();
       props.setProperty("user", "admin");
       props.setProperty("password", "admin");
       st = conn.createStatement();
       st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
       ResultSet.CONCUR_READ_ONLY);
       // Getting the string with all the information from the database
       ResultSet rs;
       String query = "select "+ userColumns + " from users where " +
                      control1 + input + "'";     
       rs = st.executeQuery(query);
       // Inserting the respective values to the variables      
       if (rs.next()) {
            rs = st.executeQuery(query);
            while (rs.next()) {
                int uid = rs.getInt("userid");
                String email = rs.getString("email");
                String displayname = rs.getString("displayName");
                String password = rs.getString("password");
                boolean isAdmin = rs.getBoolean("Admin");
                boolean isBlocked = rs.getBoolean("Blocked");               
                user.setUserID(uid);
                user.setAdmin(isAdmin);
                user.setBlocked(isBlocked);
                user.setEmail(email);
                user.setPassword(password);
                user.setDisplayName(displayname);
            }
       }
       else{
           System.out.println("Couldn't find: " + input);
            }
       }
       catch (SQLException ex) {
            Logger.getLogger(DBUserController.class.getName()).log(Level.SEVERE, null, ex);
            error = ex.toString();
       }
       return user;
    }
}