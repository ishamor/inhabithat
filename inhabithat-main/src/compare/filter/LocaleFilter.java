package compare.filter;

import inhabithat.base.Locale;
import inhabithat.base.LocaleName;

public class LocaleFilter extends AbstractFilter {
	public LocaleName state;
	public LocaleFilter(LocaleName filterState){
		this.state = filterState;
	}

	public Double getFit(Locale locale) {
		if (locale.state.equals(state))
			return AbstractFilter.MAX_FIT;
		else
			return AbstractFilter.MIN_FIT;
	}
	public String toString(){
		return state.toString();
	}
}
