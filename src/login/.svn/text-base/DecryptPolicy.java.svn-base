/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

/**
 *
 * @author Pa
 */
public class DecryptPolicy {

    //Implemented policies: PCC
    //Might implement Vector if we need to

    static private String Policy = "ShiftInsert";


    public static String getPolicy () {
        return Policy;
    }

    public static void setPolicy (String val) throws DecPolicyException {
        if (val.equals("ShiftInsert"))
        {
            Policy = val;
        }
        else throw new DecPolicyException();
    }
    public static class DecPolicyException extends Exception
{
}
}


