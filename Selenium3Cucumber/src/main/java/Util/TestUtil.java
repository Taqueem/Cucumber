package Util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.poi.hssf.record.RightMarginRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;



public class TestUtil {


  private enum browserType {
    Chrome, Firefox, IE;
  }



  protected static WebDriver driver;

  protected static WebDriver chromeDriver;

  // protected static WebDriver driver2;
  protected Properties prop = new Properties();
  private Workbook testWorkbook = null;
  protected int verifyCounter = 0;
  private File file;
  private FileInputStream inputStream;
  private Sheet sheet;
  private Cell cell;

  private DesiredCapabilities chromeCapability;

  private DesiredCapabilities firefixCapability;

  private ChromeOptions chromeOptions;

  private static String browser = "Chrome";


  private void setChromeCapability() {
    chromeCapability = DesiredCapabilities.chrome();
//     chromeOptions = new ChromeOptions();
    //// chromeOptions.addArguments("test-type");
    chromeCapability.setCapability("chrome.binary", Constants.Path_ChromeBinary);
    chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    // chromeCapability.setCapability(chromeCapability, true);

  }



  private void initializeExcel() throws IOException {

    // Create a object of File class to open xlsx file
    file = new File(Constants.Path_TestExcel);

    // Create an object of FileInputStream class to read excel file
    inputStream = new FileInputStream(file);
    testWorkbook = new XSSFWorkbook(inputStream);
  }

  private void initializePageObjects() {
    // PageFactory.initElements(driver, TestUtil.homepage);
  

  }

  public void readExcel(String sheetName, String key, int colNum) {
    int rowNum = getRowNum(sheetName, key);
  }

