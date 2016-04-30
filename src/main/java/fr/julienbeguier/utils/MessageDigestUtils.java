package fr.julienbeguier.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

	private static MessageDigestUtils instance = null;

	private MessageDigestUtils() {

	}

	public static MessageDigestUtils getInstance() {
		if (instance == null) {
			instance = new MessageDigestUtils();
		}
		return instance;
	}

	public String md5(String s) {
		String result = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(s.getBytes());
			byte[] digest = m.digest();

			result = new String(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
}
