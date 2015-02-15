package hr.foi.rsc.lifeline.mvp.presenters;

/**
 * Created by dino on 22/11/14.
 */
public interface RegisterPresenter extends BasePresenter {

    public void register(String username, String password);
}