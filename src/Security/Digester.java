package Security;

import Logic.ConfigLoader;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * Hele klassen er hentet fra vores fælles server: https://github.com/emilstepanian/Undervisningsevaluering
 *
 * Klassen indeholder alle hash og decrypterings metoder
 * Hashing foregår med md5 hashing.
 * Kryptering foregår med XOR og BASE64.
 */


public class Digester {


    private final static String SALT = ConfigLoader.HASH_SALT;
    private final static String KEY = ConfigLoader.ENCRYPT_KEY;
    private static MessageDigest digester;


    //Opretter objekt, som benyttes af MD5 (hashfunktion)
    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hash string with MD5 hashing
     * @param str
     * @return MD5 hash of string
     */

    //Hashing påbegyndes
    public static String hash(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Error");
        }
        return Digester._hash(str);
    }

    /**
     * Hashing med salt
     * @param str
     * @return
     */
    public static String hashWithSalt(String str){
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Error");
        }

        str = str + Digester.SALT;

        return Digester._hash(str);
    }

    /**
     * Konvertere hashværdien til hexidecimaler
     * @param str
     * @return
     */
    private static String _hash(String str){
        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (byte aHash : hash) {
            if ((0xff & aHash) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & aHash)));
            } else {
                hexString.append(Integer.toHexString(0xFF & aHash));
            }
        }
        return hexString.toString();
    }

    /**
     * Krypterings metoden kaldt i andre klasser der kryptere en string
     * @param s taget en String
     * @return krypteret streng.
     */
    public static String encrypt(String s) {

        String encrypted_string = s;

        if(ConfigLoader.ENCRYPTION.equals("TRUE")){
            encrypted_string = base64Encode(xorWithKey(encrypted_string.getBytes(), KEY.getBytes()));
        }
        return encrypted_string;
    }

    /**
     * Dekrypterings metoden der bliver kaldt i andre klasser der dekryptere Strengen.
     * @param s tager en String.
     * @return Dekrypteret streng
     */
    public static String decrypt(String s) {

        String decrypted_string = s;

        if(ConfigLoader.ENCRYPTION.equals("TRUE")) {
            decrypted_string = new String(xorWithKey(base64Decode(s), KEY.getBytes()));
        }
        return decrypted_string;
    }

    //
    private static byte[] xorWithKey(byte[] a, byte[] key) {
        byte[] out = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            out[i] = (byte) (a[i] ^ key[i%key.length]);
        }
        return out;
    }

    //Metode til at dekryptering

    /**
     * Base64 decode metode der bliver kaldt i dekrypterings metoden.
     * @param s tager en String af det der skal dekrypteres
     * @return returnere den dekrypteret
     */
    private static byte[] base64Decode(String s) {
        try {
            BASE64Decoder d = new BASE64Decoder();

            return d.decodeBuffer(s);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    //metode til kryptering
    private static String base64Encode(byte[] bytes) {
        BASE64Encoder enc = new BASE64Encoder();
        return enc.encode(bytes).replaceAll("\\s", "");

    }
}