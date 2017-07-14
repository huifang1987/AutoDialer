package marvell.android.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import marvell.android.testsuit.TConstant;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class MTelephonyManager {

	static public String getMsisdn(Context context, int slotId) {

		String msisdn = "";

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getMsisdn", new Class[] { int.class });

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			msisdn = (String) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] { slotId });

			LogUtil.d("slot" + slotId + " msisdn=" + msisdn);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msisdn;

	}

	static public int getCallState(Context context) {

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		int state = telephonyMgr.getCallState();

		LogUtil.d("total call state=" + state);

		return state;
	}

	static public int getCallState(Context context, int slotId) {

		int state = TelephonyManager.CALL_STATE_IDLE;

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getCallState",
							new Class[] { long.class });

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			state = (int) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] { slotId });

//			LogUtil.d("slot" + slotId + " state=" + state);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return state;
	}

	static public String getIMEI(Context context, int slotId) {

		String imei = "";

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getImei", new Class[] { int.class });

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			imei = (String) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] { slotId });

			LogUtil.d("slot" + slotId + " imei=" + imei);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imei;

	}

	static public String getIMSI(Context context, int slotId) {

		String imsi = "";

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {
			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getSubscriberId",
							new Class[] { int.class });

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			imsi = (String) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] { slotId });

			LogUtil.d("slot" + slotId + " imsi=" + imsi);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imsi;

	}

	static public int getPhoneCount(Context context) {

		int phoneCount = 1;

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getPhoneCount", new Class[] {});

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			phoneCount = (int) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] {});

			LogUtil.d("phoneCount=" + phoneCount);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return phoneCount;

	}

	static public int getSims(Context context) {

		boolean hasSim1 = hasSIM(context, TConstant.firstSlotId);

		boolean hasSim2 = hasSIM(context, TConstant.secondSlotId);

		if (hasSim1 && hasSim2)
			return TConstant.twoSIM;
		else if (!hasSim1 && !hasSim2)
			return TConstant.noSIM;
		else if (hasSim1)
			return TConstant.onlySIM1;
		else
			return TConstant.onlySIM2;
	}

	static public boolean hasSIM(Context context, int slotId) {

		int simState = getSimState(context, slotId);

		if (TelephonyManager.SIM_STATE_READY == simState) {

			LogUtil.d("slot" + slotId + " has sim");

			return true;

		} else {

			LogUtil.d("slot" + slotId + " no sim");

			return false;
		}
	}

	static private int getSimState(Context context, int slotId) {

		int simState = 0;

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("getSimState", new Class[] { int.class });

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			simState = (int) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] { slotId - 1 });

			LogUtil.d("slot" + slotId + " simState=" + simState);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return simState;

	}

	static public boolean endCall(Context context) {

		
		LogUtil.d("Now start to disconnect the call");
		
		boolean result = false;

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("endCall", new Class[] {});

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			result = (boolean) getITelephonyMethod.invoke(telephonyMgr,
					new Object[] {});

			if (result)
				LogUtil.d("Successfully end the call");
			else
				LogUtil.d("Fail to end the call");

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	static public boolean isCMCC(Context context, int slotId) {

		String imsi = getIMSI(context, slotId);
		if (imsi.startsWith("46000") || imsi.startsWith("46002")
				|| imsi.startsWith("46007") || imsi.startsWith("46008"))
			return true;
		else
			return false;

	}

	static public boolean answerRingingCall(Context context) {

		boolean result = true;

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {

			Method getITelephonyMethod = TelephonyManager.class
					.getDeclaredMethod("answerRingingCall", new Class[] {});

			getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

			getITelephonyMethod.invoke(telephonyMgr, new Object[] {});

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtil.d("NoSuchMethodException");
			result = false;

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			LogUtil.d("IllegalAccessException");
			e.printStackTrace();
			result = false;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			LogUtil.d("IllegalArgumentException");
			e.printStackTrace();
			result = false;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			LogUtil.d("InvocationTargetException");
			e.printStackTrace();
			try {
				LogUtil.d("Sandy" + "for version 4.1 or larger");
				Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				context.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");
			} catch (Exception e2) {
				LogUtil.d("Sandy" + e2);
				Intent meidaButtonIntent = new Intent(
						Intent.ACTION_MEDIA_BUTTON);
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
				context.sendOrderedBroadcast(meidaButtonIntent, null);
			}
			result = false;
		} catch (Exception e) {
			LogUtil.d("Sandy");
			try {
				LogUtil.d("Sandy" + "for version 4.1 or larger");
				Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				context.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");
			} catch (Exception e2) {
				LogUtil.d("Sandy" + e2);
				Intent meidaButtonIntent = new Intent(
						Intent.ACTION_MEDIA_BUTTON);
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
				context.sendOrderedBroadcast(meidaButtonIntent, null);
			}
		}

		if (result)
			LogUtil.d("Successfully end the call");
		else
			LogUtil.d("Fail to end the call");

		return result;

	}

	static public ITelephony getITelephony(Context context) throws Exception {

		TelephonyManager telephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		Method getITelephonyMethod = TelephonyManager.class
				.getDeclaredMethod("getITelephony");

		getITelephonyMethod.setAccessible(true);// 私有化函数也能使用

		return (ITelephony) getITelephonyMethod.invoke(telephonyMgr);

	}

}
