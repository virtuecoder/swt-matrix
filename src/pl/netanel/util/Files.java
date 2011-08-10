package pl.netanel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * File utilities.
 *
 * @author Jacek
 * @created Jan 3, 2010
 */
public class Files {
	private static final String LINE_END = System.getProperty("line.separator");
	
	/* Block size that we want to read in one time. */
	private static final int READ_BLOCK = 8192;

	/*
	 * Read all from stream, using nio.
	 * 
	 * @param is source stream.
	 * 
	 * @return result byte array that read from source
	 * 
	 * @throws IOException by {@code Channel.read()}
	 */
	public static byte[] readToEnd(InputStream is) throws IOException {
		// create channel for input stream
		ReadableByteChannel bc = Channels.newChannel(is);
		ByteBuffer bb = ByteBuffer.allocate(READ_BLOCK);

		while (bc.read(bb) != -1) {
			bb = resizeBuffer(bb); // get new buffer for read
		}
		byte[] result = new byte[bb.position()];
		bb.position(0);
		bb.get(result);

		return result;
	}

	private static ByteBuffer resizeBuffer(ByteBuffer in) {
		ByteBuffer result = in;
		if (in.remaining() < READ_BLOCK) {
			// create new buffer
			result = ByteBuffer.allocate(in.capacity() * 2);
			// set limit to current position in buffer and set position to zero.
			in.flip();
			// put original buffer to new buffer
			result.put(in);
		}

		return result;
	}
	
	/**
	 * Writes the content of the string to the file.
	 * @param file to write the text to 
	 * @param content to store in the file
	 * @throws IOException
	 */
	public static void write(File file, String content) throws IOException {
		Preconditions.checkNotNull(file);

		//use buffering
		Writer output = new BufferedWriter(new FileWriter(file));
		try {
			//FileWriter always assumes default encoding is OK!
			output.write(content);
		}
		finally {
			output.close();
		}
	}
	
