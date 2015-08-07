package inhabithat.base;

import inhabithat.base.AbstractAttribute.AttrType;
import inhabithat.utils.StringTools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttributeGroup extends AbstractAttribute{
	/**
	 * List all group, subGroups, sub-sub Groups etc. in a flat list. Index each sublist separately.
	 * @author ishamor
	 *
	 */

	protected AbstractAttribute[] attributes;

	public AttributeGroup(AttrType gtype){
		this.type=gtype;
		//this.attrTypes = grp2Attr(gtype);
	}
	
	
	/**
	 * Add an attribute to the group, at index attr.idx
	 * @param subGroupDepth 1: group, 2: sub-group, 3: sub-sub-group etc.
	 */
	public void addAttribute(Attribute attr) {
		if (attr.depth()==depth()+1)//Attribute is one of this group's attributes
			attributes[attr.index()] = attr;
		else{//atribute is lower in the hierarachy, part of a sub-group of this group
			((AttributeGroup)attributes[attr.path()[depth()+1].idx]).addAttribute(attr);
		}
		
	}
	public String toString(){return type.toString();}
//	/**
//	 * Get empty list with a group of each type.
//	 */
//	public static  List<AttributeGroup> initGroups() {
//		List<AttributeGroup> glist= new ArrayList<AttributeGroup>();
//		for (GroupType gtype : GroupType.values()){
//			AttributeGroup group = new AttributeGroup(gtype);
//			glist.add(gtype.ordinal(),group);
//		}
//		return glist;
//	}
	@Override
	public double calcScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeFile(BufferedWriter writer, int writeDepth) throws IOException {
		writer.write(StringTools.charRepeated(writeDepth, '\t')+type+"\n");
		++writeDepth;
		for (AbstractAttribute attr : attributes){
			if (attr!=null)
				attr.writeFile(writer,writeDepth);
		}
	}
/**
 * Create an array of empty groups with the group types of the top level groups. Decend down and create the rest of the hierarchy with empty arrays in all levels.
 * @return
 */
	protected static AbstractAttribute initAttr(AttrType atype){
		if (atype.isGroup()){
			AttributeGroup	attr = new AttributeGroup(atype);
			attr.attributes = new AbstractAttribute[atype.children.length];
			for (AttrType childType : atype.children){
				attr.attributes[childType.idx] = initAttr(childType);
			}
			return attr;
		}
		else {
			Attribute attr = new Attribute(atype,"0");
			return attr;
		}
	}

}
