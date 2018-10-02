package RestAssured.RestAssuredAPI;

import org.hamcrest.Matchers;
import org.testng.Reporter;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

public class TestWeatherWithAPPID {

	@Test
	public void getWeatherInfoBangalore() {
		RestAssured.baseURI = "http://api.openweathermap.org/data/2.5/weather";

		String response = RestAssured.given().param("id", "1277333").param("appid", "302ec8a27a2d3007196f638138f836e0")
				.when().get().then().extract().asString();

		// Method 1 way to verify
		ValidatableResponse res = RestAssured.given().param("id", "1277333")
				.param("appid", "302ec8a27a2d3007196f638138f836e0").when().get().then();
		res.statusCode(200);
		res.contentType(ContentType.JSON);

		// For verifying the contents of body
		Object countryName = res.extract().response().path("sys.country");
		System.out.println(countryName);

		// Method 2 to verify using JSON path
		JsonPath jasonExecutor = new JsonPath(response);
		Object countryNm = jasonExecutor.get("sys.country");
		System.out.println("Country Name: " + countryNm);

		res.body("sys.country", Matchers.notNullValue());
		res.body("sys.country", Matchers.equalToIgnoringCase("IN"));
		Reporter.log("Country code has verified successfully", true);

		res.body("name", Matchers.notNullValue());
		res.body("name", Matchers.equalToIgnoringCase("Bangalore"));
		Reporter.log("City name has verified successfully", true);

	}

	// Note purposely made spelling mistake to get 404
	@Test(enabled = false)
	public void getWeatherInfoOfBangaloreWithInValidData1() {
		ValidatableResponse res = RestAssured.given().param("q", "Bangaloree")
				.param("appid", "302ec8a27a2d3007196f638138f836e0").when().get().then();

		Reporter.log("Response is: " + res.extract().asString(), true);

		res.statusCode(404);
		res.body("message", Matchers.notNullValue());
		res.body("message", Matchers.equalToIgnoringCase("city not found"));
		Reporter.log("Verified the error message successfully", true);
	}

	// Gave wrong get to the 401
	@Test(enabled = false)
	public void getWeatherInfoOfBangaloreWithInValidData2() {
		ValidatableResponse res = RestAssured.given().param("q", "Bangalore")
				.param("appid", "17e5c69afcef0f16365a6c3b0cba4400A").when().get().then();

		Reporter.log("Response is: " + res.extract().asString(), true);

		res.statusCode(401);
		res.body("message", Matchers.notNullValue());
		res.body("message", Matchers.containsString("Invalid API key"));
		Reporter.log("Verified the error message successfully", true);
	}

	public void someImportantpoints() {

		// Note
		RestAssured.given().auth().preemptive().basic("userName", "password").param("grant_type", "password");

		// How to pass access token and other things in header
		Header first = new Header("Authorization", "access Token key");
		Header second = new Header("X-Remote-User", "username");
		Header third = new Header("Content-Type", "application/json");

		Headers headers = new Headers(first, second, third);
		RestAssured.given().headers(headers).param("", "");
	}

}
