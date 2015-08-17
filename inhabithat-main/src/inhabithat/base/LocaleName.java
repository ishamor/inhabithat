package inhabithat.base;

import inhabithat.utils.ListTools;
import inhabithat.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * class includes a string with locale name, like Los Angeles and supplies all manners of formatting and substitute names for this locale
 * @author ishamor
 *
 */
public class LocaleName {
	public enum NameFormat {
		Lower_,//los_angeles, boston
		Lower,//los angeles, boston
		Capital//Los Angeles, Boston
	}
	String name;
	public LocaleName(String name){
		this.name = format(new String(name),NameFormat.Capital);
	}
	/**
	 * Copy constructor
	 */
	public LocaleName(LocaleName copyMe) {
		this(copyMe.name);
	}

	public String formatAs(NameFormat format){
		return format(name,format);
	}
	
	public static String format(String str,NameFormat format) {
		//turn to lowerCase, replace _ with space and continue from there
		String ret = new String(str);
		ret = ret.toLowerCase();
		ret = ret.replaceAll("_", " ");
		String[] parts = ret.split(" ");
		for (int pi=0;pi< parts.length;++pi){
			StringBuilder part = new StringBuilder(parts[pi]);
			parts[pi]= StringTools.capitalize(part).toString();
		}
		ret = ListTools.concat(ListTools.asList(parts), " ");
		if (format==NameFormat.Capital) return ret;
		else if (format == NameFormat.Lower) return ret.toLowerCase();
		else if (format == NameFormat.Lower_) {
			ret = ret.replaceAll(" ", "_");
			return ret.toLowerCase();
		}
		return null;
	}

	public String toString(){
		return name;
	}
	
	public static void main(String[] args){
		List<String> regression = new ArrayList<String>();
		regression.add("new_york");
		regression.add("newyork");
		regression.add("New York");
		for (String name : regression){
			System.out.println(LocaleName.format(name, NameFormat.Capital));
			System.out.println(LocaleName.format(name, NameFormat.Lower));
			System.out.println(LocaleName.format(name, NameFormat.Lower_));
		}
	}
}
