package hr.foi.rsc.lifeline.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hr.foi.rsc.lifeline.R;

/**
 * Created by dino on 23/11/14.
 */
public class SettingsFragment extends BaseFragment {

    @InjectView(R.id.spinner_language)
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.inject(this, view);

        setLanguage();

        return view;
    }

    private void setLanguage() {

    }
}
