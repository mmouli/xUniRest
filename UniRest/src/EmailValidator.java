import org.json.JSONObject;

import com.autocognite.unitee.lib.teststyler.TestMethodSuite;
import com.autocognite.unitee.lib.validator.Assertions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class EmailValidator extends TestMethodSuite {

	public void testvalid_Pass() throws Exception {
		HttpResponse<JsonNode> response = Unirest
				.get("https://pozzad-email-validator.p.mashape.com/emailvalidator/validateEmail/john@gmail.com")
				.header("X-Mashape-Key", "hQlHUigcOumshDtc63QPtAGwVMUsp1Utuopjsns4hApyIuwPwB")
				.header("Accept", "application/json").asJson();
		JSONObject resJson = response.getBody().getObject();
		Assertions.assertTrue(resJson.getBoolean("isValid"));
	}
}

// @Exclude
/*
 * public void testvalid_Fail() throws Exception { // GetRequest getRequest =
 * Unirest.get(
 * "https://pozzad-email-validator.p.mashape.com/emailvalidator/validateEmail/john@gmail.com"
 * ); // .header("X-Mashape-Key",
 * "hQlHUigcOumshDtc63QPtAGwVMUsp1Utuopjsns4hApyIuwPwB") // .header("Accept",
 * "application/json") // .asJson(); // JSONObject resJson =
 * response.getBody().getObject(); // System.out.println(resJson.keySet()); //
 * Assertions.assertTrue(resJson.getBoolean("isValid")); }
 * 
 * @Exclude public void testInvalid_Pass() throws Exception{
 * System.out.println("OK"); GetRequest getRequest = new
 * GetRequest(HttpMethod.GET,
 * "https://pozzad-email-validator.p.mashape.com/emailvalidator/validateEmail");
 * System.out.println("OK"+getRequest.toString()); getRequest =
 * getRequest.header("X-Mashape-Key",
 * "hQlHUigcOumshDtc63QPtAGwVMUsp1Utuopjsns4hApyIuwPwB");
 * System.out.println("OK"+getRequest.toString()); HttpResponse<JsonNode> resp =
 * getRequest.asJson();//queryString("john@gmail.org", "").asJson();
 * Assertions.assertFalse(resp.getBody().getObject().getBoolean("isValid")); }
 * 
 * public void testIP_Pass() throws Exception{ GetRequest request = new
 * GetRequest(HttpMethod.GET, "http://httpbin.org/ip"); HttpResponse<JsonNode>
 * resp = request.asJson();
 * System.out.println(resp.getBody().getObject().keySet());
 * System.out.println(InetAddress.getLocalHost());
 * System.out.println(resp.getBody().getObject().get("origin")); } }
 */
