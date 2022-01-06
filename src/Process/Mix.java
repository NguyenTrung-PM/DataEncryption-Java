package Process;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.plaf.synth.SynthMenuBarUI;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Mix {
	private PublicKey publicKey;
	private PrivateKey privateKey;

	private String algorithmA, modeA, paddingA, algorithmS, modeS, paddingS;
	private int keySizeA, keySizeS;
	private Cipher cipherA, cipherS;
	private IvParameterSpec ivSpec;

	private BufferedInputStream bis;
	private BufferedOutputStream bos;

	private SecretKey sKey;
	private KeyPairGenerator keyPairGenerator;
	private KeyPair keyPair;

	private File file;

	public Mix() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public void setAsymmetric(String algorithmA, int keySizeA, String modeA, String paddingA) {
		this.algorithmA = algorithmA;
		this.keySizeA = keySizeA;
		this.modeA = modeA;
		this.paddingA = paddingA;
		this.keyPair = genKeyPair();
	}

	public void setSymmetric(String algorithmS, int keySizeS, String modeS, String paddingS) {
		this.algorithmS = algorithmS;
		this.keySizeS = keySizeS;
		this.modeS = modeS;
		this.paddingS = paddingS;
	}

	public boolean encrypt(String src, String des) {
		this.file = new File(src);
		if (!file.isFile())
			return false;

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(this.algorithmS, "BC");
			keyGenerator.init(this.keySizeS);
			SecretKey sKey = keyGenerator.generateKey();

			byte[] iv = new byte[Cipher.getInstance(this.algorithmS, "BC").getBlockSize()];
			SecureRandom sr = new SecureRandom();
			sr.nextBytes(iv);
			this.ivSpec = new IvParameterSpec(iv);

			this.cipherA = Cipher.getInstance(this.algorithmA + "/" + this.modeA + "/" + this.paddingA);
			this.cipherA.init(Cipher.ENCRYPT_MODE, this.privateKey);
			byte[] keyEncrypt = new byte[this.keySizeA / 8 - 11];
			keyEncrypt = cipherA.doFinal(sKey.getEncoded());

			this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));
			bos.write(keyEncrypt);
			bos.write(iv);

			this.cipherS = Cipher.getInstance(this.algorithmS + "/" + this.modeS + "/" + this.paddingS, "BC");
			this.cipherS.init(Cipher.ENCRYPT_MODE, sKey, this.ivSpec);
			this.bis = new BufferedInputStream(new FileInputStream(this.file));

			boolean response = FileProcess.process(this.cipherS, this.bis, this.bos);

			if (response) {
				return true;
			} else {
				return false;
			}

		} catch (Exception exception) {
			System.out.println("Mix encrypt: " + exception.getMessage());
		}
		return false;
	}

	public boolean decrypt(String src, String des) {
		this.file = new File(src);
		if (!file.isFile())
			return false;

		try {
			this.cipherA = Cipher.getInstance(this.algorithmA + "/" + this.modeA + "/" + this.paddingA);
			this.cipherA.init(Cipher.DECRYPT_MODE, this.publicKey);
			byte[] keyEncrypt = new byte[this.keySizeA / 8];

			this.bis = this.bis = new BufferedInputStream(new FileInputStream(this.file));
			this.bis.read(keyEncrypt);
			byte[] keyDecrypt = cipherA.doFinal(keyEncrypt);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyDecrypt, this.algorithmS);

			byte[] iv = new byte[Cipher.getInstance(this.algorithmS, "BC").getBlockSize()];
			this.bis.read(iv);
			this.ivSpec = new IvParameterSpec(iv);

			this.cipherS = Cipher.getInstance(this.algorithmS + "/" + this.modeS + "/" + this.paddingS, "BC");
			cipherS.init(Cipher.DECRYPT_MODE, secretKeySpec, this.ivSpec);

			this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));
			boolean response = FileProcess.process(this.cipherS, this.bis, this.bos);

			if (response) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			System.out.println("Mix decrypt: " + exception.getMessage());
		}
		return false;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	/* generate pub and pri key */
	public Mix(String algorithmA, int keySizeA) {
		this.algorithmA = algorithmA;
		this.keySizeA = keySizeA;
		this.keyPair = genKeyPair();
	}

	public KeyPair genKeyPair() {
		try {
			this.keyPairGenerator = KeyPairGenerator.getInstance(this.algorithmA);
			this.keyPairGenerator.initialize(this.keySizeA);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return keyPairGenerator.generateKeyPair();
	}

	public PrivateKey generatePrivateKey() {
		return this.keyPair.getPrivate();
	}

	public PublicKey generatePublicKey() {
		return this.keyPair.getPublic();
	}

	/* export and load key */
	public static boolean exportPrivate(PrivateKey privateKey, String src) {
		String key64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
			bw.write(key64);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception exception) {
			System.out.println("Mix exportPrivate: " + exception.getMessage());
		}
		return false;
	}

	public static boolean exportPublic(PublicKey publicKey, String src) {
		String key64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
			bw.write(key64);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception exception) {
			System.out.println("Mix exportPublic: " + exception.getMessage());
		}
		return false;
	}

	public boolean setPublicKeyFromFile(String src) {
		try {
			String key64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] keybytes = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
			X509EncodedKeySpec xeks = new X509EncodedKeySpec(keybytes);
			KeyFactory keyFactory = KeyFactory.getInstance(this.algorithmA);
			this.publicKey = keyFactory.generatePublic(xeks);
			return true;
		} catch (Exception exception) {
			System.out.println("Mix setPublicKeyFromFile: " + exception.getMessage());
		}
		return false;
	}

	public boolean setPrivateKeyFromFile(String src) {
		try {
			String key64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] keyByte = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(this.algorithmA);
			Arrays.fill(keyByte, (byte) 0);
			this.privateKey = keyFactory.generatePrivate(keySpec);
			return true;
		} catch (Exception exception) {
			System.out.println("Mix setPrivateKeyFromFile: " + exception.getMessage());
		}
		return false;
	}

}
