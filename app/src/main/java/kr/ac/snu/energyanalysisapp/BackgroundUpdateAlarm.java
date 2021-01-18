package kr.ac.snu.energyanalysisapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class BackgroundUpdateAlarm {

    private final static int FIVE_SECOND = 5 * 1000;
    // private final static int ONE_MINUES = 2 * 60 * 1000;
    private final static int ONE_MINUES = 5 * 1000;

    private static BackgroundUpdateAlarm _instance;

    public static BackgroundUpdateAlarm getInstance() {
        if (_instance == null) _instance = new BackgroundUpdateAlarm();
        return _instance;
    }

    public void startOneMinuteAlarm(Context context, int bitrate) {
        Intent alarmIntent = new Intent(context, BackgroundAlarmReceiver.class);
	alarmIntent.putExtra("bitrate", bitrate);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        startAlarm(context, pendingIntent, ONE_MINUES);
    }

    public void startFiveSecondAlarm(Context context) {
        Log.i("Jongyun", "startFiveSecondAlarm!!!");
        Intent alarmIntent = new Intent(context, BackgroundAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        startAlarm(context, pendingIntent, FIVE_SECOND);
    }

    private void startAlarm(Context context, PendingIntent pendingIntent, int delay) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
        }
    }


}
