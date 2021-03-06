package kr.ac.snu.energyanalysisapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private TextView mOutputView;
    private Button mStartButton;
    private Button mStopButton;

    private Button mSendButton;
    private EditText mBitrateEdit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	mOutputView = (TextView)findViewById(R.id.txt_output);
	mBitrateEdit = (EditText)findViewById(R.id.edit_bitrate);
	mStartButton = (Button)findViewById(R.id.btn_start);
	mStartButton.setOnClickListener(onButtonsClick);
	mStopButton = (Button)findViewById(R.id.btn_stop);
	mStopButton.setOnClickListener(onButtonsClick);
	mSendButton = (Button)findViewById(R.id.btn_send);
	mSendButton.setOnClickListener(onButtonsClick);	
	requestPermission();
    }
    
    OnClickListener onButtonsClick = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_start:
		    // int voltage = EnergyUtils.getVoltageNow();
		    // int current = EnergyUtils.getCurrentNow();
		    // mOutputView.setText("v: " + voltage + ", c: " + current);
		    startService(new Intent(MainActivity.this, EnergyService.class));
		    Toast.makeText(MainActivity.this, "start measurement service", Toast.LENGTH_SHORT).show();
		    break;
		case R.id.btn_stop:
		    stopService(new Intent(MainActivity.this, EnergyService.class));
		    Toast.makeText(MainActivity.this, "energy.csv is created in the /sdcard", Toast.LENGTH_SHORT).show();
		    break;
		case R.id.btn_send:
		    Toast.makeText(MainActivity.this, "start packet sending (after 60sec)", Toast.LENGTH_SHORT).show();
		    int bitrate = Integer.parseInt(mBitrateEdit.getText().toString());
		    BackgroundUpdateAlarm.getInstance().startOneMinuteAlarm(MainActivity.this, bitrate);
		    break;		    
		}
	    }
	};

    public void requestPermission() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
	    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
	       || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
		if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
		}

		requestPermissions(new String[] {
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE}, 2);  

	    } else {
	    }
	}
    }
}
