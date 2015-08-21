package inhabithat.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class InhabithatConfig {
	//---Paths
	public String homePath;
	public String dataPath;
	public String citiesPath;
	public String rawPath;
	public String locPath;
	public String locDBPath;//path to most up to date locale database
	public String locDBSummaryPath;//path to most up to date locale database
	
	//---Configurations
	public boolean metricSystem;// true metric, false imperial
	public String localeVersion = "V1.1";;

	private static InhabithatConfig instance = null;
	private Properties properties = new Properties();
	public synchronized static InhabithatConfig getInstance() {
		if (instance == null) {
			synchronized (InhabithatConfig.class) {
				instance = new InhabithatConfig();
			}
		}
		return instance;
	}

	private InhabithatConfig(){
		//		if (System.getProperty("MATHEMATICA_MATH_KERNEL") == null && System.getenv("MATHEMATICA_MATH_KERNEL") != null)
		//			System.setProperty("MATHEMATICA_MATH_KERNEL", System.getenv("MATHEMATICA_MATH_KERNEL"));
		//		
		//		if (System.getProperty("MATHEMATICA_JLINK") == null && System.getenv("MATHEMATICA_JLINK") != null)
		//			System.setProperty("MATHEMATICA_JLINK", System.getenv("MATHEMATICA_JLINK"));
		//		
		//		if (System.getProperty("EQ_HOME") == null && System.getenv("EQ_HOME") != null)
		//			System.setProperty("EQ_HOME", System.getenv("EQ_HOME"));


		fillPaths();
	}
	private void fillPaths(){
		//homePath = System.getProperty("INT_HOME");TODO fill in the path.
		//[C:\Users\ishamor\Documents\Personal\Oren\Symbolab\eqsquest-main/etc/tests/regression/solver.txt]
		homePath = "C:/Users/ishamor/Documents/Personal/Oren/inhabithat";
		if (homePath == null) {
			File home = new File("..");
			try {
				homePath = home.getCanonicalPath();
			} catch (IOException e) {
				homePath = home.getAbsolutePath();
			}
		}

		dataPath = homePath + "/data";
		citiesPath = dataPath + "/cities";
		locPath = dataPath + "/locales";
		rawPath = dataPath + "/raw";
		locDBPath = dataPath + "/locales_10_8";
		locDBSummaryPath =  dataPath + "/locales_10_8_summary";
	}
}
