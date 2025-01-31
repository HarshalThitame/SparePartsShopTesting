package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@id='identifier']")
    private WebElement txtEmail;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement txtPassword;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setPassword(String password) {
        txtPassword.sendKeys(password);

    }

    public void clickLogin() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnLogin);
    }


}
