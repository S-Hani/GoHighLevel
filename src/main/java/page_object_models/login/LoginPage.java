package page_object_models.login;

import driver.ChromeDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {
    public static final By APP_LOADER = By.cssSelector("div.app-loader");
    public static final By INPUT_EMAIL = By.cssSelector("input[id='email']");
    public static final By INPUT_PASSWORD = By.cssSelector("input[id='password']");
    public static final By SIGN_IN_BUTTON = By.cssSelector("button[type=button]:not(.close)");
    private ChromeDriver chromeDriver;
    Logger logger = LoggerFactory.getLogger(this.getClass());



    public LoginPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        ChromeDriverProvider.getWait(30,1).until(driver -> !driver.findElement(APP_LOADER).isDisplayed());
        logger.info("Opened Login Page");
    }

    public void enterEmail(String username) {
        chromeDriver.findElement(INPUT_EMAIL).clear();
        chromeDriver.findElement(INPUT_EMAIL).sendKeys(username);
        logger.info("Entered email");
    }

    public void enterPassword(String password) {
        chromeDriver.findElement(INPUT_PASSWORD).clear();
        chromeDriver.findElement(INPUT_PASSWORD).sendKeys(password);
        logger.info("Entered password");
    }

    public void signIn() {
        chromeDriver.findElement(SIGN_IN_BUTTON).click();
        ChromeDriverProvider.getWait(30,1).until(driver -> !driver.getCurrentUrl().contains("dashboard"));
        logger.info("Logged in successfully");
    }
}
