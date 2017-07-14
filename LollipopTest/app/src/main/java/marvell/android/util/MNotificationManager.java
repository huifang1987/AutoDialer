package marvell.android.util;


import marvell.android.lollipop.MainActivity;
import marvell.android.lollipop.R;
import marvell.android.testsuit.TConstant;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class MNotificationManager {


	static public void showAutoAnswerCallNotification(Context context) {

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification();

		notification.icon = R.drawable.ic_launcher;
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.flags |= Notification.FLAG_NO_CLEAR;
		
		 PendingIntent pt=PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0); 
		notification.setLatestEventInfo(context, "���е绰�����Զ�����", "����������ý���ȡ��", pt);
		notificationManager.notify(TConstant.AutoAnswerCall_Notification_ID,
				notification);
	}

	static public void cancleAutoAnswerCallNotification(Context context) {

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.cancel(TConstant.AutoAnswerCall_Notification_ID);
	}

}
