package com.example.week5weekend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class ImageDialogFragment extends AppCompatDialogFragment
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener{

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ImageDialogFragment.this.dismiss();
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_with_image, null))
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            // Doesn't matter which button was pressed
            case BUTTON_POSITIVE:
            case BUTTON_NEGATIVE:
                ImageDialogFragment.this.getDialog().cancel();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("Log.d", "Dialog Dismissed");
        handler.removeCallbacks(runnable);
    }

    public void dismissAfterSeconds(int seconds){
        handler.postDelayed(runnable, seconds*1000);
    }
}
