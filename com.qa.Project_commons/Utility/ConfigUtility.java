package Utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtility {
	
	public static String getConfigProperty(String fileName,String property) throws IOException
	{
		Properties properties =new Properties();
		
		FileReader reader= new FileReader(System.getProperty("user.dir")+fileName);
		properties.load(reader);
		return properties.getProperty(property);
		
	}

}
