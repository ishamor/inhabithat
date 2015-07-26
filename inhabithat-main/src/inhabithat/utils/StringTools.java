package inhabithat.utils;

public class StringTools {
	public static StringBuilder capitalize(StringBuilder text) {
		if (text == null || text.length()==0) return null;
		StringBuilder ret = new StringBuilder(text);
		char ch = ret.charAt(0);
		ret.setCharAt(0, Character.toUpperCase(ch));
		return ret;
	}
	/**
	 * Returns a string composed of numRepeats repetitions of the char ch
	 */
	public static String charRepeated(int numRepeats, char ch){
		return new String(new char[numRepeats]).replace('\0', ch);
	}
}
