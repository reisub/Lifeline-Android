package hr.foi.rsc.lifeline.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import hr.foi.rsc.lifeline.R;
import hr.foi.rsc.lifeline.mvp.presenters.AchievementsPresenter;
import hr.foi.rsc.lifeline.mvp.presenters.impl.AchievementsPresenterImpl;
import hr.foi.rsc.lifeline.mvp.views.AchievementsView;

public class AchievementsFragment extends BaseFragment implements AchievementsView {

    @InjectView(R.id.achievement_1st_time)
    ImageView achievement1stTime;

    @InjectView(R.id.achievement_2_year)
    ImageView achievement2Year;

    @InjectView(R.id.achievement_3_year)
    ImageView achievement3Year;

    @InjectView(R.id.achievement_5_times)
    ImageView achievement5Times;

    @InjectView(R.id.achievement_10_times)
    ImageView achievement10Times;

    @InjectView(R.id.achievement_heart)
    ImageView achievementHeart;

    @InjectViews({R.id.achievement_1st_time, R.id.achievement_2_year, R.id.achievement_3_year,
        R.id.achievement_5_times, R.id.achievement_10_times, R.id.achievement_heart})
    List<ImageView> achievementImages;

    private AchievementsPresenter achievementsPresenter;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievements, container, false);
        ButterKnife.inject(this, view);

        achievementsPresenter = new AchievementsPresenterImpl(this);
        achievementsPresenter.loadAchievements();

        return view;
    }

    @Override
    public void showAchievements(boolean[] achievements) {
        for (int i = 0; i < achievements.length && i < achievementImages.size(); i++) {
            if (!achievements[i]) {
                achievementImages.get(i).setImageAlpha(128);
            }
        }
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
        super.onDestroyView();
    }
}
