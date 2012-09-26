/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package users;
import entities.User;
import java.sql.SQLException;
import login.Encrypt;
import networkInterface.DBRatingsControl;
import networkInterface.DBUserController;
import ratings.RatingControl;

/**
 *
 * @author David
 */
public class UserControl {

//    public static User getUserByEmail(String email)
//    {
//        DBUserController dbuc;
//	try
//        {
//	    dbuc = new DBUserController();
//	}
//        catch (SQLException ex)
//        {
//            JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
//            return null;
//	}
////        DBUserController dbuc = new DBUserController();
//        Encrypt encrypter = new Encrypt();
//        String Eemail = encrypter.encrypt(email);
//        User user = dbuc.getUserByEmail(Eemail);
//        return user;
//    }

//    public static User getUserByName(String name)
//    {
//        DBUserController dbuc;
//	try
//        {
//	    dbuc = new DBUserController();
//	}
//        catch (SQLException ex)
//        {
//            JOptionPane.showMessageDialog(null, "Error, Cannot Connect To The Database, Please Restart the Application");
//            return null;
//	}
//        User user = dbuc.getUserByName(name);
//        return user;
//    }

    public static String changeData(User user, String Username, String email)
    {
        // Encrypt User Data (Username, Email)
        Encrypt pierre = new Encrypt();
        String encryptedUsername = pierre.encrypt(Username);
        String encryptedEmail = pierre.encrypt(email);

        DBUserController dbuc;
	try
        {
	    dbuc = new DBUserController();
	}
        catch (SQLException ex)
        {
	    return "The user data could not be changed. There was a problem connecting to the database.";
	}

        User existingUser = dbuc.getUserByEmail(encryptedEmail);
        if((existingUser.getUserID() == 0) || (user.getUserID() == existingUser.getUserID()))
        {
            String result0 = dbuc.changeUserName(user, encryptedUsername);
            String result1 = dbuc.changeUserEmail(user, encryptedEmail);

            if(result0.equals("all is well") && result1.equals("all is well"))
            {
                user.setDisplayName(Username);
                user.setEmail(email);
                return "The user data was successfully changed.";
            }
            else
            {
                return "The user data could not be changed.";
            }
            
        }
        else
        {
            return "The e-mail address already exists. Please try another one or contact the administrator.";
        }
    }

    public static String blockUser(User user)
    {
        DBUserController dbuc;
	try
        {
	    dbuc = new DBUserController();
	}
        catch (SQLException ex)
        {
	    return "The user could not be blocked. There was a problem connecting to the database.";
	}
        String result = dbuc.changeBlocked(user, true);
        if(result.equals("all is well"))
        {
            return "The user was successfully blocked.";
        }
        return "The user could not be blocked. Please try again.";
    }

    public static String unblockUser(User user)
    {
        DBUserController dbuc;
	try
        {
	    dbuc = new DBUserController();
	}
        catch (SQLException ex)
        {
	    return "The user could not be unblocked. There was a problem connecting to the database.";
	}
        String result = dbuc.changeBlocked(user, false);
        if(result.equals("all is well"))
        {
            return "The user was successfully unblocked.";
        }
        return "The user could not be unblocked. Please try again.";
    }

    public static String deleteUser(User user)
    {
        DBUserController dbuc;
	try
        {
	    dbuc = new DBUserController();
	}
        catch (SQLException ex)
        {
	    return "The user could not be deleted. There was a problem connecting to the database.";
	}

        // delete User ratings from Database
        String resultRatings = RatingControl.deleteAllUserRatings(user);
        if(resultRatings.equals("Not every rating could be deleted."))
        {
             return "Not every rating could be deleted. Please contact the admin.";
        }
        
        // delete User from Database
        String resultUser = dbuc.deleteUser(user);
        if(resultUser.equals("all is well"))
        {

             return "The user was successfully deleted.";
        }
        return "The user could not be deleted. Please try again.";
    }

    public static String changeUserPassword(User loggedUser, String emailEntered, String oldPassword, String newPassword)
    {
        if(!loggedUser.getEmail().equals(emailEntered))
        {
            return "The e-mail address was incorrect! Please type in your e-mail address.";
        }
        else
        {
            if(!oldPassword.equals(newPassword))
            {
                return "Both passwords were not equal. Try again!";
            }
            else
            {
                if (newPassword.length() < 5 || newPassword.length() > 25)
                {
                    return "Password must be between 5 and 25 characters";
                }
                else
                {
                    DBUserController dbuc;
                    try
                    {
                        dbuc = new DBUserController();
                    }
                    catch (SQLException ex)
                    {
                        return "The password could not be changed. There was a problem connecting to the database.";
                    }
                    Encrypt pierre = new Encrypt();
                    String result = dbuc.changeUserPassword(loggedUser, pierre.encrypt(newPassword));
                    if(result.equals("all is well"))
                    {
                        return "The password was successfully changed.";
                        
                    }
                    return "The password could not be changed. Please try again.";
                }
            }
        }
    }

//    public static String setAdmin(User user)
//    {
//        DBUserController dbuc;
//	try
//        {
//	    dbuc = new DBUserController();
//	}
//        catch (SQLException ex)
//        {
//	    return "The status could not be changed. There was a problem connecting to the database.";
//	}
//        String result = dbuc.changeAdmin(user, true);
//        if(result.equals("all is well"))
//        {
//            return "The user was granted administrator rights.";
//        }
//        return "The user's status to administrator could not be changed. Please try again.";
//    }
//
//    public static String resetAdmin(User user)
//    {
//        DBUserController dbuc;
//	try
//        {
//	    dbuc = new DBUserController();
//	}
//        catch (SQLException ex)
//        {
//	    return "The status could not be changed. There was a problem connecting to the database.";
//	}
//        String result = dbuc.changeAdmin(user, false);
//        if(result.equals("all is well"))
//        {
//            return "The administrator was granted user rights.";
//        }
//       return "The administrator's status to user could not be changed. Please try again.";
//    }
}