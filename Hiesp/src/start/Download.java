package start;

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

public class Download {

	/**
	 * saveUrl: this Function is used to download a file from specified url to
	 * your wanted path
	 * 
	 * @param filename
	 *            - Path and filename of your wanted download
	 * @param urlString
	 *            - url of file which you want to download
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void saveUrl(final String filename, final String urlString) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);
			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
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
	 * garpLink: gets url and rel, runs through all links of url and returns
	 * array of Links with the same rel or rels starting the same as input rel
	 * and link ending of edning
	 * 
	 * @param url
	 *            of Website
	 * @param regex
	 *            of Links wanted
	 * @param ending
	 *            ending or url
	 * @return array of wanted Links from Url
	 */
	public Link[] grabLink(String url, String regex, String ending) {
		Vector<Link> LinkArray = new Vector<Link>();
		BasicFunctions.print("Fetching %s...", url);
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");										// hohlt alle Elemente vom Typ a[href]
			String currentRel; 	// current Text of URL
			String curl;		// current URL
			Pattern p = Pattern.compile(regex);	// pattern used to match Text
			for (Element link : links) {												// geht alle Elemente durch
				currentRel = link.text();	
				curl = link.attr("abs:href");
				if (curl.endsWith(ending)){												// endet diese Url mit dem erwünschten DateiTp?
					if(p.matcher(currentRel).matches()){								// stimmt das pattern des Textes mit dem gewuenschten Text ueberein?
						LinkArray.add(new Link(curl, currentRel));						// dann behalte diesen link
					}
				}
					
			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}
		return LinkArray.toArray(new Link[LinkArray.size()]);
	}
}
