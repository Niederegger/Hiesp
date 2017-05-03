package start;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Loader {

	static Config config = new Config();

	public static void main(String[] args) {
		if(args.length <= 0 || args.length>1){											// Ueberpruefen ob genau ein Argument, der Config-Pfad angegeben wurde.
			System.err.println("Use this Programm like this: java -jar hiesp.jar "		//   wenn nicht, wird eine Error-Message geprintet
					+ "'Path of config'. Config Files end have the eding .conf");		// 
			return;																		//   und das Programm beendet
		} 
		else {																		// -> ansonsten
			String file = args[0]; //"D:\\Alexey\\EclipseWorkspace\\Hiesp\\Configs\\test.conf"; // 														// lese den Pfad aus
			if(BasicFunctions.checkConfigPath(file)){									// UeberPruefe ob dieser Pfad Ok ist
				if(loadConfig(file)){													// falls der PFad korrekt ist, lade diese Config, und Ueberpruefe ob diese ebenfalls korrekt ist
					HIESP();															// die Config wurde geladen und beinhaltet das richtige Fromat, nun kann der Download beginnen
				} else {																// 
					System.err.println("This Config file doesn't fit the definition.");	// die Config beinhaltet ein falsches Format, ein Error wird geprintet
				}																		//
			} else {																	//
				System.err.println("This Config file doesn't exists: " + file+".");		 
			}
		}
	}

	/**
	 * HIESP ist der ablauf vom filtern aller links einer Webseite
	 * bis hin zum Download der gewuenschten file
	 */
	public static void HIESP() {
		Link[] links = Download.grabLink(config.WebSite, config.Rel, config.Ending); 	// hier werden alle Links der Website herausgefiltert
		if (links.length > 0) {															// falls mindestens ein Link gefunden wurde
			// Programm Start	
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 			// Startzeit des Programms
			System.out.println("Start: " + timestamp.toString());						//
			
			String storePath = BasicFunctions.getStorePath(links[0]); 					// Generierung des Datei - Namens und Pfades
			String fileName = BasicFunctions.getFileName(links[0]);						// hier wird der Filename erstellt zum abspeichern des Dokumentes
			
			System.out.println(storePath); // COnsole Store Print
			// Exestiert diese File bereits?
			if (!BasicFunctions.containsFile(config.Path, fileName)) {					// Ueberprueft ob der Folder beretis die File beinhaltet 
				saveFile(storePath, links);												// saveFile - Call der Download Funktion
			} else {
				System.out.println("File was already Downloaded");						// Printe dass diese Datei bereits in diesem Pfad exestiert	
			}
			// Abschluss
			timestamp = new Timestamp(System.currentTimeMillis());						// 
			System.out.println("End: " + timestamp.toString());							// Printe Timestamp am ende 
		} else {
			System.err.println("Error: no links found!");								// keine links wurden gefunden
		}

	}

	/**
	 * Aus optischen Gruenden wurde diese Methode aus der Main extrahiert
	 * diese funktion ladet den ersten Link innerhalb des LinkArrays runter
	 * @param file - der Dateipfad
	 */
	public static void saveFile(String file, Link[] links) {
		try {
			System.out.println(links[0].url);
			Download.saveUrl(file, links[0].url); 										// hier geschieht der download
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode kann genutzt werden um alle Links printen zu lassen.
	 */
	static void debugLinks(Link[] links) {
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
