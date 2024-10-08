package com.example.backend.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.backend.model.CreditCard;

import java.util.Base64;

public class CryptoUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    static public String decrypt(String encryptedData, String secretKey) throws Exception {
        logger.info("Starting the decryption process...");

        if (secretKey.length() != 16) {
            logger.error("The key must be 16 bytes (128 bits).");
            throw new IllegalArgumentException("The key must be 16 bytes (128 bits).");
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        logger.info("Decrypting data...");
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        String decryptedData = new String(decryptedBytes, "UTF-8");
        logger.info("Decryption completed successfully.");

        return decryptedData;
    }

    static public String encrypt(String data, String secretKey) throws Exception {
        logger.info("Starting the encryption process...");

        if (secretKey.length() != 16) {
            logger.error("The key must be 16 bytes (128 bits).");
            throw new IllegalArgumentException("The key must be 16 bytes (128 bits).");
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        logger.info("Encrypting data...");
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

        logger.info("Encryption completed successfully.");

        return encryptedData;
    }

    static public CreditCard decryptCard(CreditCard card, String secretKey) throws Exception {
        logger.info("Starting the decryption of credit card...");

        CreditCard decryCard = new CreditCard();

        decryCard.setCardNumber(decrypt(card.getCardNumber(), secretKey));
        decryCard.setHolder(decrypt(card.getHolder(), secretKey));
        decryCard.setExpirationDate(decrypt(card.getExpirationDate(), secretKey));
        decryCard.setSecurityCode(decrypt(card.getSecurityCode(), secretKey));
        decryCard.setBrand(decrypt(card.getBrand(), secretKey));

        logger.info("Credit card decryption completed successfully.");

        return decryCard;
    }
}
