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

  public static void main(String[] args) {                                  // das Programm erwartet genau ein Argument (den Pfad der Config-Datei)
    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    StatusPrinter.print(lc);                                                // print logback's internal status

    if (args.length != 1) {
      logger.error("Invalid amount of arguments: {}", args.length);
      return;
    }

    String file = args[0];                                              // ab hier ist sicher, dass genau ein Parameter uebergeben wurde

    if (!BasicFunctions.checkConfigPath(file)) {                        // existiert die Datei tatsaechlich?
      logger.error("Invalid Config path and/or name: {}", file);
      return;
    }

    if (loadConfig(file))                                               // kann die Config-Datei geladen werden?
      loadData();                                                       // nun kann versucht werden die Datei zu laden
    else
      logger.error("Invalid Config: {}", file);
  }

  /**
   * Sucht auf der Webseite die URL für die gesuchte Datei mittels Regex und führt den Download aus.
   */
  public static void loadData() {
    Link[] links = Download.grabLink(config.WebSite, config.Rel, config.Ending);  // Suche auf Website nach Links die auf das RelRegex passen

    if (links.length > 0) {                                                       // falls mindestens ein Link gefunden wurde
      logger.info("Loader start.");

      String fileName = config.FileName + "_" + links[0].date + config.Ending;    // Bildung des Dateinamens
      String storePath = config.Path + fileName;                                  // Pfad+Dateiname zusammengesetzt 

      logger.info("Storing file to: {}", storePath);

      if (!BasicFunctions.containsFile(config.Path, fileName)) {          // Wenn die Datei noch nicht existiert...
        saveFile(storePath, links);                                       // ... dann Download ausführen
      } else {
        logger.info("File was alread downloaded: {}", fileName);          // sonst Printe dass diese Datei bereits in diesem Pfad existiert
      }
      logger.info("Loader end.");

    } else {
      logger.error("No Links found on site: {}, rel {}", config.WebSite, config.Rel);
    }
  }

  /**
   * Aus optischen Gruenden wurde diese Methode aus der Main extrahiert diese
   * funktion ladet den ersten Link innerhalb des LinkArrays runter
   * 
   * @param file
   *          - der Dateipfad
   */
  public static void saveFile(String file, Link[] links) {
    try {
      logger.info("Found Url: {}", links[0].url);

      Download.saveUrl(file, links[0].url);                       // hier geschieht der download

      if (config.Ending.toLowerCase().equals(".zip")) {           // wenn eine .zip-Datei geholt wurde...
        String to = file.split("\\.")[0] + ".txt";
        BasicFunctions.unZip(file, to, true);                     // ... dann wird siehier entpackt
      } else {
        System.out.println("no Zip: " + config.Ending);
      }
    } catch (IOException e) {
      logger.error("IOException: {}", e.getMessage());
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
          logger.info("Link: {}", l.toString());
        }
      }
    }
  }

  /**
   * loading Config
   * 
   * @param file
   * @return true bei Erfolg, false bei Fehler
   */
  public static boolean loadConfig(String file) {
    Gson gson = new Gson();
    try {                                    // die Config wird als Json object aus dem Dateipfad geladen und in ein Config Object konvertiert
      config = gson.fromJson(new FileReader(file), Config.class);
      if (config.debug)
        logger.info(config.toString());
      return true;
    } catch (JsonSyntaxException e) {
      logger.error("JsonSyntaxException: {}", e.getMessage());
    } catch (JsonIOException e) {
      logger.error("JsonIOException: {}", e.getMessage());
    } catch (FileNotFoundException e) {
      logger.error("FileNotFoundException: {}", e.getMessage());
    }
    return false;
  }
}
