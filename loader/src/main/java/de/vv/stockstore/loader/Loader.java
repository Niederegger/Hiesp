package de.vv.stockstore.loader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class Loader {
	final static Logger logger = LoggerFactory.getLogger(Loader.class);

	static Config config = new Config();

	public static void main(String[] args) {
		// das Programm erwartet genau ein Argument:
		// den Pfad der Config
	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	    // print logback's internal status
	    StatusPrinter.print(lc);
		if (args.length <= 0 || args.length > 1) {
			logger.error("Invalid amount of arguments: {}", args.length);
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
					logger.error("Invalid Config: {}",file);
				} 
			} else {
				logger.error("Invalid Config path and/or name: {}",file);
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
			logger.info("Loader start.");// Programm Start
			String storePath = BasicFunctions.getStorePath(links[0]);
			// Bildung des Dateinamens
			String fileName = BasicFunctions.getFileName(links[0]);
			logger.info("Storing file to: {}", storePath);
			// Exestiert diese File bereits?
			if (!BasicFunctions.containsFile(config.Path, fileName)) {
				saveFile(storePath, links); // Ausfuehrung des Downloads
			} else {
				// Printe dass diese Datei bereits in diesem Pfad exestiert
				logger.info("File was alread downloaded: {}",fileName);
			}
			logger.info("Loader end.");// Abschluss

		} else {
			logger.error("No Links found on site: {}, rel {}", config.WebSite, config.Rel);
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
			logger.info("Found Url: {}", links[0].url);
			Download.saveUrl(file, links[0].url); // hier geschieht der download
		} catch (IOException e) {
			logger.error("IOException: {}",e.getMessage());
		}
	}

	/**
	 * Diese Methode kann genutzt werden um alle Links printen zu lassen.
	 */
	static void debugLinks(Link[] links) {
		if (config.debug) {
			if (links != null) {
				logger.info("Links grabbed: {}", links.length);
				for (Link l : links) {
					logger.info("Link: {}",l.toString());
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
				logger.info(config.toString());
			return true;
		} catch (JsonSyntaxException e) {
			logger.error("JsonSyntaxException: {}",e.getMessage());
		} catch (JsonIOException e) {
			logger.error("JsonIOException: {}",e.getMessage());
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException: {}",e.getMessage());
		}
		return false;
	}
}
