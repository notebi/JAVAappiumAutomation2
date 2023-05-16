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
     }

    private void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = driver.findElement(by);
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }

}
