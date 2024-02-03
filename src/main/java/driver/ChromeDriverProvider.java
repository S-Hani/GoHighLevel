package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ChromeDriverProvider {

    private static ChromeDriver driver;
    public static Wait<ChromeDriver> wait;
    static Logger logger = LoggerFactory.getLogger(ChromeDriverProvider.class);


    public static ChromeDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        logger.info("Started Chrome Driver");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static void navigateToUrl(String url) {
        driver.get(url);
        logger.info("Navigated to URL: {}", url);
    }

    public static Wait<ChromeDriver> getWait(int maxWaitTimeInSeconds, int pollingTimeInSeconds) {
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(maxWaitTimeInSeconds))
                .pollingEvery(Duration.ofSeconds(pollingTimeInSeconds));
        return wait;
    }

    public static void closeDriver() {
        driver.quit();
        logger.info("Closed Chrome Driver");
    }

}
