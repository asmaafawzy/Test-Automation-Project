package org.pages;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class RestAssuredAPIs {
    private static final String BASE_URI = Utilities.getJsonData("APIsTestData","BaseURI");
    private static final String CLIENTNAME = Utilities.getJsonData("APIsTestData", "clientName");
    private static final String CLIENTEMAIL = Utilities.getJsonData("APIsTestData", "clientEmail") + Utilities.getTimestamp() + "@gmail.com";
    private static final String BOOKID = Utilities.getJsonData("APIsTestData", "bookId");
    private static String token;
    public static String UpdatedClientName = CLIENTNAME + Utilities.getTimestamp();
    protected static JsonObject jsonBody;
    public static Response apiResponse;
    public static String getAccessToken() {
        jsonBody = new JsonObject();
        jsonBody.addProperty("clientName", CLIENTNAME);
        jsonBody.addProperty("clientEmail", CLIENTEMAIL);
        apiResponse = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .post("/api-clients/");
        token = apiResponse.jsonPath().getString("accessToken");
        return token;
    }
    public static Response submitNewOrder(String accessToken) {
        jsonBody = new JsonObject();
        jsonBody.addProperty("bookId", BOOKID);
        jsonBody.addProperty("customerName", CLIENTNAME);
        apiResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .post("/orders");

        return apiResponse;
    }
    public static Response UpdateOrderByOrderID(String accessToken, String ID) {
        jsonBody = new JsonObject();
        jsonBody.addProperty("customerName", UpdatedClientName);
        apiResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .pathParam("pathParamName", ID)
                .patch("/orders/{pathParamName}");
        apiResponse.prettyPrint();
        return apiResponse;
    }

    public static Response GetOrderByOrderID(String accessToken, String ID) {
        jsonBody = new JsonObject();
        jsonBody.addProperty("customerName", CLIENTNAME);
        apiResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .pathParam("pathParamName", ID)
                .get("/orders/{pathParamName}");
        return apiResponse;
    }
    public static Response DeleteOrderByOrderID(String ID) {
        apiResponse = given()
                .baseUri(BASE_URI)
                .header("Authorization", token)
                .contentType(ContentType.JSON)
                .pathParam("pathParamName", ID)
                .delete("/orders/{pathParamName}");
        return apiResponse;
    }
}
