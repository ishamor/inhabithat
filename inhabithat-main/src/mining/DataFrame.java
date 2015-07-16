package mining;

import java.util.ArrayList;
import java.util.List;

import utils.ListTools;

/**
 * frame holds a table. Each colums is a variable and each row a measurement.
 * @author ishamor
 *
 */
public class DataFrame {
	private List<String> titles;
	private List<List<String>> data = new ArrayList<List<String>>();


	public void setTitles(List<String> titles){
		this.titles = titles;
	}
	public void addDataRow(List<String> row){
		data.add(row);
	}
	public String toString(){
		String ret = ListTools.concat(titles,"\t");
		for (List<String> row : data){
			ret+="\n";
			ret+= ListTools.concat(row,"\t");
		}

		return ret;

	}
}
