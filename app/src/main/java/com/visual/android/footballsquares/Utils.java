package com.visual.android.footballsquares;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by RamiK on 8/29/2016.
 */
public class Utils {

    public static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
