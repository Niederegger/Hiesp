package de.vv.stockstore.loader;

/**
 * @author Alexey Gasevic
 */
public class Link {
	public String url; // Url of this link
	public String rel; // The Clickable Text of this Link (seen at website)
	public String rawDate; // Raw Date of this rel
	public String date; // Formatted Date

	public Link() {
	}

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
		rawDate = BasicFunctions.match(rel, Loader.config.DateRegex);
		System.out.println(rawDate);
		convertDate();
	}

	/**
	 * this function converts raw date into ordered date, setup for order is
	 * contained in Config
	 */
	void convertDate() {
		if (rawDate != null) {
			String[] snipped = rawDate.split(" ");
			if (snipped.length > 0) {
				date = "";
				String s;
				for (int i : Loader.config.dateOrder) {
					if (i >= 0 && i < snipped.length) {
						s = snipped[i];
						if (s.contains(":"))
							s = s.replace(':', '_');
						date += s + (i != snipped.length - 1 ? "_" : "");
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return "* a: " + url + " (" + rel + ") " + date;
	}
}
