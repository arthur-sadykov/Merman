package merman.util.dao;

import merman.alert.AlertFX;
import merman.util.dao.exception.DAOConfigurationException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Arthur Sadykov
 */
public class DAOProperties {


    private static final String PROPERTIES_FILE = "dao.properties";
    private static final String PROPERTIES_FILE_PATH = "/merman/daoproperties/dao.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        InputStream propertiesFile = DAOProperties.class.getResourceAsStream(PROPERTIES_FILE_PATH);
        if (propertiesFile == null) {
            throw new DAOConfigurationException("Файл " + PROPERTIES_FILE + " отсутствует на пути к классам.");
        }
        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException ex) {
            AlertFX.showError("Не удается прочитать файл " + PROPERTIES_FILE + ".", ex);
            throw new DAOConfigurationException("Не удается прочитать файл " + PROPERTIES_FILE + ".", ex);
        }
    }

    private final String specificKey;

    public DAOProperties(String specificKey) {
        this.specificKey = specificKey;
    }

    public String getProperty(String key, boolean mandatory) throws DAOConfigurationException {
        String fullKey = specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);
        if (property == null || property.trim().length() == 0) {
            if (mandatory) {
                throw new DAOConfigurationException("Требуемое свойство " + fullKey + " отсутствует в файле свойств " + PROPERTIES_FILE + ".");
            } else {
                property = null;
            }
        }
        return property;
    }

    public void setProperty(String key, String value) {
        String fullKey = specificKey + "." + key;
        PROPERTIES.put(fullKey, value);
    }

    public List<String> getURLs() {
        List<String> urls = new ArrayList<>();
        PROPERTIES.forEach((key, value) -> urls.add(value.toString()));
        return urls;
    }

    public List<String> getURLKeys() {
        List<String> keys = new ArrayList<>();
        PROPERTIES.forEach((key, value) -> keys.add(key.toString()));
        return keys;
    }

    public void store() {
        try (OutputStream propertiesFile = new FileOutputStream(DAOProperties.class.getResource(PROPERTIES_FILE_PATH).getPath())) {
            PROPERTIES.store(propertiesFile, "URLS");
        } catch (Exception ex) {
            AlertFX.showError(ex.getMessage(), ex);
        }
    }
}
