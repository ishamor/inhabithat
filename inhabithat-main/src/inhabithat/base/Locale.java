package inhabithat.base;

import inhabithat.base.BasicAttribute.AttrType;
import inhabithat.base.AttributeGroup.GroupType;
import inhabithat.base.LocaleName.NameFormat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * A locale holds several groups -each group with its own attributes.
 * @author ishamor
 *
 */
public class Locale {
	enum LocaleType {STATE,COUNTY,CITY,TOWN};
	public LocaleName name;
	public ZipCode zipCode;
	public Locale parent;
	public String state;
	public LocaleCoords coords;
	private Double score;
	//private Map<GroupType,Map<AttrType,BasicAttribute>> attributes = new HashMap<GroupType,Map<AttrType,BasicAttribute>>();
	private List<AttributeGroup> groups;
	
public Locale(String city, String state, String strLat, String strLong) {
		this.state = state;
		this.name = new LocaleName(city);
		this.coords = new LocaleCoords(strLat, strLong);
		this.groups = AttributeGroup.initGroups();
	}
	//TODO change name to Locale
	//Have constructor with town and state create a list of empty groups with their names only. Add coords as well.
	public void addAttribute(BasicAttribute attr){
		GroupType gtype = AttributeGroup.attr2Grp(attr.getType());
		AttributeGroup group = groups.get(gtype.ordinal());
		group.addAttribute(attr);
	}
	public String name(NameFormat format){
		return name.formatAs(format);
	}
}
