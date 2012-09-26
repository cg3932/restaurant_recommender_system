/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

/**
 *
 * @author Pa
 */
public class EncryptTester {
    public static void main(String args[])
    {
        Encrypt crypter = new Encrypt();
        System.out.println(crypter.encrypt("|{[ ~"));
        String x = crypter.encrypt("|{[ ~");
        System.out.println(crypter.decrypt(x));

        for (int i = 0;i<=126;i++)
        {
            System.out.println( (char) i +  (crypter.encrypt((char) i + "")) + crypter.decrypt( crypter.encrypt((char)i + "")) );
        }
    }
}

