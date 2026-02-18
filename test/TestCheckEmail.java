import java.util.TreeMap;
import org.json.JSONObject;
import web.framework.Context;
import web.framework.Rockstar;

public class TestCheckEmail {
	
	// Case 1: Correct Photo Code and Existing Email
	public void test001() {
		Context context = new Context();
		TreeMap output = (TreeMap)UserService.createPhotoCode(context);
		String result = (String)output.get("result");
		String photo  = (String)output.get("photo");
		assert "OK".equals(result);
		assert photo != null;
		
		TreeMap<String, Object> input = new TreeMap<>();
		input.put("email", "user@email.com");
		input.put("photoCode", (String)context.getSession("photo-code"));
		
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context1 = new Context();
		context1.setSession("photo-code", context.getSession("photo-code"));
		context1.setBodyBuffer(buffer);
		TreeMap output1 = (TreeMap)UserService.checkUserEmail(context1);
		String result1 = (String)output1.get("result");
		assert "OK".equals(result1);

		Boolean passed = (Boolean)output1.get("correctPhotoCode");
		assert true == passed;
	}
	
	// Case 2: Correct Photo Code and New Email
	public void test002() {
		Context context = new Context();
		TreeMap output = (TreeMap)UserService.createPhotoCode(context);
		String result = (String)output.get("result");
		String photo  = (String)output.get("photo");
		assert "OK".equals(result);
		assert photo != null;
		
		TreeMap<String, Object> input = new TreeMap<>();
		input.put("email", "user@emailx.com");
		input.put("photoCode", (String)context.getSession("photo-code"));
		
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context1 = new Context();
		context1.setSession("photo-code", context.getSession("photo-code"));
		context1.setBodyBuffer(buffer);
		TreeMap output1 = (TreeMap)UserService.checkUserEmail(context1);
		String result1 = (String)output1.get("result");
		assert "ERROR".equals(result1);

		Boolean passed = (Boolean)output1.get("correctPhotoCode");
		assert true == passed;
	}
	
	// Case 3: Incorrect Photo Code and Existing Email
	public void test003() {
		Context context = new Context();
		TreeMap output = (TreeMap)UserService.createPhotoCode(context);
		String result = (String)output.get("result");
		String photo  = (String)output.get("photo");
		assert "OK".equals(result);
		assert photo != null;
		
		TreeMap<String, Object> input = new TreeMap<>();
		input.put("email", "user@email.com");
		input.put("photoCode", "123X");
		
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context1 = new Context();
		context1.setSession("photo-code", context.getSession("photo-code"));
		context1.setBodyBuffer(buffer);
		TreeMap output1 = (TreeMap)UserService.checkUserEmail(context1);
		String result1 = (String)output1.get("result");
		assert "OK".equals(result1);

		Boolean passed = (Boolean)output1.get("correctPhotoCode");
		assert false == passed;
	}
	
	
	// Case 4: Incorrect Photo Code and New Email
	public void test004() {
		Context context = new Context();
		TreeMap output = (TreeMap)UserService.createPhotoCode(context);
		String result = (String)output.get("result");
		String photo  = (String)output.get("photo");
		assert "OK".equals(result);
		assert photo != null;
		
		TreeMap<String, Object> input = new TreeMap<>();
		input.put("email", "user@emailx.com");
		input.put("photoCode", "123X");
		
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context1 = new Context();
		context1.setSession("photo-code", context.getSession("photo-code"));
		context1.setBodyBuffer(buffer);
		TreeMap output1 = (TreeMap)UserService.checkUserEmail(context1);
		String result1 = (String)output1.get("result");
		assert "ERROR".equals(result1);

		Boolean passed = (Boolean)output1.get("correctPhotoCode");
		assert false == passed;
	}
	
	// Test checkUserEmail() with no email
	public void test008() {
		TreeMap<String, Object> input = new TreeMap<>();
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context = new Context();
		context.setBodyBuffer(buffer);
		
		TreeMap output = (TreeMap)UserService.checkUserEmail(context);
		String result = (String)output.get("result");
		assert "ERROR".equals(result);
	}
	
	// Test checkUserEmail() with user@email.com
	public void test009() {
		TreeMap<String, Object> input = new TreeMap<>();
		input.put("email", "user@email.com");
		JSONObject json = Rockstar.fromMap(input);
		String buffer = json.toString();
		
		Context context = new Context();
		context.setBodyBuffer(buffer);
		
		TreeMap output = (TreeMap)UserService.checkUserEmail(context);
		String result = (String)output.get("result");
		assert "OK".equals(result);
		
		String firstName = (String)output.get("firstName");
		assert "System".equals(firstName);
		
		String lastName  = (String)output.get("lastName");
		assert "Administrator".equals(lastName);
	}
	
}
