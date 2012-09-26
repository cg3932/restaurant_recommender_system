/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

/**
 *
 * @author Pa
 */
public class ShiftInserter implements EStrategy,DStrategy
{

    int key = 7;

    public String encrypt(String toEncrypt)
    {
        int length = toEncrypt.length();
        char[] returnString = new char[length*2];
        for (int i = 0; i < length*2; i++)
        {
            if (i%2 == 0)
            {
                returnString[i] = (char) (40 + (toEncrypt.charAt(i/2) + i-1)%80);
            }
            else
            {
                returnString[i] = (char) (40 + ((toEncrypt.charAt(i/2) + key))%85);
            }
        }
        return new String(returnString);
    }

    public String decrypt(String toDecrypt)
    {
        int length = toDecrypt.length();
        char[] returnString = new char[length/2];
        for (int i = 1; i < length; i+=2)
        {
            returnString[(i-1)/2] = (char) ((40 + (90 + toDecrypt.charAt(i) - key)%85));
        }
        return new String(returnString);
    }
   
}
