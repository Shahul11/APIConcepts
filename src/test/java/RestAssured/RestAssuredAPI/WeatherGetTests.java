package RestAssured.RestAssuredAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherGetTests {

	@Test
	public void getWeatherDetails() {

		// 1. define the base url
		//RestAssured.baseURI = "https://api.github.com/users/";

		// 2. define the http request:
		RequestSpecification httpRequest = RestAssured.given();

		// 3. make a request/execute the request:
	//	Response response = httpRequest.request(Method.GET, "naveenanimation20");
		Response response=httpRequest.get("https://api.github.com/users/Shahul11");
		
		JsonPath jasonExecutor= response.jsonPath();
		String username=jasonExecutor.get("login");
		System.out.println(username);

		// 4. get the Response body
		String responseBody = response.getBody().asString();
		System.out.println("Response body is:" + responseBody);

		Assert.assertEquals(responseBody.contains("Shahul11"), true);

		// 5. get the status code and validate it
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);

		// 6. Assertions
		Assert.assertEquals(statusCode, 200);
		System.out.println("The status line is:" + response.statusLine());

		// 7. Getting headers
		Headers headers = response.getHeaders();
		System.out.println(headers);


		// To Get specific headers
		String content = response.getHeader("Content-Type");
		System.out.println("The content type is: " + content);
		
		// you can use jason path to get the values
		JsonPath jasonPathVaule=response.jsonPath();
		int repo= jasonPathVaule.get("public_repos");
		System.out.println(repo);
	}

}
