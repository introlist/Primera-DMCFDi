package CFDiTest.resources;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Demon on 20/02/2017.
 */
public class TestClass {

    WebDriver driver;

    public static void main(String[] args) {
        TestClass tc = new TestClass();
    }

    public TestClass(){
        try {
            driveDriver();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void driveDriver() throws InterruptedException, MalformedURLException {
//        DesiredCapabilities capabilities = DesiredCapabilities.edge();
//        capabilities.setBrowserName("MicrosoftEdge");
//        capabilities.setPlatform(Platform.WIN10);
//        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:7777"),capabilities);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
//        driver.navigate().to("www.google.com");
//        Thread.sleep(10000);
//        driver.quit();

        System.setProperty("webdriver.chrome.driver","C:\\resources\\chromedriver.exe" );
        driver = new ChromeDriver();
        driver.navigate().to("http://seleniumsimplified2.com");

        Assert.assertTrue("title should start differently",
                driver.getTitle().startsWith("Selenium Simplified"));

        driver.close();
        driver.quit();
    }


}
