package Process;

public class PBEConstant {
    public static final String ENCRYPT = "Encrypt";
    public static final String DECRYPT = "Decrypt";

    public static final String PBEWIDTHMD5AND3DES = "PBEWithMD5AndTripleDES";
    public static final String PBEWITHSHA1ANDDESEDE = "PBEwithSHA1AndDESede";
    public static final String PBEWITHSHA1ANDRC2_128 = "PBEwithSHA1AndRC2_128";
    public static final String PBEWITHSHA1ANDRC4_40 = "PBEwithSHA1AndRC4_40";
    public static final String PBEWITHSHA1ANDRC4_128 = "PBEwithSHA1AndRC4_128";

    private static String[] processGroup;
    private static String[] algorithmGroup;

    public PBEConstant() {
        processGroup = new String[]{ENCRYPT, DECRYPT};
        algorithmGroup = new String[]{PBEWIDTHMD5AND3DES, PBEWITHSHA1ANDDESEDE, PBEWITHSHA1ANDRC2_128, PBEWITHSHA1ANDRC4_40, PBEWITHSHA1ANDRC4_128};
    }

    public static String[] getProcessGroup() {
        return processGroup;
    }

    public static String[] getAlgorithmGroup() {
        return algorithmGroup;
    }
}
