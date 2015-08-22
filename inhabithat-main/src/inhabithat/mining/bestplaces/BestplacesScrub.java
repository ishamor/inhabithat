package inhabithat.mining.bestplaces;
import inhabithat.base.AttributeDB.AttrType;
import inhabithat.base.Attribute;
import inhabithat.base.Locale;
import inhabithat.base.LocaleName;
import inhabithat.base.LocaleName.NameFormat;
import inhabithat.utils.InhabithatConfig;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

//Subclasses: http://www.bestplaces.net/economy/city/california/los_angeles
//overview, people, health, ecomony, housing, rankings, climate, crime, education, comments, transportation, cost_of_living, religion, voting. 
public class BestplacesScrub {
	/**
	 * Scrub data from http://www.bestplaces.net/
	 * @throws Exception 
	 */
	private static String urlBase = "http://www.bestplaces.net/";
	private static String decRegex = "\\d+\\.?\\d*";
	private static Long sleepTimeMS = 10000L;//15 seconds
	public static void main(String[] args) throws Exception {
		try {
			//BestplacesScrub.mineClimate("california", "los_angeles");
			//BestplacesScrub.minePeople("california", "los_angeles");
			//BestplacesScrub.mineHealth("california", "los_angeles");
			//BestplacesScrub.mineEconomy("california", "los_angeles");
			//BestplacesScrub.mineHousing("california", "los_angeles");
			//BestplacesScrub.mineCrime("california", "los_angeles");
			//BestplacesScrub.mineEducation("california", "los_angeles");
			//BestplacesScrub.mineTransportation("california", "los_angeles");
			//BestplacesScrub.mineCost("california", "los_angeles");
			//BestplacesScrub.mineReligion("california", "los_angeles");
			//BestplacesScrub.mineVoting("california", "los_angeles");
			printPage("http://www.bestplaces.net/climate/city/texas/el_paso");
			//testMatch();

		}
		catch (Exception err){
			System.out.println(err.getStackTrace());
		}
		//testMatch();

	}
	public static void mineAll(Locale loc){
		String city = loc.name(NameFormat.Lower_);//This is a format enum of LocaleName
		String state = LocaleName.format(loc.stateName(),NameFormat.Lower_);
		try{
			System.out.println();
			minePeople(state, city,loc);
			Thread.sleep(sleepTimeMS);
//			mineClimate(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineCost(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineCrime(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineEconomy(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineEducation(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineHealth(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineHousing(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineReligion(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineTransportation(state, city,loc);
//			Thread.sleep(sleepTimeMS);
//			mineVoting(state, city,loc);
//			Thread.sleep(sleepTimeMS);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	public static void mineVoting(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.DEMOCRAT,AttrType.REPUBLICAN,AttrType.INDEPENDENT));
		//		-- All numbers are percentages
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Democrat<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Republican<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Independent Other<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		
		minePrint("voting",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineReligion(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.RELIGIOUS,AttrType.CATHOLIC,AttrType.LDS,AttrType.BAPTIST,AttrType.EPISCOPALIAN,
				AttrType.PENTECOSTAL,AttrType.LUTHERAN,	AttrType.METHODIST,AttrType.PRESBYTERIAN,
				AttrType.OTHER_CHRISTIAN,AttrType.JEWISH,AttrType.EASTERN,AttrType.ISLAM));
		//		-- All numbers are percentages
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Percent Religious<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Catholic<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">LDS<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Baptist<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Episcopalian<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Pentecostal<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Lutheran<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Methodist<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Presbyterian<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Other Christian<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Jewish<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Eastern<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Islam<.*?>("+decRegex+")%",Pattern.CANON_EQ));
		
