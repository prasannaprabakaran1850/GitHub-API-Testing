package assertion;

import com.relevantcodes.extentreports.LogStatus;

import org.testng.Assert;
import base.TestBase;

public class Verify extends TestBase {

	public static boolean verifyString(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
			report.log(LogStatus.PASS,"PASS Actual Result:: " + actual + " Expected Result:: " + expected);
			return true;

		} catch (AssertionError assertionError) {
			report.log(LogStatus.FAIL,"FAIL Actual Result:: " + actual + " Expected Result:: " + expected);
			return false;
		}
	}

	public static boolean verifyBoolean(boolean actual, boolean expected) {
		try {
			Assert.assertEquals(actual, expected);
			report.log(LogStatus.PASS,"PASS Actual Result:: " + actual + " Expected Result:: " + expected);
			return true;

		} catch (AssertionError assertionError) {
			report.log(LogStatus.FAIL,"FAIL Actual Result:: " + actual + " Expected Result:: " + expected);
			return false;
		}
	}
	
	public static void verifyInteger(int actual, int expected) {
		try {
			Assert.assertEquals(actual, expected);
			report.log(LogStatus.PASS,"PASS Actual Result:: " + actual + " Expected Result:: " + expected);

		} catch (AssertionError assertionError) {
			report.log(LogStatus.FAIL,"FAIL Actual Result:: " + actual + " Expected Result:: " + expected);
		}
	}
}
