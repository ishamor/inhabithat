package mining;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import utils.InhabithatConfig;
import utils.ListTools;

public class BestplacesScrub {
	/**
	 * Scrub data from http://www.bestplaces.net/
	 */

	//Call structure:
	//Zip: http://www.bestplaces.net/zip-code/minnesota/st._paul/55108
	//city: http://www.bestplaces.net/city/california/los_angeles
	//town: http://www.bestplaces.net/city/minnesota/falcon_heights

	//Subclasses: http://www.bestplaces.net/economy/city/california/los_angeles
	//overview, people, health, ecomony, housing, rankings, climate, crime, education, comments, transportation, cost_of_living, religion, voting. 
	public static void main(String[] args) throws Exception {
//		String wikiURL = "https://en.wikipedia.org/wiki/List_of_United_States_cities_by_population";
//
//
//		URL oracle = new URL("http://www.bestplaces.net/climate/city/minnesota/falcon_heights");
//		BufferedReader in = new BufferedReader(
//				new InputStreamReader(oracle.openStream()));
//
//		String inputLine;
//		while ((inputLine = in.readLine()) != null)
//			System.out.println(inputLine);
//		in.close();
		
		String fpath = InhabithatConfig.getInstance().citiesPath+"/wiki above 100K.txt";
		
		DataFrame test = BestplacesScrub.readWikiTable(fpath);
		boolean t = false;
		
		
//		String test = "abs a[6]d and [7] is";
//		String test1 = "4\t abs a[6]d and [7] is";
//		String res = test.replaceAll("\\[\\d+\\]", "");
//		res = test1.replaceAll("^\\d+\\t","");
//		boolean resb = test1.equals(res);
//		res = test.replaceAll("^\\d+\\t","");
//		resb = test.equals(res);




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
}
