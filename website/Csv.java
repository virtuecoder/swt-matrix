

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Constitutes the logic of reading and writing to a CSV input/output.
 * Once defined can be used to read/write many CSV files ensuring consistent rules.
 *
 * @author Jacek
 * @created Jun 28, 2009
 */
public class Csv {
	private static final String LINE_END = System.getProperty("line.separator");

	/** Default quote character. Can be changed. */
	public char quotator = '"';
	/** Default delimiting character. Can be changed. */
	public char delimiter = ',';
	/** Specifies whether the output should contain column headers. */
	public boolean withHeaders = false;
	
	private BufferedReader breader;

	
	
	/**
	 * Read into cells from the given reader. 
	 * The content of the cells is cleared before reading.
	 * Headers of columns in cells are not changed by reading.
	 * Closes the reader at the end.
	 * 
	 * @param reader
	 * @param cells
	 * @throws IOException
	 */
	public List<String[]> read(Reader reader) throws IOException {
		ArrayList<String[]> list = new ArrayList<String[]>(); 
		this.breader = new BufferedReader(reader);
		
		String[] line;
		while ((line = readLine()) != null) {
			list.add(line);
		}
		breader.close();
		return list;
	}

	private String[] readLine() throws IOException {
		String line = breader.readLine();
        if (line == null) return null;
        
        ArrayList tokens = new ArrayList();
        StringBuffer sb = new StringBuffer();
        boolean inQuotes = false;
        do {
        	if (inQuotes) {
                // continuing a quoted section, re-append newline
                sb.append(LINE_END);
                line = breader.readLine();
                if (line == null)
                    break;
            }
            for (int i = 0; i < line.length(); i++) {

                char c = line.charAt(i);
                if (c == quotator) {
                	// this gets complex... the quote may end a quoted block, or escape another quote.
                	// do a 1-char lookahead:
                	if( inQuotes  // we are in quotes, therefore there can be escaped quotes in here.
                	    && line.length() > (i+1)  // there is indeed another character to check.
                	    && line.charAt(i+1) == quotator ){ // ..and that char. is a quote also.
                		// we have two quote chars in a row == one quote char, so consume them both and
                		// put one on the token. we do *not* exit the quoted text.
                		sb.append(line.charAt(i+1));
                		i++;
                	}else{
                		inQuotes = !inQuotes;
                		// the tricky case of an embedded quote in the middle: a,bc"d"ef,g
                		if(i>1 //not on the begining of the line
                				&& line.charAt(i-1) != this.delimiter //not at the begining of an escape sequence 
                				&& line.length()>(i+1) &&
                				line.charAt(i+1) != this.delimiter //not at the	end of an escape sequence
                		){
                			sb.append(c);
                		}
                	}
                } else if (c == delimiter && !inQuotes) {
                    tokens.add(sb.toString());
                    sb = new StringBuffer(); // start work on next token
                } else {
                    sb.append(c);
                }
            }
        } while (inQuotes);
        tokens.add(sb.toString());
        return (String[]) tokens.toArray(new String[0]);
    }
}
