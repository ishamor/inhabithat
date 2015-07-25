package inhabithat.base;

import inhabithat.base.AttributeGroup.GroupType;

public class BasicAttribute {
	public enum AttrType {
		//Climate
		TEMP_AVG_SMR_HIGH(0),
		TEMP_AVG_SMR_LOW(1),
		TEMP_AVG_WTR_HIGH(2),
		TEMP_AVG_WTR_LOW(3),
		SNOWFALL(4),
		RAINFALL(5),
		PRECIP_DAYS(6),
		SUN_DAYS(7),
		UV_IDX(8),
		COMFORT_IDX(9),

		//Ecology
		AIR_QLTY(0),
		WATER_QLTY(1),

		//Quality of Living
		VIOLENT_CRIME(0),
		PROPERTY_CRIME(1),
		PUPIL_TEACHER_RATIO(2),
		COMMUTE_TIME(3),

		//Economy
		COL_OVERALL(0),
		UNEMPLOYMENT_RATE(1),
		INCOME_TAX(2),
		SALES_TAX(3),
		PROPERTY_TAX(4),
		INCOME_PER_CAPITA(5),
		MEDIAN_HOME_COST(6),

		//Demographics
		POPULATION(0),
		POPULATION_DENSITY(1),
		RACE(2),
		RELIGION(3),
		VOTING(4),



		//location
		ELEVATION(0);
		AttrType(int index){
			this.idx = index;
		}
		public int idx; //used for indexing attribute lists within groups

	};
	private GroupType groupType;
	private AttrType type;
	private String rawData;
	private Double data;
	private Double score;
	private Double weight;
	/**
	 * rawData must be a double number in string format. No added chars such as '%'(0), '$' allowed.
	 */
	public BasicAttribute(AttrType type, String rawData){
		this.type = type;
		this.rawData = rawData;
		this.groupType = AttributeGroup.attr2Grp(type);
		data = Double.valueOf(rawData);
	}

	public AttrType getType() {
		return type;
	}
	public void setType(AttrType type) {
		this.type = type;
	}
	public GroupType getGroupType() {
		return groupType;
	}
	
	public String toString(){
		return type + "  "+rawData;
	}
}
