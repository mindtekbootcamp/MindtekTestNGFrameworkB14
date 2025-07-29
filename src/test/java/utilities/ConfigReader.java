package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    /**
     * This class reads the Configuration.properties file and
     * returns values based on provided keys.
     */

    private static FileInputStream input;
    private static Properties properties;
    static {
        String path = "/Users/mindtekbatch14/IdeaProjects/MindtekTestNGFramework/src/test/resources/configurations/Configuration.properties";
        try {
            input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("Path to properties file is invalid or file is missing");
        } catch (IOException e) {
            System.out.println("Failed to load properties file");
        } finally {
            try {
                assert input != null;
                input.close();
            } catch (IOException e) {
                System.out.println("Failed to close FileInputStream");
            }
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
