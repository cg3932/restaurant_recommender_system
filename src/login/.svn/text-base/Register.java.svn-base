/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import entities.*;
import g9_rest_recommender_final.GUI_Layout;
import networkInterface.DBUserController;

/**
 *
 * @author Pa
 */
public class Register {

    User foundUser;
    GUI_Layout program;

    

    public Register(GUI_Layout program)
    {
        this.program = program;
    }

    public String register(String email, String password, String username) {

        if (email.length() < 5 || email.length() > 25) return "Email must be between 5 and 25 characters";
        if (password.length() < 5 || password.length() > 25) return "Password must be between 5 and 25 characters";
        if (username.length() < 4 || username.length() > 15) return "Username must be between 5 and 15 characters";

        Encrypt encrypter = new Encrypt();
        String Eemail = encrypter.encrypt(email);
        String Epassword = encrypter.encrypt(password);
        String Eusername = encrypter.encrypt(username);
        String feedback = attemptRegister(Eemail, Epassword, Eusername);
        if (!feedback.equals("registered")) {
            return feedback;
        } else {
           
            return "success";
        }
    }

    private String attemptRegister(String Eemail, String Epassword, String Eusername) {
        User newUser = new User();
        newUser.setEmail(Eemail);
        newUser.setDisplayName(Eusername);
        newUser.setPassword(Epassword);
        DBUserController controller;
        try {
        controller = new DBUserController();
        }
        catch (Exception e)
        {
            return "Unable to establish connection to server\n" + e.toString();
        }

        if (controller.getUserByEmail(Eemail).getUserID() != 0) {
            return "Invalid e-mail adress: Adress already used";
        } else {
            if (controller.getUserByName(Eusername).getUserID() != 0) {
                 return "Invalid username: Username already taken";
                 }else{
                
                String feedback = controller.setUser(newUser);
                System.out.println(feedback);
                if (feedback.equals("User Already exists")) return feedback;
                else return "registered";
            }
        }
    }
}
