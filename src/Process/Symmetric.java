package Process;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

public class Symmetric {
	private String algorithm;
	private String mode;
	private String padding;
	private int keySize;
	private SecretKey secretKey;

	private IvParameterSpec ivSpec;

	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private File file;
	private Cipher cipher;

	public Symmetric(String algorithm, int keySize, String mode, String padding) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.algorithm = algorithm;
		this.mode = mode;
		this.padding = padding;
		this.keySize = keySize;
	}

	public boolean encrypt(String src, String des) {
		this.file = new File(src);
		if (!this.file.isFile())
			return false;
		if (this.secretKey == null)
			return false;

		try {
			this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding, "BC");
			this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));

			this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);
			this.bis = new BufferedInputStream(new FileInputStream(this.file));
			boolean response = FileProcess.process(this.cipher, this.bis, this.bos);

			if (response) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			System.out.println("Symmetric encrypt: " + exception.getMessage());
		}
		return false;
	}

	public boolean decrypt(String src, String des) {
		this.file = new File(src);
		if (!this.file.isFile())
			return false;
		if (this.secretKey == null)
			return false;

		try {
			this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding, "BC");
			this.bis = new BufferedInputStream(new FileInputStream(this.file));

			this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, ivSpec);

			this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));
			boolean response = FileProcess.process(this.cipher, this.bis, this.bos);

			if (response) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			System.out.println("Symmetric decrypt: " + exception.getMessage());
		}
		return false;
	}

	public String encrypt(String plainText) {
		try {
			this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding, "BC");

			this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.ivSpec);

			byte[] plainByte = plainText.getBytes("UTF-8");
			byte[] cipherByte = this.cipher.doFinal(plainByte);
			return Base64.getEncoder().encodeToString(cipherByte);
		} catch (Exception exception) {
			System.out.println("Symmetric encrypt: " + exception.getMessage());
		}
		return null;
	}

	public String decrypt(String cipherText) {
		try {
			this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding, "BC");

			this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey, this.ivSpec);

			byte[] cipherByte = Base64.getDecoder().decode(cipherText.getBytes("UTF-8"));
			byte[] plainByte = this.cipher.doFinal(cipherByte);
			return new String(plainByte, "UTF-8");
		} catch (Exception exception) {
			System.out.println("Symmetric decrypt: " + exception.getMessage());
		}
		return null;
	}

	/* method for key generater */
	public Symmetric(String algorithm, int keySize) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		this.algorithm = algorithm;
		this.keySize = keySize;
	}

	public SecretKey generateKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithm, "BC");
			keyGenerator.init(this.keySize);
			return keyGenerator.generateKey();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public byte[] generateIv() {
		try {
			byte[] iv = new byte[Cipher.getInstance(this.algorithm, "BC").getBlockSize()];
			SecureRandom sr = new SecureRandom();
			sr.nextBytes(iv);
			return iv;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public boolean exportKey(String src, SecretKey secretKey) {
		String key64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
			bw.write(key64);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception exception) {
			System.out.println("Symmetric exportKey: " + exception.getMessage());
		}
		return false;
	}

	public boolean exportIv(String src, byte[] iv) {
		String iv64 = Base64.getEncoder().encodeToString(iv);
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
			bw.write(iv64);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception exception) {
			System.out.println("Symmetric exportIv: " + exception.getMessage());
		}
		return false;
	}

	public boolean setKeyFromFile(String src) {
		try {
			String key64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] temp = Base64.getDecoder().decode(key64.getBytes("UTF-8"));
			byte[] decodedKey = new byte[Cipher.getInstance(this.algorithm, "BC").getBlockSize()];
			for (int i = 0; i < decodedKey.length; i++) {
				decodedKey[i] = temp[i];
			}
			this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.algorithm);
			return true;
		} catch (Exception exception) {
			System.out.println("Symmetric setKeyFromFile: " + exception.getMessage());
		}
		return false;
	}

	public boolean setIvSpecFromFile(String src) {
		try {
			String iv64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] temp = Base64.getDecoder().decode(iv64.getBytes("UTF-8"));
			byte[] ivByte = new byte[Cipher.getInstance(this.algorithm, "BC").getBlockSize()];
			for (int i = 0; i < ivByte.length; i++) {
				ivByte[i] = temp[i];
			}
			this.ivSpec = new IvParameterSpec(ivByte);
			return true;
		} catch (Exception exception) {
			System.out.println("Symmetric setIvSpecFromFile: " + exception.getMessage());
		}
		return false;
	}
}
