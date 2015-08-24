package compare.filter;

import inhabithat.base.AttributeDB;
import inhabithat.base.AttributeDB.AttrType;
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
		//Score is normalized by attribute min-max thresholds into the common score.
		double range;
		double min = AttributeDB.minValue(attribute.type);
		double max = AttributeDB.maxValue(attribute.type);
		if (AttributeDB.biggerIsBetter(attribute.type))
			range = attribute.data-min;
		else
			range = max-attribute.data;
		
		Double score = (range/(max-min))*AbstractFilter.SCORE_RANGE;
		//--Correction for PUPIL_TEACHER_RATIO which has two out-liers
		if (attribute.type==AttrType.PUPIL_TEACHER_RATIO){
			if(attribute.data>=30)
				score = AbstractFilter.SCORE_RANGE;
			else
				score = ((attribute.data-min)/(30-min))*AbstractFilter.SCORE_RANGE;
		}
		
		return score;
	}


}
