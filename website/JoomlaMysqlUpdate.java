import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoomlaMysqlUpdate {
	private static final String LINE_END = System.getProperty("line.separator");
	
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/netanel15";
		Connection con = DriverManager.getConnection(url, "root", "");
		try {
			JoomlaMysqlUpdate app = new JoomlaMysqlUpdate();
			app.features(con);
//			app.snippets(con);
		} 
		finally {
			con.close();
		}
	}

	void features(Connection con) throws Exception {
		Csv csv = new Csv();
		List<String[]> lines = csv.read(new FileReader("website/features.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='data' cellspacing='1'>");
		appendRow(sb, "th", lines.get(0));
		for (int i = 1, imax = lines.size(); i < imax; i++) {
			appendRow(sb, "td", lines.get(i));
		}
		sb.append("</table>");
		
		// Get the current content and replace with the new list of snippets
		int id = 5;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select introtext from jos_content where id = "+id);
		rs.next();
		Pattern p = Pattern.compile(
				"(.*<!-- generated start -->)(.*)(<!-- generated end -->.*)", 
				Pattern.DOTALL);
		String content = rs.getString("introtext");
		rs.close();

		Matcher matcher = p.matcher(content);
		if (matcher.find()) {
			// Update content
			PreparedStatement stmt2 = con.prepareStatement(
					"update jos_content set introtext = ? where id = "+id);
			stmt2.setString(1, matcher.replaceFirst("$1"+sb+"$3"));
			stmt2.execute();
		}
	}
	
	void appendRow(StringBuilder sb, String tag, String[] row) {
		sb.append("<tr>");
		for (int i = 0; i < row.length; i++) {
			String s = row[i];
			boolean h = i == 0 && s.startsWith("+");
			sb.append("<").append(tag);
			if (h) { 
				sb.append(" colspan='7' class='header'>");
				sb.append("<h3>"); s = s.substring(1);
				sb.append(s);
				sb.append("</h3>");
				sb.append("</").append(tag).append(">");
				break;
			} else {
				if (i == 0) sb.append(" style='white-space: nowrap'");
				sb.append(">");
				sb.append(s);
				sb.append("</").append(tag).append(">");
			}
		}
		sb.append("</tr>");
	}

	void snippets(Connection con) throws Exception {
		// Get the list of snippets
		StringBuilder sb = new StringBuilder();
		for (File file: new File("src_demo/pl/netanel/swt/matrix/snippets").listFiles()) {
			
			if (file.getName().endsWith(".java")) {
				// Get the description
				Pattern p = Pattern.compile(".*\\/\\*\\*(.*?)(@|\\*\\/).*", Pattern.DOTALL);
				Matcher matcher = p.matcher(readText(file));
				if (matcher.find()) {
					String description = matcher.group(1);
					description = description.replace("*", "").replaceAll("\\s{2,}", "").trim();
					sb.append("<li><a href='snippets/"+file.getName()+"'>")
						.append(description).append("</a></li>");
				}
			} else {
				if (sb.length() > 0) sb.append("</ul>");
				sb.append("<p>").append(readText(file)).append("</p><ul>");
			}
		}
		sb.append("</ul>");
		
		// Get the current content and replace with the new list of snippets
		int id = 11;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select introtext from jos_content where id = "+id);
		rs.next();
        String s = rs.getString("introtext").replaceFirst(
    		"(<!-- generated start -->)(.*)(<!-- generated end -->)", "$1"+sb+"$3");
		rs.close();

		// Update content
		PreparedStatement stmt2 = con.prepareStatement(
				"update jos_content set introtext = ? where id = "+id);
		stmt2.setString(1, s);
		stmt2.execute();
	}
	
	
	
	
	
	/**
	 * Reads the content of the text file into the string.
	 * @param file to read
	 * @return content
	 * @throws IOException
	 */
	public static String readText(File file) throws IOException {
		return readText(new FileReader(file));
	}
	
	public static String readText(File file, String encoding) throws IOException {
		return readText(new InputStreamReader(new FileInputStream(file), encoding));
	}
	
	public static String readText(InputStreamReader stream) throws IOException {
		StringBuilder content = new StringBuilder();
		
		BufferedReader input = new BufferedReader(stream);
		try {
			String line = null; // not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line
			 * MINUS the newline. it returns null only for the END of the
			 * stream. it returns an empty String if two newlines appear in
			 * a row.
			 */
			while ((line = input.readLine()) != null) {
				content.append(line);
				content.append(LINE_END);
			}
		} finally {
			input.close();
		}
		int len = LINE_END.length();
		if (content.length() > len) 
			content.delete(content.length() - len, content.length());
		return content.toString();
	}
	
	/*
	* Reads the lines of a text file into a list of strings.
	* @param file to read
	* @return content
	* @throws IOException
	*/
	public static List<String> readLines(File file) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		
		BufferedReader input = new BufferedReader(new FileReader(file));
		try {
			String line = null; // not declared within while loop
			while ((line = input.readLine()) != null) {
				list.add(line);
			}
		} finally {
			input.close();
		}
		return list;
	}
	
}
