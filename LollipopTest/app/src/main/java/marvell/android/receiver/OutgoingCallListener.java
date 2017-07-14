package marvell.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

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
//                Log.d("fanghui", "IDLE");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_DIALING:
//                Log.d("fanghui", "DIALING");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_ALERTING:
//                Log.d("fanghui", "ALERTING");
//                break;
//            case PreciseCallState.PRECISE_CALL_STATE_ACTIVE:
//                Log.d("fanghui", "ACTIVE");
//                break;
//        }
    }
}