package Process;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Asymmetric {
    private String algorithm;
    private String mode;
    private String padding;
    private int keySize;

    private File file;

    private KeyPair keyPair;
    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;

    private DataInputStream dis;
    private DataOutputStream dos;
    private Cipher cipher;

    public Asymmetric(String algorithm, int keySize, String mode, String padding) {
        this.algorithm = algorithm;
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }


    public void initKeypair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
            keyPairGenerator.initialize(this.keySize);
            this.keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String text) {
        try {
            this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding);
            this.cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            byte[] plainByte = text.getBytes("UTF-8");
            byte[] cypherByte = cipher.doFinal(plainByte);

            return Base64.getEncoder().encodeToString(cypherByte);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public String decrypt(String text) {
        try {
            this.cipher = Cipher.getInstance(this.algorithm + "/" + this.mode + "/" + this.padding);
            this.cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] cypherByte = Base64.getDecoder().decode(text.getBytes("UTF-8"));
            byte[] plainText = cipher.doFinal(cypherByte);
            return new String(plainText, "UTF-8");

        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    /* generate public/private key*/
    public Asymmetric(String algorithm, int keySize) {
        this.algorithm = algorithm;
        this.keySize = keySize;
    }

    public PrivateKey generatePrivateKey() {
        return this.keyPair.getPrivate();
    }

    public PublicKey generatePublicKey() {
        return this.keyPair.getPublic();
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /* export and open key*/
    public static boolean exportPrivate(PrivateKey privateKey, String src) {
        String key64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
            bw.write(key64);
            bw.flush();
            bw.close();
            return true;
        } catch (Exception exception) {
            System.out.println("Asymmetric exportPrivate: " + exception.getMessage());
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
            System.out.println("Asymmetric exportPublic: " + exception.getMessage());
        }
        return false;
    }

    public boolean setPublicKeyFromFile(String src) {
        try {
            String key64 = new String(Files.readAllBytes(Paths.get(src)));
            byte[] keybytes = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
            X509EncodedKeySpec xeks = new X509EncodedKeySpec(keybytes);
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            this.publicKey = keyFactory.generatePublic(xeks);
            return true;
        } catch (Exception exception) {
            System.out.println("Asymmetric setPublicKeyFromFile: " + exception.getMessage());
        }
        return false;
    }

    public boolean setPrivateKeyFromFile(String src) {
        try {
            String key64 = new String(Files.readAllBytes(Paths.get(src)));
            byte[] keyByte = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
            KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
            Arrays.fill(keyByte, (byte) 0);
            this.privateKey = keyFactory.generatePrivate(keySpec);
            return true;
        } catch (Exception exception) {
            System.out.println("Asymmetric setPrivateKeyFromFile: " + exception.getMessage());
        }
        return false;
    }

}

