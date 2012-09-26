/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * User entity class - stores user objects for use in the system.
 *==============================================================================
 * A simple class, just constructors, getters, and setters. The most important
 * thing to know about this entity class is that it does NOT verify the contents
 * of the user object. It is up to the caller to ensure that the information
 * stored is all valid!
 *==============================================================================
 * Author: Iain Macdonald - Nov. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
 */

package entities;

public class User extends entity {

    // Fields from the database
    private int userID;
    private String displayName;
    private String password;
    private String email;
    private boolean blocked;
    private boolean admin;

    /**
     * Parameterless constructor
     *
     * Does not initialize any of the fields This constructor should be used
     * higher up, for creating new database users simply.
     */
    public User() {}

    /**
     * Parameterized constructor
     *
     * Initializes all fields to the input values. When using, please pay
     * attention to the order of arguments. If you do know a particular
     * argument, and would still like to use this constructor, enter -1, or "",
     * or null, etc. for the unknown fields. This constructor should be used for
     * database retrieval operations, to easily initialize all fields for the
     * particular user.
     *
     * @param userID
     * @param displayName
     * @param password
     * @param email
     * @param blocked
     * @param admin
     */
    public User(int userID, String displayName, String password, String email, 
	    boolean blocked, boolean admin) {
	this.userID = userID;
	this.displayName = displayName;
	this.password = password;
	this.email = email;
	this.blocked = blocked;
	this.admin = admin;
    }

    

    // Getters & Setters, automatically generated by NetBeans
    public boolean getAdmin() {
	return admin;
    }

    public void setAdmin(boolean admin) {
	this.admin = admin;
    }

    public boolean getBlocked() {
	return blocked;
    }

    public void setBlocked(boolean blocked) {
	this.blocked = blocked;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public int getUserID() {
	return userID;
    }

    public void setUserID(int userID) {
	this.userID = userID;
    }

    /**
     * toString
     *
     * Returns the current state of the object in a string.
     */
    public String toString(){

	String returnString = "";
	String nxt = ", ";

	// concatenate all fields to the return string
	returnString += userID + nxt;
	returnString += displayName + nxt;
	returnString += password + nxt;
	returnString += email + nxt;
	returnString += blocked + nxt;
	returnString += admin;

	return returnString;

    }

}
