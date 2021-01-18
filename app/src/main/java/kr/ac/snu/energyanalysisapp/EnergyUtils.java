package kr.ac.snu.energyanalysisapp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Process;
import java.lang.RuntimeException;
import java.lang.StringBuffer;

public class EnergyUtils {
    
    public static int getVoltageNow() {
	int res = -1;
	try {
	    String voout = runAsRoot("cat /sys/class/power_supply/battery/voltage_now");
	    Log.i("JONGYUN", "voout:" + voout);
	    res = Integer.parseInt(voout.replaceAll("[^\\d.]", ""));
	} catch(Exception e) {
	    Log.i("JONGYUN", "e:" + e.getMessage());
	}
	return res;
    }
        
    public static int getCurrentNow() {
	int res = -1;
	try {
	    res = Integer.parseInt(runAsRoot("cat /sys/class/power_supply/battery/current_now").replaceAll("[^\\d.]", ""));
	} catch(Exception e) {
	    Log.i("JONGYUN", "e:" + e.getMessage());
	}
	return res;
    }
    
    public static String runAsRoot(String cmd) {
	try {
	    String line;
	    Process process = Runtime.getRuntime().exec("su");
	    OutputStream stdin = process.getOutputStream();
	    InputStream stderr = process.getErrorStream();
	    InputStream stdout = process.getInputStream();

	    stdin.write((cmd + "\n").getBytes());
	    stdin.write("exit\n".getBytes());
	    stdin.flush();

	    stdin.close();
	    BufferedReader br =
		new BufferedReader(new InputStreamReader(stdout));
	    String out = "";
	    while ((line = br.readLine()) != null) {
		Log.d("[Output]", line);
		out += line;
	    }
	    br.close();
	    br =
	    	new BufferedReader(new InputStreamReader(stderr));
	    while ((line = br.readLine()) != null) {
	    	Log.e("[Error]", line);
	    }
	    br.close();

	    process.waitFor();
	    process.destroy();
	    return out;
	} catch (Exception ex) {
	    Log.i("JONGYUN", "e:" + ex.getMessage());
	}
	return "";
    }
}
