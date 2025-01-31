package utilities;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testCases.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestListener implements ITestListener {
    ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.createInstance();
        ExtentReportManager.getInstance().setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));
        ExtentReportManager.getInstance().setSystemInfo("OS", context.getCurrentXmlTest().getParameter("os"));
        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (includedGroups != null && !includedGroups.isEmpty()) {
            ExtentReportManager.getInstance().setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = ExtentReportManager.getInstance().createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = ExtentReportManager.getInstance().createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " Test Failed: " + result.getThrowable().getMessage());
        try {
            String screenshotPath = new BaseClass().captureScreenShot(result.getName());
            System.out.println(screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getInstance().createTest(result.getName())
                .log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
        String pathPfExtentReport = System.getProperty("user.dir") + "/reports/" + ExtentReportManager.reportName;
        File extentReport = new File(pathPfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
