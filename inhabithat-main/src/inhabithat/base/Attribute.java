package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;

import compare.filter.AbstractFilter;
import compare.filter.AttributeFilter;
import inhabithat.base.AttributeDB.AttrType;
import inhabithat.base.AttributeDB.ScoreCalcType;
import inhabithat.utils.StringTools;
import inhabithat.utils.ThreadTools;

public class Attribute extends AbstractAttribute{
	private String rawData;
	public Double data;
	/**
	 * rawData must be a double number in string format. No added chars such as '%'(0), '$' allowed.
	 */
	public Attribute(AttrType type, String rawData){
		this.type = type;
		this.rawData = rawData;
		this.data = str2Double(rawData);
		if (AttributeDB.scoreType(type)==ScoreCalcType.STATIC){
			weight = AbstractFilter.MAX_WEIGHT;
			score = AttributeFilter.getStaticScore(this);
		}
		else {
			weight = AbstractFilter.MIN_WEIGHT;
		}
		//TODO do static steScore and setWeight. If attribute is not comparable type, set weight to max and score according to calculation.
		//else, set weight to zero and score to Nan.
	}

	private Double str2Double(String str) {
		if (str==null || str.equals("null")) return  Double.NaN;
		else {
			try{
				str = str.replace(",","");//3,456 --> 3456
				return  Double.valueOf(str);
			}
			catch(Exception e){
				e.printStackTrace();
				return Double.NaN;
			}
		}
	}

	public AttrType getType() {
		return type;
	}
	public void setType(AttrType type) {
		this.type = type;
	}

	public String toString(){
		return type + "  "+rawData;
	}

	@Override
	public double calcScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeFile(BufferedWriter writer, int writeDepth) throws IOException {
		writer.write(StringTools.charRepeated(writeDepth, '\t'));
		writer.write(type + "\t"+rawData+"\n"); 
	}

	public AttrType[] path() {
		return type.path;
	}
	public Attribute copyElement() {
		Attribute attr = (Attribute) super.copy();

		if (attr != null){
			attr.rawData = new String(rawData);
			attr.data = new Double(data);
		}
		return attr;
	}

	@Override
	/**
	 * A filter carries information for calculating the score of this attribute
	 */
	public void filter(AttributeFilter filter) {
		if (filter.attrType!=type){
			ThreadTools.throwException("Attribute.filter(): Incorrect Attribute Type in filtering, passed "+filter.attrType+" to "+type);
		}
		weight = filter.weight;
		if (type.scoreType==ScoreCalcType.COMPARATIVE)
			score = filter.getScore(this);

	}

}
