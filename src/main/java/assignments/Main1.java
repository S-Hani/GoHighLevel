package assignments;

import driver.ChromeDriverProvider;
import navigation.NavigationHelper;
import navigation.NavigationPaths;
import org.openqa.selenium.chrome.ChromeDriver;
import page_object_models.contacts.ContactDetialPage;
import page_object_models.contacts.SmartListPage;
import page_object_models.conversations.TemplatesPage;
import page_object_models.login.LoginPage;
import properties.CredentialsProvider;

import java.util.UUID;

public class Main1 {
    static ChromeDriver chromeDriver;

    static {
        chromeDriver = ChromeDriverProvider.getChromeDriver();
    }

    public static void main(String[] args) {
        // login to the application
        CredentialsProvider credentialsProvider = new CredentialsProvider(args[0]);
        login(credentialsProvider);
        NavigationHelper navHelper = new NavigationHelper(chromeDriver);
        navHelper.navigateToPage(NavigationPaths.DASHBOARD.getPath(), NavigationPaths.CONVERSATIONS.getPath());
        navHelper.navigateToPage(NavigationPaths.CONVERSATIONS.getPath(), NavigationPaths.CONVERSATION_TEMPLATES.getPath());

        TemplatesPage templatesPage = new TemplatesPage(chromeDriver);
        templatesPage.addTemplate("Text Template");
        String templateName = UUID.randomUUID().toString();
        templatesPage.enterName(templateName);
        String templateContent = UUID.randomUUID().toString();
        templatesPage.enterContent(templateContent);
        templatesPage.verifyMessageBubbleContent(templateContent);
        templatesPage.saveTemplate();
        templatesPage.verifyTemplateSaved(templateName, templateContent);

        navHelper.navigateToPage(NavigationPaths.CONVERSATION_TEMPLATES.getPath(), NavigationPaths.CONTACTS_SMART_LIST.getPath());

        SmartListPage smartListPage = new SmartListPage(chromeDriver);
        smartListPage.openContact("Hanish Shetty");

        ContactDetialPage contactDetialPage = new ContactDetialPage(chromeDriver);
        contactDetialPage.clickIcon("Insert template");
        contactDetialPage.selectTemplate(templateName);
        contactDetialPage.sendMessage();
        contactDetialPage.verifyMessageBubbleContent(templateContent);

        ChromeDriverProvider.closeDriver();
    }

    private static void login(CredentialsProvider credentialsProvider) {
        ChromeDriverProvider.navigateToUrl(credentialsProvider.getURL());
        LoginPage loginPage = new LoginPage(chromeDriver);
        loginPage.enterEmail(credentialsProvider.getUsername());
        loginPage.enterPassword(credentialsProvider.getPassword());
        loginPage.signIn();
    }

}