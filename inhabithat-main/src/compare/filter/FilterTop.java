package compare.filter;

import inhabithat.base.AttributeDB;
import inhabithat.base.Locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterTop {
	public static List<Locale> allLocales;

	static{
		allLocales = AttributeDB.loadDB();
	}

	public static List<Locale> filter(List<AbstractFilter> filters){
		//Make copy of base list
		List<Locale> ret = new ArrayList<Locale>();
		long t0 = System.currentTimeMillis();
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
		long t1 = System.currentTimeMillis();
		System.out.println("Copy, score and sort time: "+(t1-t0)+"\n");
		return ret;
	}
	public static void main(String[] args){
		List<AbstractFilter> filters = new ArrayList<AbstractFilter>();
		List<Locale> sorted = filter(filters);
		AttributeDB.createSummaryFiles2(sorted);
		
		for (int li=0; li<50;++li){
			System.out.println(sorted.get(li));
		}
	}
}
