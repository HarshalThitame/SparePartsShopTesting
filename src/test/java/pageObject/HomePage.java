package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(xpath = "//span[normalize-space()='Sign Up']")
    private WebElement signUpButton;

    @FindBy(xpath = "//span[normalize-space()='Sign In']")
    private WebElement signInButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickSignUpButton() {
        signUpButton.click();
    }

    public void clickSignInButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

}
