package hr.foi.rsc.lifeline.receivers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

import hr.foi.rsc.lifeline.activities.LoginActivity;
import hr.foi.rsc.lifeline.activities.MainActivity;

/**
 * Created by dino on 22/11/14.
 */
public class PushReceiver extends ParsePushBroadcastReceiver {

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        if (ParseUser.getCurrentUser() != null) {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(intent.getExtras());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Intent i = new Intent(context, LoginActivity.class);
            i.putExtras(intent.getExtras());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        Log.e("Push", "Received");
        if (ParseUser.getCurrentUser() != null) {
            super.onPushReceive(context, intent);
        }
    }
}
