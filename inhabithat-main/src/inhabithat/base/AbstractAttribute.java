package inhabithat.base;

public abstract class AbstractAttribute {
	enum AttrType {
		//Climate
		TEMP_AVG_SMR_HIGH,
		TEMP_AVG_SMR_LOW,
		TEMP_AVG_WTR_HIGH,
		TEMP_AVG_WTR_LOW,
		SNOWFALL,
		RAINFALL,
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
private AttributeGroup group;
private AttrType type;
private String rawData;
private Double data;
private Double score;
private Double weight;
public AttrType getType() {
	return type;
}
public void setType(AttrType type) {
	this.type = type;
}
public AttributeGroup getGroup() {
	return group;
}
public void setGroup(AttributeGroup group) {
	this.group = group;
}

}
