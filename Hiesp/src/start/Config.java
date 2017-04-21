package start;

import java.util.regex.Pattern;

/**
 * @author Alexey Gasevic Config is used to configure standart variables
 */
public class Config {
	public boolean debug;
	public String Path;
	public String FileName;
	public String Ending;
	public String WebSite;
	public String Rel;
	public String logName;
	public String DateRegex;
	public int[] dateOrder;
	
	 @Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("debug: " + debug + "\n" );
		 sb.append("Path: " + Path + "\n" );
		 sb.append("FileName: " + FileName + "\n" );
		 sb.append("Ending: " + Ending + "\n" );
		 sb.append("WebSite: " + WebSite + "\n" );
		 sb.append("Rel: " + Rel + "\n" );
		 sb.append("logName: " + logName + "\n" );
		 sb.append("DateRegex: " + DateRegex + "\n" );
		 
		 sb.append("dateOrder: {" );
		 for(int od : dateOrder){
			 sb.append(od+",");
		 }
		 sb.append("}");
		return sb.toString();
	}
}
