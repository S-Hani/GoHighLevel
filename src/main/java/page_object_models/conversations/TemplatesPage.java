package page_object_models.conversations;

import driver.ChromeDriverProvider;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplatesPage {
    public static final By PAGE_HADER = By.cssSelector("div.hl_settings--controls h2");
    public static final String PAGE_TITLE = "Message Templates (Snippets)";
    public static final By TEMPLATES_TABLE_ROW = By.cssSelector("section.hl_template--container table tr");
    public static final By OPENED_DROPDOWN_MENU = By.cssSelector("div.dropdown-menu.show");
    public static final String DROPDOWN_ITEM = ".//a[@class='dropdown-item'][contains(text(),'%s')]";
    public static final By ADD_EDIT_TEMPLATE_MODAL = By.cssSelector("div#edit-event-modal.show");
    public static final String IFRAME_ID_FOR_RICH_TEXT = "editsmseditor_ifr";
    public static final By MODAL_CLOSE_BUTTON = By.cssSelector("button[data-dismiss='modal']");
    public static final By INPUT_TEMPLATE_NAME = By.cssSelector("input[type=text][name='msgsndr2']");
    public static final By INPUT_RICH_TEXT = By.cssSelector("#tinymce > p");
    public static final By TEMPLATE_HEADER = By.cssSelector("div.hl_settings--controls");
    public static final By ADD_TEMPLATE_BUTTON = By.cssSelector("div.dropdown>button");
    public static final By SAVE_TEMPLATE_BUTTON = By.cssSelector("div#edit-event-modal.show div.card-footer button");
    public static final By MESSAGE_INSIDE_MESSAGE_BUBBLE = By.cssSelector("div.message-bubble > p");
    private ChromeDriver chromeDriver;
    private int templateCount;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public TemplatesPage(ChromeDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(PAGE_HADER).getText().startsWith(PAGE_TITLE));
        logger.info("Opened Templates Page");
    }

    public void addTemplate(String templateType) {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElements(TEMPLATES_TABLE_ROW).size() > 1);
        templateCount = chromeDriver.findElements(TEMPLATES_TABLE_ROW).size();
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                WebElement container = chromeDriver.findElement(TEMPLATE_HEADER);
                container.findElement(ADD_TEMPLATE_BUTTON).click();
                ChromeDriverProvider.getWait(30,1).until(driver -> container.findElement(OPENED_DROPDOWN_MENU).isDisplayed());
                WebElement dropdown = container.findElement(By.xpath(String.format(DROPDOWN_ITEM, templateType)));
                ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", dropdown);
                ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ADD_EDIT_TEMPLATE_MODAL).isDisplayed());
                ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(By.id(IFRAME_ID_FOR_RICH_TEXT)).isDisplayed());
                logger.info("Selected {} template type", templateType);
                return;
            } catch (NoSuchElementException e) {
                WebElement modalContainer = chromeDriver.findElement(ADD_EDIT_TEMPLATE_MODAL);
                ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", modalContainer.findElement(MODAL_CLOSE_BUTTON));
                if (++count == maxTries) throw e;
            }
        }
    }

    public void enterName(String name) {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ADD_EDIT_TEMPLATE_MODAL).isDisplayed());
        WebElement modalContainer = chromeDriver.findElement(ADD_EDIT_TEMPLATE_MODAL);
        WebElement nameTextBox = modalContainer.findElement(INPUT_TEMPLATE_NAME);
        nameTextBox.clear();
        nameTextBox.sendKeys(name);
    }

    public void enterContent(String content) {
        chromeDriver.switchTo().frame(IFRAME_ID_FOR_RICH_TEXT);
        WebElement richText = chromeDriver.findElement(INPUT_RICH_TEXT);
        richText.click();
        richText.sendKeys(content);
        chromeDriver.switchTo().defaultContent();
    }

    public void saveTemplate() {
        ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", chromeDriver.findElement(SAVE_TEMPLATE_BUTTON));
    }

    public void verifyMessageBubbleContent(String content) {
        Assert.assertEquals("Entered content is not matching message bubble", content, chromeDriver.findElement(MESSAGE_INSIDE_MESSAGE_BUBBLE).getText().trim());
    }

    public void verifyTemplateSaved(String templateName, String templateContent) {
        ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(By.xpath(String.format("//tr[td[normalize-space(text())='%s']][td[normalize-space(text())='%s']]", templateName, templateContent))).isDisplayed());
        Assert.assertEquals("Template did not get created", ++templateCount, chromeDriver.findElements(TEMPLATES_TABLE_ROW).size());
        logger.info("Template created successfully and template count is: {}", templateCount);
        editTemplateWithoutSaving(templateName);
    }

    public void editTemplateWithoutSaving(String templateName){
        int count = 0;
        int maxTries = 3;
        while (true) {
            try {
                WebElement templateRow = chromeDriver.findElement(By.xpath(String.format("//tr[td[normalize-space(text())='%s']]", templateName)));
                WebElement editButton = templateRow.findElement(By.cssSelector("i.fa-edit"));
                editButton.click();
                ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(ADD_EDIT_TEMPLATE_MODAL).isDisplayed());
                ChromeDriverProvider.getWait(30,1).until(driver -> driver.findElement(By.id(IFRAME_ID_FOR_RICH_TEXT)).isDisplayed());
                return;
            } catch (NoSuchElementException e) {
                WebElement modalContainer = chromeDriver.findElement(ADD_EDIT_TEMPLATE_MODAL);
                ((JavascriptExecutor) chromeDriver).executeScript("arguments[0].click();", modalContainer.findElement(MODAL_CLOSE_BUTTON));
                if (++count == maxTries) throw e;
            }
        }
    }
}
