package start;

/**
 * @author Alexey Gasevic Config is used to configure standart variables
 */
public class Config {
	public boolean debug; // wird genutzt um gewisse print methoden zu
							// unterdruecken
	public String Path; // der Pfad (das Verzeichnis) in welches die Dateie
						// geladen wird
	public String FileName; // unter welchem Namen soll die Datei gespeichert
							// werden
	public String Ending; // welches foramt, welches ending (zb: .csv) hat diese
							// datei, der link der website muss auch damit enden
	public String WebSite; // die url der webseite von der die datei geladen
							// werden soll
	public String Rel; // der regex, welcher den text mit dem der download link
						// verlinkt ist
	public String logName; // obsolete ?
	public String DateRegex; // in welchem format ist das datum in der
								// relationellen text geschrieben
	public int[] dateOrder; // damit ist es moeglich das datumsformat zu aendern

	/**
	 * diese funktion dient der ueberpruefung welche einstellung die config
	 * besitzt
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("debug: " + debug + "\n");
		sb.append("Path: " + Path + "\n");
		sb.append("FileName: " + FileName + "\n");
		sb.append("Ending: " + Ending + "\n");
		sb.append("WebSite: " + WebSite + "\n");
		sb.append("Rel: " + Rel + "\n");
		sb.append("logName: " + logName + "\n");
		sb.append("DateRegex: " + DateRegex + "\n");

		sb.append("dateOrder: {");
		for (int od : dateOrder) {
			sb.append(od + ",");
		}
		sb.append("}");
		return sb.toString();
	}
}
