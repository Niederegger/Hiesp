package de.vv.stockstore.loader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebFunctions {
  final static Logger logger = LoggerFactory.getLogger(WebFunctions.class);

  /**
   * saveUrl: this Function is used to download a file from specified url to your wanted path
   * 
   * @param filename
   *          - Path and filename of your wanted download
   * @param urlString
   *          - url of file which you want to download
   * @throws MalformedURLException
   * @throws IOException
   */
  public static void downloadFileFromUrl(final String filename, final String urlString) throws MalformedURLException, IOException {
    BufferedInputStream in = null;            // download stream
    FileOutputStream fout = null;             // schreibe stream
    try {
      in = new BufferedInputStream(new URL(urlString).openStream());    // oeffne den Download stream von der Webseite
      fout = new FileOutputStream(filename);	                          // oeffne den schreibe stream fuer die lokale Datei
      final byte data[] = new byte[1024];
      int count;
      while ((count = in.read(data, 0, 1024)) != -1) {                  // solange "count" Zeichen gelesen werden können (höchstetns 1024)...
        fout.write(data, 0, count);                                     // ... schreibe auch "count" Zeichen in die Datei
      }
    } finally {
      if (in != null) {
        in.close();
      }
      if (fout != null) {
        fout.close();
      }
    }
  }

  /**
   * grabLink: gets url and rel, runs through all links of url and returns
   * array of Links with the same rel or rels starting the same as input rel
   * and link ending of edning
   * 
   * @param url
   *          of Website
   * @param regex
   *          of Links wanted
   * @param ending
   *          ending or url
   * @return array of wanted Links from Url
   */
  public static Link[] grabLink(String url, String regex, String ending) {
    Vector<Link> linkArray = new Vector<Link>();
    logger.info("Fetching {}...", url);
    Document document;
    try {
      document = Jsoup.connect(url).get();
      Elements elements = document.select("a[href]");						// holt alle Elemente vom Typ a[href]
      String currentTextOfUrl; 															    // current Text of URL
      String currentUrl;																        // current URL
      Pattern pattern = Pattern.compile(regex);									// pattern used to match Text
      for (Element element : elements) {												// geht alle Elemente durch
        currentTextOfUrl = element.text();
        currentUrl = element.attr("abs:href");
        if (pattern.matcher(currentTextOfUrl).matches()) {			// stimmt das pattern des Textes mit dem gewuenschten Text ueberein?
        	Link l = new Link(currentUrl, currentTextOfUrl);
          linkArray.add(l);																			// dann behalte diesen link
        }
      }
    } catch (IOException e) {
      logger.error("IOException: {}", e.getMessage());
      e.printStackTrace();
    }
    return linkArray.toArray(new Link[linkArray.size()]);
  }
}
