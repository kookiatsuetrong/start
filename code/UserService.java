import java.util.Map;
import java.util.TreeMap;
import web.framework.Context;
import jakarta.servlet.http.HttpSession;

class UserService {

	/**
	 * Creates a new photo code and ...
	 * 1. send photo to the client
	 * 2. keep value in the session
	 * 
	 * GET /service-create-photo-code
	 */
	static Object 
	createPhotoCode(Context context) {
		String value = Tool.randomPhotoCode();
		String photo = Tool.createPhotoCode(value);
		System.out.println("New Photo Code is " + value);

		if (context.testing == true) {
			context.setSession("photo-code", value);
		}

		if (context.testing == false) {
			HttpSession session = context.getSession(true);
			session.setAttribute("photo-code", value);
		}

		TreeMap<String, Object> map = new TreeMap<>();
		map.put("result", "OK");
		map.put("photo", photo);
		return map;
	}
	/*
	Windows:
	curl http://localhost:5200/service-create-photo-code

	Unix:
	curl http://localhost:5200/service-create-photo-code
	*/


	/**
	 * Checks the existence of the given email
	 * 
	 * context.bodyBuffer is a string of JSON such as:
	 * "{ email: 'user@email.com', photoCode: '1234' }"
	 * 
	 */
	static Object 
	checkUserEmail(Context context) {
		Map input = context.read();
		System.out.println(input);
		String email = (String)input.get("email");
		
		TreeMap<String, Object> output = new TreeMap<>();
		if (email == null) {
			output.put("result", "ERROR");
			output.put("reason", "Invalid email");
			return output;
		}
		
		// Case 1: Correct code   and Existing email ---> Ask password
		// Case 2: Correct code   and New Email      ---> Create account
		// Case 3: Incorrect code and Existing email ---> Restart
		// Case 4: Incorrect code and New Email      ---> Restart
		
		String photoCode = "";
		if (context.testing == true) {
			photoCode = (String)context.getSession("photo-code");
		}

		if (context.testing == false) {
			HttpSession session = context.getSession(true);
			photoCode = (String)session.getAttribute("photo-code");
			session.removeAttribute("photo-code");
		}
	
		boolean correctPhotoCode = false;
		String code = (String)input.get("photoCode");
		if (photoCode == null) {
			photoCode = "";
		}
		if (photoCode.equals(code)) {
			correctPhotoCode = true;
		}
		
		User user = Storage.getUserByEmail(email);
		if (user == null) {
			output.put("result", "ERROR");
			output.put("reason", "Email was not found");
			output.put("correctPhotoCode", correctPhotoCode);
		}
		
		if (valid(user)) {
			output.put("result", "OK");
			output.put("reason", "Email was found");
			output.put("firstName", user.firstName);
			output.put("lastName",  user.lastName);
			output.put("correctPhotoCode", correctPhotoCode);
		}
		
		return output;
	}
	/*
	HTTP Transaction: 
	POST /service-check-email
	Content-Type: application/json

	{ "email": "user@email.com", "photoCode": "1234" }

	Windows:
	curl --verbose --data {email:"user@email.com", photoCode: "1234"} ^
	http://localhost:5200/service-check-email

	Unix:

	*/

	static boolean
	valid(Object o) {
		return o != null;
	}

}
