package core;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by astrate on 03.10.2014.
 */
public class Settings {
    public final String USERNAME;
    public final String PASSWORD;
    public final String BASE_URL;
    public final String FILTER;
    public final String PROJECT;
    public final String REPOSITORY;
    public final Long POLL_TIME;

    public Settings() {
        Properties settingsProperties = new Properties();
        try {
            settingsProperties.load(new FileReader("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("No configuration found");
        }
        USERNAME = settingsProperties.getProperty("username");
        PASSWORD = settingsProperties.getProperty("password");
        BASE_URL = settingsProperties.getProperty("base_url");
        FILTER = settingsProperties.getProperty("filter");
        PROJECT = settingsProperties.getProperty("project");
        REPOSITORY = settingsProperties.getProperty("repository");
        POLL_TIME = Long.valueOf(settingsProperties.getProperty("poll_time")) * 1000;
    }
}
