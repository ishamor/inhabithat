package inhabithat.base;

import inhabithat.utils.ListTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * frame holds a table. Each column is a variable and each row a measurement.
 * DataFrame row and column numbering starts from 1
 * @author ishamor
 *
 */
public class DataFrame {
	private List<String> titles;
	private List<List<String>> data = new ArrayList<List<String>>();


	public DataFrame(String rpath) {
		read(rpath);
	}
	public DataFrame() {
	}
	private void read(String rpath) {
		if (!isLegalDFFile(rpath)) return;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(rpath), "UTF-8"));
			//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname,true),"UTF-8"));
			String query;
			List<String> tmpRow = null;
			int lineNum = 1;
			while ((query = reader.readLine()) != null){
				if (lineNum==1){//title line
					String[] titles = query.split("\t");
					setTitles(ListTools.asList(titles));
				}
				else {
					//keep on parsing lines into data rows
					String[] data = query.split("\\t");
					tmpRow.addAll(ListTools.asList(data));
					addDataRow(tmpRow);
				}
				lineNum++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
	/**
	 * Data frame only reads and write to files of type _df.txt
	 */
	public boolean write(String fname,boolean append) throws Exception{
		if (isLegalWriteFile(fname)==false) return false;
		fname = makeDFFile(fname);
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
	/**
	 * Data frame only reads and write to files of type *_df.txt
	 */
	private String makeDFFile(String fname) {
		if (isLegalDFFile(fname)) return fname;
		else{
			//We know it ends in .txt
			return fname.substring(0,fname.length()-4)+"_df"+fname.substring(fname.length()-3,fname.length());
		}
	}
	private boolean isLegalDFFile(String fname) {
		return fname.matches(".*_df.txt");
	}
	/**
	 * Is this a legal file for DataFrame to write
	 */
	private boolean isLegalWriteFile(String fname) {
		// legal if *.txt
		if (fname.matches(".+\\.txt")) return true;
		return false;
	}
	public boolean isValid(){
		return titles!=null;
	}
	public int numRows(){
		return data.size();
	}
	public int numCols(){
		if (isValid()) return titles.size();
		return -1;
	}
	//----------------------------
	// Get/Set data methods
	//----------------------------
	/**
	 * Get data from row rowNum and column colName. If any are out-of-bounds return null
	 */
	public String getData(int rowNum, String colName) {
		if (validRowNum(rowNum)==false) return null;
		int colNum = getColNum(colName);
		if (colNum<0) return null;
		return data.get(rowNum).get(colNum);
	}
	/**
	 * Converts from name in title row to column name. 
	 * @return -1 if title is not present. Else a number between 1 and numCols()
	 */
	public int getColNum(String colName) {
		if (isValid()==false) return -1;
		return titles.indexOf(colName)+1;
	}
	private boolean validRowNum(int rowNum) {
		return rowNum>0&&rowNum<=numRows();
	}
}
