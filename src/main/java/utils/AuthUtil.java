package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthUtil {
    public static Response basicAuth(String username, String password, String url) {
        return given()
                .log().all()
                .auth()
                .basic(username, password)
                .get(url);
    }

    public static Response digestAuth(String username, String password, String url) {
        return given()
                .log().all()
                .auth()
                .digest(username, password)
                .get(url);
    }

    public static RequestSpecification accessToken(String accessToken) {
        return given()
                .auth()
                .oauth2(accessToken);

    }

    public static Response apiKey(String key, String value, String url) {
        return given()
                .contentType(ContentType.JSON)
                .queryParam(key, value)
                .log().all()
                .when()
                .get(url);
    }

    public static Response bearerToken(String token, String url) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get(url);
    }
}
