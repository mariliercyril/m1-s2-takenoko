package com.raccoon.takenoko.tool;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The {@code PropertiesFileReader} class defines the Singleton instance for the reader of properties file.
 * <p>The single instance of this class should properly behave in a multi-threaded environment.</p>
 */
public final class PropertiesFileReader {

	private static final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);

	private static final String PROPERTIES_FILE_EXTENSION = "properties";

	private static final String PROPERTIES_FILE_SUFFIX = Separator.POINT.getSign() + PROPERTIES_FILE_EXTENSION;
	private static final String PROPERTIES_FILE_PATH_FORMAT = Separator.SLASH.getSign() + "%s" + PROPERTIES_FILE_SUFFIX;

	private static final String KEY_NAME_FORMAT = "%s" + Separator.POINT.getSign() + "%s";

	// The PropertiesFileReader Singleton should never be instantiated from outside the class.
	private PropertiesFileReader() {
	}

	/**
	 * The {@code PropertiesFileReaderHolder} is the holder of the responsibility
	 * for the only instantiation of the {@code PropertiesFileReader} Singleton.
	 */
    private static class PropertiesFileReaderHolder {

        private static final PropertiesFileReader INSTANCE = new PropertiesFileReader();
    }

    /**
     * Returns the single instance of the {@code PropertiesFileReader}.
     * 
     * @return INSTANCE
     *  the single instance of the PropertiesFileReader
     */
    public static PropertiesFileReader getInstance() {

        return PropertiesFileReaderHolder.INSTANCE;
    }

	/**
	 * Returns the {@code String} value corresponding to a key in the properties file.
	 * 
	 * @param propertiesFileName
	 *  the name of the properties file
	 * @param key
	 * 	the key in the properties file
	 * @param defaultValue
	 *  a default value if the key is not found
	 * 
	 * @return value
	 *  the value, as a <i>String</i>, corresponding to a key in the properties file
	 */
	public String getStringProperty(String propertiesFileName, String key, String defaultValue) {

		String property = "";

		try {
			Properties properties = this.getProperties(String.format(PROPERTIES_FILE_PATH_FORMAT, propertiesFileName));
			property = properties.getProperty(String.format(KEY_NAME_FORMAT, propertiesFileName, key), defaultValue);
		} catch (NullPointerException | IOException e) {
			LOGGER.error(e);
		}

		return property;
	}

	/**
	 * Returns the {@code int} value corresponding to a key in the properties file.
	 * 
	 * @param propertiesFileName
	 *  the name of the properties file
	 * @param key
	 * 	the key in the properties file
	 * @param defaultValue
	 *  a default value if the key is not found
	 * 
	 * @return value
	 *  the value, as an <i>int</i>, corresponding to a key in the properties file
	 */
	public int getIntProperty(String propertiesFileName, String key, int defaultValue) {

		return Integer.parseInt(this.getStringProperty(propertiesFileName, key, String.valueOf(defaultValue)));
	}

	/**
	 * Returns the {@code float} value corresponding to a key in the properties file.
	 * 
	 * @param propertiesFileName
	 *  the name of the properties file
	 * @param key
	 * 	the key in the properties file
	 * @param defaultValue
	 *  a default value if the key is not found
	 * 
	 * @return value
	 *  the value, as an <i>float</i>, corresponding to a key in the properties file
	 */
	public float getFloatProperty(String propertiesFileName, String key, float defaultValue) {

		return Float.parseFloat(this.getStringProperty(propertiesFileName, key, String.valueOf(defaultValue)));
	}

	/**
	 * Returns the properties of the file of which the name is as a parameter.
	 * 
	 * @param propertiesFileName
	 *  the name of the properties file
	 * 
	 * @return properties
	 *  the properties as a list
	 * 
	 * @throws IOException 
	 */
	private Properties getProperties(String propertiesFilePath) throws IOException {

		Properties properties = new Properties();

		InputStream inputStream = this.getClass().getResourceAsStream(propertiesFilePath);
		properties.load(inputStream);
		inputStream.close();

		return properties;
	}

}
