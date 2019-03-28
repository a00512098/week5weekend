package com.example.week5weekend;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 200;
    private static final int SMS_REQUEST_CODE = 42;
    private static final String CHANNEL_ID = "NOTIFICATION_CHANNEL";
    private static final String CHANNEL_NAME = "WEEK_5_WEEKEND";
    private static final String CHANNEL_DESCRIPTION = "This is the notification channel for the Week 5 Weekend project";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNotificationChannel();
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

    public void sendNotification(View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_message_black_24dp);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendSms(View view) {
        Intent intent = new Intent(this, SmsActivity.class);
        startActivityForResult(intent, SMS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SMS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Message Send", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Message Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
