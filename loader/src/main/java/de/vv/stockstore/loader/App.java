package de.vv.stockstore.loader;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	final static Logger logger = LoggerFactory.getLogger(App.class);

	static Config config = new Config();

	public static void main(String[] args) {                              // das Programm erwartet genau ein Argument (den Pfad der Config-Datei)

		if (args.length != 1) {																							// wurde dem Programm ein Parameter uebergeben?
			logger.error("Invalid amount of arguments: {}", args.length);
			return;
		}
		// args[0] enthaellt den Pfad zu Config
		// ab hier ist sicher, dass genau ein Parameter uebergeben wurde

		if (BasicFunctions.initConfig(args[0]))                             // initialiserit die config-Variable
			beginProgram();                                                   // nun kann versucht werden die Datei zu laden
		else
			logger.error("Invalid Config: {}", args[0]);
	}

	/**
	 * Sucht auf der Webseite die URL für die gesuchte Datei mittels Regex und führt den Download aus.
	 */
	public static void beginProgram() {
		Link[] links = WebFunctions.grabLink(config.WebSite, config.Rel, config.Ending);  	// Suche auf Website nach Links die auf das RelRegex passen

		if (!(links.length > 0)) {                                                        	// kein Link gefunden
			logger.error("No Links found on site: {}, rel {}", config.WebSite, config.Rel);
			return;
		}

		String file = BasicFunctions.getStorePath(BasicFunctions.getFileName(links[0]));
		logger.info("Loader start. Found Url: {}", links[0].url);													// loggt url
		logger.info("Storing file to: {}", file);																					// loggt dateiPfad

		try {																																								// startet den Download Prozess

			WebFunctions.downloadFileFromUrl(file, links[0].url);       											// hier geschieht der download
			handleZipData(file);																															// Falls die Datei eine Zip ist, wird die File entpackt

		} catch (IOException e) {
			logger.error("IOException: {}", e.getMessage());
		}
	}

	private static void handleZipData(String file) {
		if (config.Ending.toLowerCase().equals(".zip")) {           // wenn eine .zip-Datei geholt wurde...
			String to = file.split("\\.")[0] + ".txt";								// erstellt dem zip-Datei-Namen den Namen fuer die Entpackte-Datei
			BasicFunctions.unZip(file, to, true);                     // ... mit anschliesendem Entapcken
		}
	}

}
