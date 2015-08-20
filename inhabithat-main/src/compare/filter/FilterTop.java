package compare.filter;

import inhabithat.base.Locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterTop {
	public static List<Locale> allLocales;

	static{
		loadLocales();
	}

	private static void loadLocales() {
		// TODO at startup go to up to date database and load static list of all known locales. Use path and loc.readFile() for this.
	}
	public List<Locale> filter(List<AbstractFilter> filters){
		//Make copy of base list
		List<Locale> ret = new ArrayList<Locale>();
		for (Locale statLoc : allLocales){
			Locale loc = statLoc.copy();
			for (AbstractFilter filter : filters){
				//pass all filters to all locales
				loc.filter(filter);
			}
			loc.calcScore();
			ret.add(loc);
		}
		//Sort list and return.
		Collections.sort(ret);
		return ret;
	}
}
