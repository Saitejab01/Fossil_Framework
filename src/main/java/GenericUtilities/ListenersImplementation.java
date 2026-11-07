
package GenericUtilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenersImplementation implements ITestListener {

    ExtentReports report;

    ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        System.out.println("SUITE STARTED");

        ExtentSparkReporter er = new ExtentSparkReporter(
                ".\\ExtentReport\\Extent-Report-"
                + new JavaUtility().GenrateDate() + ".html"
        );

        er.config().setDocumentTitle("Fossil Automation Report");
        er.config().setTheme(Theme.DARK);
        er.config().setReportName("Fossil Execution Report");

        report = new ExtentReports();
        report.attachReporter(er);

        report.setSystemInfo("Base Browser", "Chrome");
        report.setSystemInfo("Base Platform", "Windows");
        report.setSystemInfo("Base URL", "Test Env");
        report.setSystemInfo("Reporter Name", "Sai Teja");
    }

    @Override
    public void onTestStart(ITestResult result) {

        String methodName = result.getMethod().getMethodName();

        System.out.println(methodName + " TEST EXECUTION STARTED");

        ExtentTest extentTest = report.createTest(methodName);

        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        String methodName = result.getMethod().getMethodName();

        System.out.println(methodName + " TEST EXECUTION SUCCESSFUL");

        test.get().log(Status.PASS,
                methodName + " - Test Execution PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String methodName = result.getMethod().getMethodName();

        System.out.println(methodName + " TEST EXECUTION FAILED");

        test.get().log(Status.FAIL,
                methodName + " - Test Execution FAILED");

        test.get().log(Status.FAIL, result.getThrowable());

        try {

            JavaUtility j = new JavaUtility();
            WebDriverUtility w = new WebDriverUtility();

            String screenshotName = methodName + j.GenrateDate();

            String path = w.captureScreenShot(
                    BaseClass.sdriver, screenshotName
            );

            test.get().addScreenCaptureFromPath(path);

        } catch (IOException e) {

            test.get().log(Status.WARNING,
                    "Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        String methodName = result.getMethod().getMethodName();

        System.out.println(methodName + " TEST EXECUTION SKIPPED");

        test.get().log(Status.SKIP,
                methodName + " - Test Execution SKIPPED");

        if (result.getThrowable() != null) {
            test.get().log(Status.SKIP, result.getThrowable());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onFinish(ITestContext context) {

        System.out.println("SUITE EXECUTION FINISHED");

        report.flush();
    }
}
