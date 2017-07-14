package marvell.android.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static int ipToInt(String ipAddress) {
		String[] addrArray = ipAddress.split("\\.");
		int num = 0;
		for (int i = 0; i < addrArray.length; i++) {
			int power = 3 - i;
			num += ((Integer.parseInt(addrArray[i]) % 256 * Math
					.pow(256, power)));
		}
		return num;
	}

	public static List<String> splitString(String sourceString, String regular) {

		List<String> result = new ArrayList<String>();

		String[] info = null;
		info = sourceString.split(regular); // 通过分割符号将字符串分割并保存到info数组中
		for (int i = 0; i < info.length; i++)
			result.add(i, info[i]);

		return result;

	}

	public static String FormatButtonText(String targetStr, int stringLength) {
		int curLength = targetStr.getBytes().length;
		if (targetStr != null && curLength > stringLength)
			targetStr = SubStringByte(targetStr, stringLength);
		String newString = "";
		int cutLength = stringLength - targetStr.getBytes().length;
		for (int i = 0; i < cutLength; i++)
			newString += " ";
		return targetStr + newString;
	}

	public static String SubStringByte(String targetStr, int stringLength) {

		while (targetStr.getBytes().length > stringLength)
			targetStr = targetStr.substring(0, targetStr.length() - 1);
		return targetStr;
	}

	// 截取数字
	public static String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	public static String getDateString(Date date) {

		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		return sfd.format(date);
	}

	public static String getUUID() {

		return UUID.randomUUID().toString().trim().replaceAll("-", "");

	}
	
	public static String getNumberString(String s) {
		
		String regEx="[^0-9]";   
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(s);   
		
		return  m.replaceAll("").trim();
	}
}
