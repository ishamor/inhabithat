package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inhabithat.base.AttributeGroup.GroupType;
import inhabithat.utils.StringTools;

public class Attribute extends AbstractAttribute{
	/**
	 * List every attribute with a flat hierarchy of groups and subgroups. Init every one with an index in the lowest list
	 * Indices run by the bottom-most subgroup. All free items not within a sub-group must come last int he group.
	 * @author ishamor
	 *
	 */
	public enum AttrType {
		//Climate
		TEMP_AVG_SMR_HIGH(0,GroupType.CLIMATE,GroupType.TEMPERATURES),
		TEMP_AVG_SMR_LOW(1,GroupType.CLIMATE,GroupType.TEMPERATURES),
		TEMP_AVG_WTR_HIGH(2,GroupType.CLIMATE,GroupType.TEMPERATURES),
		TEMP_AVG_WTR_LOW(3,GroupType.CLIMATE,GroupType.TEMPERATURES),
		
		SNOWFALL(0,GroupType.CLIMATE,GroupType.PRECIPITATION),
		RAINFALL(1,GroupType.CLIMATE,GroupType.PRECIPITATION),
		PRECIP_DAYS(2,GroupType.CLIMATE,GroupType.PRECIPITATION),
		
		SUN_DAYS(0,GroupType.CLIMATE,GroupType.SUNSTATS),
		UV_IDX(1,GroupType.CLIMATE,GroupType.SUNSTATS),
		
		COMFORT_IDX(3,GroupType.CLIMATE),

		//Ecology
		AIR_QLTY(0,GroupType.ECOLOGY),
		WATER_QLTY(1,GroupType.ECOLOGY),

		//Quality of Living
		VIOLENT_CRIME(0,GroupType.QOL,GroupType.CRIME),
		PROPERTY_CRIME(1,GroupType.QOL,GroupType.CRIME),
		
		PUPIL_TEACHER_RATIO(1,GroupType.QOL),
		COMMUTE_TIME(2,GroupType.QOL),
		DR_PER_100K(3,GroupType.QOL),

		//Economy
		COL_OVERALL(0,GroupType.ECONOMY,GroupType.COST_OF_LIVING),
		HEALTH_COST(1,GroupType.ECONOMY,GroupType.COST_OF_LIVING),
		
		INCOME_TAX(0,GroupType.ECONOMY,GroupType.TAX),
		SALES_TAX(1,GroupType.ECONOMY,GroupType.TAX),
		PROPERTY_TAX(2,GroupType.ECONOMY,GroupType.TAX),

		UNEMPLOYMENT_RATE(2,GroupType.ECONOMY),
		INCOME_PER_CAPITA(3,GroupType.ECONOMY),
		MEDIAN_HOME_COST(4,GroupType.ECONOMY),
		
		//Demographics
			
		WHITE(0,GroupType.DEMOGRAPHIC,GroupType.RACE),
		BLACK(1,GroupType.DEMOGRAPHIC,GroupType.RACE),
		ASIAN(2,GroupType.DEMOGRAPHIC,GroupType.RACE),
		HISPANIC(3,GroupType.DEMOGRAPHIC,GroupType.RACE),
		
		RELIGIOUS(0,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		CATHOLIC(1,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		LDS(2,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		BAPTIST(3,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		EPISCOPALIAN(4,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		PENTECOSTAL(5,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		LUTHERAN(6,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		METHODIST(7,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		PRESBYTERIAN(8,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		OTHER_CHRISTIAN(9,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		JEWISH(10,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		EASTERN(11,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		ISLAM(12,GroupType.DEMOGRAPHIC,GroupType.RELIGION),
		
		VOTE_DEMOCRAT(0,GroupType.DEMOGRAPHIC, GroupType.VOTING),
		VOTE_REPUBLICAN(1,GroupType.DEMOGRAPHIC, GroupType.VOTING),
		VOTE_INDEP(2,GroupType.DEMOGRAPHIC, GroupType.VOTING),
		
		POPULATION(3,GroupType.DEMOGRAPHIC),
		POPULATION_DENSITY(4,GroupType.DEMOGRAPHIC),

		//location
		ELEVATION(0,GroupType.LOCATION);
		
		AttrType(int index,GroupType ...gtypes){
			this.idx = index;
			this.gtypes = gtypes;
		}
		public int idx; //used for indexing attribute lists within groups
		public GroupType[] gtypes;//Hierarchical tree of groups
		public int subGroupDepth(){
			return gtypes.length;
		}

	};
	//private GroupType groupType;
	private AttrType type;
	private String rawData;
	private Double data;
	/**
	 * rawData must be a double number in string format. No added chars such as '%'(0), '$' allowed.
	 */
	public Attribute(AttrType type, String rawData){
		this.type = type;
		this.rawData = rawData;
		//this.groupType = type.gtype;
		data = Double.valueOf(rawData);
	}

	public AttrType getType() {
		return type;
	}
	public void setType(AttrType type) {
		this.type = type;
	}
	
	public String toString(){
		return type + "  "+rawData;
	}

	@Override
	public double calcScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeFile(BufferedWriter writer, int writeDepth) throws IOException {
		writer.write(StringTools.charRepeated(writeDepth, '\t'));
		writer.write(type + "\t"+rawData+"\n"); 
	}
}
