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
		int[] missing = {177,184,199};
		//int[] missing = {0   ,2   ,5  ,11  ,15  ,24  ,26  ,28  ,40  ,41  ,54  ,69  ,79  ,87  ,88  ,99 ,100 ,110 ,134 ,135 ,138 ,139 ,142 ,148 ,155 ,159 ,166 ,168 ,177 ,179 ,183 ,184 ,185 ,186 ,192 ,199 ,202 ,210 ,211 ,213 ,214 ,215 ,220 ,228 ,229 ,232 ,236 ,242 ,245 ,246 ,248 ,251 ,259 ,264 ,265 ,266 ,271 ,277 ,284 ,286 ,288 ,289};
		for (int li: missing){
			Locale loc = locales.get(li);
			//complete missing fields
			loc = Locale.readFile(Locale.locFileName(loc.townName(), loc.stateName(), InhabithatConfig.getInstance().locDBPath));
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
