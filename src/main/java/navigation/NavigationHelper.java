package navigation;

import driver.ChromeDriverProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigationHelper {
    public static final By ACTIVE_NAV_ITEM = By.cssSelector("a.active[id^=sb]:not(.divider)");
    public static final By ACTIVE_TOPMEMU_ITEM = By.cssSelector("a.active.topmenu-navitem");
    public static final By CUSTOMIZED_HEADER = By.cssSelector("div.customized-header");
    private ChromeDriver chromeDriver;
    Logger logger = LoggerFactory.getLogger(this.getClass());


    public NavigationHelper(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public void navigateToPage(String oldPath, String newPath) {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.getCurrentUrl().contains(oldPath));
        String baseLink = StringUtils.substringBefore(chromeDriver.getCurrentUrl(), oldPath);
        chromeDriver.navigate().to(baseLink + newPath);
        String[] splitLink = StringUtils.split(newPath, "/");
        String finalSelectedSidebarMenuItem = WordUtils.capitalizeFully(splitLink[0], '_').replaceAll("_", " ");
        String finalSelectedTopMenuNavItem = WordUtils.capitalizeFully(splitLink[1], '_').replaceAll("_", " ");
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ACTIVE_NAV_ITEM).isDisplayed());
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ACTIVE_NAV_ITEM).getText().contains(finalSelectedSidebarMenuItem));
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ACTIVE_TOPMEMU_ITEM).getText().contains(finalSelectedTopMenuNavItem));
        if (splitLink.length > 2) {
            String finalCustomizedHeader = WordUtils.capitalizeFully(splitLink[2], '_').replaceAll("_", " ");
            ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(CUSTOMIZED_HEADER).getText().contains(finalCustomizedHeader));
        }
        logger.info("Navigated from {} to {}", oldPath, newPath);
    }


}
