package utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer {

    /**
     *  This class will retry a failed test case when implemented
     */

    int retry = 1;
    int numberOfRetries = 3;

    @Override
    public boolean retry(ITestResult iTestResult){
        if (retry < numberOfRetries){
            retry++;
            return true;
        }
        return false;
    }
}
