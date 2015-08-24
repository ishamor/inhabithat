package inhabithat.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ListTools {
	static public <T> List<T> unique(List<T> list) {
		return new ArrayList<T>(new HashSet<T>(list));
	}
	/**
	 * create a list from objects of the same kind
	 */
	public static <T> List<T> asList(T ...objects) {
		List<T> list = new ArrayList<T>();
		for (T t : objects) {
			list.add(t);
		}
		return list;
	}

	/**
	 * Convert from list of Object to list of String using the formula toString() with separator sep
	 */
	public static <T> List<String> listToString(List<T> list) {
		if (list==null) return null;
		List<String> ret = new ArrayList<String>();
		for (Object el : list) ret.add(el.toString());
		return ret;
	}
	/**
	 * concatenate objects list into one string with separator sep
	 * @return
	 */
	public static <T> String concat(List<T> objs,String sep){
		String ret = "";
		for (int oi=0;oi<objs.size();++oi){
			ret+=objs.get(oi).toString();
			if (oi<objs.size()-1)
				ret +=sep;
		}
		return ret;
	}
	/**
	 * Concatenate two arrays
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