	public static void write(File file, String content, String encoding) throws IOException {
		Preconditions.checkNotNull(file);
		
		//use buffering
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), encoding);
		try {
			//FileWriter always assumes default encoding is OK!
			output.write(content);
		}
		finally {
			output.close();
		}
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
	
	
	
	
	public static File createTempFile() throws IOException {
		File file = File.createTempFile("tt_", ".tmp");
		file.deleteOnExit();
		return file;
	}

	public static void copyFile(String in, String out) throws IOException {
		copyFile(new FileInputStream(in),new FileOutputStream(out));
	}
	
	public static void copyFile(FileInputStream in, FileOutputStream out) throws IOException {
		FileChannel inChannel = in.getChannel();
		FileChannel outChannel = out.getChannel();
		try {
			if (System.getProperty("os.name").startsWith("Windows")) { //$NON-NLS-1$ //$NON-NLS-2$
				// magic number for Windows, 64Mb - 32Kb
				// http://forum.java.sun.com/thread.jspa?threadID=439695&messageID=2917510
				int maxCount = 64 * 1024 * 1024 - 32 * 1024;
				long size = inChannel.size();
				long position = 0;
				while (position < size)
					position += inChannel.transferTo(position, maxCount,
							outChannel);
			} else {
				// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4643189
				inChannel.transferTo(0, inChannel.size(), outChannel);
			}
		} 
		finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
	}
	
	public static void mergeTo(File target, File ...file) {
		try {
			FileInputStream[] a = new FileInputStream[file.length];
			for (int i = 0; i < a.length; i++) {
				a[i] = new FileInputStream(file[i]);
			}
			mergeTo(new FileOutputStream(target), a);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
			
		}
		
	}
	public static void mergeTo(FileOutputStream out, FileInputStream ...in) throws IOException {
		FileChannel outChannel = out.getChannel();
		for (int i = 0; i < in.length; i++) {
			FileChannel inChannel = in[i].getChannel();
			try {
				if (System.getProperty("os.name").startsWith("Windows")) { //$NON-NLS-1$ //$NON-NLS-2$
					// magic number for Windows, 64Mb - 32Kb
					// http://forum.java.sun.com/thread.jspa?threadID=439695&messageID=2917510
					int maxCount = 64 * 1024 * 1024 - 32 * 1024;
					long size = inChannel.size();
					long position = 0;
					while (position < size)
						position += inChannel.transferTo(position, maxCount,
								outChannel);
				} else {
					// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4643189
					inChannel.transferTo(0, inChannel.size(), outChannel);
				}
			}
			finally {
				if (inChannel != null) inChannel.close();
			}
		} 
		if (outChannel != null) outChannel.close();
	}

	public static void copyFile(File file1, File file2) throws IOException {
		copyFile(file1.getAbsolutePath(), file2.getAbsolutePath());
	}
	
	
	/**
	 * Creates directory and checks the result of operation To avoid findbugs reports. 
	 * @param file
	 * @return true if operation was successful
	 */
	public static boolean mkdir(File ...file) {
		boolean b = true;
		for (int i = 0; i < file.length; i++) {
			b = b && file[i] != null && file[i].mkdir();			
		}
		return b;
	}

	/**
	 * Creates file and its parent folder if needed.
	 * 
	 * @param file to create
	 * @return true if operation was successful
	 */
	public static boolean create(File file) {
		try {
			mkdir(file.getParentFile());
			return file.createNewFile();
		} catch (IOException e) {
			return false;
			
		}
	}
	
	/**
	 * Creates file and its parent folder if needed.
	 * 
	 * @param file to create
	 * @return true if operation was successful
	 */
	public static File create(String path) {
		try {
			File file = new File(path);
			mkdir(file.getParentFile());
			return file.createNewFile() ? file : null;
		} catch (IOException e) {
			return null;
			
		}
	}
	
	/**
	 * Creates file and its parent folder if needed.
	 * 
	 * @param file to create
	 * @return true if operation was successful
	 */
	public static File create(File parent, String path) {
		try {
			File file = new File(parent, path);
			mkdir(file.getParentFile());
			return file.createNewFile() ? file : null;
		} catch (IOException e) {
			return null;
			
		}
	}

	/**
	 * Delete files and directories recursively. 
	 * Throws exception if cannot delete.
	 * 
	 * @param file
	 * @throws RuntimeException
	 */
	public static void delete(File ...file) {
		List<File> list = new ArrayList<File>();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory()) {
				try {
					delete(file[i].listFiles());
				} catch (FileDeleteException e) {
					list.addAll(e.files);
				}
			}

			else if (!file[i].delete())
				list.add(file[i]);
		}
		if (!list.isEmpty())
			throw new FileDeleteException(list);
	}
	
	/**
	 * Notifies file deletion failure. 
	 * files field contains the list of the files that could not be deleted.
	 *
	 * @author Jacek
	 * @created Jan 3, 2010
	 */
	public static class FileDeleteException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public final List<File> files;
		public FileDeleteException(List<File> list) {
			this.files = list;
		}
		
		@Override
		public String getMessage() {
			return "Could not delete "+files;
		}
	}

	/**
	 * Deletes files and directories recursively. 
	 * Does not throw exceptions if delete fails. 
	 * 
	 * @param files to delete
	 * @return false if any of the files cannot be deleted and true otherwise
	 */
	public static boolean deleteGracefully(File ...files) {
		boolean result = true;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory() && !deleteGracefully(file.listFiles()))
				result = false;
			else if (!file.delete()) 
				result = false;
		}
		return result;
	}

	public static void writeLines(File file, List<String> lines) throws IOException {
		Preconditions.checkNotNull(file);

		//use buffering
		Writer output = new BufferedWriter(new FileWriter(file));
		try {
			//FileWriter always assumes default encoding is OK!
			for (String line: lines) {
				output.write(line);
				output.write("\n");
			}
		}
		finally {
			output.close();
		}	
	}
	

	public static void writeLinesUTF(File file, List<String> lines) throws IOException {
		Preconditions.checkNotNull(file);

		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(0);
		try {
			for (String line: lines) {
				raf.writeUTF(line);
			}
		}
		finally {
			raf.close();
		}	
	}

	public static List<String> readLinesUTF(File file, long start, long end) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "r");
			if (end == -1) end = raf.length();
			raf.seek(start);
			while (raf.getFilePointer() < end) {
				list.add(raf.readUTF());
			}
		} finally {
			if (raf != null) raf.close();
		}
		return list;
	}
	

	public static void write(Writer writer, Object ...o) throws IOException {
		for (int i = 0; i < o.length; i++) {
			Object o2  = o[i];
      writer.append(o2 == null ? "" : o2.toString());
		}
	}

	
	public static String getExtesion(File file) {
		String name = file.getName();
		int index = name.lastIndexOf('.');
		return index < 0 || index == name.length() - 1 ? "" : name.substring(index + 1, name.length());
	}

	public static String getBaseName(File file) {
		String name = file.getName();
		int index = name.lastIndexOf('.');
		return index < 0 || index == name.length() - 1 ? name : name.substring(0, index);
	}

	public static Properties readProperties(File file) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		FileInputStream input = new FileInputStream(file);
    properties.load(input);
    input.close();
		return properties;
	}

	public static void wrtieProperties(File file, Properties properties) throws FileNotFoundException, IOException {
		FileOutputStream output = new FileOutputStream(file);
    properties.store(output, "");
    output.close();
	}

	
}