package marvell.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

/**
 * @author: Fanghui
 * @mail: fangh18@chinaunicom.cn
 * @date: 2017-7-13.
 */


public class OutgoingCallListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        switch (intent.getIntExtra(TelephonyManager.EXTRA_FOREGROUND_CALL_STATE, -2)){
//            case PreciseCallState.PRECISE_CALL_STATE_IDLE:
//                Log.d(This.LOG_TAG, "IDLE");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_DIALING:
//                Log.d(This.LOG_TAG, "DIALING");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_ALERTING:
//                Log.d(This.LOG_TAG, "ALERTING");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_ACTIVE:
//                Log.d(This.LOG_TAG, "ACTIVE");
//                break;
//        }
    }
}