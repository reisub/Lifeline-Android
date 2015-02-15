package hr.foi.rsc.lifeline.models;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by dino on 23/11/14.
 */
public class User {

    public static final String NAME = "name";

    public static final String SURNAME = "surname";

    public static final String ADDRESS = "address";

    public static final String BLOOD_TYPE = "bloodType";

    public static final String SEX = "sex";

    public static final String ADDITIONAL = "additional";

    public static final String USER_DATA = "UserData";

    public static final String USER_OBJECT_ID = "userObjectId";

    public static final String USER_DONATION = "UserDonation";

    public static final String DONOR = "donor";

    public static final String TYPE = "type";

    public static final float BLOOD_PER_DONATION_LITERS = 0.5f;

    public static final float BLOOD_FOR_TICKET_LITERS = 2f;

    private ParseUser parseUser;

    public static User getInstance() {
        return new User(ParseUser.getCurrentUser());
    }

    public User(ParseUser parseUser) {
        this.parseUser = parseUser;
    }

    public void getUserData(final GetCallback<ParseObject> getCallback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(USER_DATA);
        query.whereEqualTo(USER_OBJECT_ID, parseUser.getObjectId());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (parseObject == null) {
                    parseObject = new ParseObject(USER_DATA);
                    parseObject.put(USER_OBJECT_ID, parseUser.getObjectId());
                    getCallback.done(parseObject, e);
                }
                getCallback.done(parseObject, e);
            }
        });
    }

    public void getUserDonations(final FindCallback<ParseObject> findCallback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(USER_DONATION);
        query.whereEqualTo(USER_OBJECT_ID, parseUser.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> donationList, ParseException e) {
                findCallback.done(donationList, e);
            }
        });
    }
}
