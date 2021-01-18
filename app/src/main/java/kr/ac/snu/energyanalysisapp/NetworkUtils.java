package kr.ac.snu.energyanalysisapp;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Runnable;
import java.lang.Thread;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkUtils {
    
    private static final int SERVERPORT = 8888;
    private static final String SERVER_IP = "147.46.130.215";
    private int bitrate = 0;
    private boolean sending = false;

    public void send(int bitrate) {
	new Thread(new ClientThread()).start();
	this.bitrate = bitrate;
	sending = true;
    }

    public void receive(int bitrate) {
	new Thread(new ClientThread()).start();
	this.bitrate = bitrate;
	sending = false;
    }
    
    class ClientThread implements Runnable {
	@Override
	public void run() {
	    try {
		InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
		Socket socket = new Socket(serverAddr, SERVERPORT);

		PrintWriter out
		    = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		if (sending) {
		    out.write("send");
		    int cnt = 0;
		    long cbitrate = bitrate * 1000000;
		    char[] dummy = new char[(int)(cbitrate/1000)];
		    while (cnt < 1000) {
			out.write(dummy);
			Log.i("JONGYUN", "cnt : " + cnt);
			cnt++;
			try {
			    Thread.sleep(1);
			} catch (InterruptedException ignore) { }
		    }
		} else {
		    out.write("recv");
		    int charsRead = 0;
		    char[] buffer = new char[2048];
		    while ((charsRead = in.read(buffer)) != -1) {
			Log.i("JONGYUN", "read" + charsRead);
		    }
		}
		socket.close();
	    } catch (UnknownHostException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }	
	}
    }    
}
