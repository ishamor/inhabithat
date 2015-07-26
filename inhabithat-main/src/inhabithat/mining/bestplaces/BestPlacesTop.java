package inhabithat.mining.bestplaces;

import inhabithat.base.Locale;
import inhabithat.base.DataFrame;
import inhabithat.utils.InhabithatConfig;

import java.util.ArrayList;
import java.util.List;

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
				String[] lat_lon = coords.split(" ");
				Locale loc = new Locale(city,state,lat_lon[0],lat_lon[1]);
				locales.add(loc);
				loc.writeFile();
			}
		}
	}

}
