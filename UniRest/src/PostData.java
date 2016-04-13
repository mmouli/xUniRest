import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import com.autocognite.unitee.lib.testcommons.annotate.Exclude;
import com.autocognite.unitee.lib.teststyler.TestMethodSuite;
import com.autocognite.unitee.lib.validator.Assertions;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class PostData extends TestMethodSuite {

	@Exclude
	public void testPostBodyData() throws Exception {
		String expected = "I am Back";
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		RequestBodyEntity requestbody = request.body(expected);
		HttpResponse<JsonNode> resp = requestbody.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		Assertions.assertEquals(expected, jsonResp.getString("data"));

	}

	@Exclude
	public void testPostFormData() throws Exception {
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Name", "Shailu");
		HttpResponse<JsonNode> resp = request.fields(map).asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		Assertions.assertEquals(new JSONObject(map).toString(), jsonResp.getJSONObject("form").toString());

	}

	@Exclude
	public void testPostFileInBody() throws Exception {
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		byte[] bytes = getBytes("C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
		request.body(bytes);
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		System.out.println(jsonResp);
		System.out.println(jsonResp.get("data").getClass());
	}

	@Exclude
	public void testPostFileInForm() throws Exception {
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		String filePath = "C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt";
		File f = new File(filePath);
		request.field("file", f);
		String expected = readFileToString(filePath);
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		String actual = jsonResp.getJSONObject("files").get("file").toString();
		Assertions.assertEquals(actual, expected);

	}

	@Exclude
	public void testPostFileInFormUsingRawBody() throws Exception {
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		String filePath = "C:/Users/TestMile/workspace/Ex-UniRest/UniRest/src/FileUpload.txt";
		File f = new File(filePath);
		request.field("file", f);
		String expected = readFileToString(filePath);
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = convertRawBodyToJson(resp);
		String actual = jsonResp.getJSONObject("files").get("file").toString();
		Assertions.assertEquals(actual, expected);

	}
	
	public void testGetFile() throws Exception {
		HttpRequest request = new HttpRequestWithBody(HttpMethod.GET, "http://httpbin.org/robots.txt");
		HttpResponse<String> resp = request.asString();
		System.out.println(resp.getBody());
		System.out.println(resp.getHeaders().getFirst("Content-Type"));
//		String 
//		System.out.println(ContentType.TEXT_PLAIN.toString());//APPLICATION_JSON);
//		JSONObject jsonResp = convertRawBodyToJson(resp);
//		String actual = jsonResp.getJSONObject("files").get("file").toString();
//		System.out.println(actual);
//		Assertions.assertEquals(actual, expected);

	}

	@Exclude
	public void testBasicAuth() throws Exception {
		GetRequest request = new GetRequest(HttpMethod.GET, "http://httpbin.org/headers");
		String userName = "One";
		String password = "two";
		request = request.basicAuth(userName, password);
		String actual = request.getHeaders().get("Authorization").toString().replace("Basic ", "");
		String expected = userName+":"+password;
		Assertions.assertEquals(new String(Base64.decodeBase64(actual), Charset.defaultCharset()), expected);
		System.out.println(new File(System.getenv("ProgramFiles") + " (x86)/Appium").exists());
	}

	public byte[] getBytes(String filePath) throws Exception {
		File f = new File(filePath);
		return getBytes(f);
	}

	public byte[] getBytes(File f) throws Exception {
		FileInputStream f1 = new FileInputStream(f);
		return getBytes(f1);
	}

	public byte[] getBytes(InputStream is) throws Exception {
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		is.close();
		return bytes;
	}

	public JSONObject convertRawBodyToJson(HttpResponse<?> resp) throws Exception {
		InputStream stream = resp.getRawBody();
		return new JSONObject(new String(getBytes(stream), StandardCharsets.UTF_8));
	}

	public String readFileToString(String filePath) throws Exception {
		byte[] bytes = Files.readAllBytes(Paths.get(filePath));
		return new String(bytes, Charset.defaultCharset());

	}
}

// MultipartBody multipartBody = new MultipartBody(request);
// multipartBody.field("Name", "Shailu");

// Iterator<String> itr = jsonResp.keys();
// while(itr.hasNext()){
// String temp = itr.next();
// System.out.println(temp+":"+jsonResp.get(temp));
// }
// System.out.println(jsonResp.getJSONObject("form").toString());
// System.out.println(new JSONObject(map).toString());