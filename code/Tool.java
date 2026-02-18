import java.io.FileReader;

public class Tool {
	
	public static String createPhotoCode(final String text) {
		String name = "photo-code/" + text + ".txt";
		String buffer = "";
		try {
			FileReader fr = new FileReader(name);
			while (true) {
				int k = fr.read();
				if (k == -1) break;
				buffer += (char)k;
			}
		} catch (Exception e) { 
			System.out.println("File not found " + name);
		}
		return buffer;
	}
	
	public static String randomPhotoCode() {
		int r = (int)(Math.random() * 10000);
		String s = "" + r;
		while (s.length() < 4) { s = "0" + s; }
		return s;
	}
	
	public static String randomActivationCode() {
		int r = (int)(Math.random() * 1000000);
		String s = "" + r;
		while (s.length() < 6) { s = "0" + s; }
		return s;
	}
}