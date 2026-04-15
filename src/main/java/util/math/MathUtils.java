package util.math;

public class MathUtils {
	
	public static int max(int ...numbers) {
		int maximum = Integer.MIN_VALUE;
		for (int n : numbers) {
			if (n > maximum) maximum = n;
		}
		
		return maximum;
	}

}
