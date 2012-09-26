/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
/**
 *
 * @author Pa
 */
public class Encrypt {

    private EStrategy Encrypter;
    private DStrategy Decrypter;

    public String encrypt(String toEncrypt) {
        String policy = EncryptPolicy.getPolicy();
        setEncrypter(policy);
        String returnString = Encrypter.encrypt(toEncrypt);
        return returnString;
    }

    public String decrypt(String toDecrypt) {
        String policy = DecryptPolicy.getPolicy();
        setDecrypter(policy);
        String returnString = Decrypter.decrypt(toDecrypt);
        return returnString;
    }

    private void setEncrypter(String strat) {
        if (strat.equals("ShiftInsert")) {
            Encrypter = new ShiftInserter();
        } else {
            Encrypter = null;
        }
    }

    private void setDecrypter(String strat) {
        if (strat.equals("ShiftInsert")) {
            Decrypter = new ShiftInserter();
        } else {
            Decrypter = null;
        }
    }
}
