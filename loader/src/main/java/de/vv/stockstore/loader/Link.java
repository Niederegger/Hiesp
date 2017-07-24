package de.vv.stockstore.loader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alexey Gasevic
 */
public class Link {
	public String url; 			// Url of this link
	public String rel; 			// The Clickable Text of this Link (seen at website)
	public String rawDate; 	// Raw Date of this rel
	public String date; 		// Formatted Date

	public Link() {}

	/**
	 * erstellt ein object link, welches einer url einen Text zuweißt, welcher
	 * auf einer website zu finden ist
	 * 
	 * @param url
	 * @param rel
	 */
	public Link(String url, String rel) {
		this.url = url;
		this.rel = rel;
		if (App.config.useWebsiteDate) {
			rawDate = BasicFunctions.match(rel, App.config.DateRegex);
			convertDate();
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date currentDate = new Date();
			date = dateFormat.format(currentDate);
		}
	}

	/**
	 * this function converts raw date into ordered date, setup for order is
	 * contained in Config
	 */
	void convertDate() {
		if (rawDate == null) {
			String[] dateSplitted = rawDate.split(" ");
			if (!(dateSplitted.length > 0)) {
			}
			date = "";
			String datePart;
			for (int i : App.config.dateOrder) {
				if (i >= 0 && i < dateSplitted.length) {
					datePart = dateSplitted[i];
					if (datePart.contains(":"))
						datePart = datePart.replace(':', '_');
					date += datePart + (i != dateSplitted.length - 1 ? "_" : "");
				}
			}
		}
	}

	@Override
	public String toString() {
		return "* a: " + url + " (" + rel + ") " + date;
	}
}
