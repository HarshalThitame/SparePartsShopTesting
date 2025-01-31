package testCases;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class BaseClass {
    public static WebDriver driver;
    public Logger log;
    public Properties config = new Properties();


    @Parameters({"os", "browser"})
    @BeforeClass(groups = {"Sanity", "Master", "Regression", "DataDriven"})
    public void setUp(@Optional("Windows") String os, @Optional("chrome") String browser) throws IOException {
        log = LogManager.getLogger(this.getClass());

        log.info("**** Test execution started **** ");


        FileInputStream fis = null;
        fis = new FileInputStream("./src/test/resources/config.properties");
        config.load(fis);
        log.info("config properties loaded");

        if (config.getProperty("test.env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);
            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else {
                System.out.println("Unrecognized platform " + os);
                log.info("Unrecognized platform " + os);
            }
            switch (browser.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    log.info("chrome browser loaded");
                    break;
                case "firefox":
                    capabilities.setBrowserName("firefox");
                    log.info("firefox browser loaded");
                    break;
                case "ie":
                    capabilities.setBrowserName("ie");
                    log.info("ie browser loaded");
                    break;
                case "safari":
                    capabilities.setBrowserName("safari");
                    log.info("safari browser loaded");
                    break;
                case "edge":
                    capabilities.setBrowserName("edge");
                    log.info("edge browser loaded");
                    break;
                default:
                    System.out.println("Unrecognized browser " + browser);
                    log.info("Unrecognized browser " + browser);
                    break;
            }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        } else if (config.getProperty("test.env").equalsIgnoreCase("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    log.info("Chrome driver loaded");
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    log.info("Firefox driver loaded");
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    log.info("Edge driver loaded");
                    break;
                default:
                    System.out.println("Invalid browser");
                    log.info("Invalid browser");
                    return;
            }
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(config.getProperty("url"));
        log.info("Navigated to URL: " + driver.getCurrentUrl());
    }

    @AfterClass(groups = {"Sanity", "Master", "Regression", "DataDriven"})
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("**** Test execution ended **** ");
        }
    }


    public String generateRandomString(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }

        return result.toString();
    }

    public String randomNumber() {
        Random rand = new Random();
        int firstDigit = 7 + rand.nextInt(3);
        long remainingDigit = (long) (rand.nextDouble() * 1_000_000_000L);
        return firstDigit + String.format("%09d", remainingDigit);
    }

    public String captureScreenShot(String testName) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String path = System.getProperty("user.dir") + "/screenshot/" + testName + "_" + timeStamp + ".png";


        File srcFile = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }
}
