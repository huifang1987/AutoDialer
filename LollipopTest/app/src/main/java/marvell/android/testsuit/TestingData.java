package marvell.android.testsuit;

import java.util.HashMap;
import java.util.Map;
import marvell.android.util.MSharedPreferences;
import android.content.Context;

public class TestingData {

	public static String testingXml = "testing";

	static public boolean disableAutoAnswerState(Context context) {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(String.valueOf(TConstant.State.autoAnswer), false);

		return writeBooleanData(context, map);

	}

	static public boolean enableAutoAnswerState(Context context) {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(String.valueOf(TConstant.State.autoAnswer), true);

		return writeBooleanData(context, map);

	}

	static public boolean disableTestingState(Context context) {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(String.valueOf(TConstant.State.testing), false);

		return writeBooleanData(context, map);

	}

	static public boolean enableTestingState(Context context) {

		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put(String.valueOf(TConstant.State.testing), true);

		return writeBooleanData(context, map);

	}

	static public boolean isAutoReset(Context context) {

		return getBoolean(context, String.valueOf(TConstant.State.autoReset),
				false);

	}

	static public boolean isTesting(Context context) {

		return getBoolean(context, String.valueOf(TConstant.State.testing),
				false);

	}

	static public boolean isAutoAnswer(Context context) {

		return getBoolean(context, String.valueOf(TConstant.State.autoAnswer),
				true);

	}

	static public String getSim1Msisdn(Context context) {

		return getString(context, String.valueOf(TConstant.Setting.sim1Msisdn),
				"");

	}
	
	static public String getSim2Msisdn(Context context) {

		return getString(context, String.valueOf(TConstant.Setting.sim2Msisdn),
				"");

	}

	static public Map<String, Boolean> getState(Context context) {

		Map<String, ?> allData = getAll(context);

		Map<String, Boolean> stateMap = new HashMap<String, Boolean>();
		for (TConstant.State s : TConstant.State.values()) {

			stateMap.put(String.valueOf(s), (Boolean) allData.get(s));

		}

		return stateMap;

	}

	static public void initialData(Context context) {

		initialStatistics(context);
		initialState(context);
		initialSetting(context);

	}

	static public void initialSetting(Context context) {

		Map<String, String> data = new HashMap<String, String>();

		for (TConstant.Setting s : TConstant.Setting.values()) {

			data.put(String.valueOf(s), "");

		}

		writeStringData(context, data);

	}

	static public void initialState(Context context) {

		Map<String, Boolean> data = new HashMap<String, Boolean>();

		for (TConstant.State s : TConstant.State.values()) {

			if (TConstant.State.autoAnswer == s) {

				data.put(String.valueOf(s), true);

			} else

				data.put(String.valueOf(s), false);

		}

		writeBooleanData(context, data);

	}

	static public void initialStatistics(Context context) {

		Map<String, Integer> data = new HashMap<String, Integer>();

		for (TConstant.Statistics s : TConstant.Statistics.values()) {

			data.put(String.valueOf(s), 0);

		}

		writeIntData(context, data);

	}

	static public boolean writeStringData(Context context,
			Map<String, String> map) {

		return MSharedPreferences.writeStringData(context, testingXml, map);
	}

	static public boolean writeIntData(Context context, Map<String, Integer> map) {

		return MSharedPreferences.writeIntData(context, testingXml, map);
	}

	static public boolean writeBooleanData(Context context,
			Map<String, Boolean> map) {

		return MSharedPreferences.writeBooleanData(context, testingXml, map);
	}

	static public Map<String, ?> getAll(Context context) {

		return MSharedPreferences.getAll(context, testingXml);
	}

	static public String getString(Context context, String key, String defValue) {

		return MSharedPreferences.getString(context, testingXml, key, defValue);

	}

	static public boolean getBoolean(Context context, String key,
			boolean defValue) {

		return MSharedPreferences
				.getBoolean(context, testingXml, key, defValue);
	}

	static public int getInt(Context context, String key, int defValue) {
		return MSharedPreferences.getInt(context, testingXml, key, defValue);

	}

	static public float getFloat(Context context, String key, float defValue) {

		return MSharedPreferences.getFloat(context, testingXml, key, defValue);

	}

	static public long getLong(Context context, String key, long defValue) {

		return MSharedPreferences.getLong(context, testingXml, key, defValue);

	}
}
