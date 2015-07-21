package mining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	public List<String> getTitles(){
		return titles;
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
	/**
	 * Write content of dataframe to file. Return write status.
	 */
	public boolean write(String fname) throws Exception{
		return write(fname,false);
	}
	public boolean write(String fname,boolean append) throws Exception{
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname,append),"UTF-8"));
			writer.write(ListTools.concat(titles,"\t"));
			for (List<String> row : data){
				writer.write("\n");
				writer.write(ListTools.concat(row,"\t"));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
