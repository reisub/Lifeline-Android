package hr.foi.rsc.lifeline.mvp.presenters.impl;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import hr.foi.rsc.lifeline.models.User;
import hr.foi.rsc.lifeline.mvp.presenters.RegisterPresenter;
import hr.foi.rsc.lifeline.mvp.views.RegisterView;

/**
 * Created by dino on 22/11/14.
 */
public class RegisterPresenterImpl implements RegisterPresenter {

    private boolean canceled;

    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void register(String username, String password) {
        canceled = false;
        registerView.showProgress();

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(username);
        user.put(User.TYPE, User.DONOR);

        user.signUpInBackground(signUpCallback);
    }

    private SignUpCallback signUpCallback = new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            registerView.hideProgress();

            if (canceled) {
                return;
            }

            if (e == null) {
                registerView.onSuccess();
            } else {
                registerView.showError(e.getMessage());
            }
        }
    };

    @Override
    public void cancel() {
        canceled = true;
    }
}
