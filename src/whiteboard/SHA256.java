// via https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java

package whiteboard;

import java.util.Base64;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SHA256 {
    public static String hash(String textToHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByetArray = digest.digest(byteOfTextToHash);
            String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
            return encoded;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

