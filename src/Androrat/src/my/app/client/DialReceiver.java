package my.app.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DialReceiver extends BroadcastReceiver {
	public static final String fromDial="fromDial";
	
	@Override
	public void onReceive(Context context, final Intent intent) {

        if ("android.provider.Telephony.SECRET_CODE".equals(intent.getAction())) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setClass(context, LauncherActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(fromDial, true);
            context.startActivity(i);
        }
	}
}