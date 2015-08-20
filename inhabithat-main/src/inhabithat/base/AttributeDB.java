package inhabithat.base;

import inhabithat.base.AttributeDB.AttrType;
import inhabithat.base.AttributeDB.ScoreCalcType;

import java.util.ArrayList;
import java.util.Arrays;
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
		public static AttrType[] topAttributes = parseArrtibutes(new ArrayList<AttrType>(),0);
		private static int attri = 0;//running index for this method
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
	private static Set<AttrType> staticScore;
	private static Set<AttrType> biggerIsBetter;
	static {
		staticScore = new HashSet<AttrType>(Arrays.asList(AttrType.COMFORT_IDX, AttrType.HEALTH_COST, AttrType.PROPERTY_CRIME, 
				AttrType.VIOLENT_CRIME, AttrType.PUPIL_TEACHER_RATIO, AttrType.COMMUTE_TIME,AttrType.DR_PER_100K, AttrType.WATER_QLTY));
		biggerIsBetter = new HashSet<AttrType>(Arrays.asList(AttrType.AIR_QLTY, AttrType.INCOME_TAX, AttrType.SALES_TAX, AttrType.UNEMPLOYMENT_RATE));
	}


	//--Set filtering info for all attributes.
	public enum ScoreCalcType {
		STATIC,//attributes whose score can be derived from the data alone, for example COMFORT_IDX;
		COMPARATIVE//attribute whose score calculation requires some input from the user in the form of thresholds. For example MEDIAN_HOME_COST
	}

	public static ScoreCalcType scoreType(AttrType type) {
		if (staticScore.contains(type)) return ScoreCalcType.STATIC;
		else return ScoreCalcType.COMPARATIVE;
	}
	/**
	 * True for attributes for which a bigger score is better like COMFORT_IDX and not like PROPERTY_CRIME
	 */
	public static boolean biggerIsBetter(AttrType type) {
		return biggerIsBetter.contains(type);
	}
	private static Map<AttrType,Double> maxValues;
	private static Map<AttrType,Double> minValues;
	static{
		//TODO Go over all DB and create a file with a table. Each attribute is a column with the name on top and a list of all values at bottom. First column will be
		//city names and then a colums with state names.
		//Try and load this into R dataframe and check out histograms of several attributes. 
		//Create another file with just the min/max values for all attributes. 
		//Read this file here on startup and fill in the min/max values map.
	}
	/**
	 * Returns the minimal value of this attribute in our database
	 */
	public static double minValue(AttrType type) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Returns the minimal value of this attribute in our database
	 */
	public static double maxValue(AttrType type) {
		// TODO Auto-generated method stub
		return 0;
	};



}
