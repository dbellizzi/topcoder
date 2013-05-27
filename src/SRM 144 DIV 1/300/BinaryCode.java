public class BinaryCode {
	public static String NONE = "NONE";
	
	public String[] decode(String Q) throws Exception {
		String[] P = new String[2];
		for(int attempt = 0; attempt <= 1; attempt++) {
			String result = Integer.toString(attempt);
			try {
				for (int i = 0; i < Q.length() - 1; i++) {
					int before = 0;
					if (i - 1 >= 0) {
						before = charToInt(result, i-1);
					}
					int current = charToInt(Q, i) - charToInt(result, i) - before;
					if (current < 0 || current > 1) {
						result = NONE;
						break;
					}
					result += Integer.toString(current);
				}
				int before = 0;
				if (result.length() - 2 >= 0) {
					before = charToInt(result, result.length() - 2);
				}
				if (charToInt(Q, Q.length()-1) != charToInt(result, result.length()-1) + before) {
					result = NONE;
				}
			} catch (Exception e) {
				result = NONE;
			}
			P[attempt] = result;
		}
		return P;
	} 
	
	int charToInt(String str, int pos) {
	  return Integer.parseInt(Character.toString(str.charAt(pos)));
	}
}