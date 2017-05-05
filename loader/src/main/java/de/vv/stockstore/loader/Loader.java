package de.vv.stockstore.loader;

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
		// das Programm erwartet genau ein Argument:
		// den Pfad der Config
		if (args.length <= 0 || args.length > 1) {
			System.err.println("Use this Programm like this: java -jar hiesp.jar "
					+ "'Path of config'. Config Files end have the eding .conf");
			return;
		} else {
			// es wurde der Cnfig Pfad uebergeben:
			String file = args[0];
			// exestiert die File tatsaehlich?
			if (BasicFunctions.checkConfigPath(file)) {
				// kann die Config geladen werden?
				if (loadConfig(file)) {
					// nun kann versucht werden die Datei zu laden
					loadData();
				} else { 
					System.err.println("This Config file doesn't fit the definition.");
				} 
			} else {
				System.err.println("This Config file doesn't exists: " + file + ".");
			}
		}
	}

	/**
	 * HIESP ist der ablauf vom filtern aller links einer Webseite bis hin zum
	 * Download der gewuenschten file
	 */
	public static void loadData() {
		// Suche auf der Website nach Links die auf das RelRegex passen
		Link[] links = Download.grabLink(config.WebSite, config.Rel, config.Ending);
		if (links.length > 0) { // falls mindestens ein Link gefunden wurde
			// Programm Start
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			System.out.println("Start: " + timestamp.toString()); //
			String storePath = BasicFunctions.getStorePath(links[0]);
			// Bildung des Dateinamens
			String fileName = BasicFunctions.getFileName(links[0]);
			System.out.println(storePath); // COnsole Store Print
			// Exestiert diese File bereits?
			if (!BasicFunctions.containsFile(config.Path, fileName)) {
				saveFile(storePath, links); // Ausfuehrung des Downloads
			} else {
				// Printe dass diese Datei bereits in diesem Pfad exestiert
				System.out.println("File was already Downloaded");
			}
			// Abschluss
			timestamp = new Timestamp(System.currentTimeMillis()); //
			System.out.println("End: " + timestamp.toString());

		} else {
			System.err.println("Error: no links found!");
		}

	}

	/**
	 * Aus optischen Gruenden wurde diese Methode aus der Main extrahiert diese
	 * funktion ladet den ersten Link innerhalb des LinkArrays runter
	 * 
	 * @param file
	 *            - der Dateipfad
	 */
	public static void saveFile(String file, Link[] links) {
		try {
			System.out.println(links[0].url);
			Download.saveUrl(file, links[0].url); // hier geschieht der download
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
	 * 
	 * @param file
	 * @return
	 */
	public static boolean loadConfig(String file) {
		Gson gson = new Gson();
		try { // die Config wird als Json object aus dem Dateipfad geladen und
				// in ein Config Object konvertiert
			config = gson.fromJson(new FileReader(file), Config.class);
			if (config.debug)
				System.out.println(config);
			return true;
		} catch (JsonSyntaxException e) {
			System.err.println("JsonSyntaxException: " + e.getMessage());
		} catch (JsonIOException e) {
			System.err.println("JsonIOException: " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
		}
		return false;
	}
}
