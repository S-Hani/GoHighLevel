package properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CredentialsProvider {

    Properties properties;

    public CredentialsProvider(String filename) {
        properties = readPropertiesFile(filename);
    }

    public String getUsername() {
        return properties.getProperty("login.username");
    }

    public String getPassword() {
        return properties.getProperty("login.password");
    }

    public String getURL() {
        return properties.getProperty("login.url");
    }

    public Properties readPropertiesFile(String fileName) {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return prop;
    }
}
