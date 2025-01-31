package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyProfilePage;

public class TC002_LoginTest extends BaseClass {


    @Test(groups = {"Sanity", "Master"})
    public void testLogin() {
        log.info("**** Starting TC002_LoginTest **** ");

        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickSignInButton();
            log.info("Clicked Sign In Button");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(config.getProperty("email"));
            loginPage.setPassword(config.getProperty("password"));
            loginPage.clickLogin();

            MyProfilePage myProfilePage = new MyProfilePage(driver);
            boolean profileDropdownDisplayed = myProfilePage.isProfileDropdownDisplayed();
            if (!profileDropdownDisplayed) {
                log.info("log in unsuccessful");
            }
            Assert.assertTrue(profileDropdownDisplayed, "Log in unsuccessful");
            log.info("login successful");
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.fail(e.getMessage());
        }

        log.info("**** Finished TC002_LoginTest **** ");
    }
}
