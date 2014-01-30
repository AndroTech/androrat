package my.app.client;

import my.app.client.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LauncherActivity extends Activity {
    /** Called when the activity is first created. */
	
	Intent Client, ClientAlt;
	Button btnStart, btnStop;
	EditText ipfield, portfield;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if(!bundle.getBoolean(DialReceiver.fromDial, false))finish();
        }
        
        setContentView(R.layout.main);

        Client = new Intent(this, Client.class);
        Client.setAction(LauncherActivity.class.getName());
        
        btnStart = (Button) findViewById(R.id.buttonstart);
        btnStop = (Button) findViewById(R.id.buttonstop);
        ipfield = (EditText) findViewById(R.id.ipfield);
        portfield = (EditText) findViewById(R.id.portfield);
        
        ipfield.setText(Config.ip);
        portfield.setText(String.valueOf(Config.port));

    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(isMyServiceRunning())start();
    	else stop();
    }
    
    private boolean isMyServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Client.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    
    public void onStart(View v){
    	start();
    }
    
    public void onStop(View v){
    	stop();
    }
    
    private void start(){
    	Client.putExtra("IP", ipfield.getText().toString());
    	Client.putExtra("PORT", Integer.valueOf(portfield.getText().toString()));
        startService(Client);
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }
    
    private void stop(){
        stopService(Client);  
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);    	
    }
}