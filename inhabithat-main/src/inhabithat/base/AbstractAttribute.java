package inhabithat.base;

import inhabithat.base.AttributeDB.AttrType;
import inhabithat.utils.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import compare.filter.AttributeFilter;


public abstract class AbstractAttribute implements Cloneable {
	protected Double score;
	protected Double weight;
	public AttrType type;

	public int depth(){
		return type.depth;
	}

	public int index() {
		return type.idx;
	}

	abstract public List<Pair<Double,Double>> getScores();
	abstract public void writeFile(BufferedWriter writer, int writeDepth)  throws IOException;
	abstract public void filter(AttributeFilter filter);
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
	//TODO add a method which maps each attr type to a filtering type and add the field.
	//Add Filter object and fill it according to this type. Have defualt on/off and calc score on 'on' types.
	/**
	 * Changes the memory placement of the element
	 */
	public AbstractAttribute copy() {
		try {
			AbstractAttribute ret = (AbstractAttribute) super.clone();
			ret.score = score==null? null : new Double(score);
			ret.weight = weight==null? null : new Double(weight);
			ret.type = type;
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
