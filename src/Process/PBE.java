package Process;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Random;

public class PBE {
    private String algorithm, password;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;
    private File file;
    private PBEKeySpec pbeKeySpec;
    private PBEParameterSpec pbeParameterSpec;
    private Cipher cipher;

    public PBE(String algorithm, String password) {
        this.algorithm = algorithm;
        this.password = password;
    }

    public boolean encrypt(String src, String des) {
        this.file = new File(src);
        if (!this.file.isFile()) return false;

        try {
            this.bis = new BufferedInputStream(new FileInputStream(this.file));
            this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));

            this.pbeKeySpec = new PBEKeySpec(this.password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory
                    .getInstance(this.algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(this.pbeKeySpec);

            byte[] salt = new byte[8];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(salt);

            this.pbeParameterSpec = new PBEParameterSpec(salt, 100);
            this.cipher = Cipher.getInstance(this.algorithm);
            this.cipher.init(Cipher.ENCRYPT_MODE, secretKey, this.pbeParameterSpec);
            this.bos.write(salt);

            boolean response = FileProcess.process(this.cipher, this.bis, this.bos);
            if(response){
                return true;
            }else{
                return false;
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public boolean decrypt(String src, String des) {
        this.file = new File(src);
        if (!this.file.isFile()) return false;

        try {
            this.bis = new BufferedInputStream(new FileInputStream(this.file));
            this.bos = new BufferedOutputStream(new FileOutputStream(new File(des)));

            this.pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory
                    .getInstance(this.algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(this.pbeKeySpec);

            byte[] salt = new byte[8];
            this.bis.read(salt);

            this.pbeParameterSpec = new PBEParameterSpec(salt, 100);

            this.cipher = Cipher.getInstance(this.algorithm);
            this.cipher.init(Cipher.DECRYPT_MODE, secretKey, this.pbeParameterSpec);

            boolean response = FileProcess.process(this.cipher, this.bis, this.bos);
            if(response){
                return true;
            }else{
                return false;
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
