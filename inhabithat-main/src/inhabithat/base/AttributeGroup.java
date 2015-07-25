package inhabithat.base;

import inhabithat.base.BasicAttribute.AttrType;
import inhabithat.utils.ListTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeGroup {
	public enum GroupType {
		CLIMATE,
		ECOLOGY,
		QOL,
		ECONOMY,
		LOCATION,
		DEMOGRAPHIC
	}
	private GroupType type;
	private List<BasicAttribute> attributes = new ArrayList<BasicAttribute>();
	private List<AttrType> attrTypes;
	private Double score;
	private Double weight;
	private static Map<AttrType,GroupType> attr2GrpMap;
	private static Map<GroupType,List<AttrType>> grp2AttrMap;
	static {
		attr2GrpMap = new HashMap<AttrType,GroupType>();
		attr2GrpMap.put(AttrType.TEMP_AVG_SMR_HIGH, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.TEMP_AVG_SMR_LOW, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.TEMP_AVG_WTR_HIGH, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.TEMP_AVG_WTR_LOW, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.SNOWFALL, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.RAINFALL, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.SUN_DAYS, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.UV_IDX, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.COMFORT_IDX, GroupType.CLIMATE);
		attr2GrpMap.put(AttrType.AIR_QLTY, GroupType.ECOLOGY);
		attr2GrpMap.put(AttrType.WATER_QLTY, GroupType.ECOLOGY);
		attr2GrpMap.put(AttrType.VIOLENT_CRIME, GroupType.QOL);
		attr2GrpMap.put(AttrType.PROPERTY_CRIME, GroupType.QOL);
		attr2GrpMap.put(AttrType.PUPIL_TEACHER_RATIO, GroupType.QOL);
		attr2GrpMap.put(AttrType.COMMUTE_TIME, GroupType.QOL);
		attr2GrpMap.put(AttrType.COL_OVERALL, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.UNEMPLOYMENT_RATE, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.INCOME_TAX, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.SALES_TAX, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.PROPERTY_TAX, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.INCOME_PER_CAPITA, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.MEDIAN_HOME_COST, GroupType.ECONOMY);
		attr2GrpMap.put(AttrType.POPULATION, GroupType.DEMOGRAPHIC);
		attr2GrpMap.put(AttrType.POPULATION_DENSITY, GroupType.DEMOGRAPHIC);
		attr2GrpMap.put(AttrType.RACE, GroupType.DEMOGRAPHIC);
		attr2GrpMap.put(AttrType.RELIGION, GroupType.DEMOGRAPHIC);
		attr2GrpMap.put(AttrType.VOTING, GroupType.DEMOGRAPHIC);
		attr2GrpMap.put(AttrType.ELEVATION, GroupType.LOCATION);


		grp2AttrMap = new HashMap<GroupType,List<AttrType>>();
		grp2AttrMap.put(GroupType.CLIMATE, ListTools.asList(AttrType.TEMP_AVG_SMR_HIGH,AttrType.TEMP_AVG_SMR_LOW,AttrType.TEMP_AVG_WTR_HIGH,
				AttrType.TEMP_AVG_WTR_LOW,AttrType.SNOWFALL,AttrType.RAINFALL,AttrType.SUN_DAYS,AttrType.UV_IDX,AttrType.COMFORT_IDX));
		grp2AttrMap.put(GroupType.ECOLOGY,ListTools.asList(AttrType.AIR_QLTY,AttrType.WATER_QLTY));
		grp2AttrMap.put(GroupType.QOL,ListTools.asList(AttrType.VIOLENT_CRIME,AttrType.PROPERTY_CRIME,AttrType.PUPIL_TEACHER_RATIO,AttrType.COMMUTE_TIME));
		grp2AttrMap.put(GroupType.ECONOMY,ListTools.asList(AttrType.COL_OVERALL,AttrType.UNEMPLOYMENT_RATE,AttrType.INCOME_TAX,AttrType.SALES_TAX,
				AttrType.PROPERTY_TAX,AttrType.INCOME_PER_CAPITA,AttrType.MEDIAN_HOME_COST));
		grp2AttrMap.put(GroupType.DEMOGRAPHIC,ListTools.asList(AttrType.POPULATION,AttrType.POPULATION_DENSITY,AttrType.RACE,AttrType.RELIGION,AttrType.VOTING));
		grp2AttrMap.put(GroupType.LOCATION,ListTools.asList(AttrType.ELEVATION));

	}
	public AttributeGroup(GroupType gtype){
		this.type=gtype;
		this.attrTypes = grp2Attr(gtype);
	}
/**
 * return a list of the attribute names under this group
 */

	public static GroupType attr2Grp(AttrType attr){
		return attr2GrpMap.get(attr);
	}
	public static List<AttrType> grp2Attr(GroupType grp){
		return grp2AttrMap.get(grp);
	}
	public List<AttrType> attrTypes(){
		return grp2Attr(type);
	}
	/**
	 * Add an attribute to the group, at index attr.idx
	 */
	void addAttribute(BasicAttribute attr) {
		attributes.add(attr.getType().idx, attr);

	}
	/**
	 * Get empty list with a group of each type.
	 */
	public static  List<AttributeGroup> initGroups() {
		 List<AttributeGroup> glist= new ArrayList<AttributeGroup>();
		for (GroupType gtype : GroupType.values()){
			AttributeGroup group = new AttributeGroup(gtype);
			glist.add(gtype.ordinal(),group);
		}
		return glist;
	}

}
