package feature.api;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetAPITest {

    @Test
    public void testUserDetails() {
        //Thiết lập restassured
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = RestAssured
                .given()
                .when()
                .get("/users/1"); //phương thức

        // Status code
        assertEquals(response.statusCode(), 200, "Status code phải là 200");

        // Field id
        assertEquals(response.jsonPath().getInt("id"), 1, "ID phải bằng 1");
        // Field username
        assertEquals(response.jsonPath().getString("username"), "Bret", "Username phải là 'Bret'");

        //Field: email định dạng đúng
        String email = response.jsonPath().getString("email");
        assertTrue(email.contains("@"), "Email không hợp lệ");
    }
}