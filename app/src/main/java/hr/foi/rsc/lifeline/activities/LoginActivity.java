package hr.foi.rsc.lifeline.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.mvp.presenters.LoginPresenter;
import hr.foi.rsc.lifeline.mvp.presenters.impl.LoginPresenterImpl;
import hr.foi.rsc.lifeline.mvp.views.LoginView;

public class LoginActivity extends BaseActivity implements LoginView {

    private LoginPresenter loginPresenter;

    @InjectView(R.id.input_username)
    protected EditText usernameText;

    @InjectView(R.id.input_password)
    protected EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        loginPresenter = new LoginPresenterImpl(this);

        if (ParseUser.getCurrentUser() != null) {
            navigateToHome();
        }
    }

    @OnClick(R.id.button_login)
    protected void login() {

        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (password.isEmpty()) {
            passwordText.setError(getString(R.string.field_required));
            passwordText.requestFocus();
        }

        if (username.isEmpty()) {
            usernameText.setError(getString(R.string.field_required));
            usernameText.requestFocus();
        }

        if (!username.isEmpty() && !password.isEmpty()) {
            loginPresenter.authenticateUser(username, password);
        }
    }

    @OnClick(R.id.button_forgot_password)
    protected void resetPassword() {

        String username = usernameText.getText().toString().trim();

        if (username.isEmpty()) {
            usernameText.setError(getString(R.string.field_required));
            usernameText.requestFocus();
        }

        if (!username.isEmpty()) {
            loginPresenter.resetPassword(username);
        }
    }

    @OnLongClick(R.id.image_logo)
    protected boolean fillInloginCredentials() {
        usernameText.setText("test@example.com");
        passwordText.setText("test");
        return true;
    }

    @OnClick(R.id.button_fb_login)
    protected void facebookLogin() {
        loginPresenter.authenticateUserFb(this);
    }

    @OnClick(R.id.button_twitter_login)
    protected void twitterLogin() {
        loginPresenter.authenticateUserTwitter(this);
    }

    @OnClick(R.id.button_register)
    protected void showRegisterScreen() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onPasswordReset() {
        showDialog(getString(R.string.password_reset_success));
    }

    @Override
    public void showProgress() {
        showProgressBar();
    }

    @Override
    public void hideProgress() {
        hideProgressBar();
    }

    @Override
    public void showError(String message) {
        showDialog(message);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.cancel();
        super.onDestroy();
    }
}
