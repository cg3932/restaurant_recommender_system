/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import g9_rest_recommender_final.GUI_Layout;
import entities.User;
import networkInterface.DBUserController;
import java.util.regex.Pattern;

/**
 *
 * @author Pa
 */
public class Login {

    User foundUser;
    GUI_Layout program;

    public Login(GUI_Layout program)
    {
        this.program = program;
    }

    public String login(String email,String password)
    {
        if (email.length() < 5 || email.length() > 25) return "Invalid Email";
        if (password.length() < 5 || password.length() > 25) return "Invalid Password";

        Encrypt encrypter = new Encrypt();
        String Eemail = encrypter.encrypt(email);
        String Epassword = encrypter.encrypt(password);

        String feedback = attemptLogin(Eemail,Epassword);
        if (!feedback.equals("found")) return feedback;
        else
        {
         //Then do Login Operations.
            foundUser.setEmail(encrypter.decrypt(foundUser.getEmail()));
            foundUser.setPassword(encrypter.decrypt(foundUser.getPassword()));
            foundUser.setDisplayName(encrypter.decrypt(foundUser.getDisplayName()));
            program.setLoggedUser(foundUser);
            return feedback;
        }


    }

    private String attemptLogin(String Eemail,String Epassword)
    {
        DBUserController controller;
        try{
        controller = new DBUserController();
        }
        catch (Exception e)
        {
            return "Unable to connect to server\n" + e.toString();
        }
        System.out.println("Encrypted email: " + Eemail);
        foundUser = controller.getUserByEmail(Eemail);

            if (foundUser.getUserID() == 0) return "Invalid e-mail adress";
            System.out.println("IT came to this: " + foundUser.getPassword());

            if (foundUser.getPassword().equals(Epassword)) 
                if (!foundUser.getBlocked()) return "found";
                else return "You have been blocked by a system administrator";
            else return "Invalid password";

    }

}
