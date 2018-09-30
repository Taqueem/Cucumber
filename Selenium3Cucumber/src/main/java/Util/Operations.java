package Util;

import java.io.File;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Operations extends TestUtil {

  public static boolean HandleStaleElement(WebElement element) {
    boolean result = false;
    int attempts = 0;

    while (attempts < 2) {
      try {
        element.isDisplayed();
        result = true;

        break;
      } catch (StaleElementReferenceException e) {
      }

      attempts++;
    }

    return result;
  }

  public static void doubleClick(WebElement element) {
    Actions action = new Actions(driver);

    action.doubleClick(element);
  }

  public static void explicitWait(WebDriver driver, WebElement element) {
    WebDriverWait wait = new WebDriverWait(driver, 10);

    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  public static void fluentWait(WebDriver driver, By locator) {
    FluentWait wait = new FluentWait(driver).withTimeout(25, TimeUnit.SECONDS)
        .pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

    /*
     * WebElement foo = wait.until(new Function() {
     *
     * public WebElement apply(WebDriver driver, By locator) {
     *
     * return driver.findElement(locator);
     *
     * }
     *
     * });
     */
  }

  public static void handleAlert() {
    Alert alert = driver.switchTo().alert();

    alert.accept();
  }

  public static void javaScriptClick(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
  }

  public static String javaScriptGetInnerText() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String sText = js.executeScript("return document.documentElement.innerText;").toString();

    return sText;
  }

  public static String javaScriptGetTitle() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String sText = js.executeScript("return document.title;").toString();

    return sText;
  }

  public static void javaScriptPopUp() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript("alert('hello world');");
  }

  public static void javaScriptRefresh() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    js.executeScript("history.go(0)");
  }

  public static void javaScriptScrollPage() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // Vertical scroll - down by 150 pixels
    js.executeScript("window.scrollBy(0,150)");
  }

  public static void mouseHoverAndClick(WebElement mainElement, WebElement subElement) {
    Actions action = new Actions(driver);

    action.moveToElement(mainElement).moveToElement(subElement).click().build().perform();
  }

  public static void mouseHoverAndClick(WebElement Element) {
    Actions action = new Actions(driver);
    action.moveToElement(Element).click().build().perform();
  }

  public static void mouseHover(WebElement Element) {
    Actions action = new Actions(driver);
    action.moveToElement(Element).build().perform();
  }

  public static void rightClick(WebElement element) {

  }

  public static void rightClickAndSelectOption(WebElement element, int position) {
    Actions action = new Actions(driver);
    action.contextClick(element).build().perform();
    for (int i = 0; i < position; i++)
      action.sendKeys(Keys.ARROW_DOWN).build().perform();
    action.sendKeys(Keys.RETURN).perform();
  }

  public static void selectByText(WebElement element, String text) {
    Select dd = new Select(element);

    dd.selectByVisibleText(text);
  }

  public static void takeScreenShot() {
    try {
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

      // The below method will save the screen shot in d drive with name "screenshot.png"
      String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

      System.out.println(methodName);
      FileUtils.copyFile(scrFile, new File(Constants.Path_ScreenShots + methodName + ".jpg"));
    } catch (Exception e) {

      // TODO: handle exception
    }
  }

  public static void waitForPagetoLoad() {
    driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
  }

  public static void waitForScript() {
    driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);
  }

  // private static Logger Log = Logger.getLogger(Log.class.getName());
}


// ~ Formatted by Jindent --- http://www.jindent.com
