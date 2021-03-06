package marvell.android.testsuit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.android.internal.telephony.ITelephony;

import marvell.android.lollipop.MainActivity;
import marvell.android.util.Configure;
import marvell.android.util.LogUtil;
import marvell.android.util.MTelephonyManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class MBroadcastReceiver extends BroadcastReceiver {

	private static final String Boot_EVENT = "android.intent.action.BOOT_COMPLETED";
	private static final String Call_EVENT = "android.intent.action.PHONE_STATE";

	@Override
	public void onReceive(Context context, Intent intent) {

		LogUtil.d("getAction=" + intent.getAction());

		if (intent.getAction().equals(Boot_EVENT)) {

			showMainActivity(context);

		} else if (intent.getAction().equals(Call_EVENT)) {

//			autoAnswerCall(context, intent);

		}
	}

	private void showMainActivity(Context context) {

		LogUtil.d("show the MainActivity");

		Intent intent = new Intent(context, MainActivity.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(intent);

	}

	 private void autoAnswerCall(Context context, Intent intent) {
	
	 LogUtil.d("autoAnswerCall");
	
	 MTelephonyManager.answerRingingCall(context);
	
	
	 }


	/**
	 * 该方法可以用于4.1的接听
	 */
	public void autoAnswerPhone(Context context, ITelephony itelephony) {

		try {
			itelephony.silenceRinger();

			itelephony.answerRingingCall();

		} catch (Exception e1) {

			LogUtil.d("Exception e1 for autoAnswerPhone");

			try {
				Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				context.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");

				intent = new Intent("android.intent.action.MEDIA_BUTTON");
				keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
				context.sendOrderedBroadcast(intent,
						"android.permission.CALL_PRIVILEGED");

				Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
				localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				localIntent1.putExtra("state", 1);
				localIntent1.putExtra("microphone", 1);
				localIntent1.putExtra("name", "Headset");
				context.sendOrderedBroadcast(localIntent1,
						"android.permission.CALL_PRIVILEGED");

				Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
				KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_HEADSETHOOK);
				localIntent2.putExtra("android.intent.extra.KEY_EVENT",
						localKeyEvent1);
				context.sendOrderedBroadcast(localIntent2,
						"android.permission.CALL_PRIVILEGED");

				Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
				KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				localIntent3.putExtra("android.intent.extra.KEY_EVENT",
						localKeyEvent2);
				context.sendOrderedBroadcast(localIntent3,
						"android.permission.CALL_PRIVILEGED");

				Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
				localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				localIntent4.putExtra("state", 0);
				localIntent4.putExtra("microphone", 1);
				localIntent4.putExtra("name", "Headset");
				context.sendOrderedBroadcast(localIntent4,
						"android.permission.CALL_PRIVILEGED");

			} catch (Exception e2) {

				LogUtil.d("Exception e2 for autoAnswerPhone");

				Intent meidaButtonIntent = new Intent(
						Intent.ACTION_MEDIA_BUTTON);
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
						KeyEvent.KEYCODE_HEADSETHOOK);
				meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
				context.sendOrderedBroadcast(meidaButtonIntent, null);
			}
		}
	}

}