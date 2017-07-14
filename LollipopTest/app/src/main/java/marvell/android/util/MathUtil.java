package marvell.android.util;

public class MathUtil {

	public static int getRandomInRanget(int min, int max) {

		return (int) Math.round(Math.random() * (max - min) + min);

	}

	public static boolean rangeInDefined(int current, int min, int max) {
		return Math.max(min, current) == Math.min(current, max);
	}
}
