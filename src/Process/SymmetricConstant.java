package Process;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.ISO;

public class SymmetricConstant {
	public static final String ENCRYPT = "Encrypt";
	public static final String DECRYPT = "Decrypt";

	public static final String DES = "DES";
	public static final String DESEDE = "DESede";
	public static final String AES = "AES";
	public static final String ARIA = "ARIA";
	public static final String RC2 = "RC2";
	public static final String RC5 = "RC5";
	public static final String RC6 = "RC6";
	public static final String SERPENT = "Serpent";
	public static final String BLOWFISH = "Blowfish";
	public static final String TWOFISH = "Twofish";
	public static final String CAMELLIA = "Camellia";
	public static final String IDEA = "IDEA";
	public static final String SEED = "SEED";

	public static final String CBC = "CBC";
	public static final String CFB = "CFB";
	public static final String OFB = "OFB";
	public static final String CTR = "CTR";

	public static final String NOPADDING = "NoPadding";
	public static final String PKCS5PADDING = "PKCS5Padding";
	public static final String PKCS7PADDING = "PKCS7Padding";
	public static final String ISO10126PADDING = "ISO10126Padding";
	public static final String ISO7816_4PADDING = "ISO7816-4Padding";


	public static final String TEXT = "Text";
	public static final String FILE = "File";

	private static String[] typeGroup;

	private static Integer[] keyDES;
	private static Integer[] keyDESede;
	private static Integer[] keyAES;
	private static Integer[] keyARIA;
	private static Integer[] keyRC2;
	private static Integer[] keyRC5;
	private static Integer[] keyRC6;
	private static Integer[] keySerpent;
	private static Integer[] keyBlowfish;
	private static Integer[] keyTwofish;
	private static Integer[] keyCamellia;
	private static Integer[] keyIDEA;
	private static Integer[] keySEED;

	private static String[] algorithmGroup;
	private static String[] modeGroup;
	private static String[] paddingDefault;
	private static Map<String, Integer[]> keySizeGroup = new HashMap<>();
	private static Map<String, String[]> paddingGroup = new HashMap<>();
	private static String[] processGroup;

	public SymmetricConstant() {
		typeGroup = new String[] { TEXT, FILE };
		algorithmGroup = new String[] { DES, DESEDE, AES, RC2, RC5, RC6, SERPENT, BLOWFISH, TWOFISH, CAMELLIA, IDEA,
				SEED };
		modeGroup = new String[] { CBC, CFB, OFB, CTR };
		paddingDefault = new String[] { PKCS5PADDING, PKCS7PADDING,ISO7816_4PADDING, ISO10126PADDING };
		processGroup = new String[] { ENCRYPT, DECRYPT };

		keyDES = new Integer[] { 56 };
		keyDESede = new Integer[] { 112, 168 };
		keyAES = new Integer[] { 128, 192, 256 };
		keyARIA = new Integer[] { 128, 192, 256 };
		keyRC2 = new Integer[] { 64, 128, 256, 512, 1024 };
		keyRC5 = new Integer[] { 128, 192, 256, 512, 1024 };
		keyRC6 = new Integer[] { 128, 192, 256, 512, 1024 };
		keySerpent = new Integer[] { 128, 192, 256 };
		keyBlowfish = new Integer[] { 32, 64, 128, 256, 448 };
		keyTwofish = new Integer[] { 128, 192, 256 };
		keyCamellia = new Integer[] { 128, 192, 256 };
		keyIDEA = new Integer[] { 128 };
		keySEED = new Integer[] { 128 };

		keySizeGroup.put(DES, keyDES);
		keySizeGroup.put(DESEDE, keyDESede);
		keySizeGroup.put(AES, keyAES);
		keySizeGroup.put(ARIA, keyARIA);
		keySizeGroup.put(RC2, keyRC2);
		keySizeGroup.put(RC5, keyRC5);
		keySizeGroup.put(RC6, keyRC6);
		keySizeGroup.put(SERPENT, keySerpent);
		keySizeGroup.put(BLOWFISH, keyBlowfish);
		keySizeGroup.put(TWOFISH, keyTwofish);
		keySizeGroup.put(CAMELLIA, keyCamellia);
		keySizeGroup.put(IDEA, keyIDEA);
		keySizeGroup.put(SEED, keySEED);

		paddingGroup.put(CBC, paddingDefault);
		paddingGroup.put(CFB, paddingDefault);
		paddingGroup.put(OFB, paddingDefault);
		paddingGroup.put(CTR, new String[] { NOPADDING });
	}

	public static Integer[] getKeyDES() {
		return keyDES;
	}

	public static Integer[] getKeyRC2() {
		return keyRC2;
	}

	public static Integer[] getKeyAES() {
		return keyAES;
	}

	public static Integer[] getKeyDESede() {
		return keyDESede;
	}

	public static Integer[] getKeyBlowfish() {
		return keyBlowfish;
	}

	public static String[] getAlgorithmGroup() {
		return algorithmGroup;
	}

	public static String[] getModeGroup() {
		return modeGroup;
	}

	public static Map<String, Integer[]> getKeySizeGroup() {
		return keySizeGroup;
	}

	public static String[] getProcessGroup() {
		return processGroup;
	}

	public String[] getTypeGroup() {
		return typeGroup;
	}

	public static String[] getpaddingDefault() {
		return paddingDefault;
	}

	public static Map<String, String[]> getPaddingGroup() {
		return paddingGroup;
	}
}