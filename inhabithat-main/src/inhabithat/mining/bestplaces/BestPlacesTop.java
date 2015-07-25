package inhabithat.mining.bestplaces;

import inhabithat.base.Locale;
import inhabithat.base.DataFrame;
import inhabithat.utils.InhabithatConfig;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Mine data from the http://www.bestplaces.net site
 * @author ishamor
 *
 */
public class BestPlacesTop {
	private List<Locale> locales;
	public BestPlacesTop(){
		initLocales();
		mine();
	}
	private void mine() {
		for (Locale loc : locales){
			BestplacesScrub.mineAll(loc);
		}
		
	}
	/**
	 * Gather state, city info and init the list
	 */
	private void initLocales() {
		locales = new ArrayList<Locale>();
		String rpath = InhabithatConfig.getInstance().citiesPath+"/wiki_above_100K_df.txt";
		DataFrame df = new DataFrame(rpath);
		if (df.isValid()){
			for (int ri=1;ri<df.numRows();++ri){
				String city = df.getData(ri,"City");
				String state = df.getData(ri,"State");
				String coords = df.getData(ri,"Location");
				Locale loc = new Locale(city,state,coords);
				locales.add(loc);
			}
		}
	}

}
