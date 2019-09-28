package com.wp.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration 
{
	public static String getConfig(String key) throws IOException
	{
		String rootDir = System.getProperty("user.dir");
		
		FileInputStream fileInputStream = new FileInputStream(rootDir+"/config.properties");
		
		Properties properties = new Properties();
		
		properties.load(fileInputStream);
		
		String environment = properties.getProperty(key);
		
		System.out.println(environment);
		
		return environment;
	}
}
