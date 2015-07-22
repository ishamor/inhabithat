package inhabithat.mining.bestplaces;

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
	private List<AbstractLocale> locales;
	public BestPlacesTop(){
		initLocales();
		mine();
	}
	private void mine() {
		for (AbstractLocale loc : locales){
			BestplacesScrub.mine(loc);
		}
		
	}
	/**
	 * Gather state, city info and init the list
	 */
	private void initLocales() {
		locales = new ArrayList<AbstractLocale>();
		String rpath = InhabithatConfig.getInstance().citiesPath+"/wiki_above_100K_df.txt";
		DataFrame df = new DataFrame(rpath);
		if (df.isValid()){
			for (int ri=1;ri<df.numRows();++ri){
				String city = df.getData(ri,"City");
				String state = df.getData(ri,"State");
				String coords = df.getData(ri,"Location");
				CityLocale loc = new CityLocale(city,state,coords);
				locales.add(loc);
			}
		}
	}

}
