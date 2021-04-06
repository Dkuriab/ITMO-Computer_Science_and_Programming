public class SumLong {	
	public static void main(String[] args) {
		long d = 0;
		
		for (int j = 0; j < args.length; j++) {			
			int i = 0;
			int start = 0;
			while (i < args[j].length()) {
				if (!Character.isWhitespace(args[j].charAt(i))) {
					start = i;
					while ((i < args[j].length()) && !Character.isWhitespace(args[j].charAt(i))) {
						i++;
					}
					d += Long.parseLong(args[j].substring(start, i));
				}		
			    i++;
			}
		}
		System.out.println(d);
    }
}