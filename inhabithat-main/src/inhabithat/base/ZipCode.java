package inhabithat.base;

import java.util.List;

/**
 * Class holds a zip code and methods to format it and convert from zip to locale and back.
 * @author ishamor
 *
 */
public class ZipCode {
	private String zipCode;
	public ZipCode(String zip){
		this.setZipCode(zip);
	}
	
	public static List<ZipCode> locale2Zip(Locale loc){
		return null;
	}
	public static Locale zip2Locale(ZipCode zip){
		return null;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}
