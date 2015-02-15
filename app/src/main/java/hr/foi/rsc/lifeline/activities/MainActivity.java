package hr.foi.rsc.lifeline.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.fragments.AchievementsFragment;
import hr.foi.rsc.lifeline.fragments.ProfileFragment;
import hr.foi.rsc.lifeline.fragments.ProfileOverviewFragment;

public class MainActivity extends ActionBarActivity
    implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final String COM_PARSE_DATA = "com.parse.Data";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @InjectView(R.id.my_awesome_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // push notification received
        if (getIntent() != null && getIntent().hasExtra(COM_PARSE_DATA)) {
            try {
                JSONObject pushData = new JSONObject(getIntent().getStringExtra(COM_PARSE_DATA));
                String alertText = pushData.optString("alert");
                String eventId = pushData.optString("eventId");
                String location = pushData.optString("location");
                String time = pushData.optString("time");
                showPushDialog(eventId, location, time);
            } catch (Exception e) {
                Log.v("com.parse.ParsePushReceiver",
                    "Unexpected JSONException when receiving push data: ", e);
                e.printStackTrace();
            }
        }

        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
            getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
            R.id.navigation_drawer,
            (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            if (ParseUser.getCurrentUser().isNew()) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                    .replace(R.id.container, new ProfileFragment())
                    .addToBackStack(null).commit();
            }
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        hr.foi.rsc.lifeline.models.MenuItem menuItem = null;

        if (mNavigationDrawerFragment != null) {
            menuItem = mNavigationDrawerFragment.getItem(position);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (menuItem != null) {
            switch (menuItem.getTitleResId()) {
                case R.string.menu_home:
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, new ProfileOverviewFragment())
                        .commit();
                    break;
                case R.string.menu_profile:
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, new ProfileFragment())
                        .commit();
                    break;
                case R.string.menu_achievements:
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, new AchievementsFragment())
                        .commit();
                    break;
                case R.string.menu_logout:
                    showLogoutConfirmation();
                    break;
            }
        }
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(R.string.confirm_logout);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ParseUser.logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void showPushDialog(final String eventId, String location, String time) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        String message = String.format(getString(R.string.event_call_question), location, time);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ParseObject attendee = new ParseObject("Attendees");
                attendee.put("eventId", eventId);
                attendee.put("userId", ParseUser.getCurrentUser().getObjectId());
                attendee.saveInBackground();
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }

}
