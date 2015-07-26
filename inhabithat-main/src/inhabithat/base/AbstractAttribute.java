package inhabithat.base;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class AbstractAttribute {
	private Double score;
	private Double weight;
	
	abstract public double calcScore();
	abstract public void writeFile(BufferedWriter writer, int writeDepth)  throws IOException;

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
	
}
