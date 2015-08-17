package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractAttribute {
	protected Double score;
	protected Double weight;
	protected AttrType type;
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
	};
	public int depth(){
		return type.depth;
	}

	public int index() {
		return type.idx;
	}
	
	abstract public double calcScore();
	abstract public void writeFile(BufferedWriter writer, int writeDepth)  throws IOException;

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	//TODO add a method which maps each attr type to a filtering type and add the field.
	//Add Filter object and fill it according to this type. Have defualt on/off and calc score on 'on' types.
	/**
	 * Changes the memory placement of the element
	 */
	public AbstractAttribute copy() {
		try {
			AbstractAttribute ret = (AbstractAttribute) super.clone();
			ret.score = new Double(score);
			ret.weight = new Double(weight);
			ret.type = type;
			return ret;
		} catch (Exception e) {
			return null;
		}
	}
	
}
