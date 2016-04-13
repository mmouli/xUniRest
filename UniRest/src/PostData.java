import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.autocognite.unitee.lib.testcommons.annotate.Exclude;
import com.autocognite.unitee.lib.teststyler.TestMethodSuite;
import com.autocognite.unitee.lib.validator.Assertions;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

public class PostData extends TestMethodSuite {

	@Exclude
	public void testPostBodyData() throws Exception{
		String expected = "I am Back";
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		RequestBodyEntity requestbody = request.body(expected);
		HttpResponse<JsonNode> resp = requestbody.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		Assertions.assertEquals(expected, jsonResp.getString("data"));
		
	}
	
	@Exclude
	public void testPostFormData() throws Exception{
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("Name", "Shailu");
		HttpResponse<JsonNode> resp = request.fields(map).asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		Assertions.assertEquals(new JSONObject(map).toString(), jsonResp.getJSONObject("form").toString());
		
	}
	
	@Exclude
	public void testPostFileInBody() throws Exception{
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
		byte[] bytes = getBytes("C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
		request.body(bytes);
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		System.out.println(jsonResp);
		System.out.println( jsonResp.get("data").getClass());	
	}
	
	
	public void testPostFileInForm() throws Exception{
		HttpRequestWithBody request = new HttpRequestWithBody(HttpMethod.POST, "http://httpbin.org/post");
//		byte[] bytes = getBytes("C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
//		MultipartBody mulBody = new MultipartBody(request);
//		mulBody.field("file", bytes, ContentType.MULTIPART_FORM_DATA, "C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
		request.field("file", new File("C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt"));
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		InputStream stream = resp.getRawBody();
		byte[] bytes = getBytes(stream);
		for(byte b : bytes){
			System.out.println(Byte.valueOf(b));
		}
		System.out.println(resp.getBody());
		System.out.println(jsonResp);
		System.out.println( jsonResp.get("files").getClass());	
	}
	@Exclude
	public void testBasicAuth() throws Exception{
		GetRequest request = new GetRequest(HttpMethod.GET, "http://httpbin.org/headers");
		request =request.basicAuth("One", "two");
//		byte[] bytes = getBytes("C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
//		MultipartBody mulBody = new MultipartBody(request);
//		mulBody.field("file", bytes, ContentType.MULTIPART_FORM_DATA, "C:/Users/TestMile/workspace/UniRest/src/FileUpload.txt");
		HttpResponse<JsonNode> resp = request.asJson();
		JSONObject jsonResp = resp.getBody().getObject();
		System.out.println(jsonResp);
		System.out.println( jsonResp.get("files").getClass());	
	}    	
	
	public byte[] getBytes(String filePath)throws Exception{
		File f = new File(filePath);
		return getBytes(f);
	}
	
	public byte[] getBytes(File f)throws Exception{
		FileInputStream f1 = new FileInputStream(f);
		return getBytes(f1);
	}
	
	public byte[] getBytes(InputStream is)throws Exception{
		byte[] bytes = new byte[is.available()];
		is.read(bytes);
		is.close();
		return bytes;
	}
}


//MultipartBody multipartBody = new MultipartBody(request);
//multipartBody.field("Name", "Shailu");

//Iterator<String> itr = jsonResp.keys();
//while(itr.hasNext()){
//	String temp = itr.next();
//	System.out.println(temp+":"+jsonResp.get(temp));
//}
//System.out.println(jsonResp.getJSONObject("form").toString());
//System.out.println(new JSONObject(map).toString());