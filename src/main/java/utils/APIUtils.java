package utils;

import constants.Constants;
import constants.FilePathConstants;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidationException;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.specification.FilterableRequestSpecification;
import org.testng.Assert;
import payload.pojo.RepositoryPayload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class APIUtils {
    public static Response postRepo(String token, String url, Object body) {
        return given()
                .accept(Constants.ACCEPT)
                .contentType(ContentType.JSON)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .body(body)
                .log().all()
                .when()
                .post(url);
    }

    public static Response delete(String token, String url, String owner, String repo) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .log().all()
                .when()
                .delete(url);
    }
    public static Response getRepo(String token, String url, String owner, String repo) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .log().all()
                .when()
                .get(url);
    }

    public static Response patchRepo(String token, String url, String owner, String repo,RepositoryPayload body) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .body(body)
                .log().all()
                .when()
                .patch(url);
    }

    public static Response putWithoutBody(String token, String url, String owner, String repo) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .log().all()
                .when()
                .put(url);
    }
    public static Response postWithParam(String token, String url, String owner, String repo,Object body) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, Constants.BEARER + token)
                .body(body)
                .log().all()
                .when()
                .post(url);
    }
    public static boolean validateJsonSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
            return true;
        } catch (AssertionError e) {
            return false;
        }
        catch (JsonSchemaValidationException e) {
            Assert.fail("JSON schema validation failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getValueByJsonPath(String json, String jsonPathExpression) {
        try {
            JsonPath jsonPath = new JsonPath(json);
            return jsonPath.getString(jsonPathExpression);
        } catch (JsonPathException e) {
            System.out.println("An exception occurred while parsing JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static void enableRequestResponseLogging() {
        PrintStream logStream;
        try {
            logStream = new PrintStream(new FileOutputStream(FilePathConstants.LOG_FILE_PATH));
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("Failed to create log file: " + FilePathConstants.LOG_FILE_PATH, exception);
        }

        FilterableRequestSpecification requestSpec = (FilterableRequestSpecification) RestAssured.given()
                .filter(new RequestLoggingFilter(LogDetail.ALL, logStream))
                .filter(new ResponseLoggingFilter(LogDetail.ALL, logStream));

        RestAssured.requestSpecification = requestSpec;
    }
}