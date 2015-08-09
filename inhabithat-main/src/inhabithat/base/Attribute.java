package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;

import inhabithat.utils.StringTools;

public class Attribute extends AbstractAttribute{
	private String rawData;
	private Double data;
	/**
	 * rawData must be a double number in string format. No added chars such as '%'(0), '$' allowed.
	 */
	public Attribute(AttrType type, String rawData){
		this.type = type;
		this.rawData = rawData;
		this.data = str2Double(rawData);
		
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

}
