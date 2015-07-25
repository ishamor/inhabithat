package inhabithat.base;

import inhabithat.base.AttributeGroup.GroupType;

public class BasicAttribute {
	public enum AttrType {
		//Climate
		TEMP_AVG_SMR_HIGH,
		TEMP_AVG_SMR_LOW,
		TEMP_AVG_WTR_HIGH,
		TEMP_AVG_WTR_LOW,
		SNOWFALL,
		RAINFALL,
		PRECIP_DAYS,
		SUN_DAYS,
		UV_IDX,
		COMFORT_IDX,

		//Ecology
		AIR_QLTY,
		WATER_QLTY,

		//Quality of Living
		VIOLENT_CRIME,
		PROPERTY_CRIME,
		PUPIL_TEACHER_RATIO,
		COMMUTE_TIME,

		//Economy
		COL_OVERALL,
		UNEMPLOYMENT_RATE,
		INCOME_TAX,
		SALES_TAX,
		PROPERTY_TAX,
		INCOME_PER_CAPITA,
		MEDIAN_HOME_COST,

		//Demographics
		POPULATION,
		POPULATION_DENSITY,
		RACE,
		RELIGION,
		VOTING,



		//location
		ELEVATION

	};
	private GroupType groupType;
	private AttrType type;
	private String rawData;
	private Double data;
	private Double score;
	private Double weight;
	/**
	 * rawData must be a double number in string format. No added chars such as '%', '$' allowed.
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
