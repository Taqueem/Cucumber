package easy.util;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class BaseUtils {
	
	protected WebDriver driver;
	
	public void setBrowser()
	{
		System.setProperty("webdriver.gecko.driver",ConstantPath.pathGeckoDriver);
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Version\\55.0\\firefox.exe");
		 driver = new FirefoxDriver(options);
	}

}
