package inhabithat.base;

import inhabithat.base.AttributeDB.AttrType;
import inhabithat.base.LocaleName.NameFormat;
import inhabithat.utils.InhabithatConfig;
import inhabithat.utils.ListTools;
import inhabithat.utils.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compare.filter.AbstractFilter;
import compare.filter.AttributeFilter;
import compare.filter.LocaleFilter;
/**
 * A locale holds several groups -each group with its own attributes.
 * @author ishamor
 *
 */
public class Locale implements Comparable<Locale>{
	enum LocaleType {STATE,COUNTY,CITY,TOWN};
	public LocaleName name;
	public ZipCode zipCode;
	public Locale parent;
	public LocaleName state;
	public LocaleCoords coords;
	private Double score = Double.NaN;
	private Double fit = Double.NaN;
	private AbstractAttribute[] attributes;
	private String fileName;

	public Locale(String city, String stateStr, String strLat, String strLong) {
		this.state = new LocaleName(stateStr);
		this.name = new LocaleName(city);
		this.coords = new LocaleCoords(strLat, strLong);
		this.fileName = locFileName(name.formatAs(NameFormat.Lower_), state.formatAs(NameFormat.Lower_));
		initAttributes();
	}



	private void initAttributes() {
		attributes = new AbstractAttribute[AttrType.topAttributes.length];
		for (AttrType attr : AttrType.topAttributes){
			attributes[attr.idx] = AttributeGroup.initAttr(attr);
		}
	}


	public void setState(String newState){
		this.state = new LocaleName(newState);
		this.fileName = locFileName(name.formatAs(NameFormat.Lower_), state.formatAs(NameFormat.Lower_));
	}

	public void addAttribute(Attribute attr){
		if (attr.depth()==0)
			attributes[attr.index()] = attr;
		else{
			((AttributeGroup)attributes[attr.path()[0].idx]).addAttribute(attr);
		}
	}
	public double getAttributeData(AttrType attr){
		if (attr.depth==0)
			return ((Attribute)attributes[attr.idx]).data;
		else{
			return ((AttributeGroup)attributes[attr.path[0].idx]).getAttributeData(attr);
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
	public String townName() {
		return name.toString();
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
			writer.write(name+"\t"+state+"\n");
			writer.write(zipCode+"\n");
			writer.write(coords+"\n");
			for (AbstractAttribute attr : attributes){
				if (attr!= null)
					attr.writeFile(writer,0);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Locale readFile(String town, String state){
		return readFile(locFileName(town,state));
	}
	public static Locale readFile(String rpath){
		//String rpath = locFileName(town,state);
		Locale loc = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rpath), "UTF-8"));
			//---Read meta info to init Locale
			String version = reader.readLine();
			String allName = reader.readLine();
			String[] town_state = allName.split("\t");
			String zip = reader.readLine();
			String allCoords = reader.readLine();
			String[] coords = allCoords.split("\t");
			loc = new Locale(town_state[0],town_state[1],coords[0],coords[1]);
			//--Read in all attributes and values
			String query;
			while ((query = reader.readLine()) != null){
				//--remove leading spaces and tabs
				query = query.trim();
				//--split by \t and if there are tow parts, init an attribute
				String[] parts = query.split("\t");
				if (parts.length==2){
					AttrType type = AttrType.valueOf(parts[0]);
					Attribute attr = new Attribute(type,parts[1]);
					loc.addAttribute(attr);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loc;
	}
	public static String locFileName(String town, String state) {
		return locFileName(town,state,InhabithatConfig.getInstance().locPath);//default locale folder path
	}
	public static String locFileName(String town, String state,String locPath) {
		return locPath+"/"+LocaleName.format(town,NameFormat.Lower_)+" "+LocaleName.format(state,NameFormat.Lower_)+".loc.txt";
	}



	public String fileName(){ return fileName;}
	public static void main(String[] args){

		//TODO check locale addAttribute, write to file and load from file;
		Locale loc = new Locale("eylon", "israel", "46WW", "44NN");
		double value = 1.5;
		//Load location with every attribute we have
		for (AttrType type : AttrType.botAttributes){
			Attribute attr = new Attribute(type, String.valueOf(value++));
			loc.addAttribute(attr);
		}
		loc.writeFile();

		//Check read from file
		Locale loc2 = Locale.readFile("eylon", "israel");
		loc2.setState("lebanon");
		loc2.writeFile();

	}

	@Override
	public int compareTo(Locale other) {
		return this.score.compareTo(other.score);
	}
	public Locale copy() {
		try{
			Locale loc = (Locale) super.clone();
			if (loc!=null){
				loc.name = new LocaleName(name);
				loc.zipCode = zipCode==null?null:new ZipCode(zipCode);
				loc.parent = parent==null?null:parent.copy();
				loc.state = new LocaleName(state);
				loc.coords = new LocaleCoords(coords);
				loc.score = new Double(score);
				loc.attributes = new AbstractAttribute[attributes.length];
				loc.fileName = new String(fileName);
				for (int i=0;i<attributes.length;++i) 
					loc.attributes[i] = attributes[i].copy();
			}
			return loc;
		}
		catch(Exception e){
			return null;
		}
	}



	public void filter(AbstractFilter abstf) {
		if (abstf instanceof LocaleFilter){
			LocaleFilter lf = (LocaleFilter)abstf;
			fit = lf.getFit(this);
		}
		else {
			AttributeFilter af = (AttributeFilter)abstf;
			int arrayInd = af.attrType.path[0].idx;//This filter belongs to an attribute found in this index of the locale attribute array
			attributes[arrayInd].filter(af);
		}

	}


	/**
	 * Based on the score and weight of all the sub-attributes and based on the fit, calculate the final Locale score
	 */
	public void calcScore() {
		score = 0.0;
		List<Pair<Double,Double>> weight_score = new ArrayList<Pair<Double,Double>>();
		for (AbstractAttribute attr : attributes){
			weight_score.addAll(attr.getScores());
		}
		//--The total weight of all the attributes must always be numAttributes * MAX_WEIGHT.
		//-- Gather the weight of all the attributes, calculate an expansion factor and multiply each weight by that factor
		//-- Then calculate each weight*score and add them up.
		double sumWeights = 0;
		for (Pair<Double,Double> w_s : weight_score)
			sumWeights += w_s.fst;
		double factor = (AttrType.numBotAttributes*AbstractFilter.MAX_WEIGHT)/sumWeights;
		for (Pair<Double,Double> w_s : weight_score)
			score += w_s.fst*factor*w_s.snd;

		if (Double.isNaN(fit)==false)
			score *=fit;

	}


	public static void sortByName(List<Locale> locales) {
		Collections.sort(locales,new  Comparator<Locale>(){

			@Override
			public int compare(Locale l1, Locale l2) {
				String name1 = l1.name.toString()+l1.stateName();
				String name2 = l2.name.toString()+l2.stateName();
				return name1.compareTo(name2);
			}
		});
	}
}
