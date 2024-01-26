package restAssuredTests;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pages.RestAssuredAPIs;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;


public class RestAssuredTestCases {
    private static String authtoken;
    private static Response response;
    private static String orderId;
    private static SoftAssert softAssert;
    private static final Logger logger = LogManager.getLogger(RestAssuredTestCases.class);


    @BeforeClass
    private void setUp() {
        logger.info("----------------------------------------------------------");
        logger.info("Get Access Token ....");
        authtoken = RestAssuredAPIs.getAccessToken();
        logger.info("Access Token: " + authtoken);
    }
    @BeforeMethod
    private static void checkThatUserSubmitsNewOrder() {
        logger.info("Start Test checkThatUserSubmitsNewOrder...");
        response = RestAssuredAPIs.submitNewOrder(authtoken);
        orderId = response.jsonPath().getString("orderId");
        logger.debug("Response Body: {}", response.prettyPrint());
        Assert.assertEquals(response.statusCode(), 201, "Unexpected status code");
        logger.info("End Test checkThatUserSubmitsNewOrder with orderId: " + orderId);
        logger.info("----------------------------------------------------------");
    }
    @Test
    private static void checkUpdateOrderbyOrderID() {
        logger.info("Start Test checkUpdateOrderbyOrderID...");
        softAssert = new SoftAssert();
        logger.info("Update order: " + orderId + " with the customer name to be: "+ RestAssuredAPIs.UpdatedClientName);
        //Update the Order
        response = RestAssuredAPIs.UpdateOrderByOrderID(authtoken, orderId);
        softAssert.assertEquals(response.getStatusCode(), 204, "Unexpected status code");
        //Get the Order
        Response newResponse = RestAssuredAPIs.GetOrderByOrderID(authtoken, orderId);
        logger.debug("Response Body: {}", newResponse.prettyPrint());
        softAssert.assertEquals(newResponse.jsonPath().getString("id"), orderId, "Order Id isn't the same");
        softAssert.assertEquals(newResponse.jsonPath().getString("customerName"), RestAssuredAPIs.UpdatedClientName, "Client Name isn't updated");
        softAssert.assertAll();
        logger.info("End Test checkUpdateOrderbyOrderID......");
    }
    @Test
    private static void CheckThatUserDeleteOrder() {
        softAssert = new SoftAssert();
        logger.info("Start Test CheckThatUserDeleteOrder...");
        //Delete Order
        response = RestAssuredAPIs.DeleteOrderByOrderID(orderId);
        softAssert.assertEquals(response.getStatusCode(), 204, "Unexpected status code");

        //Get The Deleted Order
        response = RestAssuredAPIs.GetOrderByOrderID(authtoken, orderId);
        softAssert.assertEquals(response.getStatusCode(), 404, "Unexpected status code");
        softAssert.assertTrue(response.jsonPath().getString("error").contains(orderId), "the order isn't deleted");
        logger.debug("Response Body: {}", response.prettyPrint());
        softAssert.assertAll();
        logger.info("End Test CheckThatUserDeleteOrder......");
    }
    @AfterMethod
    private static void tearDown()
    {
        logger.info("----------------------------------------------------------");
    }
}