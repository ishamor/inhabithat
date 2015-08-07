package inhabithat.base;

import inhabithat.base.AbstractAttribute.AttrType;
import inhabithat.base.LocaleName.NameFormat;
import inhabithat.utils.InhabithatConfig;
import inhabithat.utils.ListTools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * A locale holds several groups -each group with its own attributes.
 * @author ishamor
 *
 */
public class Locale{
	enum LocaleType {STATE,COUNTY,CITY,TOWN};
	public LocaleName name;
	public ZipCode zipCode;
	public Locale parent;
	public LocaleName state;
	public LocaleCoords coords;
	private Double score;
	//private Map<GroupType,Map<AttrType,BasicAttribute>> attributes = new HashMap<GroupType,Map<AttrType,BasicAttribute>>();
	private AbstractAttribute[] attributes;
	private String fileName;

	public Locale(String city, String stateStr, String strLat, String strLong) {
		this.state = new LocaleName(stateStr);
		this.name = new LocaleName(city);
		this.coords = new LocaleCoords(strLat, strLong);
		this.fileName = InhabithatConfig.getInstance().locPath+"/"+name.formatAs(NameFormat.Lower_)+" "+state.formatAs(NameFormat.Lower_)+".loc.txt";
		initAttributes();
	}
	
	
	
	private void initAttributes() {
		attributes = new AbstractAttribute[AttrType.topAttributes.length];
		for (AttrType attr : AttrType.topAttributes){
			attributes[attr.idx] = AttributeGroup.initAttr(attr);
		}
	}



	public Locale(String readPath) {
		readFile(readPath);
	}
	private void readFile(String readPath) {
		// TODO Auto-generated method stub
		
	}
	public void addAttribute(Attribute attr){
		if (attr.depth()==0)
			attributes[attr.index()] = attr;
		else{
			((AttributeGroup)attributes[attr.path()[0].idx]).addAttribute(attr);
		}
	}
	public String name(NameFormat format){
		return name.formatAs(format);
	}
	/**
	 * Change when moving to pointers to State Locale later on
	 * @return
	 */
	public String stateName() {
		return state.toString();
	}
	public String toString(){
		return name+", "+stateName();
	}
	/**
	 * Write locale with all its attributes into a file. This file can then be read into a locale using readFile.
	 * File name is "town state.loc.txt
	 */
	public void writeFile() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName,false),"UTF-8"));//overwrite any existing file
			writer.write(InhabithatConfig.getInstance().localeVersion+"\n");
			writer.write(name+","+state+"\n");
			for (AbstractAttribute group : attributes){
				if (group!= null)
					group.writeFile(writer,0);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String fileName(){ return fileName;}
	public static void main(String[] args){
		//TODO check locale addAttribute, write to file and load from file;
		Locale loc = new Locale("los angeles", "california", "46WW", "44NN");
		double value = 1.5;
		//Load location with every attribute we have
		for (AttrType type : AttrType.botAttributes){
			Attribute attr = new Attribute(type, String.valueOf(value++));
			loc.addAttribute(attr);
		}
		loc.writeFile();

		//Check read from file
//		Locale loc2 = new Locale(loc.fileName());
//		loc2.writeFile();

	}
}
