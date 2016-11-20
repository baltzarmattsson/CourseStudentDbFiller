package app.utils;

public class Utils {
	
	public static boolean isNumber(String inputString) {
		
		char[] ca = inputString.toCharArray();
		for (char c : ca) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
		
	}

}
