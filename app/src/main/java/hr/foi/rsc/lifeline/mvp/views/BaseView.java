package hr.foi.rsc.lifeline.mvp.views;

/**
 * Created by dino on 22/11/14.
 */
public interface BaseView {

    public void showProgress();

    public void hideProgress();

    public void showError(String message);
}
