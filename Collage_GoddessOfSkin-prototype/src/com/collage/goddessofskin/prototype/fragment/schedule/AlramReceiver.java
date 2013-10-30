package com.collage.goddessofskin.prototype.fragment.schedule;



import com.collage.goddessofskin.prototype.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore.Audio;
import android.widget.Toast;

public class AlramReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT);

		showNotification(context, R.drawable.ic_launcher, "알람!!",
				"지금 이러고 있을 시간 없다");

	}

	private void showNotification(Context context, int statusBarlconID,
			String statusBarTextID, String detailedTextID) {
		// TODO Auto-generated method stub

		Intent contentIntent = new Intent(context, TestPageWrite.class);

		PendingIntent theappIntent = PendingIntent.getActivity(context, 0,
				contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		CharSequence from = "알람";
		CharSequence message = "무슨짓을 해야 알람이 꺼질까 ";

		Notification notif = new Notification(statusBarlconID, null,
				System.currentTimeMillis());
		notif.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
				"5");

		notif.flags = Notification.FLAG_INSISTENT;
		notif.setLatestEventInfo(context, from, message, theappIntent);
		notif.ledARGB = Color.GREEN;
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		nm.notify(1234, notif);

	}

}
