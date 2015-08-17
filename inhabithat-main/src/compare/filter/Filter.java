package compare.filter;

public class Filter {
enum FilterType {LOCALE_STATE,ATTRIBUTE};
public FilterType type;
//TODO change this to AbstractFilter. Crate two children - LOCFilter and ATTRFilter.
//For attrFilter create types, currently only upper-lower threshold
//Filter has method filter(Attribute attr) which according to filtering type of the attr and data computes the score.
//Same thing about Locale filtering, currently only filtering by State.
//Set min/max scores.
}
