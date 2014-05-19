package com.example.earphoneunplugvibrate;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private static final String TAG = "MainActivity";
	HeadsetStateReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 receiver = new HeadsetStateReceiver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override public void onResume() {
		  IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		    registerReceiver(receiver, filter);
	    super.onResume();
	}

	private class HeadsetStateReceiver extends BroadcastReceiver {
	    @Override public void onReceive(Context context, Intent intent) {
	        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
	            int state = intent.getIntExtra("state", 1);
	            switch (state) {
	            case 0:
	                Log.d(TAG, "Headset is unplugged");
	                Toast.makeText(getApplicationContext(), "Headset is unplugged", Toast.LENGTH_SHORT).show();
	                
	                break;
	            case 1:
	                Log.d(TAG, "Headset is plugged");
	                Toast.makeText(getApplicationContext(), "Headset is plugged", Toast.LENGTH_SHORT).show();
	                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	                vibrator.vibrate(2000);
	                break;
	            default:
	            	 Toast.makeText(getApplicationContext(), "I have no idea what the headset state is", Toast.LENGTH_SHORT).show();
	                Log.d(TAG, "I have no idea what the headset state is");
	            }
	        }
	    }
	}

	@Override public void onPause() {
	    unregisterReceiver(receiver);
	    super.onPause();
	}

}
