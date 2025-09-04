package tests.UIAutomation;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// 🔹 Import Allure annotations
import io.qameta.allure.*;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;
    Properties props = new Properties();
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("loginTestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeClass
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Load test data
        FileInputStream fis = new FileInputStream("src/test/resources/testdata.properties");
        props.load(fis);

        driver.get(props.getProperty("url"));
        loginPage = new LoginPage(driver);
    }

    // 🔹 Add Allure annotations here
    @Test(description = "Verify that invalid login shows the correct error message")
    @Feature("Login") // Specific feature
    @Story("User should not be able to login with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test enters invalid email & password and verifies the displayed error message.")
    public void invalidLoginTest() {
        test = extent.createTest("Invalid Login Test");

        loginPage.enterEmail(props.getProperty("invalidEmail"));
        loginPage.enterPassword(props.getProperty("invalidPassword"));
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        String expectedError = props.getProperty("expectedError");

        test.info("Asserting error message...");
        Assert.assertEquals(actualError, expectedError, "Error message mismatch!");
        test.pass("Test Passed! Correct error message displayed.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}
