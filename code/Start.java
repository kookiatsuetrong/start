import java.util.Map;
import java.util.TreeMap;
import static web.framework.Rockstar.*;
import web.framework.Context;
import web.framework.View;

class Start {
	
	void main() {
		handle( "GET /",          Start::showHome);
		handle( "GET /ask-email", Start::askEmail);
		handle( "GET /service-create-photo-code", UserService::createPhotoCode);
		handle("POST /service-check-email", UserService::checkUserEmail);
	}
	
	static Object showHome(Context context) {
		return new View("/start.html");
	}
	
	static Object askEmail(Context context) {
		return new View("/ask-email.html");
	}
	
	
}
