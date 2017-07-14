package marvell.android.testsuit;

import marvell.android.util.LogUtil;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class BootService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		LogUtil.d("Register MBroadcastReceiver ");

		IntentFilter filter = new IntentFilter();
		
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		
		filter.setPriority(Integer.MAX_VALUE);
	
		registerReceiver(new MBroadcastReceiver(), filter);

	}

}
