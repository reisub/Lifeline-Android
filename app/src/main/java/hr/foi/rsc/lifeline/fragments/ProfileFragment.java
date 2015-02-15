package hr.foi.rsc.lifeline.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.models.User;
import hr.foi.rsc.lifeline.mvp.presenters.ProfilePresenter;
import hr.foi.rsc.lifeline.mvp.presenters.impl.ProfilePresenterImpl;
import hr.foi.rsc.lifeline.mvp.views.ProfileView;

public class ProfileFragment extends BaseFragment implements ProfileView {

    @InjectView(R.id.input_name)
    EditText inputName;

    @InjectView(R.id.input_surname)
    EditText inputSurname;

    @InjectView(R.id.spinner_sex)
    Spinner spinnerSex;

    @InjectView(R.id.input_address)
    EditText inputAddress;

    @InjectView(R.id.spinner_blood_type)
    Spinner spinnerBloodType;

    @InjectView(R.id.spinner_rh)
    Spinner spinnerRh;

    @InjectView(R.id.input_additional)
    EditText inputAdditional;

    @InjectView(R.id.layout_blood_type)
    View layoutBloodType;

    @InjectView(R.id.layout_sex)
    View layoutSex;

    @InjectView(R.id.layout_rh)
    View layoutRh;

    private ProfilePresenter profilePresenter;

    private boolean isEdit;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);

        if (!ParseUser.getCurrentUser().isNew()) {
            isEdit = true;
        }

        if (isEdit) {
            inputName.setVisibility(View.GONE);
            inputSurname.setVisibility(View.GONE);
            layoutSex.setVisibility(View.GONE);
            layoutBloodType.setVisibility(View.GONE);
            layoutRh.setVisibility(View.GONE);

            showProgress();
            User.getInstance().getUserData(new GetCallback<ParseObject>() {
                @Override
                public void done(final ParseObject parseObject, ParseException e) {
                    inputAddress.setText(parseObject.getString(User.ADDRESS));
                    inputAdditional.setText(parseObject.getString(User.ADDITIONAL));
                    hideProgress();
                }
            });
        }

        profilePresenter = new ProfilePresenterImpl(this);

        return view;
    }

    @OnClick(R.id.button_save)
    protected void save() {
        final String name = inputName.getText().toString().trim();
        final String surname = inputSurname.getText().toString().trim();
        final String address = inputAddress.getText().toString().trim();
        final String bloodType = (String) spinnerBloodType.getSelectedItem();
        final String sex = (String) spinnerSex.getSelectedItem();
        final String additional = inputAdditional.getText().toString().trim();
        final String rhType = (String) spinnerRh.getSelectedItem();

        if (isEdit) {
            profilePresenter.saveData(address, additional);
        } else {
            if (validate(name, surname)) {
                profilePresenter
                    .saveData(name, surname, address, bloodType, sex, additional, rhType);
            }
        }
    }

    private boolean validate(String name, String surname) {
        boolean valid = true;

        if (surname.isEmpty()) {
            valid = false;
            inputSurname.setError(getString(R.string.field_required));
            inputSurname.requestFocus();
        }

        if (name.isEmpty()) {
            valid = false;
            inputName.setError(getString(R.string.field_required));
            inputName.requestFocus();
        }

        return valid;
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getActivity(), getString(R.string.save_success), Toast.LENGTH_LONG).show();
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
    public void onDestroyView() {
        profilePresenter.cancel();
        super.onDestroyView();
    }
}
