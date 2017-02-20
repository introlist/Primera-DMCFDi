package CFDiTest;

import org.openqa.selenium.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

/**
 * Created by Demon on 15/02/2017.
 */
public class CFDiTest {

    WebDriver driver;
    int tiempo_captcha = 10;
    String opcion;
    String RFC = "RFCRFCRFCRFC2";
    String CIEC = "xXXXXXXXXXX";
    String fechaInicial = "02/02/2016";
    String fechaFinal = "04/04/2016";

    public CFDiTest() {
        //System.setProperty("webdriver.gecko.driver","C:\\resources\\geckodriver.exe" );
       //driver = new FirefoxDriver();
        // System.setProperty("webdriver.ie.driver","C:\\resources\\IEDriverServer.exe");
       //driver = new InternetExplorerDriver();

    }

    public void profileTest(){
        FirefoxProfile prof = new FirefoxProfile();
        //TODO prof.setPreference();
    }


    public static void main(String[] args) {
        CFDiTest webSrcapper = new CFDiTest();        webSrcapper.openTestSite();
        if(webSrcapper.loginSAT()){
            System.out.println("Exito");
            //Continue
        }else{
            System.out.println("Fallo");
            webSrcapper.driver.close();
            webSrcapper.driver.quit();
        }
        //webSrcapper.getText();
        //webSrcapper.saveScreenshot();
        //webSrcapper.closeBrowser();
    }

    private void openTestSite() {
        driver.navigate().to("https://cfdiau.sat.gob.mx/nidp/app/login?id=SATUPCFDiCon&sid=0&option=credential&sid=0");
       // driver.get("https://cfdiau.sat.gob.mx/nidp/app/login?id=SATUPCFDiCon&sid=0&option=credential&sid=0");

    }

    public boolean loginSAT() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Enviar']")));
        WebElement password_editbox = driver.findElement(By.name("Ecom_Password"));
        WebElement userName_editbox = driver.findElement(By.name("Ecom_User_ID"));
        System.out.println("done "+driver.getTitle());
        userName_editbox.sendKeys(RFC);
        password_editbox.sendKeys(CIEC);
        wait = new WebDriverWait(driver, tiempo_captcha);
        try{
            wait.until(ExpectedConditions.titleContains("NetIQ Access"));
            return true;
        }catch (TimeoutException exLogin){
            System.out.println(exLogin);
            return false;
        }

    }


    public void modificarFecha() {
        if (opcion.equalsIgnoreCase("emitidas")) {
            driver.findElement(By.id("ctl00_MainContent_CldFechaInicial2_BtnFecha2")).click();
            System.out.println("waiting for datepicks");
            System.out.println("from: " + fechaInicial + " to: " + fechaFinal);
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver)
                        .executeScript("updateDateField('ctl00$MainContent$CldFechaInicial2$Calendario_text', " + "'" + fechaInicial + "'" + ");");
            }
            System.out.println("firstone");
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver)
                        .executeScript("updateDateField('ctl00$MainContent$CldFechaFinal2$Calendario_text', " + "'" + fechaFinal + "'" + ");");
            }
            System.out.println("now about to submit search...");
        }

        //----------------------------------------------------------------------
                    /*Caso Recibidas*/
        else {
            System.out.println("Metodo recibidas");
            Select yearSelection = new Select(driver.findElement(By.id("DdlAnio")));
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'width: 80px;');",
                        driver.findElement(By.id("DdlAnio")));
            }
            System.out.println("year selector should be displayed");
            List<WebElement> yearOptions = yearSelection.getOptions();
            yearOptions.stream().map((yearOption) -> {
                System.out.println("this year is: " + yearOption.getAttribute("value"));
                return yearOption;
            }).forEach((yearOption) -> {
                System.out.println("igual a esto? " + fechaInicial.substring(3, 7));
                if (yearOption.getAttribute("value").equalsIgnoreCase(fechaInicial.substring(3, 7))) {
                    yearOption.click();
                }
            });
            System.out.println("year should be selected");

            Select monthSelection = new Select(driver.findElement(By.id("ctl00_MainContent_CldFecha_DdlMes")));
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'width: 80px;');",
                        driver.findElement(By.id("ctl00_MainContent_CldFecha_DdlMes")));
            }
            String selectedMonth = monthSelection.getFirstSelectedOption().getText();
            System.out.println("month selector in string: " + monthSelection.toString());
            List<WebElement> options = monthSelection.getOptions();
            String month;
            if (fechaInicial.substring(0, 1).equals("0")) {
                month = fechaInicial.substring(1, 2);
            } else {
                month = fechaInicial.substring(0, 2);
            }

            System.out.println("month: " + month);
            options.stream().map((option) -> {
                System.out.println("value: " + option.getAttribute("value"));
                return option;
            }).filter((option) -> (option.getAttribute("value").equalsIgnoreCase(month))).map((option) -> {
                System.out.println("is selescted? " + option.getAttribute("selected"));
                return option;
            }).map((option) -> {
                System.out.println("selected");
                return option;
            }).forEach((option) -> {
                option.click();
            });
        }
    }
}