package com.symbio.dashboard.util;

import com.symbio.dashboard.constant.ProjectConst;

import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class PropertiesUtil {

	private static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(PropertiesUtil.class.getResourceAsStream(ProjectConst.PATH));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("The property load failure ");
		}

	}

	public static String getProperty(String key) {
		return prop.getProperty(key);
	}
	
}