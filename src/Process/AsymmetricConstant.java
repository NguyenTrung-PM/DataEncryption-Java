package Process;

public class AsymmetricConstant {
    public static final String ECB = "ECB";

    public static final String ENCRYPT = "Encrypt";
    public static final String DECRYPT = "Decrypt";

    public static final String PCKS1PADDING = "PKCS1Padding";
    public static final String OAEPWithSHA1 = "OAEPWithSHA-1AndMGF1Padding";
    public static final String OAEPWithSHA256 = "OAEPWithSHA-256AndMGF1Padding";

    public static final String RSA = "RSA";

    private static Integer[] keyRSA;

    private static String[] paddingGroup;
    private static String[] processGroup;

    public AsymmetricConstant() {
        keyRSA = new Integer[]{512, 1024, 2048, 3072, 4096};
        paddingGroup = new String[]{PCKS1PADDING, OAEPWithSHA1, OAEPWithSHA256};
        processGroup = new String[]{ENCRYPT, DECRYPT};
    }

    public static Integer[] getKeyRSA() {
        return keyRSA;
    }

    public static String[] getPaddingGroup() {
        return paddingGroup;
    }

    public static String[] getProcessGroup() {
        return processGroup;
    }

}
