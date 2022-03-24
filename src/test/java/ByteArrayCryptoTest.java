import com.cubetiqs.crypto.Crypto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ByteArrayCryptoTest {
    String KEY = "wAsqXsY7ylinifToEac9+Ok/lu6pbMjaLXikyCF0L0o=";
    String IV = "pZz+EH7xYsbjYANTLupdVg==";
    String FILE_PATH = "src/test/resources/.gitignore";
    String FILE_CONTENT = "#Nothing to be here in test";

    Crypto crypto = Crypto.defaultInstance(KEY, IV);

    private byte[] getFileAsBytes() {
        try {
            return java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Test
    public void encryptAndDecryptTest() {
        byte[] encrypted = crypto.encrypt(getFileAsBytes());
        System.out.println("Encrypted: " + Arrays.toString(encrypted));

        byte[] decrypted = crypto.decrypt(encrypted);
        System.out.println("Decrypted: " + Arrays.toString(decrypted));

        String content = new String(decrypted);
        System.out.println("Content: " + content);

        Assertions.assertEquals(FILE_CONTENT, content);
    }
}
