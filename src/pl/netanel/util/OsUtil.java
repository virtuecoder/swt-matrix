package pl.netanel.util;

//import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.eclipse.swt.program.Program;

public class OsUtil {
	public static enum Platform {WINDOWS, MACOS, LINUX, SOLARIS};
	
	
	public static void openURL(String url) {
		if (!(url.startsWith("http://") || url.startsWith("https://"))) //$NON-NLS-1$ //$NON-NLS-2$
			url = "http://" + url; //$NON-NLS-1$
		
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Windows")) {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			}
			else if (osName.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] {String.class});
				openURL.invoke(null, new Object[] {url});
			}
			else { //assume Unix or Linux
				String[] browsers = {
						"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++) {
					if (Runtime.getRuntime().exec(
							new String[] {"which", browsers[count]}).waitFor() == 0)
						browser = browsers[count];
				}
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] {browser, url});
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

//	public static boolean canEditFile() {
//		if (!Desktop.isDesktopSupported()) {
//			return false;
//		}
//
//		Desktop desktop = Desktop.getDesktop();
//		if (!desktop.isSupported(Desktop.Action.EDIT)) {
//			return false;
//		}
//
//		return true;
//	}

	/**
	 * Open system editor
	 * @param file
	 * @throws IOException
	 */
	public static void editFile(final File file) throws IOException {
		
		Program.launch( file.getAbsolutePath() ); 
		
//		That does not work, displays DgShellX E_FAIL
//		if (!canEditFile())
//			throw new IOException("Edit command is not supported by the operating system");
//
//		Desktop.getDesktop().edit(file);

//		try {
//			String osName = System.getProperty("os.name"); //$NON-NLS-1$
//			if (osName.startsWith("Windows")) { //$NON-NLS-1$
//				Runtime.getRuntime().exec("RUNDLL32.EXE SHELL32.DLL,OpenAs_RunDLL "+file.getAbsolutePath()); //$NON-NLS-1$
//			}
//			else
//				throw new UnsupportedOperationException();
//
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	public static Object getDefaultPath() {
		return System.getProperty("user.home");
	}
	
	public static Object getCommonPath() {
		return System.getProperty("user.home");
	}

	public static File getAllUserDirectory(final String applicationName) {
		File file;
		switch (getPlatform()) {
		case LINUX:
		case SOLARIS:
			file = new File("/var/lib", applicationName + '/');
			break;
		case WINDOWS:
			String s = System.getenv("ProgramData");
			if (s == null) {
				s = System.getenv("APPDATA");
				s = s.substring(s.lastIndexOf(System.getProperty("file.separator")), s.length());
				file = new File(System.getenv("ALLUSERSPROFILE"), s + "\\" + applicationName);  
			} else {
				file = new File(s, applicationName);
			}
			break;
		case MACOS:
			file = new File("/Library/Application Support/" + applicationName);
			break;
		default:
			return new File("." + applicationName);
		}
		if (!file.exists())
			if (!file.mkdirs())
				throw new RuntimeException("The user application directory could not be created: " + file);
		return file;
	}
	
	/**
	 * Returns the appropriate working directory for storing application data. The result of this method is platform
	 * dependant: On linux, it will return ~/applicationName, on windows, the working directory will be located in the
	 * user's application data folder. For Mac OS systems, the working directory will be placed in the proper location
	 * in "Library/Application Support".
	 * <p/>
	 * This method will also make sure that the working directory exists. When invoked, the directory and all required
	 * sub-folders will be created.
	 *
	 * @param applicationName Name of the application, used to determine the working directory.
	 * @return the appropriate working directory for storing application data.
	 */
	public static File getUserDirectory(final String applicationName) {
		final String userHome = System.getProperty("user.home", ".");
		final File file;
		switch (getPlatform()) {
		case LINUX:
		case SOLARIS:
			file = new File(userHome, '.' + applicationName + '/');
			break;
		case WINDOWS:
			final String applicationData = System.getenv("APPDATA");
			if (applicationData != null)
				file = new File(applicationData, applicationName);
			else
				file = new File(userHome, '.' + applicationName);
			break;
		case MACOS:
			file = new File(userHome, "Library/Application Support/" + applicationName);
			break;
		default:
			return new File("." + applicationName);
		}
		if (!file.exists())
			if (!file.mkdirs())
				throw new RuntimeException("The user application directory could not be created: " + file);
		return file;
	}
	
	public static Platform getPlatform() {
		final String sysName = System.getProperty("os.name").toLowerCase();
		return 
			sysName.contains("windows") ? Platform.WINDOWS : 
	        sysName.contains("mac") ? Platform.MACOS :
	        sysName.contains("linux") ? Platform.LINUX :
	        sysName.contains("solaris") ? Platform.SOLARIS : null;
	}
	

	
	public static String getNativeFileEncoding() {
		switch (getPlatform()) {
		case MACOS: return "MacCentralEurope";
		case WINDOWS: return "Cp1250";
		default : return "UTF8";
		}
	}
	
	
	
	
}
