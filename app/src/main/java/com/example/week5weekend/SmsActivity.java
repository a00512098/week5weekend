package com.example.week5weekend;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SmsActivity extends AppCompatActivity
        implements View.OnKeyListener, View.OnClickListener, PermissionsManager.OnPermissionResult{

    TextInputLayout phoneTextInputLayout;
    TextInputEditText phoneEditText;
    TextInputLayout messageTextInputLayout;
    TextInputEditText messageEditText;
    Button button;
    PermissionsManager permissionsManager;
    boolean isPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        initViews();

        permissionsManager = new PermissionsManager(this, new String[]{Manifest.permission.SEND_SMS});
    }

    private void initViews() {
        phoneTextInputLayout = findViewById(R.id.phoneTextInputLayout);
        phoneEditText = findViewById(R.id.phoneEditText);
        messageTextInputLayout = findViewById(R.id.messageTextInputLayout);
        messageEditText = findViewById(R.id.messageEditText);

        button = findViewById(R.id.sendSms);
        button.setOnClickListener(this);

        phoneEditText.setOnKeyListener(this);
        messageEditText.setOnKeyListener(this);
    }

    private boolean isPhoneValid(@Nullable Editable phone) {
        Log.d("Log.d", "isPhoneValid: " + String.valueOf(phone != null && phone.length() >= 8 && phone.length() <= 15));
        return phone != null && phone.length() >= 8 && phone.length() <= 15;
    }

    private boolean isMessageValid(@Nullable Editable message) {
        Log.d("Log.d", "isMessageValid: " + String.valueOf(message != null && message.length() > 0));
        return message != null && message.length() > 0;
    }

    @Override
    public void onClick(View view) {
        if (!isPhoneValid(phoneEditText.getText())) {
            phoneTextInputLayout.setError("Enter a valid number");
            return;
        } else {
            phoneTextInputLayout.setError(null);
        }

        if (!isMessageValid(messageEditText.getText())) {
            messageTextInputLayout.setError("You need to enter a message first");
            return;
        } else {
            messageTextInputLayout.setError(null);
        }

        permissionsManager.requestPermission();
    }

    public void sendSMS() {
        try {
            String phone = phoneEditText.getText().toString();
            String message = messageEditText.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            setResult(RESULT_OK);
        } catch (Exception e){
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d("Log.d", "onKey: " + v.getId());
        switch (v.getId()) {
            case R.id.messageEditText:
                if (isMessageValid(messageEditText.getText())) {
                    messageTextInputLayout.setError(null);
                }
                break;
            case R.id.phoneEditText:
                if (isPhoneValid(phoneEditText.getText())) {
                    phoneTextInputLayout.setError(null);
                }
                break;
        }
        return false;
    }

    @Override
    public void onPermissionResult(boolean isPermissionGranted) {
        this.isPermissionGranted = isPermissionGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.checkPermission();
        if(isPermissionGranted) {
            sendSMS();
        }
    }
}
