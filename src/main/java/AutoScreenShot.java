import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;


public class AutoScreenShot {

    public static void main(String[] args) {
        final Timer timer = new Timer();
//        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--start-fullscreen");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.nseindia.com/get-quotes/derivatives?symbol=NIFTY#quoteName");
        timer.schedule(new TimerTask() {
                           public void run() {

                               System.out.println("Opening chrome");
                               try {
//                                   driver.get("https://react-projects.netlify.app/");

//                                   driver.navigate().refresh();
                                   WebDriverWait w = new WebDriverWait(driver, Duration.of(10, ChronoUnit.MINUTES));
                                   w.until(ExpectedConditions
                                           .presenceOfElementLocated(By.name("expiryDate")));
                                   w.until(ExpectedConditions
                                           .presenceOfElementLocated(By.className("btn flat-button-white btn-min-w btn-sm")));

                                   WebElement button = driver.findElement(By.className("btn flat-button-white btn-min-w btn-sm"));
                                   button.click();

                                   Select expiryDateDropDown = new Select(driver.findElement(By.name("expiryDate")));
                                   expiryDateDropDown.selectByIndex(1);
                                   LocalDateTime localDateTime = LocalDateTime.now();
                                   int hour = localDateTime.getHour();
                                   int minute = localDateTime.getMinute();
                                   System.out.println("taking screen shot");
                                   File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                                   FileUtils.copyFile(scrFile, new File("./nseindia_" + localDateTime.toLocalDate() + "/data_(" + hour + "-" + minute + ").png"));
                                   System.out.println("Took screen shot successfully");
//                                   if (hour >= 15 && minute >= 30) {
//                                       System.out.println("Stopping the application");
//                                       timer.cancel();
//                                       driver.quit();
//                                   }
                               } catch (Exception ex) {
                                   System.out.println("Application failed");
                                   ex.printStackTrace();
                               }
                           }
                       }, 15 * 1000, 15 * 1000
        );
//        driver.quit();

    }
}
