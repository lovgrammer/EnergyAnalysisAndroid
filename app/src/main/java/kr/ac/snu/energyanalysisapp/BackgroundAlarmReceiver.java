package kr.ac.snu.energyanalysisapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BackgroundAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Jongyun", "onReceive!!!");
 	int bitrate = intent.getIntExtra("bitrate", 0);
	new NetworkUtils().send(bitrate);	
    }
}
