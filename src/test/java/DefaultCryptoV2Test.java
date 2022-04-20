import com.cubetiqs.crypto.Crypto;
import com.cubetiqs.crypto.util.FunctionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultCryptoV2Test {
    @Test
    public void encryptAndDecryptV2Test() {
        String key = Crypto.createKey();
        String iv = Crypto.createIV();
        String rawText = "I'm Sambo Chea";

        Crypto crypto = Crypto.newDefaultInstance(key, iv);
        String encrypted = crypto.encrypt(rawText);
        String decrypted = crypto.decrypt(encrypted);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertEquals(rawText, decrypted);
    }

    @Test
    public void generateKeyTest() {
        String ivBase64 = FunctionUtil.generateRandomString(16);
        String keyBase642 = FunctionUtil.generateKey(32, null, null);
        System.out.println(ivBase64);
        System.out.println(keyBase642);
    }
}
