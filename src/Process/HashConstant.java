package Process;

public class HashConstant {
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512_224 = "SHA-512/224";
    public static final String SHA_512_256 = "SHA-512/256";

    public static final String TEXT = "Text";
    public static final String FILE = "File";
    private static String[] typeGroup;
    private static String[] algorithmGroup;


    public HashConstant() {
        typeGroup = new String[]{TEXT, FILE};
        algorithmGroup = new String[]{MD5, SHA_1, SHA_224, SHA_256, SHA_384, SHA_512_224, SHA_512_256};
    }

    public String[] getAlgorithmGroup() {
        return algorithmGroup;
    }

    public String[] getTypeGroup() {
        return typeGroup;
    }
}
