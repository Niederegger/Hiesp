package de.vv.stockstore.loader;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
		File[] listOfFiles = folder.listFiles(); // hole mir alle Dateien aus
													// diesem Pfad
		for (int i = 0; i < listOfFiles.length; i++) { // schau dir all diese
														// Dateien an
			if (listOfFiles[i].isFile()) { // falls die Datei eine Datei ist
				if (listOfFiles[i].getName().equals(file)) // und genauso heisst
															// wie die
															// gewuenschte Datei
					return true; // wird true ausgegeben
			}
		}
		return false; // ansonsten false
	}

	/**
	 * Checks whether the config file has the right ending and exists or not
	 * 
	 * @param path
	 *            - path to config file
	 * @return boolean
	 */
	public static boolean checkConfigPath(String path) {
		if (path.endsWith(".conf")) {
			File f = new File(path);
			return (f.exists() && !f.isDirectory()); // ist diese File eine File
														// und kein Ordner
		}
		return false;
	}

	public static void unZip(String from, String to, boolean deleteFrom) {
		byte[] buffer = new byte[1024];

		try {

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(from));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				File newFile = new File(to);

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = null;
			}

			zis.closeEntry();
			zis.close();

			if(deleteFrom){
				new File(from).delete();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
