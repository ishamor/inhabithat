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
		//int[] missing = {14,26,114,141,226,276,292};
		int[] missing = {253};
		for (int li: missing){
			Locale loc = locales.get(li);
			BestplacesScrub.mineAll(loc);
			loc.writeFile();
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
			for (int ri=0;ri<df.numRows();++ri){
				String city = df.getData(ri,"City");
				String state = df.getData(ri,"State");

				//--Correct some special cases

				if (city.equals("Augusta"))
					city = "augusta-richmond_county";
				else if(city.equals("Boise"))
					city = "boise_city";
				else if(city.equals("Lexington"))
					city = "lexington-fayette";
				else if(city.equals("Saint Paul"))
					city = "st._paul";
				else if(city.equals("Ventura"))
					city = "ventura_(san_buenaventura)";
				else if(city.equals("Winston–Salem"))
					city = "winston-salem";
				else if(city.equals("Honolulu"))
					state = "hawaii";			

				String coords = df.getData(ri,"Location");
				String[] lat_lon = coords.split(" ");
				Locale loc = new Locale(city,state,lat_lon[0],lat_lon[1]);
				locales.add(loc);
			}
		}
		Locale.sortByName(locales);
	}
	public static void main(String[] args){
		new BestPlacesTop();
	}
}
