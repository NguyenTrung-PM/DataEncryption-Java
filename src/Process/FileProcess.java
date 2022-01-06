package Process;

import javax.crypto.Cipher;
import java.io.*;

public class FileProcess {
    public static boolean process(Cipher cipher, BufferedInputStream bis, BufferedOutputStream bos) {
        try {
            byte buffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) bos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null) bos.write(output);

            bis.close();
            bos.flush();
            bos.close();

            return true;
        } catch (Exception exception) {
            System.out.println("FileProcess process: " + exception.getMessage());
        }
        return false;
    }
}
