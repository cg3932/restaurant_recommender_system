/**
 * Restaurantica restaurant recommendation system
 * ECSE 321 - Introduction to Software Engineering
 * Term-Long Project, Misson 4, Due: 03, Dec, 2009
 *==============================================================================
 * DBSettings class - provides the access information for the database.
 *==============================================================================
 * This class is a singleton, which speeds up the system by not requiring a new
 * connection be opened everytime a database operation takes place. As was
 * previously the connection.
 *==============================================================================
 * Author: Iain Macdonald - Nov./Dec. 2009
 * For questions, or comments e-mail:
 * xiainx@gmail.com
*/

package networkInterface;

import java.util.Properties;
import java.sql.*;

public class DBSettings {

    // server
    //	    The IP address of the server
    // dbName
    //	    The name of the database to use on the server
    // url
    //	    The URL of the database
    private static String server;
    private static String dbName;
    private static String url =  server + dbName;

    // props
    //	    The connection properties for the database
    //	username
    //	    The connection username - for connecting to MySQL
    // password
    //	    The password for the connection username
    private static Properties props = new Properties();
    private static String username;
    private static String password;

    // conn
    //	    The connection to the database
    private static Connection conn;

    // Store the instance of this class, to make it singleton
    private static DBSettings instance = null;

    /**
     * Singleton constructor
     *
     * Should only ever be called from within the DBSettings class
     * @throws SQLException - if there was a problem connecting to the database,
     * an SQLException is thrown
     */
    private DBSettings() throws SQLException {
	// REMOTE SETTINGS
	// server = jdbc:mysql://<iphere>:<porthere>/
	// default port is 3306
	this.server = "jdbc:mysql://142.157.61.138:3306/";
	this.username = "admin";
	this.password = "admin";

	// LOCAL SETTINGS
//	server = "jdbc:mysql://localhost:3306/";
//	username = "iain";
//	password = "abc123";

	dbName = "virginrestaurantica";
	url = server + dbName;
	props.setProperty("user",username);
	props.setProperty("password",password);

	// Open the database connection
	try {
	    conn = DriverManager.getConnection(url,props);
	} catch (SQLException ex) {
	    throw ex;
	}
    }

    /**
     * getInstance()
     *
     * Get the instance of the singleton, or create a new singleton, if one does
     * not already exist.
     *
     * @return
     * @throws SQLException
     */
    private static DBSettings getInstance() throws SQLException{
	if (instance == null){
	    try{
		instance = new DBSettings();
	    }
	    catch (SQLException ex) {
		throw ex;
	    }
	}
	return instance;
    }

    /**
     * getConn()
     *
     * Get the connection stored in the DBSettings.
     * 
     * @return
     * @throws SQLException - If there is a problem with the connection, an
     * SQLException is thrown.
     */
    public static Connection getConn() throws SQLException{
	if (instance == null){
	    try{
		instance = new DBSettings();
	    }
	    catch (SQLException ex) {
		throw ex;
	    }
	}
	return conn;
    }
}
