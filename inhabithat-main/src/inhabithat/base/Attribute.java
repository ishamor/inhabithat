package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		//this.groupType = type.gtype;
		data = Double.valueOf(rawData);
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
