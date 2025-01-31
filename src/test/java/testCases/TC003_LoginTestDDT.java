package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyProfilePage;
import utilities.DataProviders;

public class TC003_LoginTestDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
    public void verifyLoginDDT(String email, String password, String response) {

        log.info("**** Starting TC003_LoginTestDDT ***** ");


        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickSignInButton();
            log.info("Clicked Sign In Button");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(email);
            loginPage.setPassword(password);
            loginPage.clickLogin();

            MyProfilePage myProfilePage = new MyProfilePage(driver);
            boolean isLoggedIn = myProfilePage.isProfileDropdownDisplayed();

            if (response.equalsIgnoreCase("Valid")) {
                if (isLoggedIn) {
                    myProfilePage.logout();
                    Assert.assertTrue(true);
                } else {
                    Assert.fail();
                }
            } else if (response.equalsIgnoreCase("Invalid")) {
                if (isLoggedIn) {
                    myProfilePage.logout();
                    Assert.fail();
                } else {
                    Assert.assertTrue(true);
                }

            }
        } catch (Exception e) {
            log.error(e);
            Assert.fail(e.getMessage());
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("**** Ending TC003_LoginTestDDT ***** ");
    }

}
