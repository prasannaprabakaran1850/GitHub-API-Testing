package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import constants.FilePathConstants;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.APIUtils;

import java.lang.reflect.Method;

public class TestBase {
    public static ExtentReports extent;
    public static ExtentTest report;
    @BeforeSuite
    public void beforeSuite() {
        extent = new ExtentReports(FilePathConstants.REPORT_FILE_PATH);
        APIUtils.enableRequestResponseLogging();
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        report = extent.startTest((this.getClass().getSimpleName()+"::"+method.getName()),method.getName());
    }

    @AfterMethod
    public void afterMethod() {
        extent.endTest(report);
    }

    @AfterSuite
    public void afterSuite(){
        extent.flush();
        extent.close();
    }
}
