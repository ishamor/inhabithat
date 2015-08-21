package inhabithat.utils;

import java.io.Serializable;


public class Pair<TYPEA, TYPEB> implements Comparable<Object>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7993798498352067257L;

	public TYPEA fst;
	public TYPEB snd;

	public Pair(TYPEA fst, TYPEB snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	public Pair() {
	}

	public boolean equals(Object o) {
		if ( o != null && o instanceof Pair<?, ?>){
			Pair<?, ?> p1 = (Pair<?, ?>)o;
			return ( p1.fst.equals( this.fst ) && p1.snd.equals( this.snd ) ); 
		}
		return false;
	}	
	
	public int hashCode() { 
		return (fst.hashCode() + (31 * snd.hashCode()));
	}

	@Override
	public int compareTo(Object o) {
		if ( o != null && o instanceof Pair<?, ?>){
			Pair<?, ?> p1 = (Pair<?, ?>)o;
			if ( p1.equals(this) ) { 
				return 0; 
			} else if ( p1.hashCode() > this.hashCode() ) { 
				return 1;
			} else if ( p1.hashCode() < this.hashCode() ) { 
				return -1;  
			}
		}
		return(-1);
	}
	
	
	public String toString() {
		return "("+fst+" ; "+snd+")";
	}
}
