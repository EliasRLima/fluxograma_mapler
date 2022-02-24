package Services;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureService {
	
	public String getEncryptado(String texto) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		String s1 = "erl1234567890123";
		return encrypt("AES/CBC/PKCS5Padding", texto, getKeyFromPassword("erl", "#$erl##"), new IvParameterSpec(s1.getBytes()));
	}
	
	public String getDecryptado(String texto) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		String s1 = "erl1234567890123";
		return decrypt("AES/CBC/PKCS5Padding", texto, getKeyFromPassword("erl", "#$erl##"), new IvParameterSpec(s1.getBytes()));
	}

	private String encrypt(String algorithm, String input, SecretKey key,
		    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    return Base64.getEncoder().encodeToString(cipherText);
	}
	
	private String decrypt(String algorithm, String cipherText, SecretKey key,
		    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
		    InvalidAlgorithmParameterException, InvalidKeyException,
		    BadPaddingException, IllegalBlockSizeException {
		    
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, iv);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(cipherText));
		    return new String(plainText);
		}
	
	/*private boolean getKey(String pass, String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKey encodedKey = getKeyFromPassword("Baeldung@2021", "@$#baelDunG@#^$*");
		String encodedString = convertSecretKeyToString(encodedKey);
		SecretKey encodedKey2 = getKeyFromPassword(pass, key);
		String encodedString2 = convertSecretKeyToString(encodedKey);
		System.out.println(encodedString);
		System.out.println(encodedString2);
		return true;
	}*/

	
	private SecretKey convertStringToSecretKeyto(String encodedKey) {
	    byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
	    SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
	    return originalKey;
	}
	
	private SecretKey getKeyFromPassword(String password, String salt)
			  throws NoSuchAlgorithmException, InvalidKeySpecException {
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
	    SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
	    return originalKey;
	}
	
	private String convertSecretKeyToString(SecretKey secretKey) throws NoSuchAlgorithmException {
	    byte[] rawData = secretKey.getEncoded();
	    String encodedKey = Base64.getEncoder().encodeToString(rawData);
	    return encodedKey;
	}
}
