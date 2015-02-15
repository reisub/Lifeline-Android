package hr.foi.rsc.lifeline.mvp.presenters.impl;

import android.app.Activity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import hr.foi.rsc.lifeline.LifeLineApplication;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.models.User;
import hr.foi.rsc.lifeline.mvp.presenters.LoginPresenter;
import hr.foi.rsc.lifeline.mvp.views.LoginView;

/**
 * Created by dino on 22/11/14.
 */
public class LoginPresenterImpl implements LoginPresenter {

    private boolean canceled;

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void authenticateUser(String username, String password) {
        canceled = false;
        loginView.showProgress();
        ParseUser.logInInBackground(username, password, logInCallback);
    }

    @Override
    public void authenticateUserFb(Activity activity) {
        canceled = false;
        ParseFacebookUtils.logIn(activity, logInCallback);
    }

    @Override
    public void authenticateUserTwitter(Activity activity) {
        canceled = false;
        ParseTwitterUtils.logIn(activity, logInCallback);
    }

    @Override
    public void resetPassword(String email) {
        ParseUser.requestPasswordResetInBackground(email, requestPasswordResetCallback);
    }

    private RequestPasswordResetCallback requestPasswordResetCallback =
        new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                loginView.hideProgress();
                if (e == null) {
                    loginView.onPasswordReset();
                } else {
                    loginView.showError(e.getMessage());
                }
            }
        };

    private LogInCallback logInCallback = new LogInCallback() {
        @Override
        public void done(ParseUser parseUser, ParseException e) {
            if (canceled) {
                return;
            }

            loginView.hideProgress();

            if (parseUser != null) {
                if(!parseUser.containsKey(User.TYPE)) {
                    parseUser.put(User.TYPE, User.DONOR);
                    parseUser.saveInBackground();
                }
                if (parseUser.getString(User.TYPE).equals(User.DONOR)) {
                    loginView.navigateToHome();
                } else {
                    loginView.showError(LifeLineApplication.getInstance().getString(
                        R.string.only_donors));
                    ParseUser.logOut();
                }
            } else {
                if (e != null && e.getMessage() != null) {
                    loginView.showError(e.getMessage());
                }
            }
        }
    };

    @Override
    public void cancel() {
        canceled = true;
    }
}
