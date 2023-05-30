package endpoints;

import assertion.Verify;
import com.relevantcodes.extentreports.LogStatus;
import constants.Constants;
import constants.FilePathConstants;
import constants.UrlConstants;
import io.restassured.response.Response;
import org.testng.Assert;
import payload.data.RepositoryData;
import payload.data.TagProtectionData;
import utils.APIUtils;
import utils.PropertyParser;

import static base.TestBase.report;

public class RepositoryEndpoint {
    PropertyParser propertyParser;

    public RepositoryEndpoint() {
        propertyParser = new PropertyParser(FilePathConstants.REPOSITORY_TESTDATA_PATH);
    }


    public String verifyCreateRepository() {
        report.log(LogStatus.INFO, "Request URI: " + UrlConstants.CREATE_REPO_URL);
        report.log(LogStatus.INFO, "Request method: POST");

        String token = propertyParser.getPropertyValue("bearer_token");
        Response response = APIUtils.postRepo(token, UrlConstants.CREATE_REPO_URL, RepositoryData.createRepositoryData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_201);
        String repoName = null;
        if (actualStatus == Constants.STATUS_201) {
            report.log(LogStatus.INFO, "Verify JSON schema");
            boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.CREATE_REPO_SCHEMA_PATH);
            Verify.verifyBoolean(actualSchema, true);

            repoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(), Constants.NAME);
        }
        else {
            report.log(LogStatus.FAIL,response.getBody().asPrettyString());
            Assert.fail(response.getBody().asPrettyString());
        }
        return repoName;
    }

    public void verifyGetRepository(String repoName) {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.CREATE_REPO_URL);
        report.log(LogStatus.INFO, "Request method: GET");

        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.getRepo(token,UrlConstants.REPOSITORY_URL,owner,repoName);

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_200);

        if (actualStatus==Constants.STATUS_200) {

            report.log(LogStatus.INFO, "Verify JSON schema");
            boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.CREATE_REPO_SCHEMA_PATH);
            Verify.verifyBoolean(actualSchema, true);

            report.log(LogStatus.INFO, "Verify response body");
            String actualRepoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(), Constants.NAME);
            String login = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(), "owner.login");

            report.log(LogStatus.INFO, "Verify login username");
            Verify.verifyString(login, owner);
            report.log(LogStatus.INFO, "Verify repository name");
            Verify.verifyString(actualRepoName, repoName);
        }
        else {
            report.log(LogStatus.FAIL,response.getBody().asPrettyString());
            Assert.fail(response.getBody().asPrettyString());
        }

    }

    public String verifyUpdateRepository(String repoName) {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.CREATE_REPO_URL);
        report.log(LogStatus.INFO, "Request method: PATCH");

        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.patchRepo(token,UrlConstants.REPOSITORY_URL,owner,repoName,RepositoryData.updateRepoData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_200);

        report.log(LogStatus.INFO, "Verify JSON schema");
        boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.CREATE_REPO_SCHEMA_PATH);
        Verify.verifyBoolean(actualSchema, true);

        report.log(LogStatus.INFO,"Verify response body");
        String actualRepoName = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.NAME);
        String login = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),"owner.login");

        report.log(LogStatus.INFO,"Verify login username");
        Verify.verifyString(login,owner);
        return actualRepoName;

    }

    public void verifyEnableVulnerability(String repoName){
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.VULNERABLITY_URL);
        report.log(LogStatus.INFO, "Request method: PUT");
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.putWithoutBody(token, UrlConstants.VULNERABLITY_URL,owner,repoName);

        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
    }
    public void verifyDisableVulnerability(String repoName){
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.VULNERABLITY_URL);
        report.log(LogStatus.INFO, "Request method: DELETE");
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.delete(token, UrlConstants.VULNERABLITY_URL,owner,repoName);
        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
    }
    public void verifyDeleteRepository(String repoName) {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.REPOSITORY_URL);
        report.log(LogStatus.INFO, "Request method: DELETE");
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.delete(token, UrlConstants.REPOSITORY_URL,owner,repoName);

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_204);
    }

    public void verifyInValidToken() {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.CREATE_REPO_URL);
        report.log(LogStatus.INFO, "Request method: POST");
        String token = propertyParser.getPropertyValue("invalid_token");
        Response response = APIUtils.postRepo(token, UrlConstants.CREATE_REPO_URL, RepositoryData.createRepositoryData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_401);

        report.log(LogStatus.INFO, "Verify JSON schema");
        boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.BAD_CREDENTIALS_SCHEMA_PATH);
        Verify.verifyBoolean(actualSchema, true);

        String actualMessage = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.MESSAGE);
        String expectedMessage = propertyParser.getPropertyValue("invalid_token_message");
        Verify.verifyString(actualMessage,expectedMessage);
    }

    public void verifyInvalidRepo() {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.CREATE_REPO_URL);
        report.log(LogStatus.INFO, "Request method: GET");
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        String repoName = propertyParser.getPropertyValue("invalid_repo_name");

        Response response = APIUtils.getRepo(token,UrlConstants.REPOSITORY_URL,owner,repoName);

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_404);

        report.log(LogStatus.INFO, "Verify JSON schema");
        boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.BAD_CREDENTIALS_SCHEMA_PATH);
        Verify.verifyBoolean(actualSchema, true);

        report.log(LogStatus.INFO,"Verify response body");
        String actualMessage = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.MESSAGE);
        String expectedMessage = propertyParser.getPropertyValue("invalid_repo_message");
        Verify.verifyString(actualMessage,expectedMessage);


    }

    public String verifyCreateTagProtectionRepository(String repoName) {
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.TAG_PROTECTION_URL);
        report.log(LogStatus.INFO, "Request method: POST");

        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.postWithParam(token, UrlConstants.TAG_PROTECTION_URL,owner, repoName,TagProtectionData.createTagProtectionData());

        int actualStatus = response.getStatusCode();
        Verify.verifyInteger(actualStatus, Constants.STATUS_201);

        report.log(LogStatus.INFO, "Verify JSON schema");
        boolean actualSchema = APIUtils.validateJsonSchema(response, FilePathConstants.TAG_PROTECTION_SCHEMA_PATH);
        Verify.verifyBoolean(actualSchema, true);

        String tagProtectionId = APIUtils.getValueByJsonPath(response.getBody().asPrettyString(),Constants.ID);
        return tagProtectionId;
    }

    public void verifyDeleteTagProtection(String repoName,String tagProtectionID){
        report.log(LogStatus.INFO, "Request URI: "+UrlConstants.TAG_PROTECTION_URL);
        report.log(LogStatus.INFO, "Request method: DELETE");
        String token = propertyParser.getPropertyValue("bearer_token");
        String owner = propertyParser.getPropertyValue("repo_owner");
        Response response = APIUtils.delete(token, UrlConstants.TAG_PROTECTION_URL+"/"+tagProtectionID,owner,repoName);
        Verify.verifyInteger(response.getStatusCode(),Constants.STATUS_204);
    }
}
