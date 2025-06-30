package feature.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PostAPITest {
    @Test
    public void testCreatePost() {
        // Dữ liệu JSON gửi trong yêu cầu POST
        String requestBody = "{\n" +
                "    \"title\": \"foo\",\n" +
                "    \"body\": \"bar\",\n" +
                "    \"userId\": 1\n" +
                "}";

        // Thiết lập RestAssured
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Thực hiện yêu cầu POST
        Response response = RestAssured.given()
                .basePath("/posts")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post();
        // In log (tùy chọn)
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is: " + responseBody);

        // 🔍 Kiểm tra status code
        assertEquals(response.statusCode(), 201, "Status code phải là 201 Created");

        // 🔍 Kiểm tra header
        assertEquals(response.getContentType(), "application/json; charset=utf-8", "Sai content-type");

        // 🔍 Kiểm tra các field trả về
        assertEquals(response.jsonPath().getString("title"), "foo", "Sai title");
        assertEquals(response.jsonPath().getString("body"), "bar", "Sai body");
        assertEquals(response.jsonPath().getInt("userId"), 1, "Sai userId");

        // 🔍 Kiểm tra có trả về id mới
        int newId = response.jsonPath().getInt("id");
        assertTrue(newId > 0, "ID trả về không hợp lệ");
    }
}