  private void readPropertyfile() {
    File file = new File(Constants.Path_testData);
    FileInputStream fileInput = null;

    try {
      fileInput = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    // load properties file
    try {
      prop.load(fileInput);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void saveFileUsingRobot() {
    try {
      Robot rb = new Robot();
      Thread.sleep(3000);
      rb.keyPress(KeyEvent.VK_DOWN);
      Thread.sleep(3000);
      rb.keyPress(KeyEvent.VK_TAB);
      Thread.sleep(3000);
      rb.keyPress(KeyEvent.VK_TAB);
      Thread.sleep(3000);
      rb.keyPress(KeyEvent.VK_TAB);
      Thread.sleep(3000);
      rb.keyPress(KeyEvent.VK_ENTER);

    } catch (Exception e) {

      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // protected static Logger Log=Logger.getLogger(TestUtil.class.getName());
  private void selectBrowser() {

    if (browser.equalsIgnoreCase("chrome"))
      intializeChromeDriver();
    else if (browser.equalsIgnoreCase("firefox"))
      intializeFirefoxDriver();
    else if (browser.equalsIgnoreCase("IE"))
      intializeIEDriver();


    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    /*
     * driver2= new FirefoxDriver(ffBinary, firefoxProfile);
     * driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     * driver2.manage().window().maximize();
     */
  }

  protected void intializeChromeDriver() {
    try {
      setChromeCapability();
      System.setProperty("webdriver.chrome.driver", Constants.Path_ChromeDriver);
      driver = new ChromeDriver(chromeCapability);
    } catch (Exception e) {
      // TODO: handle exception
    }
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();

  }

  protected void intializeFirefoxDriver() {
    File pathToBinary = new File(Constants.Path_Firefox);
    FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
    FirefoxProfile firefoxProfile = new FirefoxProfile();
   // driver = new FirefoxDriver(ffBinary, firefoxProfile);
  }

  protected void intializeIEDriver() {

  }



  public void setup() {
    try {
  
      // intializeChromeDriver();
      selectBrowser();
      initializePageObjects();
      readPropertyfile();
      initializeExcel();

    } catch (Exception e) {
      // TODO: handle exception
    }

  }

  protected List<String> stringsToList(String strings) {
    List<String> items = Arrays.asList(strings.split(","));

    return items;
  }

  public void verifyTrue(String message, boolean condition) {
    if (!condition) {
      verifyCounter++;
           System.out.println(message);
    }
  }

  private int getRowNum(String sheetName, String key) {
    int row = 0;

    // Read sheet inside the workbook by its name
    sheet = testWorkbook.getSheet(sheetName);

    // Find number of rows in excel file
    int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum();

    for (int i = 0; i < totalRows + 1; i++) {
    }

    return 0;
  }

  protected void uploadUsingRobot(String FilePath) {
    try {
      setClipboardData(FilePath);
      Robot rb = new Robot();
      setSleep(2);
      rb.keyPress(KeyEvent.VK_CONTROL);
      setSleep(2);
      rb.keyPress(KeyEvent.VK_V);
      setSleep(2);
      rb.keyRelease(KeyEvent.VK_V);
      setSleep(2);
      rb.keyRelease(KeyEvent.VK_CONTROL);
      setSleep(2);
      rb.keyPress(KeyEvent.VK_ENTER);
      setSleep(2);
      rb.keyRelease(KeyEvent.VK_ENTER);
      setSleep(2);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  protected void uploadUsingAutoIT(String FilePath) {
    try {
      Runtime.getRuntime().exec("D:\\AutoIT\\uploadInChrome.exe" + "" + FilePath);
    } catch (Exception e) {
      // TODO: handle exception
    }

  }


  private void setClipboardData(String filepath) {
    StringSelection stringSelect = new StringSelection(filepath);
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelect, null);

  }

  protected void setSleep(int seconds) {
    try {
      int miliSeconds = seconds * 1000;
      Thread.sleep(miliSeconds);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  protected void openNewTabUsingRobot() {
    try {
      Robot rb = new Robot();

      rb.keyPress(KeyEvent.VK_CONTROL);
      rb.keyPress(KeyEvent.VK_T);
      rb.keyRelease(KeyEvent.VK_T);
      rb.keyRelease(KeyEvent.VK_CONTROL);
      setSleep(3);
    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  protected void navigateURLUsingRobot(String URL) {
    setClipboardData(URL);
    try {
      Robot rb = new Robot();

      rb.keyPress(KeyEvent.VK_CONTROL);
      rb.keyPress(KeyEvent.VK_V);
      rb.keyRelease(KeyEvent.VK_V);
      rb.keyRelease(KeyEvent.VK_CONTROL);
      setSleep(3);
      rb.keyPress(KeyEvent.VK_ENTER);
      rb.keyRelease(KeyEvent.VK_ENTER);
    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void openNewTab() {

    driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");

  }

  protected void openNewTabByClickingLink() {
 //   Operations.rightClickAndSelectOption(homepage.getLinks().get(0), 1);
    // Operations.rightClickAndSelectOption(link,1);
  }

  protected void closeCurrentTab() {
    try {
      Robot rb = new Robot();
      rb.keyPress(KeyEvent.VK_CONTROL);
      rb.keyPress(KeyEvent.VK_W);
      rb.keyRelease(KeyEvent.VK_W);
      rb.keyRelease(KeyEvent.VK_CONTROL);

    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void switchToNthTab(int nthTab) {
    try {
      Robot rb = new Robot();
      for (int i = 0; i < nthTab; i++) {
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_TAB);
        setSleep(3);
      }
      rb.keyRelease(KeyEvent.VK_CONTROL);
      rb.keyRelease(KeyEvent.VK_TAB);

    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void waitTillElementHasAttribute(WebElement element, String Attribute) {
    Boolean attributeNotPresent = false;
    do {
      if (element.getAttribute(Attribute) == null)
        attributeNotPresent = true;
      else
        attributeNotPresent = false;

    } while (attributeNotPresent);
  }


  protected void timerLoop(int secondsToExit, WebElement element) {
    int milliSecondsToExit = secondsToExit * 100;
    long startTime = System.currentTimeMillis();
    while (false || System.currentTimeMillis() - startTime < milliSecondsToExit) {
      try {
        if (element.isDisplayed())
          break;
      } catch (Exception e) {
        // TODO: handle exception
      }
      setSleep(1);
    }

  }
  
  protected String mouseHoverAndGetCursorType(WebElement Element)
  {
    Operations.mouseHover(Element);
    return Element.getCssValue("cursor");
  }


}


// ~ Formatted by Jindent --- http://www.jindent.com
