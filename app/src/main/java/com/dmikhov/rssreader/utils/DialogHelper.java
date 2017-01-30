package com.dmikhov.rssreader.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.dmikhov.rssreader.R;

/**
 * Created by dmikhov on 30.01.2017.
 */
public class DialogHelper {
    public static void openMessageDialog(Context context, String title, String msg) {
        String ok = context.getString(R.string.ok);
        final AlertDialog.Builder adb = new AlertDialog.Builder(context)
                .setTitle(title).setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage(msg);
        adb.show();
    }
}
