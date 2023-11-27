package az.processing.msuser.util;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class EncoderUtil {

    private static final String SECRET_KEY = "processingSecret";
    private static final String ALGORITHM = "AES";

    public String encode(String cardNumber) {
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(cardNumber.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding card number", e);
        }
    }

    public String decode(String encodedCardNumber) {
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = cipher.doFinal(Base64.getDecoder().decode(encodedCardNumber));
            return new String(decodedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decoding card number", e);
        }
    }

    public boolean match(String rawCardNumber, String encodedCardNumber) {
        return rawCardNumber.equals(decode(encodedCardNumber));
    }
}


