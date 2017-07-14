package marvell.android.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	final public static int ms = 1;
	final public static int s = 2;
	final public static int m = 3;
	final public static int h = 4;

	public static void sleep(int during, int type) {

		long milliseconds;

		switch (type) {
		case s:
			milliseconds = during * 1000;
			break;

		case m:
			milliseconds = during * 60 * 1000;
			break;

		case h:
			milliseconds = during * 60 * 60 * 1000;
			break;

		default:

			milliseconds = during;
		}

		try {

			Thread.sleep(milliseconds);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}
	
	public static String getDateString4Win(Date date) {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH-mm-ss");

		return sDateFormat.format(date);

	}

	public static String getDateString(Date date) {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		return sDateFormat.format(date);

	}

	public static Date paraseDateString(String s) {

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			return sDateFormat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	static public boolean inLimitedTime(Date startDate, Date endDate, int type,
			int unit) {

		boolean result = false;

		if (endDate.compareTo(startDate) >= 0) {

			long s = getDistanceByType(startDate, endDate, type);
			if (s <= unit)
				result = true;

		}

		return result;

	}

	public static long getDistanceByType(Date startDate, Date endDate, int type) {

		long during = endDate.getTime() - startDate.getTime();

		switch (type) {
		case s:
			during = during / 1000;
			break;

		case m:
			during = during / 60 / 1000;
			break;

		case h:
			during = during / 60 / 60 / 1000;
			break;

		}

		return during;

	}

}
