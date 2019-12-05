// via https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java

package whiteboard;

import java.security.MessageDigest;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PBKDF2 {
    public static String hashPBKDF2(String password, String salt) {
        try {
            char[] passwordChars = password.toCharArray();
            byte[] saltBytes = salt.getBytes();

            PBEKeySpec spec = new PBEKeySpec(
                    passwordChars,
                    saltBytes,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hashedPassword = key.generateSecret(spec).getEncoded();

            return String.format("%x", new BigInteger(hashedPassword));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

