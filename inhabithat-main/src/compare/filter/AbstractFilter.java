package compare.filter;

public abstract class AbstractFilter {

	public static final Double MAX_WEIGHT = 100.0;
	public static final Double MIN_WEIGHT = 0.0;
	public static final Double MAX_SCORE = 1000.0;
	public static final Double MIN_SCORE = 0.0;
	public static final Double SCORE_RANGE = MAX_SCORE-MIN_SCORE;
	
	
	
//TODO change this to AbstractFilter. Crate two children - LOCFilter and ATTRFilter.
//For attrFilter create types, currently only upper-lower threshold
//Filter has method filter(Attribute attr) which according to filtering type of the attr and data computes the score.
//Same thing about Locale filtering, currently only filtering by State.
//Set min/max scores.
}
