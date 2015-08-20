package compare.filter;

import inhabithat.base.AbstractAttribute.AttrType;
import inhabithat.base.Attribute;


public class AttributeFilter extends AbstractFilter {

	public AttrType attrType;
	public Double weight;
	private Integer maxThreshold;
	private Integer minThreshold;

	public Double getScore(Attribute attribute) {
		//calc a score according to how well the data fits within the thresholds
		if (attribute.data <= maxThreshold && attribute.data>=minThreshold)
			return AbstractFilter.MAX_SCORE;
		else
			return AbstractFilter.MIN_SCORE;
	}

	public static Double getStaticScore(Attribute attribute) {
		// TODO According to data, biggetIsBetter and maybe other normalization thresholds - calculate a score for each attribute.
		//Have the score spread out all the attribute values between min and max scores. 
		//We need for each of the static attributes the min-max of the attribute. Get this and set it in a field in the enum. Then use it to 
		//normalize
		return null;
	}


}
