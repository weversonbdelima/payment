package com.example.backend.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.backend.model.CreditCard;

import java.util.Base64;

public class CryptoUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding"; // Modificado para incluir padding e modo ECB
    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    static public String decrypt(String encryptedData, String secretKey) throws Exception {
        logger.info("Iniciando o processo de descriptografia...");

        // Log da chave secreta (não recomendado em produção)
        logger.info("Chave secreta: {}", secretKey);

        // Verifique se a chave tem o tamanho correto
        if (secretKey.length() != 16) {
            throw new IllegalArgumentException("A chave deve ter 16 bytes (128 bits).");
        }

        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        logger.info("Descriptografando os dados...");
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        String decryptedData = new String(decryptedBytes, "UTF-8");
        logger.info("Descriptografia concluída com sucesso.");

        return decryptedData;
    }

    // Método de criptografia para teste
    static public String encrypt(String data, String secretKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    static public CreditCard decryptCard(CreditCard card, String secretKey) throws Exception {

        CreditCard decryCard = new CreditCard();

        decryCard.setCardNumber(decrypt(card.getCardNumber(), secretKey));
        decryCard.setHolder(decrypt(card.getHolder(), secretKey));
        decryCard.setExpirationDate(decrypt(card.getExpirationDate(), secretKey));
        decryCard.setSecurityCode(decrypt(card.getSecurityCode(), secretKey));
        decryCard.setBrand(decrypt(card.getBrand(), secretKey));

        return decryCard;

    }
}