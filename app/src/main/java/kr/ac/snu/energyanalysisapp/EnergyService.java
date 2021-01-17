package kr.ac.snu.energyanalysisapp;

import android.app.Service; 
import android.content.Intent; 
import android.os.IBinder; 
import android.provider.Settings; 
import android.util.Log;
import androidx.annotation.Nullable; 
  
public class EnergyService extends Service implements Runnable { 
  
    private boolean ongoing = false;
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.i("JONGYUN", "flags:" + flags);
	ongoing = true;
	Thread t = new Thread(this);
	t.start();
        return START_STICKY; 
    }

    @Override
    public void run() {
	while (ongoing) {
	    int voltage = EnergyUtils.getVoltageNow();
	    int current = EnergyUtils.getCurrentNow();
	    Log.i("JONGYUN", "v:" + voltage + ",c:" + current);
	    StatusDataCollector.saveEnergy(EnergyService.this, voltage, current);
	    try {
		Thread.sleep(10);
	    } catch (InterruptedException ignore) { }
	}
    }
  
    @Override
    public void onDestroy() { 
        super.onDestroy();
	ongoing = false;
    } 
  
    @Nullable
    @Override
    public IBinder onBind(Intent intent) { 
        return null; 
    }
    
}
