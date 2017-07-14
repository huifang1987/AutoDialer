package marvell.android.util;

import java.util.HashMap;

import android.util.Log;

public class LogUtil {

	final private static String appName = "com.android.fieldtrail.";

	public static void i(String message) {
		HashMap<String, String> newMsg = msgHandler(message);
		Log.i(newMsg.get("tag"), newMsg.get("message"));
	}

	public static void v(String message) {
		HashMap<String, String> newMsg = msgHandler(message);
		Log.v(newMsg.get("tag"), newMsg.get("message"));
	}

	public static void e(String message) {
		HashMap<String, String> newMsg = msgHandler(message);
		Log.e(newMsg.get("tag"), newMsg.get("message"));
	}

	public static void w(String message) {
		HashMap<String, String> newMsg = msgHandler(message);
		Log.w(newMsg.get("tag"), newMsg.get("message"));
	}

	public static void d(String message) {
		HashMap<String, String> newMsg = msgHandler(message);
		Log.d(newMsg.get("tag"), newMsg.get("message"));
	}

	private static HashMap<String, String> msgHandler(final String message) {

		HashMap<String, String> msgMap = new HashMap<String, String>();

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		@SuppressWarnings("unused")
		String fileName = stack[4].getFileName();

		String className = StringUtil
				.splitString(stack[4].getClassName().replace(appName, ""),
						"\\$").iterator().next();
		String methodName = stack[4].getMethodName();
		String lineNumber = Integer.toString(stack[4].getLineNumber());
		String tag = className + ":" + methodName + ",L=" + lineNumber;

		String newMsg = StringUtil.FormatButtonText(message, 160);

		msgMap.put("tag", tag);
		msgMap.put("message", newMsg);

		return msgMap;

	}

}
