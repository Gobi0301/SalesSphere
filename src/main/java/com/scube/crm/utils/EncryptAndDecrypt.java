package com.scube.crm.utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * create by:sathishkumar.s
 * created date:9-June-2015
 * @author Administrator
 *This class should be use for encrypt and  decrypt the user  password
 */
public class EncryptAndDecrypt {
	
	private static final String salt="STSEHO2020";
	private static final String ALGORITHM = "AES";
	private static final int ITERATIONS = 2;
	private static final byte[] keyValue = new byte[] { 'S', 'O', 'F', 'T',
			'T', 'W', 'I', 'G', 'S', 'O', 'L', 'U', 'T', 'I', 'O', 'N' };
	
	//public static final String TOKEN = "MJK-PP";

	public static String encrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		String valueToEnc = null;
		String eValue = value;
		for (int i = 0; i < ITERATIONS; i++) {
			valueToEnc = salt + eValue;
			byte[] encValue = c.doFinal(valueToEnc.getBytes());
			eValue = Base64.getEncoder().encodeToString(encValue);
		}
		return eValue;
	}

	public static String decrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		String dValue = null;
		String valueToDecrypt = value;
		for (int i = 0; i < ITERATIONS; i++) {
			byte[] decordedValue = Base64.getDecoder().decode(valueToDecrypt);
			byte[] decValue = c.doFinal(decordedValue);
			dValue = new String(decValue).substring(salt.length());
			valueToDecrypt = dValue;
		}
		return dValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, ALGORITHM);
		return key;
	}

	
	public static void main(String[] args) throws Exception {
		System.out.println(encrypt("admin"));
		//System.out.println(decrypt("$2a$10$5uLABdl34r1oHkHkXMiP9OT/ftTPXTVE6HOLEzp0hwZQlBTpDHo02"));
	}
}
