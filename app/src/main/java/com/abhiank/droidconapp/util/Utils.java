package com.abhiank.droidconapp.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.text.format.DateUtils;

import com.abhiank.droidconapp.R;

import java.util.Date;

/**
 * Created by abhimanyuagrawal on 25/06/15.
 */
public class Utils {

    private static ProgressDialog mProgressDialog;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showProgressDialogue(Context context, String dialogueMessage) {
        if(mProgressDialog==null) {
            mProgressDialog = new ProgressDialog(context, android.R.style.Theme_Translucent);
            mProgressDialog.show();
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));
            //mProgressDialog.setMessage(dialogueMessage);
            mProgressDialog.setContentView(R.layout.progress_dialog);
            mProgressDialog.setCancelable(false);
//            ((TextView)mProgressDialog.findViewById(R.id.progress_text)).setText(dialogueMessage);
        }
    }

    public static void dismissProgressDialogue() {
        if (null != mProgressDialog ) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static String contextDateText(Date date) {
        return DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.MINUTE_IN_MILLIS).toString();
    }

    public static void showMaterialDialog(Context context, String title, String message,
                                         DialogInterface.OnClickListener positiveBtnClickListener,
                                         String positiveButton,
                                         DialogInterface.OnClickListener negativeBtnClickListener,
                                         String negativeButton) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        if (title != null)
            builder.setTitle(title);
        if (message != null)
            builder.setMessage(message);
        if (positiveBtnClickListener != null)
            builder.setPositiveButton(positiveButton, positiveBtnClickListener);
        if (negativeBtnClickListener != null)
            builder.setNegativeButton(negativeButton, negativeBtnClickListener);
        AppCompatDialog forgotDialog = builder.create();
        forgotDialog.show();
    }
}
