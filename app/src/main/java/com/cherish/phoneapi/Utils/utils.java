package com.cherish.phoneapi.Utils;

import android.app.ProgressDialog;
import android.content.Context;

public class utils {
    public  static ProgressDialog showProgressDialog(Context context, String message ){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage(message);
        dialog.setTitle(null);

        return dialog;

    }
}
