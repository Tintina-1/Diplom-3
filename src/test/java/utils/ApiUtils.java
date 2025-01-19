package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiUtils {

    private String baseUrl;

    public ApiUtils(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String registerUser(String name, String email, String password) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"" + name + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(baseUrl + "api/auth/register");

        if (response.statusCode() == 200) {
            String authToken = response.jsonPath().getString("accessToken");
            System.out.println("Generated Auth Token for Registration: " + authToken);  // Log token to console
            return authToken;
        } else {
            response.then().log().all();
            return null;
        }
    }

    public String loginUser(String email, String password) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"" + email + "\", \"password\": \"" + password + "\" }")
                .when()
                .post(baseUrl + "api/auth/login");

        if (response.statusCode() == 200) {
            String authToken = response.jsonPath().getString("accessToken");
            System.out.println("Generated Auth Token for Login: " + authToken);  // Log token to console
            return authToken;
        } else {
            response.then().log().all();
            return null;
        }
    }

    public void deleteUser(String authToken) {
        Response response = given()
                .header("Authorization", authToken)
                .header("Accept", "application/json")
                .when()
                .delete(baseUrl + "api/auth/user");

        response.then().log().all();
    }
}