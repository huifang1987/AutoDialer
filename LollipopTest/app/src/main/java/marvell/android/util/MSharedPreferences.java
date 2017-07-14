package marvell.android.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class MSharedPreferences {

	static public boolean writeIntData(Context context, String fileName,
			Map<String, Integer> map) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = mySharedPreferences.edit();

		Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Integer> e = iter.next();
			String key = e.getKey();
			int value = e.getValue();
			LogUtil.d(key + "¡ú" + value);

			editor.putInt(key, value);
			System.out.println(key + "¡ú" + value);
		}

		return editor.commit();

	}

	static public boolean writeBooleanData(Context context, String fileName,
			Map<String, Boolean> map) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = mySharedPreferences.edit();

		Iterator<Entry<String, Boolean>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Boolean> e = iter.next();
			String key = e.getKey();
			boolean value = e.getValue();
			LogUtil.d(key + "¡ú" + value);

			editor.putBoolean(key, value);
			System.out.println(key + "¡ú" + value);
		}

		return editor.commit();

	}

	static public boolean writeStringData(Context context, String fileName,
			Map<String, String> map) {

		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		SharedPreferences.Editor editor = mySharedPreferences.edit();

		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> e = iter.next();
			String key = e.getKey();
			String value = e.getValue();
			LogUtil.d(key + "¡ú" + value);

			editor.putString(key, value);
			System.out.println(key + "¡ú" + value);
		}

		return editor.commit();

	}

	static public Map<String, ?> getAll(Context context, String fileName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		Map<String, ?> map = new HashMap<String, Object>();

		map = sharedPreferences.getAll();

		return map;
	}

	static public String getString(Context context, String fileName,
			String key, String defValue) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		return sharedPreferences.getString(key, defValue);

	}

	static public boolean getBoolean(Context context, String fileName,
			String key, boolean defValue) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		return sharedPreferences.getBoolean(key, defValue);

	}

	static public int getInt(Context context, String fileName, String key,
			int defValue) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		return sharedPreferences.getInt(key, defValue);

	}

	static public float getFloat(Context context, String fileName, String key,
			float defValue) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		return sharedPreferences.getFloat(key, defValue);

	}

	public static long getLong(Context context, String fileName, String key,
			long defValue) {

		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Activity.MODE_PRIVATE);

		return sharedPreferences.getLong(key, defValue);

	}

}
