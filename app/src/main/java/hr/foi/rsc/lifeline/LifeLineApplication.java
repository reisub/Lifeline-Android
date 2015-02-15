package hr.foi.rsc.lifeline;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParsePush;
import com.parse.ParseTwitterUtils;
import com.parse.SaveCallback;

/**
 * Created by dino on 22/11/14.
 */
public class LifeLineApplication extends Application {

    private static LifeLineApplication instance;

    @Override
    public void onCreate() {
        instance = this;

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "Qz1N1B4aBwzmiszChrGKU37QalVXzZ8iew6hV2oH",
            "kWAfX9HmPe1HRXojqj8kuPEufx0r9yXFcoCzVdAq");
        ParseTwitterUtils.initialize("GtQtnF894a6USOPRIVZx2HWfv",
            "yiG7Sn6ZTlVQbKSCtylH9LRmNCGA4Ir6jdNyKXdQMSGu9oQmwx");
        ParseFacebookUtils.initialize("830133423676900");

        ParsePush.subscribeInBackground("all", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public static LifeLineApplication getInstance() {
        return instance;
    }
}
