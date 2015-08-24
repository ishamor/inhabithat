package inhabithat.base;

import inhabithat.base.AttributeDB.AttrType;
import inhabithat.base.AttributeDB.ScoreCalcType;
import inhabithat.base.LocaleName.NameFormat;
import inhabithat.utils.InhabithatConfig;
import inhabithat.utils.ListTools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttributeDB {

	public enum AttrType {
		CLIMATE(0),
		TEMPERATURES(1),
		TEMP_AVG_SMR_HIGH(2),
		TEMP_AVG_SMR_LOW(2),
		TEMP_AVG_WTR_HIGH(2),
		TEMP_AVG_WTR_LOW(2),
		PRECIPITATION(1),
		SNOWFALL(2),
		RAINFALL(2),
		PRECIP_DAYS(2),
		SUNSTATS(1),
		SUN_DAYS(2),
		UV_IDX(2),
		COMFORT_IDX(1),

		ECOLOGY(0),
		AIR_QLTY(1),
		WATER_QLTY(1),
		QOL(0),
		CRIME(1),
		VIOLENT_CRIME(2),
		PROPERTY_CRIME(2),
		PUPIL_TEACHER_RATIO(1),
		COMMUTE_TIME(1),
		DR_PER_100K(1),

		ECONOMY(0),
		COST_OF_LIVING(1),
		COL_OVERALL(2),
		HEALTH_COST(2),
		TAX(1),
		INCOME_TAX(2),
		SALES_TAX(2),
		PROPERTY_TAX(2),
		UNEMPLOYMENT_RATE(1),
		INCOME_PER_CAPITA(1),
		MEDIAN_HOME_COST(1),


		DEMOGRAPHIC(0),
		RACE(1),
		WHITE(2),
		BLACK(2),
		ASIAN(2),
		HISPANIC(2),
		RELIGION(1),
		RELIGIOUS(2),
		CATHOLIC(2),
		LDS(2),
		BAPTIST(2),
		EPISCOPALIAN(2),
		PENTECOSTAL(2),
		LUTHERAN(2),
		METHODIST(2),
		PRESBYTERIAN(2),
		OTHER_CHRISTIAN(2),
		JEWISH(2),
		EASTERN(2),
		ISLAM(2),
		VOTING(1),
		DEMOCRAT(2),
		REPUBLICAN(2),
		INDEPENDENT(2),
		POPULATION(1),
		POPULATION_DENSITY(1),

		LOCATION(0),
		ELEVATION(1),
		BY_LAKE(1),
		BY_OCEAN(1);

		AttrType(int depth){
			this.depth = depth;
		}

		public int depth; //used for indexing attribute lists within groups
		public int idx;//index to array of attributes at it's level
		public AttrType[] path;//path from root to this attribute
		public AttrType[] children;//if attribute if a group, these are the childern, else empty.
		public static int len = AttrType.values().length;

		public boolean isGroup(){
			return children!= null;
		}

		public static List<AttrType> botAttributes = new ArrayList<AttrType>();
		public static int numBotAttributes;//number of low level attributes carrying data
		public static AttrType[] topAttributes;
		private static int attri = 0;//running index for this method
		static {
			topAttributes = parseArrtibutes(new ArrayList<AttrType>(),0);
			numBotAttributes = botAttributes.size();
		}
		/**
		 * Method converts the enum AttrType to a tree structure under Locale. Each AttrType has a depth related to it.
		 * AttrType appear in order of relation - that is, an attribute with larger depth after an attribute with lesser depth is a sub-attribute.
		 * For example, CLIMATE(0) is followed by TEMPERATURES(1), followed by TEMP_AVG_SMR_HIGH(2). This means that CLIMATE is a group of attributes,
		 * the first in that group is TEMPERATURES, which is also a group of which TEMP_AVG_SMR_HIGH is the first.
		 * Each attribute type is given its path down the tree in array 'path' and a list of children in 'children'.
		 * For example, the path of TEMP_AVG_SMR_HIGH wold be {CLIMATE,TEMPERATURES} and the 
		 * children of TEMPERATURES would be {TEMP_AVG_SMR_HIGH(2),	TEMP_AVG_SMR_LOW(2),TEMP_AVG_WTR_HIGH(2),TEMP_AVG_WTR_LOW(2)}
		 */
		private static AttrType[] parseArrtibutes(List<AttrType> path,int depth){
			int arrayIdx = 0;
			List<AttrType> children = new ArrayList<AttrType>();
			while(attri<AttrType.len){
				AttrType currAttr = AttrType.values()[attri];
				int currIdx = attri;
				if (currAttr.depth==depth){
					currAttr.path = path.toArray(new AttrType[path.size()]);
					currAttr.idx = arrayIdx++;
					children.add(currAttr);
					++attri;
					if (currIdx+1<AttrType.len && AttrType.values()[currIdx+1].depth>depth){
						List<AttrType> recPath = new ArrayList<AttrType>(path);
						recPath.add(currAttr);
						currAttr.children = parseArrtibutes(recPath,depth+1);
					}
					else{
						botAttributes.add(currAttr);
					}
				}
				else if(AttrType.values()[attri].depth<depth){
					break;
				}
			}
			return children.toArray(new AttrType[children.size()]);
		}
	}//End enum AttrType


	private static Set<AttrType> staticScoreAttributes;
	private static Set<AttrType> biggerIsBetter;
	static {
		staticScoreAttributes = new HashSet<AttrType>(Arrays.asList(AttrType.COMFORT_IDX, AttrType.HEALTH_COST, AttrType.PROPERTY_CRIME, 
				AttrType.VIOLENT_CRIME, AttrType.PUPIL_TEACHER_RATIO, AttrType.COMMUTE_TIME,AttrType.DR_PER_100K, AttrType.AIR_QLTY,AttrType.WATER_QLTY));
		biggerIsBetter = new HashSet<AttrType>(Arrays.asList(AttrType.AIR_QLTY, AttrType.WATER_QLTY, AttrType.COMFORT_IDX,
				AttrType.PUPIL_TEACHER_RATIO,AttrType.DR_PER_100K));
	}


	//--Set filtering info for all attributes.
	public enum ScoreCalcType {
		STATIC,//attributes whose score can be derived from the data alone, for example COMFORT_IDX;
		COMPARATIVE//attribute whose score calculation requires some input from the user in the form of thresholds. For example MEDIAN_HOME_COST
	}

	public static ScoreCalcType scoreType(AttrType type) {
		if (staticScoreAttributes.contains(type)) return ScoreCalcType.STATIC;
		else return ScoreCalcType.COMPARATIVE;
	}
	/**
	 * True for attributes for which a bigger score is better like COMFORT_IDX and not like PROPERTY_CRIME
	 */
	public static boolean biggerIsBetter(AttrType type) {
		return biggerIsBetter.contains(type);
	}
	private static Map<AttrType,Double> maxValues = new HashMap<AttrType,Double>();
	private static Map<AttrType,Double> minValues = new HashMap<AttrType,Double>();
	static{
		DataFrame minMaxdf = new DataFrame(InhabithatConfig.getInstance().locDBSummaryPath+"/minmax_df.txt");
		for (int ci=0 ;ci<minMaxdf.numCols();++ci){
			try{
				String attrName = minMaxdf.getTitles().get(ci);
				AttrType attr = AttrType.valueOf(attrName);
				double valMin = Double.valueOf(minMaxdf.getData(0, ci));
				double valMax = Double.valueOf(minMaxdf.getData(1, ci));
				minValues.put(attr, valMin);
				maxValues.put(attr, valMax);
			}
			catch(Exception e){

			}
		}
		//Create a map from AttrType to min and max values.

	}
	/**
	 * Returns the minimal value of this attribute in our database
	 */
	public static double minValue(AttrType type) {
		return minValues.get(type);
	}
	/**
	 * Returns the minimal value of this attribute in our database
	 */
	public static double maxValue(AttrType type) {
		return maxValues.get(type);
	};

	/**
	 * Read current database, create a dataframe with all attribute values and create a min-max file
	 */
	public static void createSummaryFiles(){
		String summaryFile = InhabithatConfig.getInstance().locDBSummaryPath+"/summary_df.txt";
		String minmaxFile = InhabithatConfig.getInstance().locDBSummaryPath+"/minmax_df.txt";
		DataFrame df = new DataFrame();
		List<String> titles = new ArrayList<String>();
		titles.add("city"); titles.add("state");
		titles.addAll(ListTools.listToString(AttrType.botAttributes));
		df.setTitles(titles);
		List<Locale> locales = loadDB();
		for (Locale loc : locales) {
			List<String> data = new ArrayList<String>();
			data.add(loc.name.formatAs(NameFormat.Lower_));
			data.add(loc.state.formatAs(NameFormat.Lower_));
			for (AttrType attr : AttrType.botAttributes){
				data.add((String.valueOf(loc.getAttribute(attr).data)));
			}
			df.addDataRow(data);
		}
		df.write(summaryFile,false);
		//df = new DataFrame(summaryFile);
		df.writeMinMax(minmaxFile,false);

	}
	/**
	 * Like the first only add a Score column and get locales in input
	 */
	public static void createSummaryFiles2(List<Locale> locales){
		String summaryFile = InhabithatConfig.getInstance().locDBSummaryPath+"/summary_score_df.txt";
		//String minmaxFile = InhabithatConfig.getInstance().locDBSummaryPath+"/minmax_df.txt";
		DataFrame df = new DataFrame();
		List<String> titles = new ArrayList<String>();
		titles.add("city"); titles.add("state"); titles.add("score");
		titles.addAll(ListTools.listToString(AttrType.botAttributes));
		df.setTitles(titles);
		//List<Locale> locales = loadDB();
		for (Locale loc : locales) {
			List<String> data = new ArrayList<String>();
			data.add(loc.name.formatAs(NameFormat.Lower_));
			data.add(loc.state.formatAs(NameFormat.Lower_));
			data.add(loc.getScore().toString());
			for (AttrType attr : AttrType.botAttributes){
				data.add((String.valueOf(loc.getAttribute(attr).data)));
			}
			df.addDataRow(data);
		}
		df.write(summaryFile,false);
		//df = new DataFrame(summaryFile);
		//df.writeMinMax(minmaxFile,false);

	}
	/**
	 * Summary of all static attributes, each with score, data and min-max values
	 */
	public static void createSummaryFiles3(List<Locale> locales){
		String summaryFile = InhabithatConfig.getInstance().locDBSummaryPath+"/summary_static_df.txt";
		DataFrame df = new DataFrame();
		List<String> titles = new ArrayList<String>();
		titles.add("city"); titles.add("state"); titles.add("score");
		titles.addAll(ListTools.listToString(Arrays.asList(staticScoreAttributes.toArray())));
		df.setTitles(titles);
		//List<Locale> locales = loadDB();
		for (Locale loc : locales) {
			for (int si=0;si<4;si++){
				List<String> data = new ArrayList<String>();
				data.add(loc.name.formatAs(NameFormat.Lower_));
				data.add(loc.state.formatAs(NameFormat.Lower_));
				data.add(loc.getScore().toString());
				for (AttrType attr : staticScoreAttributes){
					if (si==0)
						data.add((String.valueOf(loc.getAttribute(attr).data)));
					else if(si==1)
						data.add((String.valueOf(minValue(attr))));
					else if(si==2)
						data.add((String.valueOf(maxValue(attr))));
					else if(si==3)
						data.add((String.valueOf(loc.getAttribute(attr).score)));
						
				}
				df.addDataRow(data);
			}
		}
		df.write(summaryFile,false);
		//df = new DataFrame(summaryFile);
		//df.writeMinMax(minmaxFile,false);

	}
	public static List<Locale> loadDB(){
		File dir = new File(InhabithatConfig.getInstance().locDBPath);
		File[] locFiles = dir.listFiles();
		if (locFiles == null) return null;
		List<Locale> ret = new ArrayList<Locale>();
		for (File locFile : locFiles) {
			Locale loc =Locale.readFile(locFile.getAbsolutePath());
			ret.add(loc);
		}
		return ret;
	}
	public static void main(String[] args){
		AttributeDB.createSummaryFiles();
	}
}
