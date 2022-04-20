import com.cubetiqs.crypto.core.CryptoUtil;
import com.cubetiqs.crypto.util.FunctionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicTest {
    String KEY = "wAsqXsY7ylinifToEac9+Ok/lu6pbMjaLXikyCF0L0o=";
    String IV = "pZz+EH7xYsbjYANTLupdVg==";

    String TEXT = "Hello World at 42";
    String ENCRYPTED = "Cx76hN6Ceoh/oMdxz2OSfINxGHcbZKTmv35+xKzYlTM=";

    CryptoUtil.Options options = CryptoUtil.Options
            .builder()
            .setKey(KEY)
            .setIv(IV)
            .build();

    @Test
    public void encryptTest() {
        String encrypted = FunctionUtil.encrypt(TEXT, options);
        System.out.println("Encrypted: " + encrypted);
        Assertions.assertEquals(ENCRYPTED, encrypted);
    }

    @Test
    public void decryptTest() {
        String decrypted = FunctionUtil.decrypt(ENCRYPTED, options);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertEquals(TEXT, decrypted);
    }

    @Test
    public void encodeToBase64Test() {
        String text = "Hello World";
        String textBase64 = "SGVsbG8gV29ybGQ=";
        String encoded = FunctionUtil.encodeToBase64(text);
        Assertions.assertEquals(textBase64, encoded);
    }
}
