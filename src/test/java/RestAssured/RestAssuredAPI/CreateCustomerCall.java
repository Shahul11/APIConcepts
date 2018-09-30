package RestAssured.RestAssuredAPI;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateCustomerCall {

	@Test
	public void createCustomer() {
		// 1. specify the url:
		RestAssured.baseURI = "http://restapi.demoqa.com/customer/register";

		// 2. Define a request:
		RequestSpecification httpRequest = RestAssured.given();

		// 3. prepare json object: add key-value pairs in JSON:
		JSONObject requestJson = new JSONObject();
		requestJson.put("FirstName", "Selenium");

		// 4. add the header:
		httpRequest.header("Content-Type", "application/json");

		// 5. add the json payload to the request:
		httpRequest.body(requestJson.toJSONString());

		// 6. hit the request:
		Response response = httpRequest.post();

		// 7. get the status code:
		System.out.println("the response code is: " + response.getStatusCode());

		// 8. get the response body:
		String responseBody = response.body().asString();
		System.out.println("The response body is: " + responseBody);

		// De-Serilization

		if (response.statusCode() == 200) { // De-Serilization
			CustomerResponse customerResponse = response.as(CustomerResponse.class);

			System.out.println(customerResponse.Message);
			System.out.println(customerResponse.SucccessCode);
		}

		else if (response.statusCode() == 201) {

			CustomerResponse customerResponse = response.as(CustomerResponse.class);

			System.out.println(customerResponse.FaultID);
			System.out.println(customerResponse.fault);

		}

	}

}
