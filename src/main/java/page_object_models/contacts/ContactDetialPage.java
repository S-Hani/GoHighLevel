package page_object_models.contacts;

import com.google.common.collect.Lists;
import driver.ChromeDriverProvider;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ContactDetialPage {

    public static final By NEW_MESSAGE_COMPOSER = By.cssSelector("div.new-message-composer");
    public static final By COMPOSE_MESSAGE_ICONS = By.cssSelector("div#message-composer svg");
    public static final By SMS_TEMPLATE_MODAL = By.cssSelector("div.hl_sms-template--modal.show");
    public static final By SEARCH_BAR_TEMPLATES = By.cssSelector("input[type='search']");
    public static final By MODAL_FOOTER_CONTAINER = By.cssSelector("div.modal-footer");
    public static final String BUTTON_WITH_TEXT = "//button[normalize-space(text())='%s']";
    public static final By MESSAGE_GROUP = By.cssSelector("div.messages-group");
    public static final By MESSAGE_BUBBLES_IN_GROUP = By.cssSelector("div.messages-group div.message-bubble");
    public static final By MESSAGE_COMPOSER = By.cssSelector("div#message-composer");
    public static final By SEND_SMS_BUTTON = By.cssSelector("button#send-sms");
    private ChromeDriver chromeDriver;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public ContactDetialPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        ChromeDriverProvider.getWait(30, 1).until(driver -> driver.findElement(NEW_MESSAGE_COMPOSER).isDisplayed());
        logger.info("Opened Contact Detail Page");
    }

    List<String> smsComposerIcons = Lists.newArrayList(
            "Attach files",
            "Insert emoji",
            "Insert template",
            "Request payment",
            "More");

    public void clickIcon(String iconName) {
        List<WebElement> displayedSVGs = chromeDriver.findElements(COMPOSE_MESSAGE_ICONS).stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
        displayedSVGs.get(smsComposerIcons.indexOf(iconName)).click();
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(SMS_TEMPLATE_MODAL).isDisplayed());
        logger.info("Clicked {} icon", iconName);
    }

    public void selectTemplate(String templateName) {
        WebElement container = chromeDriver.findElement(SMS_TEMPLATE_MODAL);
        WebElement searchBox = container.findElement(SEARCH_BAR_TEMPLATES);
        searchBox.clear();
        searchBox.sendKeys(templateName + Keys.RETURN);
        WebElement footer = container.findElement(MODAL_FOOTER_CONTAINER);
        footer.findElement(By.xpath(String.format(BUTTON_WITH_TEXT, "Use Template"))).click();
        logger.info("Selected {} template", templateName);
    }

    public void sendMessage() {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(MESSAGE_GROUP).isDisplayed());
        int previousCountOfMessages = chromeDriver.findElements(MESSAGE_BUBBLES_IN_GROUP).size();
        WebElement container = chromeDriver.findElement(MESSAGE_COMPOSER);
        container.findElement(SEND_SMS_BUTTON).click();
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElements(MESSAGE_BUBBLES_IN_GROUP).size() > previousCountOfMessages);
        logger.info("Sent message");
    }

    public void verifyMessageBubbleContent(String content) {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(MESSAGE_GROUP).isDisplayed());
        List<WebElement> elements = chromeDriver.findElements(MESSAGE_BUBBLES_IN_GROUP);
        Assert.assertEquals(content, elements.get(elements.size() - 1).getText().trim());
        logger.info("Verified message content sent successfully");
    }
}
