package testscript;

import base.TestBase;
import com.relevantcodes.extentreports.LogStatus;
import endpoints.RepositoryEndpoint;

import org.testng.annotations.Test;

public class RepositoryTest extends TestBase {
    String repoName;
    RepositoryEndpoint repositoryEndpoint =new RepositoryEndpoint();

    /**
     * This method is used to create new repository in GitHub and store the repository name in reponame
     */
    @Test(priority = 1)
    public void createRepositoryTest(){
        report.log(LogStatus.INFO,"Create repository test");
        repoName =repositoryEndpoint.verifyCreateRepository();
    }

    /**
     * This method is used to get the newly created repository and validate the response
     */
    @Test(priority = 2,dependsOnMethods = "createRepositoryTest")
    public void getRepositoryTest(){
        report.log(LogStatus.INFO,"Get repository test");
        repositoryEndpoint.verifyGetRepository(repoName);
    }
    /**
     * This method is used to update repository and validate the response
     */
    @Test(priority = 3,dependsOnMethods = "createRepositoryTest")
    public void updateRepositoryTest(){
        report.log(LogStatus.INFO,"Update repository test");
        repoName =repositoryEndpoint.verifyUpdateRepository(repoName);
    }

    /**
     * This method is used delete the repository
     */
    @Test(priority = 4,dependsOnMethods = "createRepositoryTest")
    public void deleteRepositoryTest(){
        report.log(LogStatus.INFO,"Delete repository test");
        repositoryEndpoint.verifyDeleteRepository(repoName);
    }

    /**
     * This method is used to check user can create repository using invalid token
     */
    @Test(priority = 5)
    public void inValidTokenTest(){
        report.log(LogStatus.INFO,"Create repository using in-valid test");
        repositoryEndpoint.verifyInValidToken();
    }

    /**
     * This method is used to check user can create repository using invalid token
     */
    @Test(priority = 6)
    public void getInValidRepoTest(){
        report.log(LogStatus.INFO,"Get repository using in-valid name test");
        repositoryEndpoint.verifyInvalidRepo();
    }
}
