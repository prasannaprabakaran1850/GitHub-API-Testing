package testscript;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import endpoints.RepositoryEndpoint;
import org.testng.annotations.Test;

public class TagProtectionTest extends TestBase{
    String repoName;
    String tagProtectionID;
    RepositoryEndpoint repositoryEndpoint =new RepositoryEndpoint();

    /**
     *Create a tag protection state for a repository
     */
    @Test(priority = 1)
    public void createTagProtectionTest(){
        report.log(LogStatus.INFO,"Create tag protection test");
        repoName=repositoryEndpoint.verifyCreateRepository();
        tagProtectionID= repositoryEndpoint.verifyCreateTagProtectionRepository(repoName);
    }
    /**
     *Delete a tag protection state for a repository
     */
    @Test(priority = 2)
    public void deleteTagProtectionTest(){
        report.log(LogStatus.INFO,"Delete tag protection test");
        repositoryEndpoint.verifyDeleteTagProtection(repoName,tagProtectionID);
        repositoryEndpoint.verifyDeleteRepository(repoName);
    }
}
