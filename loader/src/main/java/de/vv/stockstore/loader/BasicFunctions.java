package de.vv.stockstore.loader;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Alexey Gasevic
 *
 *         This class is used to bundle basic functions needed for this project
 */
public class BasicFunctions {

	/**
	 * StorePath: return the storepath for this link
	 * 
	 * @param l
	 *            - Link
	 * @return
	 */
	public static String getStorePath(Link l) {
		return Loader.config.Path + getFileName(l);
	}

	/**
	 * formats FileName
	 * 
	 * @param l
	 *            - Link
	 * @return
	 */
	public static String getFileName(Link l) {
		return Loader.config.FileName + "_" + l.date + Loader.config.Ending;
	}

	/**
	 * use trim to cut your String to a wished width
	 * 
	 * @param s
	 *            - your String
	 * @param width
	 *            - wanted width
	 * @return cutted String to less or equal width
	 */
	public static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1);
		else
			return s;
	}

	/**
	 * parses message and return first result matching pattern
	 * 
	 * @param message
	 *            - some message
	 * @param p
	 *            - pattern
	 */
	public static String match(String message, String regex) {
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(message);
		if (m.find()) {
			return m.group(0);
		}
		return null;
	}
	
	/**
	 * checks whether this path contains a file or not
	 * 
	 * @param path
	 * @param file
	 * @return boolean
	 */
	public static boolean containsFile(String path, String file) {
		File folder = new File(path); // hole mir diesen order des Pfades
		File[] listOfFiles = folder.listFiles(); // hole mir alle Dateien aus diesem Pfad
		for (int i = 0; i < listOfFiles.length; i++) {	// schau dir all diese Dateien an
			if (listOfFiles[i].isFile()) { // falls die Datei eine Datei ist
				if (listOfFiles[i].getName().equals(file)) // und genauso heisst wie die gewuenschte Datei
					return true; // wird true ausgegeben
			}
		}
		return false; // ansonsten false
	}
	
//	/**
//	 * initial Config, is used to print Config as Json format
//	 * @return
//	 */
//	public static Config initConfig(){
//		Config c = new Config();
//		c.debug = true;
//		c.Path = "D:\\Alexey\\HIESP\\";
//		c.FileName = "HIESP";
//		c.Ending = ".csv";
//		c.WebSite = "http://www.xetra.com/xetra-de/instrumente/alle-handelbaren-instrumente/boersefrankfurt";
//		c.Rel = "Alle handelbaren Instrumente exklusive strukturierte Produkte";
//		c.logName = "log.txt";
//		c.DateRegex = "\\d\\d [a-zA-Z]+ \\d\\d\\d\\d \\d\\d:\\d\\d";
//		c.dateOrder = new int[]{2,1,0,3,4};
//		return c;
//	}
	
	/**
	 * Checks whether the config file has the right ending and exists or not 
	 * @param path - path to config file
	 * @return boolean
	 */
	public static boolean checkConfigPath(String path){
		if(path.endsWith(".conf")){
			File f = new File(path);
			return (f.exists() && !f.isDirectory()); // ist diese File eine File und kein Ordner
		}
		return false;
	}
}
