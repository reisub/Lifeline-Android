package hr.foi.rsc.lifeline.mvp.presenters;

/**
 * Created by dino on 23/11/14.
 */
public interface ProfilePresenter extends BasePresenter {

    public void saveData(String name, String surname, String address, String bloodType, String sex,
                         String additional, String rhType);

    public void saveData(String address, String additional);
}
