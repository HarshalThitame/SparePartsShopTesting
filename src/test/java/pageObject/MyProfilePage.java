package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyProfilePage extends BasePage {
    @FindBy(xpath = "//button[@id='profileDropdown']")
    private WebElement profileDropdown;
    @FindBy(xpath = "//button[@class='dropdown-item']")
    private WebElement logoutButton;

    @FindBy(xpath = "//span[normalize-space()='Sign In']")
    private WebElement signInButton;

    public MyProfilePage(WebDriver driver) {
        super(driver);
    }

    public boolean isProfileDropdownDisplayed() {
        try {
            return profileDropdown.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement pd = wait.until(ExpectedConditions.elementToBeClickable(this.profileDropdown));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", pd);

        WebElement lb = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", lb);

        WebElement sb = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        JavascriptExecutor js22 = (JavascriptExecutor) driver;
        js22.executeScript("arguments[0].click();", sb);
    }
}
