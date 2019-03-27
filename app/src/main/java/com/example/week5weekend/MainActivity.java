package com.example.week5weekend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPDF(View view) {
        Intent intent = new Intent(this, PdfViewerActivity.class);
        startActivity(intent);
    }

    public void dialogWithImage(View view) {
        final ImageDialogFragment dialogFragment = new ImageDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "imageDialog");

        dialogFragment.dismissAfterSeconds(3);
    }

    public void alertDialogNormal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.normal_alert_dialog)
                .setMessage("This is a normal Alert Dialog")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public void alertDialogCustom(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alert_dialog_custom, null));
        builder.create().show();
    }

    public void alertDialogWithList(View view) {
        // The font of this alert dialog is on purpose
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_dialog_list_title)
                .setItems(R.array.alert_dialog_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String[] array = getResources().getStringArray(R.array.alert_dialog_options);
                        Toast.makeText(MainActivity.this, "You have selected " + array[which], Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }
}
