package inhabithat.base;

import inhabithat.base.AbstractAttribute.AttrType;
import inhabithat.base.AttributeGroup.GroupType;
import inhabithat.base.LocaleName.NameFormat;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractLocale {
	enum LocaleType {STATE,COUNTY,CITY,TOWN};
	LocaleName name;
	ZipCode zipCode;
	AbstractLocale parent;
	AbstractLocale state;
	LocaleCoords coords;
	private Map<GroupType,Map<AttrType,AbstractAttribute>> attributes;

	public void addAttribute(AbstractAttribute attr){
		GroupType gtype = AttributeGroup.attr2Grp(attr.getType());
		Map<AttrType,AbstractAttribute> attrMap = attributes.get(gtype);
		if (attrMap==null){//allocate new attr map and place in group map
			attrMap = new HashMap<AttrType,AbstractAttribute>();
			attributes.put(gtype, attrMap);
		}
		attrMap.put(attr.getType(), attr);
	}
	public String name(NameFormat format){
		return name.formatAs(format);
	}
}
