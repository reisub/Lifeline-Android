package hr.foi.rsc.lifeline.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.mvp.presenters.RegisterPresenter;
import hr.foi.rsc.lifeline.mvp.presenters.impl.RegisterPresenterImpl;
import hr.foi.rsc.lifeline.mvp.views.RegisterView;

public class RegisterActivity extends BaseActivity implements RegisterView {

    @InjectView(R.id.my_awesome_toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.input_username)
    protected EditText usernameText;

    @InjectView(R.id.input_password)
    protected EditText passwordText;

    @InjectView(R.id.input_password_confirm)
    protected EditText passwordConfirmText;

    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerPresenter = new RegisterPresenterImpl(this);
    }

    @OnClick(R.id.button_register)
    protected void register() {
        String username = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String passwordConfirm = passwordConfirmText.getText().toString().trim();

        if (validate(username, password, passwordConfirm)) {
            registerPresenter.register(username, password);
        }
    }

    private boolean validate(String username, String password, String passwordConfirmation) {

        boolean valid = true;

        if (passwordConfirmation.isEmpty()) {
            passwordConfirmText.setError(getString(R.string.field_required));
            passwordConfirmText.requestFocus();
            valid = false;
        }

        if (password.isEmpty()) {
            passwordText.setError(getString(R.string.field_required));
            passwordText.requestFocus();
            valid = false;
        }

        if (username.isEmpty()) {
            usernameText.setError(getString(R.string.field_required));
            usernameText.requestFocus();
            valid = false;
        }

        if (valid && !password.equals(passwordConfirmation)) {
            passwordConfirmText.setError(getString(R.string.passwords_must_match));
            passwordConfirmText.requestFocus();
            valid = false;
        }

        return valid;
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
    public void onSuccess() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}
