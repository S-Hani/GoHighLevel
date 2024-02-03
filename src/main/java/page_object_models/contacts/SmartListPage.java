package page_object_models.contacts;

import driver.ChromeDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartListPage {
    public static final String CONTACT_WITH_NAME = "//td[@data-title='Name']//a[text()='%s']";
    public static final By OPENED_CONTACT_DETAIL_HEADER = By.cssSelector("div.contact-detail-nav");
    public static final By NAV_MENU_TITLE = By.cssSelector("div.topmenu-navtitle");
    public static final String PAGE_HEADER = "Contacts";
    private ChromeDriver chromeDriver;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public SmartListPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(NAV_MENU_TITLE).getText().equals(PAGE_HEADER));
        logger.info("Opened Smart List Page");
    }

    public void openContact(String contactName) {
        chromeDriver.findElement(By.xpath(String.format(CONTACT_WITH_NAME,contactName))).click();
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(OPENED_CONTACT_DETAIL_HEADER).isDisplayed());
        logger.info("Opened Contact Detail Page for {}", contactName);
    }

}
