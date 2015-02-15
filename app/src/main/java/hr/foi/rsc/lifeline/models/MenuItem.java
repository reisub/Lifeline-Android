package hr.foi.rsc.lifeline.models;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * Created by dino on 22/11/14.
 */
public class MenuItem {

    private int titleResId;

    private int iconResId;

    public MenuItem(@StringRes int titleResId, @DrawableRes int iconResId) {
        this.titleResId = titleResId;
        this.iconResId = iconResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getIconResId() {
        return iconResId;
    }

}
