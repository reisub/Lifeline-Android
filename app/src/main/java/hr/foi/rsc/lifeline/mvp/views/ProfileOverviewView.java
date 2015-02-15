package hr.foi.rsc.lifeline.mvp.views;

/**
 * Created by dino on 23/11/14.
 */
public interface ProfileOverviewView extends BaseView {

    public void showData(String liters, String daysToNext, String achievements,
                         String litersToFreeTicket);
}
