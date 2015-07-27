package inhabithat.utils;

public class ThreadTools {
	public static int depthOfRecursion(String string) {
		int count = 0;
		for (StackTraceElement str: Thread.currentThread().getStackTrace()){
			if (str.toString().contains(string)) count++;
		}
		return count;
	}


	public static void throwException(String description){
		throw new RuntimeException(description);
	}
}
