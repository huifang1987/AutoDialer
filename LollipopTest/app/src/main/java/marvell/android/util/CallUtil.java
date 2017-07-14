package marvell.android.util;


import marvell.android.testsuit.TConstant;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.telecom.PhoneAccountHandle;
import android.telephony.TelephonyManager;

public class CallUtil {

	static public int lastOutgoingCallIsConnected(Context context,
			String number, int slotId) {

		LogUtil.d("Now check the lastOutgoingCallIsConnected for " + slotId);

		int duration = 0;

		Cursor cursor = getCallRecordByNumberAndTypeAndSlotId(context, number,
				CallLog.Calls.OUTGOING_TYPE, slotId);

		if (cursor.moveToFirst()) {

			duration = cursor.getInt(cursor
					.getColumnIndex(CallLog.Calls.DURATION));
		}

		return duration;

	}

	static public boolean isConnected(Context context, int slotId, int duration) {

		LogUtil.d("Now start to check " + slotId + " call state for "
				+ duration);

		int state = MTelephonyManager.getCallState(context);

		int timeout = 0;

		while (state == TelephonyManager.CALL_STATE_OFFHOOK
				&& timeout <= duration) {

			TimeUtil.sleep(1, TimeUtil.s);

			timeout++;

			state = MTelephonyManager.getCallState(context);

		}

		LogUtil.d("last state=" + state + ":timeout=" + timeout);

		if (state == TelephonyManager.CALL_STATE_OFFHOOK && timeout > duration)

			return true;

		else
			return false;

	}

	static public void setIdleMode(Context context) {

		int callState = MTelephonyManager.getCallState(context);

		while (TelephonyManager.CALL_STATE_IDLE != callState) {

			MTelephonyManager.endCall(context);

			TimeUtil.sleep(2, TimeUtil.s);
			
			callState = MTelephonyManager.getCallState(context);

		}

	}

	static public boolean initialCall(Context context, int slotId,
			String dutNumber) {

		LogUtil.d("Now initialLollipopCall for the number " + dutNumber
				+ " from " + slotId);

		PhoneAccountHandle mPhoneAccHandler = new PhoneAccountHandle(
				new ComponentName("com.android.phone",
						"com.android.services.telephony.TelephonyConnectionService"),
				String.valueOf(slotId));

		Intent mIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ dutNumber));

		mIntent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE",
				mPhoneAccHandler);
		mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mIntent);
		
		int timeout = 0;

		int state = MTelephonyManager.getCallState(context);

		while (state != TelephonyManager.CALL_STATE_OFFHOOK
				&& timeout < TConstant.moTimeout) {

			TimeUtil.sleep(1, TimeUtil.s);

			timeout++;

			state = MTelephonyManager.getCallState(context);

		}

		if (state == TelephonyManager.CALL_STATE_OFFHOOK)
			return true;
		else
			return false;

	}

	static public void getAllOutgoingCall(Context context) {

		Cursor cursor = getCallRecordByType(context,
				CallLog.Calls.OUTGOING_TYPE);

		printCallRecord(cursor);

	}

	static public void getAllIncomingCall(Context context) {

		Cursor cursor = getCallRecordByType(context,
				CallLog.Calls.INCOMING_TYPE);

		printCallRecord(cursor);
	}

	static public void getAllMissedCall(Context context) {
		Cursor cursor = getCallRecordByType(context, CallLog.Calls.MISSED_TYPE);

		printCallRecord(cursor);
	}

	static public Cursor getCallRecordByType(Context context, int type) {

		ContentResolver resolver = context.getContentResolver();

		return resolver.query(CallLog.Calls.CONTENT_URI, null,
				CallLog.Calls.TYPE + "=?",
				new String[] { String.valueOf(type) },
				CallLog.Calls.DEFAULT_SORT_ORDER);

	}

	static public Cursor getCallRecordByNumberAndType(Context context,
			String number, int type) {

		ContentResolver resolver = context.getContentResolver();

		return resolver.query(CallLog.Calls.CONTENT_URI, null,
				CallLog.Calls.NUMBER + "=? and " + CallLog.Calls.TYPE + "=?",
				new String[] { number, String.valueOf(type) },
				CallLog.Calls.DEFAULT_SORT_ORDER);

	}

	static public Cursor getCallRecordByNumber(Context context, String dutNumber) {

		ContentResolver resolver = context.getContentResolver();

		return resolver.query(CallLog.Calls.CONTENT_URI, null,
				CallLog.Calls.NUMBER + "=?", new String[] { dutNumber },
				CallLog.Calls.DEFAULT_SORT_ORDER);

	}

	static public Cursor getCallRecordByNumberAndTypeAndSlotId(Context context,
			String number, int type, int slotId) {

		LogUtil.d("getCallRecordByNumberAndTypeAndSlotId for " + slotId);

		ContentResolver resolver = context.getContentResolver();

		return resolver.query(
				CallLog.Calls.CONTENT_URI,
				null,
				CallLog.Calls.NUMBER + "=? and " + CallLog.Calls.TYPE
						+ "=? and " + CallLog.Calls.PHONE_ACCOUNT_ID + "=?",
				new String[] { number, String.valueOf(type),
						String.valueOf(slotId) },
				CallLog.Calls.DEFAULT_SORT_ORDER);

	}

	static public void printCallRecord(Cursor cursor) {

		int totalRowCounts = cursor.getCount();

		for (int rowIndex = 0; rowIndex < totalRowCounts; rowIndex++) {

			cursor.moveToPosition(rowIndex);

			for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++)
				LogUtil.d(cursor.getColumnName(columnIndex)
						+ "'="
						+ cursor.getString(cursor.getColumnIndex(cursor
								.getColumnName(columnIndex))));

		}

	}
}
