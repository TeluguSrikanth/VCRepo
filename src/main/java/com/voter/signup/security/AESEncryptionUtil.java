package com.voter.signup.security;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class AESEncryptionUtil {

	   private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
	    private static final int KEY_SIZE = 256; // 256-bit AES
	    private static final int IV_SIZE = 16; // 128-bit IV for AES
	    private static final int ITERATION_COUNT = 100000;

	    @Value("${aes.secret.key}")
	    private String secretKeyStr;

	    @Value("${aes.salt}")
	    private String saltStr;

	    private SecretKeySpec secretKey;

	    @PostConstruct
	    private void initSecretKeyAndSalt() throws Exception {
	        byte[] salt = saltStr.getBytes("UTF-8");

	        // Derive key using SecretKeyFactory
	        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        KeySpec spec = new PBEKeySpec(secretKeyStr.toCharArray(), salt, ITERATION_COUNT, KEY_SIZE);
	        SecretKey tmp = factory.generateSecret(spec);
	        byte[] encoded = tmp.getEncoded();

	        secretKey = new SecretKeySpec(encoded, "AES");
	    }

	    private IvParameterSpec generateIv() {
	        byte[] iv = new byte[IV_SIZE];
	        SecureRandom secureRandom = new SecureRandom();
	        secureRandom.nextBytes(iv);
	        return new IvParameterSpec(iv);
	    }

	    public String encrypt(String strToEncrypt) throws Exception {
	        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
	        IvParameterSpec ivParameterSpec = generateIv();
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
	        byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
	        
	        // Combine IV and encrypted data
	        byte[] ivAndEncrypted = new byte[IV_SIZE + encrypted.length];
	        System.arraycopy(ivParameterSpec.getIV(), 0, ivAndEncrypted, 0, IV_SIZE);
	        System.arraycopy(encrypted, 0, ivAndEncrypted, IV_SIZE, encrypted.length);

	        return Base64.getEncoder().encodeToString(ivAndEncrypted);
	    }

	    public String decrypt(String strToDecrypt) throws Exception {
	        byte[] decoded = Base64.getDecoder().decode(strToDecrypt);

	        // Extract IV and encrypted data
	        byte[] iv = new byte[IV_SIZE];
	        byte[] encrypted = new byte[decoded.length - IV_SIZE];
	        System.arraycopy(decoded, 0, iv, 0, IV_SIZE);
	        System.arraycopy(decoded, IV_SIZE, encrypted, 0, encrypted.length);

	        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

	        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
	        byte[] original = cipher.doFinal(encrypted);
	        return new String(original, "UTF-8");
	    }
	
	
	
	
	
}
