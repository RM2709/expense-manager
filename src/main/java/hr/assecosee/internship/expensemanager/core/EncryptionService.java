package hr.assecosee.internship.expensemanager.core;

import hr.assecosee.internship.expensemanager.ExpenseManagerApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;


@Service
public class EncryptionService {

    private static final Logger logger = LogManager.getLogger(ExpenseManagerApplication.class);

    Cipher cipher;

    @Value("${keystorePassword}")
    private String keystorePassword;

    @Value("${keystoreAesAlias}")
    private String keystoreAesAlias;

    @Value("${p12file}")
    private String p12file;

    KeyStore keystore;

    /**
     * Encrypts plain text to decrypted text using AES256.
     *
     * @param plainText Plain text to be encrypted.
     * @return encrypted text.
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws InvalidKeyException
     */
    public String encrypt(String plainText) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, InvalidKeyException {
        logger.info("Method encrypt called for the following string: " + plainText);
        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getAesKey());
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes("UTF-8")));
    }

    /**
     * Decrypts encrypted text to plain text using AES256.
     *
     * @param cipherText Encrypted text to be decrypted.
     * @return Plain text decrypted from the input.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        logger.info("Method decrypt called for the following string: " + cipherText);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getAesKey());
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)));
    }


    private Key getAesKey() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException {
        logger.info("Method getAesKey called.");
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(this.getClass().getClassLoader().getResourceAsStream(p12file), keystorePassword.toCharArray());
        return keystore.getKey(keystoreAesAlias, keystorePassword.toCharArray());
    }
}
