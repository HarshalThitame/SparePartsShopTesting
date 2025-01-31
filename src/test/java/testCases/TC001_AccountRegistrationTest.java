package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.AccountRegistrationPage;
import pageObject.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void testAccountRegistration() {
        log.info("***** Starting TC001_AccountRegistrationTest");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.clickSignUpButton();

            log.info("Clicked Sign Up Button");

            AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
            log.info("Providing registration details");
            accountRegistrationPage.setFirstName("Test");
            accountRegistrationPage.setLastName("Test");
            accountRegistrationPage.setEmail(generateRandomString(7) + "@test.com");
            accountRegistrationPage.setPhone(randomNumber());
            accountRegistrationPage.setPassword("123123");
            accountRegistrationPage.setConfirmPassword("123123");

            accountRegistrationPage.clickSubmit();

            log.info("Validating expected message");
            Assert.assertEquals(accountRegistrationPage.getMsgConfirmationTitle(), "Account Created!");

        } catch (Exception e) {
            log.error(e);
            Assert.fail("Test failed..." + e);
        }

        log.info("***** Ending TC001_AccountRegistrationTest *****");
    }
}
