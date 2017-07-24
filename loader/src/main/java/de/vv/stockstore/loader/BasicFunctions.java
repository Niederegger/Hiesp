package de.vv.stockstore.loader;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 *         This class is used to bundle basic functions needed for this project
 */
public class BasicFunctions {

	//--------------------------------------------------------------------------------------------------------------------
	// Config
	//--------------------------------------------------------------------------------------------------------------------
	
	/**
	 * initialisiert die Config Datei
	 * 
	 * @param file
	 * @return true bei Erfolg, false bei Fehler
	 */
	public static boolean initConfig(String file) {

		if (!checkConfigPath(file)) {                        // ueberpruefe ob config exestiert
			App.logger.error("Invalid Config path and/or name: {}", file);
			return false;
		}

		Gson gson = new Gson();
		try {  																															// lade das Json Object aus der Text Datei in die config-Variable
			App.config = gson.fromJson(new FileReader(file), Config.class);		// setzt die config-Variable in App.config
			if (App.config.debug)
				App.logger.info(App.config.toString());
			return true;
		} catch (Exception e) {
			App.logger.error("Exception: {}", e.getMessage());
		}
		return false;																												// Config konnte nicht erstellt werden
	}
	
	/**
	 * Checks whether the config file has the right ending and exists or not
	 * 
	 * @param path
	 *          - path to config file
	 * @return boolean
	 */
	public static boolean checkConfigPath(String path) {
		if (path.endsWith(".conf")) {
			File f = new File(path);
			return (f.exists() && !f.isDirectory());        // die Datei existiert und ist kein Ordner
		}
		return false;
	}
	
	//--------------------------------------------------------------------------------------------------------------------
	// Path
	//--------------------------------------------------------------------------------------------------------------------
	
	public static String getStorePath(String fileName) {
		return App.config.Path + fileName;
	}

	public static String getFileName(Link link) {
		return App.config.FileName + "_" + link.date + App.config.Ending;
	}
	
	/**
	 * checks whether this path contains the given filename or not
	 * 
	 * @param path
	 * @param file
	 * @return boolean
	 */
	public static boolean containsFile(String path, String file) {
		File folder = new File(path);                     // hole mir diesen Ordner des Pfades
		File[] listOfFiles = folder.listFiles();          // hole mir alle Dateien aus diesem Ordner
		for (int i = 0; i < listOfFiles.length; i++) {    // schau dir all diese Dateien an
			if (listOfFiles[i].isFile()) {                  // falls die Datei eine Datei ist
				if (listOfFiles[i].getName().equals(file))    // und genauso heisst wie die gewuenschte Datei
					return true;                                // wird true ausgegeben
			}
		}
		return false;                                     // alles durch und nichts gefunden: false zurück geben
	}

	//--------------------------------------------------------------------------------------------------------------------
	// Strings
	//--------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * use trim to cut your String to a wished width
	 * 
	 * @param s
	 *          - your String
	 * @param width
	 *          - wanted width
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
	 *          - some message
	 * @param p
	 *          - pattern
	 */
	public static String match(String message, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		if (m.find()) {
			return m.group(0);
		}
		return null;
	}

	//--------------------------------------------------------------------------------------------------------------------
	// Zip
	//--------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Da Struktierte Produkte eine File innerhalb einer Zip-Datei ist, wurde diese Funktion geschrieben um diese Text Datei direkt in
	 * das Verzeichnis aus der Zip zu ziehen
	 * 
	 * @param from
	 *          Zip-Datei
	 * @param to
	 *          entapckungsOrt + DateiNamen
	 * @param deleteFrom
	 *          True, wenn die Zip-Datei anschliessend gelöscht werden soll
	 */
	public static void unZip(String from, String to, boolean deleteFrom) {
		byte[] buffer = new byte[1024];

		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(from));    // zis - ZipInputStream
			ZipEntry ze = zis.getNextEntry();                                      // ze - ZipEntry

			while (ze != null) {
				File newFile = new File(to);

				FileOutputStream fos = new FileOutputStream(newFile);		// fos - FileOutputStrem

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = null;
			}

			zis.closeEntry();
			zis.close();

			if (deleteFrom) {								// Loesche die Zip-Datei wenn gewuenscht
				new File(from).delete();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	//--------------------------------------------------------------------------------------------------------------------
	// Debug
	//--------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Diese Methode kann genutzt werden um alle Links printen zu lassen.
	 */
	static void debugLinks(Link[] links) {
		if (App.config.debug) {
			if (links != null) {
				App.logger.info("Links grabbed: {}", links.length);
				for (Link l : links) {
					App.logger.info("Link: {}", l.toString());
				}
			}
		}
	}
}
