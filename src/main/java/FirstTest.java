import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {
    private AppiumDriver driver;
    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","Pixel 6 ");
        capabilities.setCapability("platformVersion","13.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/notebi/Desktop/2_JAVAappiumAutomation/JAVAappiumAutomation2/apks/org.wikipedia.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4725/wd/hub"),capabilities);

    }
    @After
    public void tearDown(){
        driver.quit();
    }
     @Test
     public void testOne() {
         // Онбординг
         WebElement onboarding = driver.findElementById("org.wikipedia:id/fragment_onboarding_skip_button");
         onboarding.click();

         // Проверяем, что поле ввода для поиска статьи содержит текст
         assertElementHasText(
                 By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                 "Search Wikipedia",
                 "Не удается найти Search Wikipedia"

         );
         //тап на поиск
         waitForElementAdnClik(
                 By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                 "Не удалось нажать на поле поиска'",
                 5
         );
         //Ищет ĸаĸое-то слово
         waitForElementAdnSendKeys(
                 By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                 "internet",
                 "Не удалось найти статьи про 'internet'",
                 10
         );
         //Убеждается, что найдено несĸольĸо статей (в моем слуае больше 3)
         WebElement articles = driver.findElementById("org.wikipedia:id/page_list_item_title");
         Assert.assertTrue("Найдено меньше трех статей",
                 driver.findElements(By.id("org.wikipedia:id/page_list_item_title")).size() >= 3);


         //Отменяет поисĸ, нажатие на X
         waitForElementAndClear(
                 By.id("org.wikipedia:id/search_src_text"),
                 "Ошибка отмены поиска",
                 5
         );
         // Убеждаемся, что результат поиска пропал (нет статей на странице)
         waitForElementNotPresent(
                 By.id("org.wikipedia:id/page_list_item_title"),
                 "Результат поиска не пропал, статьи отображаются на странице",
                 5

         );



     }

    private void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = driver.findElement(by);
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }
    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
        wait.withMessage(error_message + "\n");

        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAdnClik(By by, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message,timeOutInSeconds );
        element.click();
        return element;}
    private WebElement waitForElementAdnSendKeys(By by, String value, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message,timeOutInSeconds );
        element.sendKeys(value);
        return element;
    }
    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds) {
        WebElement element =waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }
    private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }



}
