package inhabithat.base;
/**
 * class maintins the locale coordinates and supplies methods for manipulating them
 * Coordinates should be supplied in decimal degree form: 40.6643°N 73.9385°W
 * @author ishamor
 *
 */
public class LocaleCoords {
	String strLat;//Latitude
	String strLong;//longitut
	Double latd;
	Double longd;
	Double latRad;
	Double longRad;
	private static final double kmInMile = 0.621371;
	private static final double earthRadiusKm = 6371;
	private static final double earthRadiusMile = earthRadiusKm*kmInMile;

	public LocaleCoords (String strNorth,String strWest){
		this.strLat = strNorth;
		this.strLong = strWest;
		this.latd = coord2Num(strNorth);
		this.longd = coord2Num(strWest);
		this.latRad = latd*Math.PI/180;
		this.longRad = longd*Math.PI/180;
	}
	/**
	 * Converts from 40.6643°N  to 40.6643
	 */
	private static Double coord2Num(String strCoord) {
		String str = strCoord.substring(0,strCoord.length()-2);
		return Double.valueOf(str);
	}
	/**
	 * formula found here: http://williams.best.vwh.net/avform.htm, use great circle calculation
	 * d = 2*asin(sqrt((sin((lat1-lat2)/2))^2+ cos(lat1)*cos(lat2)*(sin((lon1-lon2)/2))^2))
	 */
	public static Double dist (LocaleCoords lc1,LocaleCoords lc2){
		double lat1 = lc1.latRad;
		double lat2 = lc2.latRad;
		double lon1 = lc1.longRad;
		double lon2 = lc2.longRad;
		return dist(lat1,lat2,lon1, lon2);
	}
	public static double dist(double lat1, double lat2, double lon1, double lon2){
		double radDist = 2*Math.asin(Math.sqrt(Math.pow(Math.sin((lat1-lat2)/2),2)+ 
				Math.cos(lat1)*Math.cos(lat2)*Math.pow(Math.sin((lon1-lon2)/2),2)));
		return radDist*earthRadiusMile;
	}
	public Double dist(LocaleCoords lc){
		return dist(this,lc);
	}
	public static void main(String[] args){
		double lat1 = 0.592539;
		double lat2 = 0.709186;
		double lon1 = 2.066470;
		double lon2 = 1.287762;
		double dist = LocaleCoords.dist(lat1, lat2, lon1, lon2);
		System.out.println(dist);
		
	}
}
