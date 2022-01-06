package Process;

import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.Security;

public class Hash {
	private String algorithm;

	public Hash(String algorithm) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.algorithm = algorithm;
	}

	public Hash() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public String text(String text) {
		String hashText = "";
		try {
			MessageDigest md = MessageDigest.getInstance(this.algorithm, "BC");
			byte[] mdByte = md.digest(text.getBytes());
			BigInteger bigInteger = new BigInteger(1, mdByte);
			hashText = bigInteger.toString(16);
			return hashText;

		} catch (Exception exception) {
			System.out.println("Hash text: " + exception.getMessage());
		}
		return null;
	}

	public String file(String path) {
		File file = new File(path);
		if (!file.isFile())
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance(this.algorithm, "BC");
			InputStream is = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(is);
			DigestInputStream dis = new DigestInputStream(bis, md);

			byte[] buffer = new byte[1024];
			int read = dis.read(buffer);
			while (read != -1) {
				read = dis.read(buffer);
			}
			String hashText = "";
			BigInteger bigInteger = new BigInteger(1, dis.getMessageDigest().digest());
			hashText = bigInteger.toString(16);

			return hashText;

		} catch (Exception exception) {
			System.out.println("Hash file: " + exception.getMessage());
		}
		return null;
	}

	public boolean check(String hashText, String compareText) {
		if (hashText.equalsIgnoreCase(compareText))
			return true;
		return false;
	}

}
