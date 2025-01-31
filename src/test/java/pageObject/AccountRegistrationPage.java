package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

    @FindBy(xpath = "//input[@id='firstName']")
    private WebElement txtFirstName;
    @FindBy(xpath = "//input[@id='lastName']")
    private WebElement txtLastName;
    @FindBy(xpath = "//input[@id='email']")
    private WebElement txtEmail;
    @FindBy(xpath = "//input[@id='phone']")
    private WebElement txtPhone;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement txtPassword;
    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElement txtConfirmPassword;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement btnSubmit;
    @FindBy(xpath = "//h2[@id='swal2-title']")
    private WebElement msgConfirmationTitle;

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        txtFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        txtLastName.sendKeys(lastName);
    }

    public void setEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void setPhone(String phone) {
        txtPhone.sendKeys(phone);
    }

    public void setPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword(String confirmPassword) {
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public void clickSubmit() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.elementToBeClickable(btnSubmit)).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnSubmit);
    }

    public String getMsgConfirmationTitle() {
        return msgConfirmationTitle.getText();
    }

}
