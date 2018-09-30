package pageModels;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	
	@FindBy(css=".account_icon")
	public WebElement linkMyAccount;

	
	@FindBy(name="s")
	public WebElement inputSearch;
	
}