		minePrint("religion",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineCost(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.COL_OVERALL)); 
		//		-- Crime is number from 1 to 100, with 1 being the lowest crime rate.
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Overall<.*?>("+decRegex+")<",Pattern.CANON_EQ));//100 is nation average
		minePrint("cost_of_living",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineTransportation(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.COMMUTE_TIME));
		//		-- Crime is number from 1 to 100, with 1 being the lowest crime rate.
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Commute Time<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		minePrint("transportation",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineEducation(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.PUPIL_TEACHER_RATIO));
		//		-- Crime is number from 1 to 100, with 1 being the lowest crime rate.
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Pupil/Teacher Ratio<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		minePrint("education",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineCrime(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.VIOLENT_CRIME,AttrType.PROPERTY_CRIME));
		//		-- Crime is number from 1 to 100, with 1 being the lowest crime rate.
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Violent Crime<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Property Crime<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		minePrint("crime",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineHousing(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.MEDIAN_HOME_COST, AttrType.PROPERTY_TAX));

		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Median Home Cost<.*?>\\$(\\d{1,3}\\,?\\d{0,3}\\,?\\d{0,3})<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Property Tax Rate<.*?>\\$("+decRegex+")<",Pattern.CANON_EQ));
		minePrint("housing",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineEconomy(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.UNEMPLOYMENT_RATE, AttrType.SALES_TAX,
				AttrType.INCOME_TAX,AttrType.INCOME_PER_CAPITA));

		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		//patterns.add(Pattern.compile(">Unemployment Rate<.*?>("+decRegex+")%",Pattern.CANON_EQ));//The unemployment rate is expressed as a percentage of the available work force that is not employed
		patterns.add(Pattern.compile(">Unemployment Rate<.*?>("+decRegex+")%",Pattern.CANON_EQ));//The unemployment rate is expressed as a percentage of the available work force that is not employed
		patterns.add(Pattern.compile(">Sales Taxes<.*?>("+decRegex+")%",Pattern.CANON_EQ));//The total of all sales taxes for an area, including state, county and local taxes
		patterns.add(Pattern.compile(">Income Taxes<.*?>("+decRegex+")%",Pattern.CANON_EQ));//The total of all income taxes for an area, including state, county and local taxes.  Federal income taxes are not included.
		patterns.add(Pattern.compile(">Income per Cap\\.<.*?>\\$(\\d{1,3}\\,?\\d{0,3})<",Pattern.CANON_EQ));//HealthCost 100 is national average, 110 is 10% more expansive
		minePrint("economy",state,city,patterns,attrValues,attrNames,loc);
	}

	public static void mineHealth(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.AIR_QLTY, AttrType.WATER_QLTY,AttrType.DR_PER_100K, AttrType.HEALTH_COST));

		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Air Quality \\(100=best\\)<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Water Quality \\(100=best\\)<.*?>("+decRegex+")<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Physicians per Cap\\.<.*?>("+decRegex+")<",Pattern.CANON_EQ));//Dr. per 100K residents
		patterns.add(Pattern.compile(">Health Cost<.*?>("+decRegex+")<",Pattern.CANON_EQ));//HealthCost 100 is national average, 110 is 10% more expansive

		minePrint("health",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void minePeople(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.POPULATION,AttrType.POPULATION_DENSITY,
				AttrType.WHITE, AttrType.BLACK,AttrType.ASIAN, AttrType.HISPANIC));
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		patterns.add(Pattern.compile(">Population<.*?>(\\d{1,2}\\,?\\d{0,3}\\,?\\d{0,3})<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Pop. Density<.*?>(\\d{1,3}(\\,\\d{0,3})?)<",Pattern.CANON_EQ));//people per sq. mile
		patterns.add(Pattern.compile("White<.*?(\\d+\\.?\\d*)%",Pattern.CANON_EQ));//percentages
		patterns.add(Pattern.compile("Black<.*?(\\d+\\.?\\d*)%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Asian<.*?(\\d+\\.?\\d*)%",Pattern.CANON_EQ));
		patterns.add(Pattern.compile(">Hispanic<.*?(\\d+\\.?\\d*)%",Pattern.CANON_EQ));

		minePrint("people",state,city,patterns,attrValues,attrNames,loc);
	}
	public static void mineClimate(String state, String city, Locale loc) throws Exception{

		List<Pattern> patterns = new ArrayList<Pattern>();
		List<AttrType> attrNames = new ArrayList<AttrType>(Arrays.asList(AttrType.RAINFALL, AttrType.SNOWFALL, AttrType.PRECIP_DAYS, AttrType.SUN_DAYS,
				AttrType.TEMP_AVG_SMR_HIGH, AttrType.TEMP_AVG_WTR_LOW, AttrType.COMFORT_IDX, AttrType.UV_IDX, AttrType.ELEVATION));
		List<String> attrValues = new ArrayList<String>(Arrays.asList(new String[attrNames.size()]));
		//--Prepare patterns for climate data
		//Rainfall looks like this: Rainfall (in.)</a></td><td>18.1
		patterns.add(Pattern.compile("Rainfall.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Snowfall.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Precipitation Days.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Sunny Days.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("July High.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Jan. Low.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Comfort Index \\(higher=better\\).*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("UV Index\\S.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		patterns.add(Pattern.compile("Elevation ft.*?>(\\d{1,3}(\\,\\d{0,3})?)<",Pattern.CANON_EQ));

		minePrint("climate",state,city,patterns,attrValues,attrNames,loc);

	}

	private static void printAttr(List<String> attrNames,List<String> attrValues) {
		for (int ai=0;ai<attrNames.size();++ai){
			System.out.println(attrNames.get(ai)+"\t"+attrValues.get(ai));
		}
	}
	/**
	 * Given a url and a set of patters, read all lines and keep all groups found in patterns in the attrValues list. 
	 */
	private static void mineWeb(String url, List<Pattern> patterns,List<String> attrValues) throws Exception {
		URL oracle = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
		String inputLine;
		int lineCnt=0;
		while ((inputLine = in.readLine()) != null){
			for (int pi=0;pi<patterns.size();++pi){
				Matcher match = patterns.get(pi).matcher(inputLine);
				if (match.find()){
					attrValues.set(pi, match.group(1));
				}
			}
			++lineCnt;
		}
		in.close();
	}
	/**
	 * after mining, attrValues and attrNames are of the same length, one holding the type and the other the value. Enter them into locale.
	 */
	private static void minePrint(String pageName, String state, String city,List<Pattern> patterns, 
			List<String> attrValues, List<AttrType> attrTypes, Locale loc) throws Exception {
		System.out.println("Mining "+pageName+" for "+city+", "+state);
		mineWeb(urlBase+pageName+"/city/"+state+"/"+city,patterns,attrValues);
		for (int ai=0;ai<attrValues.size();++ai){
			Attribute attr = new Attribute(attrTypes.get(ai),attrValues.get(ai));
			loc.addAttribute(attr);
		}
		//printAttr(attrNames,attrValues);

	}
	private static void printPage(String url) throws Exception{
		URL oracle = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
		String inputLine;
		while ((inputLine = in.readLine()) != null){
			System.out.println(inputLine);
		}
		in.close();
	}
	private static void testMatch(){
		//Pattern.compile(">Unemployment Rate<.*?>("+decRegex+")%",Pattern.CANON_EQ)
		//Pattern rainPtrn = Pattern.compile(">Unemployment Rate</a></td><td>(\\d+\\.?\\d*)%",Pattern.CANON_EQ);
		//patterns.add(Pattern.compile("Snowfall.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ));
		Pattern rainPtrn = Pattern.compile("Rainfall.*?>(\\d+\\.?\\d*)<",Pattern.CANON_EQ);
		Matcher rainMtch = rainPtrn.matcher("");
		String rpath = InhabithatConfig.getInstance().rawPath+"/Springer_Winston_climate_raw.txt";
		//String rpath = "http://www.bestplaces.net/people/city/california/los_angeles";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rpath), "UTF-8"));
			//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname,true),"UTF-8"));
			String query;
			String rainfall;
			int lineCnt = 1;
			while ((query = reader.readLine()) != null){
				rainMtch.reset(query);
				if (rainMtch.find()){
					rainfall = rainMtch.group(1);
					System.out.println(rainfall);
				} 
				++lineCnt;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

