package inhabithat.base;

import inhabithat.base.BasicAttribute.AttrType;
import inhabithat.base.AttributeGroup.GroupType;
import inhabithat.base.LocaleName.NameFormat;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLocale {
	enum LocaleType {STATE,COUNTY,CITY,TOWN};
	public LocaleName name;
	public ZipCode zipCode;
	public AbstractLocale parent;
	public AbstractLocale state;
	public LocaleCoords coords;
	private Map<GroupType,Map<AttrType,BasicAttribute>> attributes;
	private Map<GroupType,AttributeGroup> groups;
	
//TODO change name to Locale
	//Have constructor with town and state create a list of empty groups with their names only. Add coords as well.
	public void addAttribute(BasicAttribute attr){
		GroupType gtype = AttributeGroup.attr2Grp(attr.getType());
		Map<AttrType,BasicAttribute> attrMap = attributes.get(gtype);
		if (attrMap==null){//allocate new attr map and place in group map
			attrMap = new HashMap<AttrType,BasicAttribute>();
			attributes.put(gtype, attrMap);
		}
		attrMap.put(attr.getType(), attr);
	}
	private void addGroup(GroupType type, AttributeGroup group){
		if (groups==null) groups = new HashMap<GroupType,AttributeGroup>();
		groups.put(type, group);
	}
	public String name(NameFormat format){
		return name.formatAs(format);
	}
}
