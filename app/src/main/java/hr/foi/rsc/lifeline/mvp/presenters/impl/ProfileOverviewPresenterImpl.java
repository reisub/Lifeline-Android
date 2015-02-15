package hr.foi.rsc.lifeline.mvp.presenters.impl;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import hr.foi.rsc.lifeline.models.User;
import hr.foi.rsc.lifeline.mvp.presenters.ProfileOverviewPresenter;
import hr.foi.rsc.lifeline.mvp.views.ProfileOverviewView;

/**
 * Created by dino on 23/11/14.
 */
public class ProfileOverviewPresenterImpl implements ProfileOverviewPresenter {

    boolean canceled;

    private ProfileOverviewView profileOverviewView;

    private static DecimalFormat decimalFormat = new DecimalFormat("0.##");

    public ProfileOverviewPresenterImpl(ProfileOverviewView profileOverviewView) {
        this.profileOverviewView = profileOverviewView;
    }

    @Override
    public void loadProfileData() {
        canceled = false;
        profileOverviewView.showProgress();
        User.getInstance().getUserDonations(findCallback);
    }

    private FindCallback<ParseObject> findCallback = new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> parseObjects, ParseException e) {

            if (parseObjects == null) {
                parseObjects = new ArrayList<>();
            }

            int donations = parseObjects.size();
            float liters = donations * User.BLOOD_PER_DONATION_LITERS;
            float litersNeededForTicket = liters % User.BLOOD_FOR_TICKET_LITERS;

            int daysToNext = 73; // TODO

            int achievementNum = 0;

            if (parseObjects.size() >= 15) {
                achievementNum = 4;
            } else if (parseObjects.size() >= 10) {
                achievementNum = 3;
            } else if (parseObjects.size() >= 5) {
                achievementNum = 2;
            } else if (parseObjects.size() >= 1) {
                achievementNum = 1;
            }

            profileOverviewView.hideProgress();
            profileOverviewView.showData(decimalFormat.format(liters), String.valueOf(daysToNext),
                String.valueOf(achievementNum), decimalFormat.format(litersNeededForTicket));
        }
    };

    @Override
    public void cancel() {
        canceled = true;
    }
}
