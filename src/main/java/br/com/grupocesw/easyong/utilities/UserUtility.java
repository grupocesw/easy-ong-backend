package br.com.grupocesw.easyong.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserUtility {

	/**
	 * Encrypts the user's password before saving to the database
	 * @param password
	 * @return String|null
	 */
	public static String encryptPassword(String password) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			String encryptedPassword = algorithm.digest(password.getBytes("UTF-8")).toString();

			return encryptedPassword;
		} catch (UnsupportedEncodingException e) {
			System.out.println(e);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
		} 
		
		return null;
	}
}
