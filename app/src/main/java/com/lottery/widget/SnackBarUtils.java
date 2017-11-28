package com.lottery.widget;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBarUtils {
    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static Snackbar indefiniteSnackbar(View view, String msg) {
        return Snackbar.make(view,msg, Snackbar.LENGTH_INDEFINITE);
    }
}