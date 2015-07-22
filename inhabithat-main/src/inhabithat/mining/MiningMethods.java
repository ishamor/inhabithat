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
			//--remove rank from titles
			df.getTitles().remove(0);
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
			List<String> tmpRow = null;
			int lineNum = 1;
			while ((query = reader.readLine()) != null){
				query = query.replaceAll("\\[\\d+\\]", "");//remove [5] link references
				if (lineNum==1){//title line
					String[] titles = query.split("\t");
					ret.setTitles(ListTools.asList(titles));
				}
				else {
					//keep on parsing lines into data rows until reaching a row which starts with \d\t. This row starts a new data row.
					String tmp = query.replaceAll("^\\d+\\t","");
					if (tmp.equals(query)==false){
						if (tmpRow!=null) ret.addDataRow(tmpRow);
						tmpRow = new ArrayList<String>();
						query = tmp;
					}
					String[] data = query.split("\\t");
					tmpRow.addAll(ListTools.asList(data));
				}
				lineNum++;
			}
			ret.addDataRow(tmpRow);
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
