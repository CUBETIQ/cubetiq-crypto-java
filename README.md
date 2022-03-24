# CUBETIQ Crypto for Java

### Features

- [x] Default Provider
- [x] AES
- [ ] RSA

- Basic

```java
import com.cubetiqs.crypto.core.CryptoUtil;
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
        String encrypted = CryptoUtil.encrypt(TEXT, options);
        System.out.println("Encrypted: " + encrypted);
        Assertions.assertEquals(ENCRYPTED, encrypted);
    }

    @Test
    public void decryptTest() {
        String decrypted = CryptoUtil.decrypt(ENCRYPTED, options);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertEquals(TEXT, decrypted);
    }
}
```

- Crypto Provider

```java
import com.cubetiqs.crypto.Crypto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultCryptoTest {
    String KEY = "wAsqXsY7ylinifToEac9+Ok/lu6pbMjaLXikyCF0L0o=";
    String IV = "pZz+EH7xYsbjYANTLupdVg==";

    String TEXT = "Hello World at 42";
    String ENCRYPTED = "Cx76hN6Ceoh/oMdxz2OSfINxGHcbZKTmv35+xKzYlTM=";

    Crypto crypto = Crypto.defaultInstance(KEY, IV);

    @Test
    public void encryptTest() {
        String encrypted = crypto.encrypt(TEXT);
        System.out.println("Encrypted: " + encrypted);
        Assertions.assertEquals(ENCRYPTED, encrypted);
    }

    @Test
    public void decryptTest() {
        String decrypted = crypto.decrypt(ENCRYPTED);
        System.out.println("Decrypted: " + decrypted);
        Assertions.assertEquals(TEXT, decrypted);
    }
}
```

### Contributors

- Sambo Chea <sombochea@cubetiqs.com>