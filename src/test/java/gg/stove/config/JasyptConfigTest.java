package gg.stove.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class JasyptConfigTest {

    @Test
    public void stringEncryptor() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("password");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPoolSize("1");

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);

        String plainText = "jdbc:mysql://localhost/stove?serverTimezone=Asia/Seoul";
        String encryptText = encryptor.encrypt(plainText);
        String decryptText = encryptor.decrypt(encryptText);
        then(plainText).isEqualTo(decryptText);
    }
}