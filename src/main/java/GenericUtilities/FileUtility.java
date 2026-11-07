package GenericUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {
	/**
	 * This method read data from property file
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */

	public String readDataFromPropertyFile(String key, String FilePath) throws IOException {

		FileInputStream f = new FileInputStream(FilePath);

		Properties prop = new Properties();
		prop.load(f);
		String val = prop.getProperty(key);
		return val;

	}

	

}
