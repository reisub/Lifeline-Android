package hr.foi.rsc.lifeline.mvp.presenters;

import android.app.Activity;

/**
 * Created by dino on 22/11/14.
 */
public interface LoginPresenter extends BasePresenter {

    public void authenticateUser(String username, String password);

    public void authenticateUserFb(Activity activity);

    public void authenticateUserTwitter(Activity activity);

    public void resetPassword(String email);
}
