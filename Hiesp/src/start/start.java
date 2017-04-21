package start;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class start {

	static Download d;
	static Link[] links;
	static Config config = new Config();

	public static void main(String[] args) {
		
//		loadConfig("D:\\Alexey\\EclipseWorkspace\\Hiesp\\Configs\\hiesp1.conf");
		System.out.println(args.length);
		if(args.length <= 0 || args.length>1){
			System.err.println("Use this Programm like this: java -jar hiesp.jar 'Path of config'. Config Files end have the eding .conf");
			return;
		} else {
			String file =  args[0];
					//"D:\\Alexey\\EclipseWorkspace\\Hiesp\\Configs\\hiesp1.conf" ;// args[0];
			if(BasicFunctions.checkConfigPath(file)){
				if(loadConfig(file)){
					d = new Download();
					HIESP();
				} else {
					System.err.println("This Config file doesn't fit the definition.");
				}
			} else {
				System.err.println("This Config file doesn't exists: " + file+".");
			}
		}
	}

	/**
	 * HIESP ist der ablauf vom filtern aller links einer Webseite
	 * bis hin zum Download der gewünschten file
	 */
	public static void HIESP() {
		links = d.grabLink(config.WebSite, config.Rel, config.Ending); 					// hier werden alle Links der Website herausgefiltert
		if (links.length > 0) {
			// Programm Start
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 			// Startzeit des Programms
			System.out.println("Start: " + timestamp.toString());
			
			String storePath = BasicFunctions.getStorePath(links[0]); 					// Generierung des Datei - Namens und Pfades
			String fileName = BasicFunctions.getFileName(links[0]);
			
			System.out.println(storePath); // COnsole Store Print
			// Exestiert diese File bereits?
			if (!BasicFunctions.containsFile(config.Path, fileName)) {					// Ueberprueft ob der Folder beretis die File beinhaltet 
				saveFile(storePath);													// saveFile - Call der Download Funktion
			} else {
				System.out.println("File was already Downloaded");						
			}
			// Abschluss
			timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println("End: " + timestamp.toString());
		} else {
			System.err.println("Error: no links found!");
		}

	}

	/**
	 * Aus optischen Gründen wurde diese Methode aus der Main extrahiert
	 * diese funktion ladet den ersten Link innerhalb des LinkArrays runter
	 * @param file - der Dateipfad
	 */
	public static void saveFile(String file) {
		try {
			System.out.println(links[0].url);
			d.saveUrl(file, links[0].url); 												// hier geschieht der download
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode kann genutzt werden um alle Links printen zu lassen.
	 */
	static void debugLinks() {
		if (config.debug) {
			if (links != null) {
				System.out.println("Links grabbed: " + links.length);
				for (Link l : links) {
					System.out.println(l);
				}
			}
		}
	}
	
	/**
	 * loading Config
	 * @param file
	 * @return
	 */
	public static boolean loadConfig(String file) {
		Gson gson = new Gson();
		try {
			config = gson.fromJson(new FileReader(file), Config.class);
			System.out.println(config);
			return true;
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			System.err.println("JsonSyntaxException | JsonIOException | FileNotFoundException: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
