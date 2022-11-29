# CUBETIQ Crypto for Java

### Features

- [x] Default Provider
- [x] AES
- [ ] RSA

### Install
```groovy
repositories {
    mavenCentral()
    maven {
        url 'https://jitpack.io'
    }
}

dependencies {
    implementation "com.github.CUBETIQ:crypto-java:1.2"
    
    // other deps here
}
```

- Basic

```java
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

- ByteArray Crypto Provider

```java
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
```

### Contributors

- Sambo Chea <sombochea@cubetiqs.com>
