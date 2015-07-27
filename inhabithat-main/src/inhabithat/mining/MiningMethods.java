package inhabithat.mining;

import inhabithat.base.DataFrame;
import inhabithat.utils.InhabithatConfig;
import inhabithat.utils.ListTools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MiningMethods {
/**
 * File copy pasted from https://en.wikipedia.org/wiki/List_of_United_States_cities_by_population
 * @throws Exception
 */
	public static void wikiCitiesMostPopulated() throws Exception{
		String rpath = InhabithatConfig.getInstance().citiesPath+"/wiki_above_100K.txt";
		String wpath = InhabithatConfig.getInstance().citiesPath+"/wiki_above_100K_df.txt";
		try {
			DataFrame df = readWikiTable(rpath);
			df.write(wpath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		boolean t = false;
	}
	public static DataFrame readWikiTable(String fname) throws Exception{
		//String url = "http://en.wikipedia.org/w/api.php?action=parse&format=xml&prop=text&page=List_of_United_States_cities_by_population&section=1&contentformat=text/plain";
		//String url = "http://en.wikipedia.org/w/api.php?action=parse&prop=text&page=List_of_United_States_cities_by_population&section=1&contentformat=text/plain";
		//String url = "http://en.wikipedia.org/w/api.php?action=parse&format=xml&prop=wikitext&page=List_of_United_States_cities_by_population&section=1&contentformat=text/x-wiki";
		//String url = "http://en.wikipedia.org/w/api.php?action=query&prop=text&page=List_of_United_States_cities_by_population&section=1";
		//String url = "http://en.wikipedia.org/w/api.php?action=parse&format=xml&prop=text&page=List_of_United_States_cities_by_population&section=1&contentformat=text/plain";
		DataFrame ret = new DataFrame();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname), "UTF-8"));
			//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fname,true),"UTF-8"));
			String query;
			StringBuilder tableLine = new StringBuilder();
			List<String> tmpRow = null;
			int lineNum = 1;
			while ((query = reader.readLine()) != null){
				query = query.replaceAll("\\[\\d+\\]", "");//remove [5] link references
				if (lineNum==1){//title line
					String[] titles = query.split("\t");
					ret.setTitles(ListTools.asList(titles).subList(1, titles.length));//--remove rank from titles
				}
				else {
					//Table lines are split to 3 lines, 2,3,4   5,6,7   Conaactnate them and then do the splitting
					//keep on parsing lines into data rows until reaching a row which starts with \d\t. This row starts a new data row.
					if (lineNum%3==2){
						if (tableLine.length()!=0){
							String[] data = tableLine.toString().split("\\t");
							ret.addDataRow(ListTools.asList(data));
						}
						tableLine = new StringBuilder();
						tableLine.append(query.replaceAll("^\\d+\\t",""));
						tmpRow = new ArrayList<String>();
					}
					else {
						tableLine.append(',').append(query);
					}
					
				}
				lineNum++;
			}
			String[] data = tableLine.toString().split("\\t");
			ret.addDataRow(ListTools.asList(data));
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static void main(String[] args) throws Exception {
		MiningMethods.wikiCitiesMostPopulated();
	}
}
